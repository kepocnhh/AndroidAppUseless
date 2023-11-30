package useless.android.app.provider

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.coroutines.CoroutineContext

internal fun mockContexts(
    main: CoroutineContext,
    default: CoroutineContext = UnconfinedTestDispatcher(),
): Contexts {
    return Contexts(
        main = main,
        default = default,
    )
}
