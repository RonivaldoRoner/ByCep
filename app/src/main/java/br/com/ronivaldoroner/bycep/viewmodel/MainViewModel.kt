package br.com.ronivaldoroner.bycep.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.com.ronivaldoroner.bycep.R
import br.com.ronivaldoroner.bycep.entities.Endereco
import br.com.ronivaldoroner.bycep.repository.CepRepository
import br.com.ronivaldoroner.bycep.services.CepService
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    val loadingVisibility = MutableLiveData<Boolean>()
    val endereco = MutableLiveData<Endereco>()
    private val message = MutableLiveData<String>()
    val error: LiveData<String> get() = Transformations.map(message) { it.toString() }
    private val repository: CepRepository

    init {
        val service = CepService()
        val cepService = service.getCepService()
        repository = CepRepository(cepService)
    }

    fun getAddress(cep: String) = viewModelScope.launch {
        loadingVisibility.postValue(true)

        try {
            if (validateCep(cep)) {
                val result = repository.getAddress(cep)

                if (result.isSuccessful) {
                    if (result.body() != null && hasEndereco(result.body())) {
                        endereco.postValue(result.body())
                    } else {
                        message.value =
                            getApplication<Application>().getString(R.string.address_empty)
                    }
                } else {
                    message.value = getApplication<Application>().getString(R.string.address_failed)
                }
            } else {
                message.value = getApplication<Application>().getString(R.string.length_cod)
            }

        } catch (ex: Exception) {
            message.value = ex.message
        }
        loadingVisibility.postValue(false)
    }

    private fun validateCep(cep: String?): Boolean {
        return cep?.length == 8
    }

    private fun hasEndereco(endereco: Endereco?): Boolean {
        return !endereco?.cep.isNullOrEmpty()
    }
}