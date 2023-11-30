package useless.android.app.module.foo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import useless.android.app.App

@Composable
private fun FooScreenPreview(state: FooViewModel.State?) {
    App.Theme.Composition {
        FooScreen(
            state = state,
            onClear = {
                // noop
            },
            onGetTime = {
                // noop
            },
            onRequestText = {
                // noop
            },
        )
    }
}

@Preview(name = "state null")
@Composable
private fun FooScreenPreviewStateNull() {
    FooScreenPreview(state = null)
}

@Preview(
    name = "state foobar",
    showBackground = true,
    backgroundColor = 0xff0000ff,
    showSystemUi = true,
)
@Composable
private fun FooScreenPreview() {
    FooScreenPreview(state = FooViewModel.State(text = "foobar"))
}
