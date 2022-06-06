package shafiei.homa.paraf.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import shafiei.homa.paraf.feature.model.GenresResultModel
import shafiei.homa.paraf.feature.model.UpcomingModel


interface MovieInterface {

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): Response<UpcomingModel>

    @GET("genre/movie/list")
    suspend fun getCategories(@Query("api_key") apiKey: String): Response<GenresResultModel>

}