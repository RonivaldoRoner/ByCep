package br.com.ronivaldoroner.bycep.dao

import br.com.ronivaldoroner.bycep.entities.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CepInterface {

    @GET("ws/{cep}/json/")
    suspend fun getAddress(@Path("cep")cep: String): Response<Endereco>
}