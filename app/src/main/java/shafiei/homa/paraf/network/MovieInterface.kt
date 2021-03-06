package shafiei.homa.paraf.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import shafiei.homa.paraf.feature.model.GenresResultModel
import shafiei.homa.paraf.feature.model.MovieModel


interface MovieInterface {

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): Response<MovieModel>

    @GET("genre/movie/list")
    suspend fun getCategories(@Query("api_key") apiKey: String): Response<GenresResultModel>

    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String,@Query("page") page: Int): Response<MovieModel>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") apiKey: String): Response<MovieModel>

}