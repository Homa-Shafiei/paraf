package shafiei.homa.paraf.feature.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.IntRange
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_movie_details.*
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.R
import shafiei.homa.paraf.feature.model.MovieResultModel
import shafiei.homa.paraf.utils.setImageLink


class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details), View.OnClickListener {

    private lateinit var movie: MovieResultModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable<MovieResultModel>("MOVIE") as MovieResultModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = movie.original_title
        overview.text = movie.overview
        image.setImageLink(AppSchema.instance.posterPath + movie.poster_path) {
            error(R.drawable.empty_picture)
        }
        rateCount.text = movie.vote_average.toString()

        btnBuy.setOnClickListener(this::onClick)
        btnPlus.setOnClickListener(this::onClick)
        btnMinus.setOnClickListener(this::onClick)
    }

    override fun onClick(view: View?) {
        when (view) {
            btnBuy -> {
                setTicket(1)
            }
            btnPlus -> {
                setTicket(tvBalance.text.toString().toInt() + 1)
            }
            btnMinus ->{
                setTicket(tvBalance.text.toString().toInt() - 1)
            }
        }
    }

    private fun setTicket(@IntRange(from = 1) ticketCount: Int) {
        if (ticketCount > 0)
            tvBalance.text = ticketCount.toString()

        btnPlus.isVisible = ticketCount > 0
        btnMinus.isVisible = ticketCount > 0
        flBalance.isVisible = ticketCount > 0
        btnBuy.isVisible = ticketCount <= 0
    }
}