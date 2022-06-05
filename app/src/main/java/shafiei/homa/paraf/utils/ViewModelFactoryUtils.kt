package shafiei.homa.paraf.utils

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

fun <V : ViewModel> defaultSavedStateViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null,
    creator: (savedState: SavedStateHandle) -> V
): AbstractSavedStateViewModelFactory {
    return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <V : ViewModel> create(
            key: String,
            modelClass: Class<V>,
            handle: SavedStateHandle
        ) = creator(handle) as V
    }
}