package useless.android.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import useless.android.app.module.app.Injection
import useless.android.app.provider.FinalLoggerFactory
import useless.android.app.provider.Logger

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        _injection = Injection(
            loggers = FinalLoggerFactory,
        )
    }

    companion object {
        private var _injection: Injection? = null

        @Composable
        fun newLogger(tag: String): Logger {
            return remember(tag) {
                checkNotNull(_injection).loggers.newLogger(tag)
            }
        }
    }
}
