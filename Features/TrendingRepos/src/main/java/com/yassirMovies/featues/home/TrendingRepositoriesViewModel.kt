package com.yassirMovies.featues.home

import androidx.lifecycle.ViewModel
import com.sadaPayRepos.domain.GetTrendingRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingRepositoriesViewModel @Inject constructor(getTrendingRepositoryUseCase: GetTrendingRepositoriesUseCase) :
    ViewModel() {
    val githubRepos = getTrendingRepositoryUseCase()
}