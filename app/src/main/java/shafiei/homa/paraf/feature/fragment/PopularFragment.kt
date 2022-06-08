package shafiei.homa.paraf.feature.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.werb.library.MoreAdapter
import kotlinx.android.synthetic.main.fragment_popular.*
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.adapter.PopularViewHolder
import shafiei.homa.paraf.feature.viewModel.MovieEvent
import shafiei.homa.paraf.feature.viewModel.MovieViewModel
import shafiei.homa.paraf.feature.viewModel.getMovieViewModel
import shafiei.homa.paraf.utils.EndlessRecyclerViewScrollListener
import shafiei.homa.paraf.utils.EventObserver


class PopularFragment : BaseFragment(R.layout.fragment_popular) {

    private val viewModel by activityViewModels<MovieViewModel> {
        requireActivity().getMovieViewModel()
    }

    private val popularAdapter by lazy {
        MoreAdapter()
    }

    private lateinit var endless: EndlessRecyclerViewScrollListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPopularAdapter()
        setRecyclerViewPagination()
        loadData()
        eventObserver()
        refreshList.setOnRefreshListener {
            popularAdapter.removeAllData()
            resetPaginationValues()
            refreshList.isRefreshing = true
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getPopular(endless.pageCounter)
    }

    private fun eventObserver() {
        viewModel.movieEvent.observe(this, EventObserver {
            when (it) {
                is MovieEvent.OnError -> {
                    hideLoading()
                }
                is MovieEvent.OnFullLoading -> {
                }
                is MovieEvent.OnPopular -> {
                    if (it.result.isNotEmpty()) {
                        hideLoading()
                        endless.isLoading = false
                        popularAdapter.loadData(it.result)
                    }
                }
                else -> {
                }
            }
        })
    }

    private fun setPopularAdapter() {
        popularAdapter.removeAllData()
        rvPopular.adapter = popularAdapter
        rvPopular.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            LinearLayoutManager.VERTICAL,
            false,
        )
        popularAdapter.apply {
            PopularViewHolder.register(this)
            attachTo(rvPopular)
        }
    }

    private fun resetPaginationValues() {
        endless.resetPaginationValues()
    }

    private fun setRecyclerViewPagination() {
        endless = object : EndlessRecyclerViewScrollListener(rvPopular.layoutManager!!) {
            override fun onLoadMore() {
                endless.isLoading = true
                endless.pageCounter++
                pagination.visibility = View.VISIBLE
                loadData()
            }

            override fun onScrolled(position: Int) {
            }
        }
        rvPopular.addOnScrollListener(endless)
    }

    private fun hideLoading() {
        refreshList.isRefreshing = false
        pagination.visibility = View.GONE
    }
}