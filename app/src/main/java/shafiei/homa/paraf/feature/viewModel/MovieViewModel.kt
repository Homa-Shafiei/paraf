package shafiei.homa.paraf.feature.viewModel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.feature.repository.DefaultMovieRepository
import shafiei.homa.paraf.feature.repository.MovieRepository
import shafiei.homa.paraf.utils.Event
import shafiei.homa.paraf.utils.defaultSavedStateViewModelFactory
import shafiei.homa.paraf.network.Result

class MovieViewModel(
    savedState: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    private val _onMovieEvent = MutableLiveData<Event<MovieEvent>>()
    val movieEvent : LiveData<Event<MovieEvent>> = _onMovieEvent

    fun getUpcoming() {
        viewModelScope.launch {
            _onMovieEvent.postValue(Event(MovieEvent.OnFullLoading(true)))
            val result = repository.getUpcoming(AppSchema.instance.apiKey)
            when (result) {
                is Result.Error -> {
                    _onMovieEvent.postValue(Event(MovieEvent.OnFullLoading(false)))
                    _onMovieEvent.postValue(Event(MovieEvent.OnError(result.error)))
                }
                is Result.Success -> {
                    _onMovieEvent.postValue(Event(MovieEvent.OnFullLoading(false)))
                    _onMovieEvent.postValue(Event(MovieEvent.OnUpcoming(result.data.results)))
                }
            }
        }
    }

}

fun AppCompatActivity.getMovieViewModel(
    repository: MovieRepository = DefaultMovieRepository()
) = defaultSavedStateViewModelFactory(this) { savedState ->
    MovieViewModel(savedState, repository)
}