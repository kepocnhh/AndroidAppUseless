package useless.android.app.module.foo

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import useless.android.app.App
import useless.android.app.module.app.mockInjection
import useless.android.app.provider.MockLocalDataProvider
import useless.android.app.provider.mockContexts

@RunWith(RobolectricTestRunner::class)
internal class FooScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test(timeout = 10_000)
    fun fooTest() {
        val initialText = "foobar"
        val injection = mockInjection(
            contexts = mockContexts(main = UnconfinedTestDispatcher()),
            local = MockLocalDataProvider(text = initialText),
        )
        rule.setContent {
            App::class.java.getDeclaredField("_injection").also {
                it.isAccessible = true
                it.set(App.Companion, injection)
            }
            App.Theme.Composition {
                FooScreen()
            }
        }
        rule.waitUntil {
            rule.onAllNodes(hasText(initialText))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }
}
