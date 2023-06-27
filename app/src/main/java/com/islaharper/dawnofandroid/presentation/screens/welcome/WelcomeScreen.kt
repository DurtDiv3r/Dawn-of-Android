package com.islaharper.dawnofandroid.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.OnBoardingPage
import com.islaharper.dawnofandroid.navigation.Screen
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL
import com.islaharper.dawnofandroid.ui.theme.PADDING_XLARGE
import com.islaharper.dawnofandroid.util.Constants.ONBOARDING_LAST_PAGE
import com.islaharper.dawnofandroid.util.Constants.ONBOARDING_PAGE_COUNT
import okhttp3.internal.immutableListOf

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
) {
    val pages = immutableListOf(
        OnBoardingPage.FirstPage,
        OnBoardingPage.SecondPage,
        OnBoardingPage.ThirdPage,
    )
    val pagerState = rememberPagerState()
    val saveOnBoardingClick = { welcomeViewModel.saveOnBoardingState(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = ONBOARDING_PAGE_COUNT,
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { position ->
            PagerScreen(page = pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.surfaceVariant,
            indicatorWidth = 12.dp,
            spacing = 8.dp,
        )
        FinishButton(
            modifier = Modifier.weight(1f).padding(horizontal = PADDING_XLARGE),
            pagerState = pagerState,
            saveOnBoardingClick = saveOnBoardingClick,
        ) {
            navHostController.popBackStack()
            navHostController.navigate(route = Screen.Login.route)
        }
    }
}

@Composable
fun PagerScreen(page: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = page.image),
            contentDescription = stringResource(R.string.onboarding_page),
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = page.title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PADDING_XLARGE)
                .padding(top = PADDING_SMALL),
            text = page.description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    saveOnBoardingClick: () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == ONBOARDING_LAST_PAGE,
        ) {
            Button(
                onClick = {
                    onClick()
                    saveOnBoardingClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Text(text = stringResource(R.string.finish_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(page = OnBoardingPage.FirstPage)
    }
}

@Preview(showBackground = true)
@Composable
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(page = OnBoardingPage.SecondPage)
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(page = OnBoardingPage.ThirdPage)
    }
}
