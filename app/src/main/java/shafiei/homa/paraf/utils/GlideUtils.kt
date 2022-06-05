package shafiei.homa.paraf.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import shafiei.homa.paraf.R


typealias RequestBuilderBlock = RequestBuilder<Drawable>.() -> Unit

fun ImageView.setImageLink(
    link: String?,
    requestExtra: RequestBuilderBlock = {},
): Target<Drawable>? {

    return context.getGlide()
        ?.load(link)
        ?.intoImageView(this, requestExtra)
}

fun ImageView.setResource(
    resId: Int?,
    requestExtra: RequestBuilderBlock = {},
): Target<Drawable>? {

    return context.getGlide()
        ?.load(resId)
        ?.intoImageView(this, requestExtra)
}

private inline fun RequestBuilder<Drawable>.intoImageView(
    imageView: ImageView,
    requestExtra: RequestBuilderBlock = {},
): Target<Drawable> {
    return defaultError()
        .apply(requestExtra)
        .into(imageView)
}

fun Context.getGlide(): RequestManager? {
    if (!isContextValidForGlide(this))
        return null

    return Glide.with(this)
}

fun isContextValidForGlide(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    if (context is Activity) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            !context.isDestroyed && !context.isFinishing
        } else {
            !context.isFinishing
        }
    }
    return true
}


fun RequestBuilder<Drawable>.listener(
    onSuccess: ((resource: Drawable?, model: Any?) -> Unit)? = null,
    exception: ((e: Exception?, model: Any?) -> Unit)? = null,
    onFinnish: (() -> Unit)? = null,
): RequestBuilder<Drawable> {

    return listener(object : RequestListener<Drawable> {

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            onSuccess?.invoke(resource, model)
            onFinnish?.invoke()
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            exception?.invoke(e, model)
            onFinnish?.invoke()
            return false
        }
    })

}

fun RequestBuilder<Drawable>.defaultError(): RequestBuilder<Drawable> {
    return error(GlideUtils.errorRes)
}

object GlideUtils {
    var errorRes = R.drawable.empty_picture
}