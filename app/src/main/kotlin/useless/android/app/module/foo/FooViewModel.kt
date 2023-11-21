package useless.android.app.module.foo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import useless.android.app.module.app.Injection
import useless.android.app.util.AbstractViewModel

internal class FooViewModel(private val injection: Injection) : AbstractViewModel() {
    data class State(val text: String)

    private val logger = injection.loggers.newLogger("[Foo|VM]")
    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    fun requestState() {
        logger.debug("request state...")
        injection.launch {
            TODO("request state...")
        }
    }
}
