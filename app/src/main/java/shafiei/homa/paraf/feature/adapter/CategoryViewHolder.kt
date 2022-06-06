package shafiei.homa.paraf.feature.adapter

import android.view.View
import com.werb.library.MoreAdapter
import com.werb.library.MoreViewHolder
import com.werb.library.link.RegisterItem
import kotlinx.android.synthetic.main.categories_row.*
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.model.GenresResultModel

class CategoryViewHolder(values: MutableMap<String, Any>, containerView: View) :
    MoreViewHolder<GenresResultModel.CategoryModel>(values, containerView) {

    override fun bindData(data: GenresResultModel.CategoryModel, payloads: List<Any>) {
        categoryName.text = data.name
    }

    companion object {
        fun register(adapter: MoreAdapter) {
            adapter.register(
                RegisterItem(
                    R.layout.categories_row,
                    CategoryViewHolder::class.java,
                )
            )
        }
    }
}