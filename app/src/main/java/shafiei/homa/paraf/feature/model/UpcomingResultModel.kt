package shafiei.homa.paraf.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingResultModel(
    @SerializedName("backdrop_path")
    val backdrop_path: String = "",
) : Parcelable