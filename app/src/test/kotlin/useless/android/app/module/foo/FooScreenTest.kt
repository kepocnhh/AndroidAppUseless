package useless.android.app.module.foo

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class FooScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test(timeout = 10_000)
    fun fooTest() {
        rule.setContent {
            TODO("foo screen")
        }
    }
}
