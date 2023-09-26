package com.islaharper.dawnofandroid.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.Flavour
import com.islaharper.dawnofandroid.presentation.components.RatingBar
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme
import com.islaharper.dawnofandroid.ui.theme.PADDING_LARGE
import com.islaharper.dawnofandroid.ui.theme.PADDING_MEDIUM
import com.islaharper.dawnofandroid.ui.theme.PADDING_SMALL
import com.islaharper.dawnofandroid.util.Constants

@Composable
fun FlavourItem(
    flavour: Flavour,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(400.dp)
            .clickable {
                // TODO: Navigate to details
            },
        contentAlignment = Alignment.BottomStart,
    ) {
        Surface(shape = RoundedCornerShape(size = PADDING_LARGE)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "${Constants.BASE_URL}${flavour.imageLarge}")
                    .placeholder(drawableResId = R.drawable.placeholder)
                    .error(drawableResId = R.drawable.placeholder)
                    .build(),
                contentDescription = stringResource(R.string.placeholder_image_description),
                contentScale = ContentScale.Crop,
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = PADDING_LARGE,
                bottomEnd = PADDING_LARGE,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PADDING_MEDIUM),
            ) {
                Text(
                    text = flavour.name,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = flavour.description,
                    color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = ContentAlpha.medium),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .padding(top = PADDING_SMALL),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RatingBar(
                        modifier = Modifier.padding(end = PADDING_SMALL),
                        rating = flavour.rating,
                    )
                    Text(
                        text = "(${flavour.rating})",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = ContentAlpha.medium),
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FlavourItemDarkPreview() {
    DawnOfAndroidTheme {
        Surface {
            FlavourItem(
                flavour = Flavour(
                    id = 1,
                    name = "PopTart",
                    version = "",
                    description = "Description about Android Version",
                    imageSmall = "",
                    imageLarge = "",
                    rating = 3.5,
                    year = "",
                ),
                navHostController = rememberNavController(),
            )
        }
    }
}
