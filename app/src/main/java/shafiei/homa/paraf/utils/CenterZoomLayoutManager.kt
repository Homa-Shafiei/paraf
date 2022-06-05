package shafiei.homa.paraf.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs
import kotlin.math.min

class CenterZoomLayoutManager(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean,
        private val mShrinkAmount: Float = 0.09f,
        private val mShrinkDistance: Float = 0.6f,
        private val mAlphaAmount: Float = 0.2f
) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f

            val d0 = 0f
            val d1 = mShrinkDistance * midpoint

            val s0 = 1f
            val s1 = 1f - mShrinkAmount

            val ad0 = 0f
            val ad1 = mShrinkDistance * midpoint

            val a0 = 1f
            val a1 = 1f - mAlphaAmount


            for (i in 0 until childCount) {
                val child: View = getChildAt(i)!!
                val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                val d = min(d1, abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)

                val ad = min(ad1, abs(midpoint - childMidpoint))
                val alpha = a0 + (a1 - a0) * (ad - ad0) / (ad1 - ad0)

                child.alpha = alpha
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f

            val d0 = 0f
            val d1 = mShrinkDistance * midpoint

            val s0 = 1f
            val s1 = 1f - mShrinkAmount

            val ad0 = 0f
            val ad1 = mShrinkDistance * midpoint

            val a0 = 1f
            val a1 = 1f - mAlphaAmount

            for (i in 0 until childCount) {
                val child: View = getChildAt(i)!!
                val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                val d = min(d1, abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)

                val ad = min(ad1, abs(midpoint - childMidpoint))
                val alpha = a0 + (a1 - a0) * (ad - ad0) / (ad1 - ad0)

                child.alpha = alpha
                child.scaleX = scale
                child.scaleY = scale
            }
            scrolled
        } else {
            0
        }
    }
}