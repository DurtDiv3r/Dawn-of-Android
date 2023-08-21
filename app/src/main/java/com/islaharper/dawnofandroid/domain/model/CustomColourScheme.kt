package com.islaharper.dawnofandroid.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.islaharper.dawnofandroid.ui.theme.DarkStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.DarkStarColour
import com.islaharper.dawnofandroid.ui.theme.LightStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.LightStarColour

/* https://developer.android.com/jetpack/compose/designsystems/custom
    Add additional custom colours as needed
    Usage: DawnOfAndroidTheme.colourScheme.myColour
*/

@Immutable
data class CustomColourScheme(
    val starColour: Color,
    val starBackgroundColour: Color
)

val LocalCustomColourScheme = staticCompositionLocalOf {
    CustomColourScheme(
        starColour = Color.Unspecified,
        starBackgroundColour = Color.Unspecified
    )
}

val LightCustomColours = CustomColourScheme(
    starColour = LightStarColour,
    starBackgroundColour = LightStarBackgroundColour
)

val DarkCustomColours = CustomColourScheme(
    starColour = DarkStarColour,
    starBackgroundColour = DarkStarBackgroundColour
)

object DawnOfAndroidTheme {
    val colourScheme: CustomColourScheme
        @Composable
        get() = LocalCustomColourScheme.current
}
