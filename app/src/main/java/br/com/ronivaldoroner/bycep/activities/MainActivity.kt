package br.com.ronivaldoroner.bycep.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ronivaldoroner.bycep.R
import br.com.ronivaldoroner.bycep.entities.Endereco
import br.com.ronivaldoroner.bycep.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var addressView: Endereco

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(MainViewModel::class.java)

        startViewModelObservers()

        btnSearch.setOnClickListener {
            mainViewModel.getAddress(edtCep.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_ADDRESS, addressView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val recover = savedInstanceState.getParcelable<Endereco>(EXTRA_ADDRESS)
        if (recover != null)
            showEndereco(recover)
    }

    fun startViewModelObservers() {
        mainViewModel.error.observe(this, Observer<String> { toast(it) })
        mainViewModel.endereco.observe(this, Observer<Endereco> { showEndereco(it) })
        mainViewModel.loadingVisibility.observe(this, Observer<Boolean> { showLoading(it) })
    }

    fun showEndereco(endereco: Endereco) {
        txtStreet.text = endereco.logradouro
        txtComp.text = endereco.complemento
        txtNeighborhood.text = endereco.bairro
        txtCity.text = endereco.localidade
        txtUf.text = endereco.uf
        txtUnity.text = endereco.unidade
        txtIBGE.text = endereco.ibge
        txtGia.text = endereco.gia
        addressView = endereco
    }

    fun showLoading(show: Boolean) {
        pbLoading.isVisible = show
    }

    fun toast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        const val EXTRA_ADDRESS = "lastAddress"
    }
}
