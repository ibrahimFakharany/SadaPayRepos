package com.sadaPayRepos.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.sadaPayRepos.model.GithubRepo

@Composable
fun TrendingRepositoriesItem(modifier: Modifier = Modifier, githubRepo: GithubRepo) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(20.dp)) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(64.dp).clip(CircleShape),
                model = githubRepo.owner.avatar,
                contentDescription = "user image",

                )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    githubRepo.owner.login,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(githubRepo.name, style = TextStyle(fontSize = 14.sp))
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    githubRepo.language?.let {
                        Text(it, style = TextStyle(fontSize = 14.sp))
                    }
                    Row {
                        Icon(
                            painter = painterResource(R.drawable.star_icon),
                            contentDescription = "moon icon"
                        )
                        Text(githubRepo.starsCount.toString(), style = TextStyle(fontSize = 14.sp))
                    }
                }
            }
        }
    }
}