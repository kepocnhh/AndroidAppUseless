package useless.android.app.module.foo

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import useless.android.app.App
import useless.android.app.module.app.mockInjection
import useless.android.app.provider.MockLocalDataProvider
import useless.android.app.provider.mockContexts
import useless.android.app.setInjection

@RunWith(RobolectricTestRunner::class)
internal class FooScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test(timeout = 10_000)
    fun initialTextTest() {
        val initialText = "foobar"
        val injection = mockInjection(
            contexts = mockContexts(main = UnconfinedTestDispatcher()),
            local = MockLocalDataProvider(text = initialText),
        )
        App.setInjection(injection)
        rule.setContent {
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

    @Test(timeout = 10_000)
    fun clearTest() {
        val initialText = "foobar"
        val injection = mockInjection(
            contexts = mockContexts(main = UnconfinedTestDispatcher()),
            local = MockLocalDataProvider(text = initialText),
        )
        App.setInjection(injection)
        rule.setContent {
            App.Theme.Composition {
                FooScreen()
            }
        }
        val isText = hasContentDescription("FooScreen:text")
        rule.waitUntil {
            rule.onAllNodes(isText and hasText(initialText))
                .fetchSemanticsNodes()
                .size == 1
        }
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        val isClear = hasContentDescription("FooScreen:clear")
        rule.onNode(isButton and isClear).performClick()
        rule.waitUntil {
            rule.onAllNodes(isText and hasText(""))
                .fetchSemanticsNodes()
                .size == 1
        }
    }
}
