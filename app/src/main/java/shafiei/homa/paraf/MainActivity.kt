package shafiei.homa.paraf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import shafiei.homa.paraf.feature.ImageSliderAdaptor
import shafiei.homa.paraf.utils.CenterZoomLayoutManager
import shafiei.homa.paraf.utils.getRecyclerViewLinearSnapHelper
import shafiei.homa.paraf.utils.withDelay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTopBanner()
        setBannerSlider()
    }

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

    private fun setBannerSlider() {

        val list: MutableList<String> = mutableListOf(
            "https://image.tmdb.org/t/p/w500/1Ds7xy7ILo8u2WWxdnkJth1jQVT.jpg",
            "https://image.tmdb.org/t/p/w500/kiH3KPWi7BaRMvdAigcwrUFViHl.jpg",
            "https://image.tmdb.org/t/p/w500/hcNM0HjfPonIzOVy6LVTDBXSfMq.jpg",
        )
        sliderRecyclerView.adapter = ImageSliderAdaptor(this, list) { pos ->

        }

        withDelay(100) {
            sliderRecyclerView.smoothScrollToPosition(1)
            withDelay(100) {
                sliderRecyclerView.smoothScrollToPosition(0)
            }
        }
    }
}