package shafiei.homa.paraf.feature.adapter

import android.view.View
import com.werb.library.MoreAdapter
import com.werb.library.MoreViewHolder
import com.werb.library.action.MoreClickListener
import com.werb.library.link.RegisterItem
import kotlinx.android.synthetic.main.popular_row.*
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.model.MovieResultModel
import shafiei.homa.paraf.utils.setImageLink

class PopularViewHolder(values: MutableMap<String, Any>, containerView: View) :
    MoreViewHolder<MovieResultModel>(values, containerView) {

    override fun bindData(data: MovieResultModel, payloads: List<Any>) {
        title.text = data.original_title
        image.setImageLink(AppSchema.instance.posterPath + data.poster_path) {
            error(R.drawable.empty_picture)
        }
        rateCount.text = data.vote_average.toString()
        addOnClickListener(rootView)
    }

    companion object {
        inline fun register(adapter: MoreAdapter, crossinline onItemClick: OnMovieItemClickListener) {
            adapter.register(RegisterItem(
                R.layout.popular_row,
                PopularViewHolder::class.java,
                object : MoreClickListener() {
                    override fun onItemClick(view: View, position: Int) {
                        when (view.id) {
                            R.id.rootView -> onItemClick(view, adapter.list[position] as MovieResultModel)
                        }
                    }
                },
            ))
        }
    }
}