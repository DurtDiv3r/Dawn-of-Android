package com.islaharper.dawnofandroid.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.EmptyScreenData
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.PADDING_MEDIUM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyScreen(
    data: EmptyScreenData,
    modifier: Modifier = Modifier
) {
    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        isRefreshing = true
        data.flavours?.refresh()
        isRefreshing = false
    })

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = data.icon),
                contentDescription = data.contentDescription,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium),
            )
            Text(
                text = data.message,
                modifier = Modifier.padding(top = PADDING_MEDIUM),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    DawnOfAndroidTheme {
        Surface {
            EmptyScreen(
                EmptyScreenData(
                    message = "Find a flavour",
                    icon = R.drawable.search_document
                )
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenErrorPreview() {
    DawnOfAndroidTheme {
        Surface {
            EmptyScreen(
                EmptyScreenData(
                    message = "Server unavailable",
                    icon = R.drawable.network_error,
                    contentDescription = "Network error icon"
                )
            )
        }
    }
}
