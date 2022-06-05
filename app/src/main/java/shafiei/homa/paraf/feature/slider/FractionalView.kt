package shafiei.homa.paraf.feature.slider

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SliderItemLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec((DeviceConfiguration.widthPixels * 0.88).toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec((DeviceConfiguration.heightPixels * 0.28).toInt(), MeasureSpec.EXACTLY)
        )
    }
}