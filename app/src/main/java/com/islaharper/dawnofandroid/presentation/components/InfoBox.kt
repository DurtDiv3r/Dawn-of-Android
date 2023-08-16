package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.ui.theme.INFO_ICON_SIZE
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL

@Composable
fun InfoBox(
    icon: Painter,
    iconColour: Color,
    heading: String,
    description: String,
    textColour: Color,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.infobox_icon_content_desc),
            modifier = modifier.padding(end = PADDING_SMALL).size(INFO_ICON_SIZE),
            tint = iconColour
        )
        Column {
            Text(
                text = heading,
                color = textColour,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = description,
                modifier = Modifier.alpha(ContentAlpha.medium),
                color = textColour,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InfoBoxPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.ic_android),
        iconColour = Color.Green,
        heading = "Ice Cream Sandwich",
        description = "4.0",
        textColour = Color.Black
    )
}
