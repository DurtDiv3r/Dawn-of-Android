package com.islaharper.dawnofandroid.presentation.components

import androidx.compose.runtime.Composable

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
