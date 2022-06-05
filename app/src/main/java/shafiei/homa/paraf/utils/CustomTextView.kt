package shafiei.homa.paraf.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

open class CustomTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        isInEditMode
        if (FONT_NAME == null)
            FONT_NAME = Typeface.createFromAsset(context.assets, FONT_PATH)
        this.typeface = FONT_NAME
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        isInEditMode
        try {
            if (FONT_NAME == null)
                FONT_NAME = Typeface.createFromAsset(context.assets, FONT_PATH)
            this.typeface = FONT_NAME
        } catch (ex: Exception) {

        }

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        isInEditMode
        if (FONT_NAME == null)
            FONT_NAME = Typeface.createFromAsset(context.assets, FONT_PATH)
        this.typeface = FONT_NAME
    }

    companion object {
        var FONT_NAME: Typeface? = null
        var FONT_PATH = "fonts/iranyekan-medium.ttf"

    }

}
