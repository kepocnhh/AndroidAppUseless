package useless.android.app.module.foo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import useless.android.app.App
import useless.android.app.BuildConfig
import useless.android.app.util.showToast

@Composable
internal fun FooScreen() {
    val context = LocalContext.current
    val logger = App.newLogger("[Foo]")
    val viewModel = App.viewModel<FooViewModel>()
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        logger.debug("init...")
        if (state == null) {
            logger.debug("request state...")
            viewModel.requestState()
        }
    }
    LaunchedEffect(Unit) {
        viewModel
            .broadcast
            .collect { broadcast ->
                when (broadcast) {
                    is FooViewModel.Broadcast.OnText -> {
                        val message = when {
                            broadcast.text.isEmpty() -> "[text is empty]"
                            broadcast.text.isBlank() -> "[text is blank]"
                            else -> broadcast.text
                        }
                        context.showToast(message)
                    }
                }
            }
    }
    FooScreen(
        state = state,
        onClear = {
            viewModel.updateText("")
        },
        onGetTime = {
            viewModel.updateText(System.currentTimeMillis().toString())
        },
        onRequestText = {
            viewModel.requestText()
        },
    )
}

@Suppress("LongMethod")
@Composable
internal fun FooScreen(
    state: FooViewModel.State?,
    onClear: () -> Unit,
    onGetTime: () -> Unit,
    onRequestText: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(App.Theme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(),
                text = "app id: ${BuildConfig.APPLICATION_ID}",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(),
                text = "version: ${BuildConfig.VERSION_NAME}-${BuildConfig.VERSION_CODE}",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .semantics {
                        contentDescription = "FooScreen:text"
                    }
                    .fillMaxWidth()
                    .height(64.dp)
                    .wrapContentSize(),
                text = state?.text.orEmpty(),
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .semantics {
                        role = Role.Button
                        contentDescription = "FooScreen:clear"
                    }
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable(onClick = onClear)
                    .wrapContentSize(),
                text = "clear",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .semantics {
                        role = Role.Button
                        contentDescription = "FooScreen:get:time"
                    }
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable(onClick = onGetTime)
                    .wrapContentSize(),
                text = "get time",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .semantics {
                        role = Role.Button
                        contentDescription = "FooScreen:request:text"
                    }
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable(onClick = onRequestText)
                    .wrapContentSize(),
                text = "request text",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
        }
    }
}
