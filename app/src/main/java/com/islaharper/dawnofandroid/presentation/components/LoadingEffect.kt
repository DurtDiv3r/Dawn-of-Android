package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.PADDING_LARGE
import com.islaharper.dawnofandroid.ui.theme.PADDING_MEDIUM
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL

@Composable
fun LoadingEffect(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 650,
                easing = FastOutLinearInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Loading animation",
    )
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(all = PADDING_SMALL),
        verticalArrangement = Arrangement.spacedBy(PADDING_MEDIUM),
    ) {
        items(count = 2) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(size = PADDING_LARGE),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PADDING_MEDIUM),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(30.dp)
                            .alpha(alpha = alphaAnim),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(PADDING_SMALL),

                    ) {}
                    Spacer(modifier = Modifier.padding(PADDING_MEDIUM))
                    repeat(3) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                                .alpha(alpha = alphaAnim),
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(PADDING_SMALL),
                        ) {}
                        Spacer(modifier = Modifier.padding(PADDING_SMALL))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(5) {
                            Surface(
                                modifier = Modifier
                                    .size(20.dp)
                                    .alpha(alpha = alphaAnim),
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(PADDING_SMALL),
                            ) {}
                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingItemPreview() {
    DawnOfAndroidTheme {
        Surface {
            LoadingEffect()
        }
    }
}
