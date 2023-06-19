package com.example.movilescomp2023a

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Andres","a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Armendariz","b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Eduardo","c@c.com")
                )
        }
    }
}