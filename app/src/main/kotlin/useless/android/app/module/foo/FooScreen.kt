package useless.android.app.module.foo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import useless.android.app.App

@Composable
internal fun FooScreen() {
    val logger = App.newLogger("[Foo]")
    LaunchedEffect(Unit) {
        logger.debug("init...")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        // todo
    }
}
