package shafiei.homa.paraf.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.slider_item_layout.view.*
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.model.MovieResultModel
import shafiei.homa.paraf.utils.listener
import shafiei.homa.paraf.utils.setImageLink

class ImageSliderAdaptor(
    private val context: Context,
    private val listItem: MutableList<MovieResultModel>,
    private val onItemClick: ((index: Int) -> Unit)? = null
) : RecyclerView.Adapter<ImageSliderAdaptor.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            view = LayoutInflater.from(context).inflate(
                R.layout.slider_item_layout,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(h: CustomViewHolder, position: Int) {
        val item = listItem[position]

        item.let {
            h.imageView.setImageLink(AppSchema.instance.posterPath + item.backdrop_path) {
                error(R.drawable.empty_picture)
                listener{
                    h.loading.visibility = View.GONE
                }
            }
            onItemClick?.let { itemClick ->
                h.itemView.setOnClickListener {
                    itemClick.invoke(position)
                }
            }
        }

    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.sliderImageView
        val loading: SpinKitView = itemView.imageSliderImageLoading
    }

}