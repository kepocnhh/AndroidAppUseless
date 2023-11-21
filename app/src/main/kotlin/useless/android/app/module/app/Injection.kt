package useless.android.app.module.app

import useless.android.app.provider.Contexts
import useless.android.app.provider.LocalDataProvider
import useless.android.app.provider.LoggerFactory

internal data class Injection(
    val loggers: LoggerFactory,
    val contexts: Contexts,
    val local: LocalDataProvider,
)
