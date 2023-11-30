package useless.android.app.module.app

import useless.android.app.provider.Contexts
import useless.android.app.provider.LocalDataProvider
import useless.android.app.provider.LoggerFactory
import useless.android.app.provider.MockLocalDataProvider
import useless.android.app.provider.MockLoggerFactory

internal fun mockInjection(
    loggers: LoggerFactory = MockLoggerFactory,
    contexts: Contexts,
    local: LocalDataProvider = MockLocalDataProvider(),
): Injection {
    return Injection(
        loggers = loggers,
        contexts = contexts,
        local = local,
    )
}
