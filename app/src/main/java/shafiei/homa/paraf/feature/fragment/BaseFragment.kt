package shafiei.homa.paraf.feature.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


open class BaseFragment(
    @LayoutRes open val contentLayoutId: Int = 0,
) : Fragment(contentLayoutId) {

    var rootView: View? = null
    private val savable = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (contentLayoutId != 0)
            rootView = super.onCreateView(inflater, container, savedInstanceState)

        Log.d("BaseFragment", "onCreateFragment: name = ${this.javaClass.simpleName}")

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            savable.putAll(savedInstanceState.getBundle("_state"))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle("_state", savable)
        super.onSaveInstanceState(outState)
    }

}