package useless.android.app

import useless.android.app.module.app.Injection

internal fun App.Companion.setInjection(injection: Injection) {
    val field = App::class.java.getDeclaredField("_injection")
    field.isAccessible = true
    field.set(this, injection)
}
