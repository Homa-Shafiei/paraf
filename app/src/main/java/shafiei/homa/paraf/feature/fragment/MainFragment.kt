package shafiei.homa.paraf.feature.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.werb.library.MoreAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.adapter.CategoryViewHolder
import shafiei.homa.paraf.feature.adapter.ImageSliderAdaptor
import shafiei.homa.paraf.feature.adapter.PopularViewHolder
import shafiei.homa.paraf.feature.adapter.TopRatedViewHolder
import shafiei.homa.paraf.feature.model.MovieResultModel
import shafiei.homa.paraf.feature.viewModel.MovieEvent
import shafiei.homa.paraf.feature.viewModel.MovieViewModel
import shafiei.homa.paraf.feature.viewModel.getMovieViewModel
import shafiei.homa.paraf.utils.CenterZoomLayoutManager
import shafiei.homa.paraf.utils.EventObserver
import shafiei.homa.paraf.utils.getRecyclerViewLinearSnapHelper
import shafiei.homa.paraf.utils.withDelay


class MainFragment : BaseFragment(R.layout.fragment_main), View.OnClickListener {

    private val viewModel by activityViewModels<MovieViewModel> {
        requireActivity().getMovieViewModel()
    }

    private val categoryAdapter by lazy {
        MoreAdapter()
    }

    private val popularAdapter by lazy {
        MoreAdapter()
    }

    private val topRatedAdapter by lazy {
        MoreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        eventObserver()
        setupUi()
    }


    private fun loadData() {
        viewModel.getUpcoming()
        viewModel.getCategories()
        viewModel.getPopular()
        viewModel.getTopRated()
    }

    private fun setupUi() {
        morePopular.setOnClickListener(this::onClick)
        moreTopRated.setOnClickListener(this::onClick)
    }

    private fun eventObserver() {
        viewModel.movieEvent.observe(this, EventObserver {
            when (it) {
                is MovieEvent.OnError -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
                is MovieEvent.OnFullLoading -> {
                    loading.isVisible = it.isLoading
                }
                is MovieEvent.OnUpcoming -> {
                    setBannerSlider(it.result.take(5).toMutableList())
                }
                is MovieEvent.OnCategory -> {
                    if (it.result.isNotEmpty()) {
                        setCategoryAdapter()
                        categoryAdapter.loadData(it.result)
                    }
                }
                is MovieEvent.OnPopular -> {
                    if (it.result.isNotEmpty()) {
                        setPopularAdapter()
                        popularAdapter.loadData(it.result)
                    }
                }
                is MovieEvent.OnTopRated -> {
                    if (it.result.isNotEmpty()) {
                        setTopRatedAdapter()
                        topRatedAdapter.loadData(it.result)
                    }
                }
            }
        })
    }

    //region banner
    private fun setBannerSlider(upcomingList: MutableList<MovieResultModel>) {

        val centerZoomLayoutManager =
            CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

        sliderRecyclerView.adapter = ImageSliderAdaptor(requireContext(), upcomingList) { pos -> }

        withDelay(100) {
            sliderRecyclerView.smoothScrollToPosition(1)
            withDelay(100) {
                sliderRecyclerView.smoothScrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            while (isActive) {
                delay(4000)
                if (sliderRecyclerView != null) {
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
                } else {
                    cancel()
                }
            }
        }.start()
    }
    //endregion

    //region Category
    private fun setCategoryAdapter() {
        categoryAdapter.removeAllData()
        rvCategory.adapter = categoryAdapter
        rvCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter.apply {
            CategoryViewHolder.register(this)
            attachTo(rvCategory)
        }
    }
    //endregion

    //region Popular
    private fun setPopularAdapter() {
        popularAdapter.removeAllData()
        rvPopular.adapter = popularAdapter
        rvPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularAdapter.apply {
            PopularViewHolder.register(this, onItemClick = { view, item ->
                val bundle = bundleOf(
                    "MOVIE" to item,
                )
                findNavController().navigate(
                    R.id.action_mainFragment_to_movieDetailsFragment,
                    args = bundle
                )
            })
            attachTo(rvPopular)
        }
    }
    //endregion

    // region Popular
    private fun setTopRatedAdapter() {
        topRatedAdapter.removeAllData()
        rvTopRated.adapter = topRatedAdapter
        rvTopRated.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        topRatedAdapter.apply {
            TopRatedViewHolder.register(this, onItemClick = { view, item ->
                findNavController().navigate(R.id.action_mainFragment_to_movieDetailsFragment)
            })
            attachTo(rvTopRated)
        }
    }
    //endregion

    //region event
    override fun onClick(view: View?) {
        when (view) {
            morePopular -> findNavController().navigate(R.id.action_mainFragment_to_popularFragment)
            moreTopRated -> findNavController().navigate(R.id.action_mainFragment_to_popularFragment)
        }
    }
    //endregion
}