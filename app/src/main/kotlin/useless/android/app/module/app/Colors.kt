package useless.android.app.module.app

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
internal class Colors private constructor(
    val background: Color,
    val foreground: Color,
) {
    companion object {
        val dark = Colors(
            background = Color.Black,
            foreground = Color.White,
        )
        val light = Colors(
            background = Color.White,
            foreground = Color.Black,
        )
    }
}
