package shafiei.homa.paraf.feature.repository

import shafiei.homa.paraf.feature.model.GenresResultModel
import shafiei.homa.paraf.feature.model.MovieModel
import shafiei.homa.paraf.network.Result
import shafiei.homa.paraf.network.ServiceImpl

class DefaultMovieRepository : MovieRepository {

    override suspend fun getUpcoming(apiKey: String): Result<MovieModel> {
        return ServiceImpl().apiCall(
            call = {
                ServiceImpl()
                    .movieService()
                    .getUpcoming(apiKey)
            })
    }

    override suspend fun getCategories(apiKey: String): Result<GenresResultModel> {
        return ServiceImpl().apiCall(
            call = {
                ServiceImpl()
                    .movieService()
                    .getCategories(apiKey)
            })
    }

    override suspend fun getPopular(apiKey: String, page: Int): Result<MovieModel> {
        return ServiceImpl().apiCall(
            call = {
                ServiceImpl()
                    .movieService()
                    .getPopular(apiKey, page)
            })
    }

    override suspend fun getTopRated(apiKey: String): Result<MovieModel> {
        return ServiceImpl().apiCall(
            call = {
                ServiceImpl()
                    .movieService()
                    .getTopRated(apiKey)
            })
    }
}