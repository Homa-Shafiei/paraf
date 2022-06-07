package shafiei.homa.paraf.feature.repository

import retrofit2.Response
import retrofit2.http.Query
import shafiei.homa.paraf.feature.model.GenresResultModel
import shafiei.homa.paraf.feature.model.MovieModel
import shafiei.homa.paraf.network.Result

interface MovieRepository {

    suspend fun getUpcoming(@Query("api_key") apiKey: String): Result<MovieModel>

    suspend fun getCategories(@Query("api_key") apiKey: String): Result<GenresResultModel>

    suspend fun getPopular(@Query("api_key") apiKey: String): Result<MovieModel>

}