package useless.android.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import useless.android.app.module.foo.FooViewModel
import java.util.concurrent.atomic.AtomicReference

@RunWith(RobolectricTestRunner::class)
internal class AppTest {
    @get:Rule
    val rule = createAndroidComposeRule<TestActivity>()

    @Before
    fun before() {
        App.clearStores()
    }

    @After
    fun after() {
        App.clearStores()
    }

    @Test(timeout = 10_000)
    fun viewModelTest() {
        val vm1 = AtomicReference<ViewModel?>(null)
        val vm2 = AtomicReference<ViewModel?>(null)
        val vm3 = AtomicReference<ViewModel?>(null)
        rule.setContent {
            val switcher = remember { mutableStateOf(true) }
            AnimatedVisibility(visible = switcher.value) {
                Box(
                    modifier = Modifier
                        .semantics { contentDescription = "vm1" }
                        .size(64.dp),
                ) {
                    vm1.set(App.viewModel<FooViewModel>())
                    Box(
                        modifier = Modifier
                            .semantics { contentDescription = "vm2" }
                            .fillMaxSize(),
                    ) {
                        vm2.set(App.viewModel<FooViewModel>())
                    }
                }
            }
            AnimatedVisibility(visible = !switcher.value) {
                Box(
                    modifier = Modifier
                        .semantics { contentDescription = "vm3" }
                        .size(64.dp),
                ) {
                    vm3.set(App.viewModel<FooViewModel>())
                }
            }
            Box(
                modifier = Modifier
                    .semantics {
                        role = Role.Button
                        contentDescription = "switcher"
                    }
                    .size(64.dp)
                    .clickable {
                        switcher.value = false
                    },
            )
        }
        val isButton = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        val isSwitcher = hasContentDescription("switcher")
        rule.onNode(isButton and isSwitcher).performClick()
        rule.waitUntil(timeoutMillis = 5_000) {
            rule.onAllNodes(hasContentDescription("vm3"))
                .fetchSemanticsNodes()
                .size == 1
        }
        assertNotNull(vm1.get())
        assertNotNull(vm2.get())
        assertNotNull(vm3.get())
        assertEquals(vm1.get(), vm2.get())
        assertNotEquals(vm1.get(), vm3.get())
    }
}
