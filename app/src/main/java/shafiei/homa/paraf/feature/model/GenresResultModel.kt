package shafiei.homa.paraf.feature.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenresResultModel(
    @SerializedName("genres")
    val genres: MutableList<CategoryModel> = mutableListOf(),
) : Parcelable {
    @Parcelize
    data class CategoryModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
    ) : Parcelable
}