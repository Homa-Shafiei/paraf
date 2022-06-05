package shafiei.homa.paraf.utils

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() = if(hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }

    fun peekContent() = content
}

class EventObserver<T, E: Event<T>>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<E> {
    override fun onChanged(event: E?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}
