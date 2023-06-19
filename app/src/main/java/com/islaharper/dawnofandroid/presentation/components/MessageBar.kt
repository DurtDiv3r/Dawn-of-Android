package com.islaharper.dawnofandroid.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import com.islaharper.dawnofandroid.ui.theme.ErrorRed
import com.islaharper.dawnofandroid.ui.theme.InfoGreen
import kotlinx.coroutines.delay
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun MessageBar(messageBarState: MessageBarState) {
    var startAnimation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val socketErrorMessage = stringResource(id = R.string.socket_error_message)
    val connectErrorMessage = stringResource(id = R.string.connect_error_message)

    LaunchedEffect(key1 = messageBarState) {
        if (messageBarState.error != null) {
            errorMessage = when (messageBarState.error) {
                is SocketTimeoutException -> {
                    socketErrorMessage
                }

                is ConnectException -> {
                    connectErrorMessage
                }

                else -> {
                    "${messageBarState.error.message} "
                }
            }
        }
        startAnimation = true
        delay(3000)
        startAnimation = false
    }

    AnimatedVisibility(
        visible = messageBarState.error != null && startAnimation ||
            messageBarState.message != null && startAnimation,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top,
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top,
        ),
    ) {
        Message(messageBarState = messageBarState, errorMessage = errorMessage)
    }
}

@Composable
fun Message(
    messageBarState: MessageBarState,
    errorMessage: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (messageBarState.error != null) ErrorRed else InfoGreen)
            .padding(horizontal = 20.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (messageBarState.error != null) {
                Icons.Default.Warning
            } else {
                Icons.Default.Check
            },
            contentDescription = stringResource(R.string.message_bar_icon),
            tint = Color.White,
        )
        Divider(
            modifier = Modifier.width(12.dp),
            color = Color.Transparent,
        )
        Text(
            text = if (messageBarState.error != null) {
                errorMessage
            } else {
                messageBarState.message.toString()
            },
            color = Color.White,
            fontSize = MaterialTheme.typography.button.fontSize,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
fun MessageBarSuccessPreview() {
    Message(
        messageBarState = MessageBarState(message = stringResource(R.string.successful_sign_in)),
    )
}

@Preview
@Composable
fun MessageBarErrorPreview() {
    Message(
        messageBarState = MessageBarState(error = SocketTimeoutException()),
        errorMessage = stringResource(id = R.string.socket_error_message),
    )
}

@Preview
@Composable
fun MessageBarConnectionErrorPreview() {
    Message(
        messageBarState = MessageBarState(error = ConnectException()),
        errorMessage = stringResource(id = R.string.connect_error_message),
    )
}
