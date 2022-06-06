package shafiei.homa.paraf

class AppSchema {

    var apiKey: String = "0af4734d221b30b64a8f33b9b852d8ad"
    var posterPath: String = "https://image.tmdb.org/t/p/w500/"

    companion object {
        private var ourInstance: AppSchema? = AppSchema()
        val instance: AppSchema
            get() {
                if (ourInstance == null) {
                    ourInstance = AppSchema()
                }
                return ourInstance!!
            }

        fun clear() {
            ourInstance = null
        }
    }
}