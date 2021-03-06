package shafiei.homa.paraf.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: MutableList<MovieResultModel> = mutableListOf(),
) : Parcelable