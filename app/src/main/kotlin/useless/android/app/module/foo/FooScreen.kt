package useless.android.app.module.foo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import useless.android.app.App

@Composable
internal fun FooScreen() {
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
                modifier = Modifier.fillMaxWidth().height(64.dp).wrapContentSize(),
                text = state?.text.orEmpty(),
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable {
                        viewModel.updateText("")
                    }
                    .wrapContentSize(),
                text = "clear",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable {
                        viewModel.updateText(System.currentTimeMillis().toString())
                    }
                    .wrapContentSize(),
                text = "get time",
                style = TextStyle(color = App.Theme.colors.foreground),
            )
        }
    }
}
