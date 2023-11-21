package useless.android.app.module.foo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import useless.android.app.App

@Composable
internal fun FooScreen() {
    val logger = App.newLogger("[Foo]")
    val viewModel = App.viewModel<FooViewModel>()
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        logger.debug("init...")
        if (state == null) {
            viewModel.requestState()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(App.Theme.colors.background),
    ) {
        BasicText(
            modifier = Modifier.align(Alignment.Center),
            text = state?.text.orEmpty(),
            style = TextStyle(color = App.Theme.colors.foreground),
        )
    }
}
