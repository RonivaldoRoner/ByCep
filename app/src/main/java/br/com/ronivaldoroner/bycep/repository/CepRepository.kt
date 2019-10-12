package br.com.ronivaldoroner.bycep.repository

import br.com.ronivaldoroner.bycep.dao.CepInterface
import br.com.ronivaldoroner.bycep.entities.Endereco
import retrofit2.Response

class CepRepository (private val cepInterface: CepInterface){

    suspend fun getAddress(cep: String): Response<Endereco>{
        return cepInterface.getAddress(cep)
    }

}