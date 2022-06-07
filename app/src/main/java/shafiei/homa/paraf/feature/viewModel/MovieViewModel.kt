package shafiei.homa.paraf.feature.viewModel

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import shafiei.homa.paraf.AppSchema
import shafiei.homa.paraf.feature.repository.DefaultMovieRepository
import shafiei.homa.paraf.feature.repository.MovieRepository
import shafiei.homa.paraf.network.Result
import shafiei.homa.paraf.utils.Event
import shafiei.homa.paraf.utils.defaultSavedStateViewModelFactory

class MovieViewModel(
    savedState: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    private val _onMovieEvent = MutableLiveData<Event<MovieEvent>>()
    val movieEvent : LiveData<Event<MovieEvent>> = _onMovieEvent

    fun getUpcoming() {
        viewModelScope.launch {
            _onMovieEvent.value = Event(MovieEvent.OnFullLoading(true))
            val result = repository.getUpcoming(AppSchema.instance.apiKey)
            when (result) {
                is Result.Error -> {
                    _onMovieEvent.value = Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value = Event(MovieEvent.OnError(result.error))
                }
                is Result.Success -> {
                    _onMovieEvent.value = Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value = Event(MovieEvent.OnUpcoming(result.data.results))
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _onMovieEvent.value = Event(MovieEvent.OnFullLoading(true))
            val result = repository.getCategories(AppSchema.instance.apiKey)
            when (result) {
                is Result.Error -> {
                    _onMovieEvent.value = Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value = Event(MovieEvent.OnError(result.error))
                }
                is Result.Success -> {
                    _onMovieEvent.value = Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value = Event(MovieEvent.OnCategory(result.data.genres))
                }
            }
        }
    }

    fun getPopular() {
        viewModelScope.launch {
            _onMovieEvent.value = Event(MovieEvent.OnFullLoading(true))
            val result = repository.getPopular(AppSchema.instance.apiKey)
            when (result) {
                is Result.Error -> {
                    _onMovieEvent.value= Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value= Event(MovieEvent.OnError(result.error))
                }
                is Result.Success -> {
                    _onMovieEvent.value= Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value= Event(MovieEvent.OnPopular(result.data.results))
                }
            }
        }
    }

    fun getTopRated() {
        viewModelScope.launch {
            _onMovieEvent.value = Event(MovieEvent.OnFullLoading(true))
            val result = repository.getTopRated(AppSchema.instance.apiKey)
            when (result) {
                is Result.Error -> {
                    _onMovieEvent.value= Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value= Event(MovieEvent.OnError(result.error))
                }
                is Result.Success -> {
                    _onMovieEvent.value= Event(MovieEvent.OnFullLoading(false))
                    _onMovieEvent.value= Event(MovieEvent.OnTopRated(result.data.results))
                }
            }
        }
    }

}

fun ComponentActivity.getMovieViewModel(
    repository: MovieRepository = DefaultMovieRepository()
) = defaultSavedStateViewModelFactory(this) { savedState ->
    MovieViewModel(savedState, repository)
}