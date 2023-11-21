package useless.android.app.module.foo

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class FooViewModelTest {
    @Test
    fun foo() {
        runTest(timeout = 10.seconds) {
            val injection = TODO()
            val viewModel = FooViewModel(injection)
            viewModel
                .state
                .withIndex()
                .onEach { (index, value) ->
                    when (index) {
                        else -> error("Unexpected index: $index!")
                    }
                }
                .take(4)
                .collect()
        }
    }
}
