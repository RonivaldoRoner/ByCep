package br.com.ronivaldoroner.bycep.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Endereco(
    var cep: String,
    var logradouro: String,
    var complemento: String,
    var bairro: String,
    var localidade: String,
    var uf: String,
    var unidade: String,
    var ibge: String,
    var gia: String
): Parcelable

