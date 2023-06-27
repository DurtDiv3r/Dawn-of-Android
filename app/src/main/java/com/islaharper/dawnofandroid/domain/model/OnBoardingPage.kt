package com.islaharper.dawnofandroid.domain.model

import androidx.annotation.DrawableRes
import com.islaharper.dawnofandroid.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String,
) {
    object FirstPage : OnBoardingPage(
        image = R.drawable.welcome,
        title = "Greetings",
        description = "An example App using Jetpack Compose and Ktor backend",
    )

    object SecondPage : OnBoardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "A showcase for some of the technologies learnt",
    )

    object ThirdPage : OnBoardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Google One-Tap sign in, MVVM, Room DB, Accompanist, RestAPI",
    )
}
