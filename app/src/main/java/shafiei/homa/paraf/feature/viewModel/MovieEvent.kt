package shafiei.homa.paraf.feature.viewModel

import shafiei.homa.paraf.feature.model.GenresResultModel
import shafiei.homa.paraf.feature.model.UpcomingResultModel

sealed class MovieEvent {
    class OnUpcoming(val result : MutableList<UpcomingResultModel>) : MovieEvent()
    class OnCategory(val result : MutableList<GenresResultModel.CategoryModel>) : MovieEvent()
    class OnFullLoading(val isLoading: Boolean) : MovieEvent()
    class OnError(val error: String) : MovieEvent()
}