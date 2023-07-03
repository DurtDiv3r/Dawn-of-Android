package com.islaharper.dawnofandroid.presentation.components

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islaharper.dawnofandroid.R
import com.islaharper.dawnofandroid.domain.model.MessageBarState
import kotlinx.coroutines.delay

@Composable
fun MessageBar(messageBarState: MessageBarState) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = messageBarState) {
        startAnimation = true
        delay(3000)
        startAnimation = false
    }

    AnimatedVisibility(
        visible = messageBarState is MessageBarState.Failure && startAnimation ||
            messageBarState is MessageBarState.Success && startAnimation,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top,
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top,
        ),
    ) {
        when (messageBarState) {
            is MessageBarState.Failure -> {
                Message(
                    message = messageBarState.message,
                    messageBarColour = MaterialTheme.colorScheme.error,
                    messageContentColour = MaterialTheme.colorScheme.onError,
                    messageIcon = Icons.Default.Warning,
                )
            }

            is MessageBarState.Success -> {
                Message(
                    message = messageBarState.message,
                )
            }

            else -> {
                Log.d("MessageBar", "MessageBarState.Idle")
            }
        }
    }
}

@Composable
fun Message(
    message: String = stringResource(id = R.string.successful_sign_in),
    messageBarColour: Color = MaterialTheme.colorScheme.primary,
    messageContentColour: Color = MaterialTheme.colorScheme.onPrimary,
    messageIcon: ImageVector = Icons.Default.Check,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(messageBarColour)
            .padding(horizontal = 20.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = messageIcon,
            contentDescription = stringResource(R.string.message_bar_icon),
            tint = messageContentColour,
        )
        Divider(
            modifier = Modifier.width(12.dp),
            color = messageBarColour,
        )
        Text(
            text = message,
            color = messageContentColour,
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageBarSuccessPreview() {
    Message(
        message = stringResource(R.string.successful_sign_in),
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageBarErrorPreview() {
    Message(
        message = stringResource(id = R.string.socket_error_message),
        messageBarColour = MaterialTheme.colorScheme.error,
        messageContentColour = MaterialTheme.colorScheme.onError,
        messageIcon = Icons.Default.Warning,
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageBarConnectionErrorPreview() {
    Message(
        message = stringResource(id = R.string.connect_error_message),
        messageBarColour = MaterialTheme.colorScheme.error,
        messageContentColour = MaterialTheme.colorScheme.onError,
        messageIcon = Icons.Default.Warning,
    )
}
