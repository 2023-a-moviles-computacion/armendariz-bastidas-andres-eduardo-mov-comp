package com.example.firebase.data.entity

import java.time.LocalDate


class Concesionario(
    val code: Int, //La id de clase principal (Concesionario)

    var nombre: String,
    var fecha_inaguracion: LocalDate,
    var porcentaje_personas_satisfechas: Double,
    var cantidad_empleados: Int
)