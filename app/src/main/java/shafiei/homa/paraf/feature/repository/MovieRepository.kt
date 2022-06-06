package shafiei.homa.paraf.feature.repository

import retrofit2.http.Query
import shafiei.homa.paraf.feature.model.UpcomingModel
import shafiei.homa.paraf.network.Result

interface MovieRepository {

    suspend fun getUpcoming(@Query("api_key") apiKey: String): Result<UpcomingModel>

}