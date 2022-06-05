package shafiei.homa.paraf.utils

import android.os.Handler
import android.os.Looper


fun withDelay(
    delay: Long,
    looper: Looper? = null,
    block: () -> Unit,
) {
    if (delay == 0L) {
        block()
        return
    }
    val handler = if (looper != null)
        Handler(looper)
    else
        Handler()

    handler.postDelayed(block, delay)
}