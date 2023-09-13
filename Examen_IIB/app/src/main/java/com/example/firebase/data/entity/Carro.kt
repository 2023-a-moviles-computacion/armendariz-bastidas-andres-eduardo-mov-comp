package com.example.firebase.data.entity

import java.time.LocalDate

class Carro(
    val code: Int, //La id de clase secundaria (carro)
    val deviceCode: Int, //La id de clase principal (Concesionario)

    var marca: String,
    var fecha_elaboracion: String,
    var precio: String,
    var color_subjetivo: String,
    var meses_plazo_pagar: String

)