package com.sadaPayRepos.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel<STATE : ViewState, EVENT : ViewEvent, EFFECT : ViewSideEffect> :
    ViewModel() {
    private val initialState: STATE by lazy {
        setInitialState()
    }
    private val handler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }

    open fun handleError(exception: Throwable) {

    }

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<EVENT>()

    private val _effect: Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()

    abstract fun setInitialState(): STATE

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    abstract fun handleEvents(event: EVENT)

    fun setEvent(event: EVENT){
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: STATE.() -> STATE) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    fun setEffect(block: () -> EFFECT) {
        viewModelScope.launch {
            _effect.send(block())
        }
    }
    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    fun <T> launchRequest(
        flow: Flow<T>,
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = {},
    ) {
        viewModelScope.launch {
            flow.flowOn(
                Dispatchers.IO
            ).onStart {
                onStart()
            }.onCompletion { cause ->
                onComplete()
            }.catch { exception ->
                onError(exception.message.toString())
            }.safeCollect { result ->
                onSuccess(result)
            }
        }
    }
}

suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}