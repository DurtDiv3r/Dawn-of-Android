package com.islaharper.dawnofandroid.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val StatusBarLight = Color(0xFF3700B3)
val StatusBarDark = Color(0xFF000000)

val LightGrey = Color(0xFFD8D8D8)
val DarkGrey = Color(0xFF2A2A2A)
val StarColour = Color(0xFFFFC94D)

val ShimmerLightGrey = Color(0xFFF1F1F1)
val ShimmerMediumGrey = Color(0xFFE3E3E3)
val ShimmerDarkGrey = Color(0xFF1D1D1D)

val ErrorRed = Color(0xFFFF6C60)
val InfoGreen = Color(0xFF00C096)
val LoadingBlue = Color(0xFF1A73E8)

val Colors.errorRed
    get() = ErrorRed

val Colors.infoGreen
    get() = InfoGreen

val Colors.loadingBlue
    get() = LoadingBlue

val Colors.welcomeScreenBackgroundColour
    get() = if (isLight) Color.White else Color.Black

val Colors.statusBarColour
    get() = if (isLight) StatusBarLight else StatusBarDark

val Colors.titleColour
    get() = if (isLight) DarkGrey else LightGrey

val Colors.descriptionColour
    get() = if (isLight) DarkGrey.copy(alpha = 0.5f) else LightGrey.copy(alpha = 0.5f)

val Colors.activeIndicatorColour
    get() = if (isLight) Purple500 else Purple700

val Colors.inactiveIndicatorColour
    get() = if (isLight) LightGrey else DarkGrey

val Colors.buttonBackgroundColour
    get() = if (isLight) Purple500 else Purple700

val Colors.topAppBarContentColour
    get() = if (isLight) Color.White else LightGrey

val Colors.topAppBarBackgroundColour
    get() = if (isLight) Purple500 else Color.Black