package shafiei.homa.paraf.feature.adapter

import android.view.View
import com.werb.library.MoreAdapter
import com.werb.library.MoreViewHolder
import com.werb.library.link.RegisterItem
import kotlinx.android.synthetic.main.top_rated_row.*
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.model.MovieResultModel
import shafiei.homa.paraf.utils.setImageLink

class TopRatedViewHolder(values: MutableMap<String, Any>, containerView: View) :
    MoreViewHolder<MovieResultModel>(values, containerView) {

    override fun bindData(data: MovieResultModel, payloads: List<Any>) {
        title.text = data.original_title
        image.setImageLink(AppSchema.instance.posterPath + data.backdrop_path) {
            error(R.drawable.empty_picture)
        }
        rateCount.text = data.vote_average.toString()
    }

    companion object {
        fun register(adapter: MoreAdapter) {
            adapter.register(
                RegisterItem(
                    R.layout.top_rated_row,
                    TopRatedViewHolder::class.java,
                )
            )
        }
    }
}