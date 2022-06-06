package shafiei.homa.paraf.network

class ServiceImpl : BaseService() {

    fun movieService(): MovieInterface =
        getRetrofit().create(MovieInterface::class.java)

}