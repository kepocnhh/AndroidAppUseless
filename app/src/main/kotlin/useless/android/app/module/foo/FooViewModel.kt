package useless.android.app.module.foo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import useless.android.app.module.app.Injection
import useless.android.app.util.AbstractViewModel

internal class FooViewModel(private val injection: Injection) : AbstractViewModel() {
    sealed interface Broadcast {
        data class OnText(val text: String) : Broadcast
    }
    data class State(val text: String)

    private val logger = injection.loggers.newLogger("[Foo|VM]")
    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()
    private val _broadcast = MutableSharedFlow<Broadcast>()
    val broadcast = _broadcast.asSharedFlow()

    fun requestState() {
        logger.debug("request state...")
        injection.launch {
            val text = withContext(injection.contexts.default) {
                injection.local.text
            }
            _state.value = State(text = text)
        }
    }

    fun updateText(text: String) {
        logger.debug("update text...")
        injection.launch {
            withContext(injection.contexts.default) {
                injection.local.text = text
            }
            _state.value = State(text = text)
        }
    }

    fun requestText() {
        logger.debug("request text...")
        injection.launch {
            val text = withContext(injection.contexts.default) {
                injection.local.text
            }
            _broadcast.emit(Broadcast.OnText(text = text))
        }
    }
}
