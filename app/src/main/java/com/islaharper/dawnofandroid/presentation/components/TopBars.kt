package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    darkTheme: Boolean,
    dynamicTheme: Boolean,
    onSearchClicked: () -> Unit,
    onThemeSwitchClicked: () -> Unit,
    onDynamicSwitchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.open_options),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            DropdownMenu(
                modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                Text(
                    text = stringResource(R.string.light_dark),
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                ThemeSwitcher(
                    darkTheme = darkTheme,
                    onClick = onThemeSwitchClicked
                )

                Text(
                    text = stringResource(R.string.dynamic_theme),
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                ThemeSwitcher(
                    darkTheme = dynamicTheme,
                    onClick = onDynamicSwitchClicked
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun SearchTopBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    SearchWidget(
        text = text,
        onTextChanged = onTextChanged,
        onSearchClicked = onSearchClicked,
        onCloseClicked = onCloseClicked
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTopBarPreview() {
    AppTopBar(
        title = "Preview",
        darkTheme = false,
        dynamicTheme = false,
        onSearchClicked = {},
        onThemeSwitchClicked = {},
        onDynamicSwitchClicked = {}
    )
}
