package shafiei.homa.paraf.network.utils

import android.accounts.NetworkErrorException
import android.content.res.Resources
import android.util.MalformedJsonException
import retrofit2.HttpException
import shafiei.homa.paraf.MyApplication
import shafiei.homa.paraf.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionUtil {

    fun catchException(exception: Exception): String {
        exception.printStackTrace()
        val message = when (exception) {
            is HttpException -> {
                return catchHttpException(exception.code())
            }
            is SocketTimeoutException -> R.string.common_error_net_time_out
            is UnknownHostException,
            is NetworkErrorException -> R.string.common_error_net
            is NullPointerException,
            is ClassCastException,
            is Resources.NotFoundException,
            is MalformedJsonException -> R.string.common_error_do_something_fail
//            is NullBodyException -> R.string.common_error_server_body_null
            else -> R.string.common_error_do_something_fail
        }
        return MyApplication.instance.getString(message)
    }

    fun catchHttpException(errorCode: Int): String {
        if (errorCode in 200 until 300) return ""
        return MyApplication.instance.getString(catchHttpExceptionCode(errorCode))
    }

    private fun catchHttpExceptionCode(errorCode: Int): Int = when (errorCode) {
        in 500..600 -> R.string.common_error_server
        in 400 until 500 -> R.string.common_error_request
        else -> R.string.common_error_request
    }

}