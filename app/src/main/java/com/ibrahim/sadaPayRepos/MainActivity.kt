package com.ibrahim.sadaPayRepos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.ibrahim.sadaPayRepos.ui.theme.SadaPayReposTheme
import com.yassirMovies.featues.home.TrendingReposScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            SadaPayReposTheme(darkTheme = isDarkTheme) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Trending",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        actions = {
                            IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                if (isDarkTheme) Icon(
                                    painter = painterResource(R.drawable.sun_icon),
                                    contentDescription = "sun icon"
                                )
                                else Icon(
                                    painter = painterResource(R.drawable.moon_icon),
                                    contentDescription = "moon icon"
                                )
                            }
                        },
                    )
                }) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        TrendingReposScreen()
                    }
                }
            }
        }
    }
}