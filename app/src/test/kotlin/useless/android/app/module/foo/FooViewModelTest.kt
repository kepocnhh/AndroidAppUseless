package useless.android.app.module.foo

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import useless.android.app.module.app.mockInjection
import useless.android.app.provider.mockContexts
import kotlin.time.Duration.Companion.seconds

internal class FooViewModelTest {
    @Test
    fun requestStateTest() {
        runTest(timeout = 10.seconds) {
            val injection = mockInjection(
                contexts = mockContexts(main = coroutineContext),
            )
            val viewModel = FooViewModel(injection)
            viewModel
                .state
                .withIndex()
                .onEach { (index, value) ->
                    when (index) {
                        0 -> {
                            assertNull(value)
                            viewModel.requestState()
                        }
                        1 -> {
                            assertNotNull(value)
                            checkNotNull(value)
                            assertEquals(value.text, "")
                        }
                        else -> error("Unexpected index: $index!")
                    }
                }
                .take(2)
                .collect()
        }
    }
}
