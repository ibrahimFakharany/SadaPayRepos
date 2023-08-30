package com.yassirMovies.featues.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sadaPayRepos.base.AppGreen
import com.sadaPayRepos.base.ShimmerAnimation
import com.sadaPayRepos.base.TrendingRepositoriesItem


@Composable
fun TrendingReposScreen(
    viewModel: TrendingRepositoriesViewModel = hiltViewModel()
) {
    val trendingRepositories = viewModel.githubRepos.collectAsLazyPagingItems()
    var refreshing by remember { mutableStateOf(false) }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { trendingRepositories.refresh() },
    ) {
        LazyColumn(verticalArrangement = Arrangement.Center) {

            when (trendingRepositories.loadState.refresh) {
                is LoadState.Loading -> {
                    items(8) {
                        ShimmerAnimation()
                    }
                }

                is LoadState.Error -> {
                    if (trendingRepositories.itemCount == 0) {
                        item {
                            RefreshErrorScreen {
                                trendingRepositories.refresh()
                            }
                        }
                    } else {
                        items(items = trendingRepositories, key = { post ->
                            post.id
                        }) { item ->
                            item?.let { movie ->
                                TrendingRepositoriesItem(githubRepo = movie)
                                Spacer(modifier = Modifier.size(20.dp))
                            }

                        }
                        item {
                            AppendErrorScreen {
                                trendingRepositories.retry()
                            }
                        }

                    }
                }

                else -> {
                    items(items = trendingRepositories, key = { post ->
                        post.id
                    }) { item ->
                        item?.let { movie ->
                            TrendingRepositoriesItem(githubRepo = movie)
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            when (trendingRepositories.loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> item {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> item {
                    AppendErrorScreen {
                        trendingRepositories.retry()
                    }
                }
            }
        }
    }
}

@Composable
fun RefreshErrorScreen(retry: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(com.sadaPayRepos.base.R.raw.no_internet_lottie)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        restartOnPlay = false

    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {


        LottieAnimation(
            composition, progress, modifier = Modifier.size(400.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Something went wrong",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "An Align probably blocking your signal", style = TextStyle(fontSize = 16.sp)
        )
        RetryButton(retry)
    }
}

@Composable
fun RetryButton(retry: () -> Unit) {

    OutlinedButton(
        onClick = {
            retry.invoke()
        },
        border = BorderStroke(1.dp, AppGreen),
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        shape = RoundedCornerShape(10),
    ) {
        Text("Retry", color = AppGreen)
    }
}

@Composable
fun AppendErrorScreen(retry: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("There is no internet connection")
        RetryButton(retry)
    }
}