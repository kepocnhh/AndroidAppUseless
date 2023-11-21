package useless.android.app.provider

import android.content.Context
import useless.android.app.BuildConfig

internal class FinalLocalDataProvider(context: Context) : LocalDataProvider {
    private val preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    override var text: String
        get() {
            return preferences.getString("text", null).orEmpty()
        }
        set(value) {
            preferences
                .edit()
                .putString("text", value)
                .commit()
        }
}
