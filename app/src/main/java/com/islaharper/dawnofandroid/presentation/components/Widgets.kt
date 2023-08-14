package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.ui.theme.DarkStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.DarkStarColour
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.LightStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.LightStarColour
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL
import com.islaharper.dawnofandroid.util.Constants.STAR_FLOAT
import com.islaharper.dawnofandroid.util.Constants.STAR_PATH

@Composable
fun RatingWidget(
    rating: Double,
    modifier: Modifier = Modifier,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = PADDING_SMALL
) {
    val result = calculateRatingStars(rating = rating)
    val starPath = remember { PathParser().parsePathString(STAR_PATH).toPath() }
    val starPathBounds = remember { starPath.getBounds() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it) {
                FilledStar(
                    starPath = starPath,
                    starBounds = starPathBounds,
                    scaleFactor = scaleFactor,
                    starColour = if (isSystemInDarkTheme()) {
                        DarkStarColour
                    } else {
                        LightStarColour
                    }
                )
            }
        }
        result["halfStars"]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starBounds = starPathBounds,
                    scaleFactor = scaleFactor,
                    starColour = if (isSystemInDarkTheme()) DarkStarColour else LightStarColour,
                    starBackgroundColour = if (isSystemInDarkTheme()) {
                        DarkStarBackgroundColour
                    } else {
                        LightStarBackgroundColour
                    }
                )
            }
        }
        result["emptyStars"]?.let {
            repeat(it) {
                EmptyStar(
                    starPath = starPath,
                    starBounds = starPathBounds,
                    scaleFactor = scaleFactor,
                    starBackgroundColour = if (isSystemInDarkTheme()) {
                        DarkStarBackgroundColour
                    } else {
                        LightStarBackgroundColour
                    }
                )
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starBounds: Rect,
    scaleFactor: Float,
    modifier: Modifier = Modifier,
    starColour: Color = LightStarColour
) {
    Canvas(modifier = modifier.size(26.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = size
            val pathWidth = starBounds.width
            val pathHeight = starBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / STAR_FLOAT)
            val top = (canvasSize.height / 2f) - (pathHeight / STAR_FLOAT)

            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = starColour
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starBounds: Rect,
    scaleFactor: Float,
    modifier: Modifier = Modifier,
    starColour: Color = LightStarColour,
    starBackgroundColour: Color = LightStarBackgroundColour
) {
    Canvas(modifier = modifier.size(26.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = size
            val pathWidth = starBounds.width
            val pathHeight = starBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / STAR_FLOAT)
            val top = (canvasSize.height / 2f) - (pathHeight / STAR_FLOAT)

            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = starBackgroundColour
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = starColour,
                        size = Size(
                            width = starBounds.maxDimension / STAR_FLOAT,
                            height = starBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starBounds: Rect,
    scaleFactor: Float,
    modifier: Modifier = Modifier,
    starBackgroundColour: Color = LightStarBackgroundColour
) {
    Canvas(modifier = modifier.size(26.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = size
            val pathWidth = starBounds.width
            val pathHeight = starBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / STAR_FLOAT)
            val top = (canvasSize.height / 2f) - (pathHeight / STAR_FLOAT)

            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = starBackgroundColour
                )
            }
        }
    }
}

@Composable
fun calculateRatingStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableIntStateOf(5) }
    var emptyStars by remember { mutableIntStateOf(0) }
    var halfStars by remember { mutableIntStateOf(0) }
    var filledStars by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, secondNumber) = rating.toString().split(".").map {
            it.toInt()
        }

        if (firstNumber in 0..5 && secondNumber in 0..9) {
            filledStars = firstNumber
            if (secondNumber in 1..5) {
                halfStars++
            }
            if (secondNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && secondNumber > 0) {
                emptyStars = 0
                halfStars = 0
                filledStars = 0
            }
        } else {
            Log.d("RatingWidget", "Invalid Rating")
        }
    }
    emptyStars = maxStars - (filledStars + halfStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfStars" to halfStars,
        "emptyStars" to emptyStars
    )
}

@Preview(name = "Light", showBackground = true)
@Composable
fun FilledStarPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            FilledStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f
            )
        }
    }
}

@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FilledStarDarkPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            FilledStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f,
                starColour = DarkStarColour
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
fun HalfFilledStarPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            HalfFilledStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f
            )
        }
    }
}

@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HalfFilledStarDarkPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            HalfFilledStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f,
                starColour = DarkStarColour,
                starBackgroundColour = DarkStarBackgroundColour
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
fun EmptyStarPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            EmptyStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f
            )
        }
    }
}

@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EmptyStarDarkPreview() {
    val starPath = remember {
        PathParser().parsePathString(STAR_PATH).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    DawnOfAndroidTheme {
        Surface {
            EmptyStar(
                starPath = starPath,
                starBounds = starPathBounds,
                scaleFactor = 3f,
                starBackgroundColour = DarkStarBackgroundColour
            )
        }
    }
}
