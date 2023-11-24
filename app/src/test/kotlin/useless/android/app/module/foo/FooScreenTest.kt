package useless.android.app.module.foo

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
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

    private fun textMatcher(description: String, predicate: (String) -> Boolean) : SemanticsMatcher {
        return SemanticsMatcher(description) { node ->
            val list = node.config.getOrNull(SemanticsProperties.Text)
            when {
                list == null -> false
                list.size != 1 -> false
                else -> predicate(list.single().text)
            }
        }
    }

    @Test(timeout = 10_000)
    fun getTimeTest() {
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
        val time = System.currentTimeMillis()
        val isGetTime = hasContentDescription("FooScreen:get:time")
        rule.onNode(isButton and isGetTime).performClick()
        val isAfter = textMatcher("after $time") { text ->
            val millis = text.toLongOrNull()
            if (millis == null) {
                false
            } else {
                millis >= time
            }
        }
        rule.onNode(isText).assert(isAfter)
    }

    @Test(timeout = 10_000)
    fun requestEmptyTextTest() {
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
        val isRequestText = hasContentDescription("FooScreen:request:text")
        rule.onNode(isButton and isRequestText).performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(), "[text is empty]")
    }

    @Test(timeout = 10_000)
    fun requestBlankTextTest() {
        val initialText = " "
        assertTrue(initialText.isBlank())
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
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        val isRequestText = hasContentDescription("FooScreen:request:text")
        rule.onNode(isButton and isRequestText).performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(), "[text is blank]")
    }

    @Test(timeout = 10_000)
    fun requestTextTest() {
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
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        val isRequestText = hasContentDescription("FooScreen:request:text")
        rule.onNode(isButton and isRequestText).performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(), initialText)
    }
}
