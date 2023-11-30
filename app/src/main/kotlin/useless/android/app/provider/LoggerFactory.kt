package useless.android.app.provider

internal interface LoggerFactory {
    fun newLogger(tag: String): Logger
}

internal interface Logger {
    fun debug(message: String)
    fun warning(message: String)
}
