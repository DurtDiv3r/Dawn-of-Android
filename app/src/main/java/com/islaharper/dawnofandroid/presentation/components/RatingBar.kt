package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.domain.model.Rating
import com.islaharper.dawnofandroid.ui.RatingBarColours
import com.islaharper.dawnofandroid.ui.RatingBarDefaults
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.LightStarBackgroundColour
import com.islaharper.dawnofandroid.ui.theme.LightStarColour
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL
import com.islaharper.dawnofandroid.util.Constants.STAR_PATH

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    colours: RatingBarColours = RatingBarDefaults.colours(),
    spaceBetween: Dp = PADDING_SMALL
) {
    val result by remember(rating) { mutableStateOf(calculateRating(rating = rating)) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        repeat(result.filledStars) {
            FilledStar(
                starColour = colours.starColour
            )
        }
        repeat(result.halfStars) {
            HalfFilledStar(
                starColour = colours.starColour,
                starBackgroundColour = colours.starBackgroundColour
            )
        }
        repeat(result.emptyStars) {
            EmptyStar(
                starBackgroundColour = colours.starBackgroundColour
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RatingBarPreview() {
    DawnOfAndroidTheme {
        Surface {
            Column {
                RatingBar(rating = 7.0)
                RatingBar(rating = 2.5)
                RatingBar(rating = 5.0)
            }
        }
    }
}

@Composable
fun FilledStar(
    modifier: Modifier = Modifier,
    starColour: Color = LightStarColour
) {
    val starPath = remember { PathParser().parsePathString(STAR_PATH).toPath() }
    val starPathBounds = remember { starPath.getBounds() }

    Canvas(modifier = modifier.size(24.dp)) {
        scale(scale = size.maxDimension / starPathBounds.maxDimension) {
            val left = (size.width / 2f) - (starPathBounds.width / 2f)
            val top = (size.height / 2f) - (starPathBounds.height / 2f)

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
    modifier: Modifier = Modifier,
    starColour: Color = LightStarColour,
    starBackgroundColour: Color = LightStarBackgroundColour
) {
    val starPath = remember { PathParser().parsePathString(STAR_PATH).toPath() }
    val starPathBounds = remember { starPath.getBounds() }

    Canvas(modifier = modifier.size(24.dp)) {
        val scaleFactor = size.maxDimension / starPathBounds.maxDimension
        scale(scale = scaleFactor) {
            val left = (size.width / 2f) - (starPathBounds.width / 2f)
            val top = (size.height / 2f) - (starPathBounds.height / 2f)

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
                            width = starPathBounds.width / 1.95f,
                            height = starPathBounds.height * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    modifier: Modifier = Modifier,
    starBackgroundColour: Color = LightStarBackgroundColour
) {
    val starPath = remember { PathParser().parsePathString(STAR_PATH).toPath() }
    val starPathBounds = remember { starPath.getBounds() }

    Canvas(modifier = modifier.size(24.dp)) {
        scale(scale = size.maxDimension / starPathBounds.maxDimension) {
            val left = (size.width / 2f) - (starPathBounds.width / 2f)
            val top = (size.height / 2f) - (starPathBounds.height / 2f)

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

fun calculateRating(rating: Double): Rating {
    var filledStars = 0
    var halfStars = 0

    val (filled, half) = rating.toString().split(".").map {
        it.toInt()
    }

    if (filled in 0..5 && half in 0..9) {
        filledStars = filled
        if (half in 1..5) {
            halfStars++
        }
        if (half in 6..9) {
            filledStars++
        }
        if (filled == 5 && half > 0) {
            halfStars = 0
            filledStars = 0
        }
    } else {
        Log.d("RatingBar", "Invalid Rating")
    }

    return Rating(
        filledStars = filledStars,
        halfStars = halfStars,
        emptyStars = 5 - (filledStars + halfStars)
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FilledStarPreview() {
    DawnOfAndroidTheme {
        Surface {
            FilledStar(starColour = RatingBarDefaults.colours().starColour)
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HalfFilledStarPreview() {
    DawnOfAndroidTheme {
        Surface {
            HalfFilledStar(
                starColour = RatingBarDefaults.colours().starColour,
                starBackgroundColour = RatingBarDefaults.colours().starBackgroundColour
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EmptyStarPreview() {
    DawnOfAndroidTheme {
        Surface {
            EmptyStar(starBackgroundColour = RatingBarDefaults.colours().starBackgroundColour)
        }
    }
}
