package shafiei.homa.paraf.network

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shafiei.homa.paraf.MyApplication
import java.io.File

abstract class RetrofitInstance {

    protected fun getRetrofit(): Retrofit = retrofit

    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            //setup cache
            val httpCacheDirectory =
                File(MyApplication.instance.applicationContext.cacheDir, "responses")
            val cacheSize = 10 * 1024 * 1024L // 10 MiB
            val cache = Cache(httpCacheDirectory, cacheSize)

            val client = OkHttpClient.Builder()
                .addInterceptor(ResponseInterceptor())
                .addInterceptor(ChuckInterceptor(MyApplication.instance))
                .addInterceptor(logging)
                .cache(cache)
                .build()

            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}