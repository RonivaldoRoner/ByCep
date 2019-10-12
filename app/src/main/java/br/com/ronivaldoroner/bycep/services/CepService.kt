package br.com.ronivaldoroner.bycep.services

import br.com.ronivaldoroner.bycep.dao.CepInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CepService {

    fun getCepService(): CepInterface{
        val BASE_URL = "https://viacep.com.br/"
        val loggin = HttpLoggingInterceptor()

        loggin.level = HttpLoggingInterceptor.Level.HEADERS

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggin)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit.create(CepInterface::class.java)
    }

}