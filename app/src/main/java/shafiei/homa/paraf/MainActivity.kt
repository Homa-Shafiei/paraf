package shafiei.homa.paraf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.werb.library.MoreAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import shafiei.homa.paraf.feature.adapter.CategoryViewHolder
import shafiei.homa.paraf.feature.adapter.ImageSliderAdaptor
import shafiei.homa.paraf.feature.model.UpcomingResultModel
import shafiei.homa.paraf.feature.viewModel.MovieEvent
import shafiei.homa.paraf.feature.viewModel.MovieViewModel
import shafiei.homa.paraf.feature.viewModel.getMovieViewModel
import shafiei.homa.paraf.utils.CenterZoomLayoutManager
import shafiei.homa.paraf.utils.EventObserver
import shafiei.homa.paraf.utils.getRecyclerViewLinearSnapHelper
import shafiei.homa.paraf.utils.withDelay

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels {
        getMovieViewModel()
    }

    private val categoryAdapter by lazy {
        MoreAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getUpcoming()
        viewModel.getCategories()
        setupTopBanner()
        eventObserver()
    }

    private fun eventObserver() {
        viewModel.movieEvent.observe(this, EventObserver {
            when(it){
                is MovieEvent.OnError -> {}
                is MovieEvent.OnFullLoading -> {}
                is MovieEvent.OnUpcoming -> {
                    setBannerSlider(it.result.take(5).toMutableList())
                }
                is MovieEvent.OnCategory -> {
                    if (it.result.isNotEmpty()) {
                        setCategoryAdapter()
                        categoryAdapter.loadData(it.result)
                    }
                }
            }
        })
    }

    //region banner
    private fun setupTopBanner() {

        val centerZoomLayoutManager =
            CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sliderRecyclerView.layoutManager = centerZoomLayoutManager

        val snapHelper: LinearSnapHelper = getRecyclerViewLinearSnapHelper()
        snapHelper.attachToRecyclerView(sliderRecyclerView)

        // this operation is for fixing sliders margin in snapPos
        withDelay(0) {
            sliderRecyclerView.smoothScrollToPosition(1)
            withDelay(5) {
                sliderRecyclerView.smoothScrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            while (isActive) {
                delay(4000)
                if (centerZoomLayoutManager.findLastVisibleItemPosition() < sliderRecyclerView.adapter!!.itemCount - 1) {
                    centerZoomLayoutManager.smoothScrollToPosition(
                        sliderRecyclerView,
                        RecyclerView.State(),
                        centerZoomLayoutManager.findLastVisibleItemPosition() + 1
                    )
                } else if (centerZoomLayoutManager.findLastVisibleItemPosition() == sliderRecyclerView.adapter!!.itemCount - 1) {
                    centerZoomLayoutManager.smoothScrollToPosition(
                        sliderRecyclerView,
                        RecyclerView.State(),
                        0
                    )
                }
            }
        }.start()
    }

    private fun setBannerSlider(upcomingList : MutableList<UpcomingResultModel>) {

        sliderRecyclerView.adapter = ImageSliderAdaptor(this, upcomingList) { pos ->

        }

        withDelay(100) {
            sliderRecyclerView.smoothScrollToPosition(1)
            withDelay(100) {
                sliderRecyclerView.smoothScrollToPosition(0)
            }
        }
    }
    //endregion

    //region Category
    private fun setCategoryAdapter() {
        categoryAdapter.removeAllData()
        rvCategory.adapter = categoryAdapter
        rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter.apply {
            CategoryViewHolder.register(this)
            attachTo(rvCategory)
        }
    }
    //endregion
}