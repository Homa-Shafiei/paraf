package shafiei.homa.paraf.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResultModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val backdrop_path: String = "",
    @SerializedName("original_title")
    val original_title: String = "",
    @SerializedName("poster_path")
    val poster_path: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("vote_average")
    val vote_average: Float = 5.0f,
) : Parcelable