package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL

@Composable
fun InfoBox(
    icon: Painter,
    iconColour: Color,
    mainText: String,
    description: String,
    textColour: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .semantics(mergeDescendants = true) {},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null, // decorative
            modifier = modifier
                .padding(end = PADDING_SMALL)
                .size(32.dp),
            tint = iconColour
        )
        Column {
            Text(
                text = mainText,
                color = textColour,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = description,
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                color = textColour,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun InfoBoxPreview() {
    DawnOfAndroidTheme {
        Surface {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_android),
                iconColour = MaterialTheme.colorScheme.primary,
                mainText = "4.1.1",
                description = "Version",
                textColour = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
