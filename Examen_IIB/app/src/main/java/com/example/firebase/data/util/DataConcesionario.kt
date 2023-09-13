package com.example.firebase.data.util

import com.example.firebase.data.entity.Concesionario
import java.time.LocalDate

class DataConcesionario {

    companion object {
        var concesionariosData = ArrayList<Concesionario>()

        init {
            concesionariosData.add(
                Concesionario(
                    177452, "Metropolitan",  LocalDate.parse("2018-02-21"), 459.4,47
                )
            )
        }
    }

}