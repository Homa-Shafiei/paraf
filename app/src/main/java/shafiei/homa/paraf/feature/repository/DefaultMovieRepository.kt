package shafiei.homa.paraf.feature.repository

import shafiei.homa.paraf.feature.model.UpcomingModel
import shafiei.homa.paraf.network.Result
import shafiei.homa.paraf.network.ServiceImpl

class DefaultMovieRepository : MovieRepository {

    override suspend fun getUpcoming(apiKey: String): Result<UpcomingModel> {
        return ServiceImpl().apiCall(
            call = {
                ServiceImpl()
                    .movieService()
                    .getUpcoming(apiKey)
            })
    }
}