package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.islaharper.dawnofandroid.ui.theme.DawnOfAndroidTheme

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
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SearchTopBarPreview() {
    DawnOfAndroidTheme {
        SearchWidget(
            text = "",
            onTextChanged = {},
            onSearchClicked = {},
            onCloseClicked = {}
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SearchTopBarTextPreview() {
    DawnOfAndroidTheme {
        SearchWidget(
            text = "Searched Text",
            onTextChanged = {},
            onSearchClicked = {},
            onCloseClicked = {}
        )
    }
}
