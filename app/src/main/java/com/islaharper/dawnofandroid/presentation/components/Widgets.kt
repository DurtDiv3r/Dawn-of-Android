package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme

@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(stringResource(R.string.day_mode))
        Switch(
            checked = darkTheme,
            onCheckedChange = {
                onClick()
            }
        )
        Text(stringResource(R.string.night_mode))
    }
}

@Preview(name = "Light", showBackground = true)
@Composable
fun ThemeSwitcherPreview() {
    DawnOfAndroidTheme {
        Surface {
            ThemeSwitcher {
            }
        }
    }
}

@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ThemeSwitcherDarkPreview() {
    DawnOfAndroidTheme {
        Surface {
            ThemeSwitcher(darkTheme = true) {
            }
        }
    }
}
