package useless.android.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlinx.coroutines.Dispatchers
import useless.android.app.module.app.Colors
import useless.android.app.module.app.Injection
import useless.android.app.provider.Contexts
import useless.android.app.provider.FinalLocalDataProvider
import useless.android.app.provider.FinalLoggerFactory
import useless.android.app.provider.Logger
import useless.android.app.util.AbstractViewModel

internal class App : Application() {
    object Theme {
        private val LocalColors = staticCompositionLocalOf<Colors> { error("no colors") }

        val colors: Colors
            @Composable
            @ReadOnlyComposable
            get() = LocalColors.current

        @Composable
        fun Composition(content: @Composable () -> Unit) {
            val colors = Colors.dark
            CompositionLocalProvider(
                LocalColors provides colors,
                content = content,
            )
        }
    }

    @Suppress("InjectDispatcher")
    override fun onCreate() {
        super.onCreate()
        _injection = Injection(
            loggers = FinalLoggerFactory,
            contexts = Contexts(
                main = Dispatchers.Main,
                default = Dispatchers.Default,
            ),
            local = FinalLocalDataProvider(this),
        )
    }

    companion object {
        private var _injection: Injection? = null
        private val _viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass
                    .getConstructor(Injection::class.java)
                    .newInstance(checkNotNull(_injection))
            }
        }
        private val vmStores = mutableMapOf<String, ViewModelStore>()

        @Composable
        fun newLogger(tag: String): Logger {
            return remember(tag) {
                checkNotNull(_injection).loggers.newLogger(tag)
            }
        }

        @Composable
        inline fun <reified T : AbstractViewModel> viewModel(): T {
            val key = T::class.java.name
            val (dispose, store) = synchronized(App::class.java) {
                remember { !vmStores.containsKey(key) } to vmStores.getOrPut(key, ::ViewModelStore)
            }
            DisposableEffect(Unit) {
                onDispose {
                    synchronized(App::class.java) {
                        if (dispose) {
                            vmStores.remove(key)
                            store.clear()
                        }
                    }
                }
            }
            return ViewModelProvider(store, _viewModelFactory)[T::class.java]
        }
    }
}
