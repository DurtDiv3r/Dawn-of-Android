package com.islaharper.dawnofandroid.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.islaharper.dawnofandroid.ui.theme.DarkStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.DarkStarColour
import com.islaharper.dawnofandroid.ui.theme.LightStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.LightStarColour

object RatingBarDefaults {

    @Composable
    fun colours(): RatingBarColours {
        return if (isSystemInDarkTheme()) {
            RatingBarColours(
                starColour = DarkStarColour,
                starBackgroundColour = DarkStarBackgroundColour
            )
        } else {
            RatingBarColours(
                starColour = LightStarColour,
                starBackgroundColour = LightStarBackgroundColour
            )
        }
    }
}

data class RatingBarColours(
    val starColour: Color,
    val starBackgroundColour: Color
)
