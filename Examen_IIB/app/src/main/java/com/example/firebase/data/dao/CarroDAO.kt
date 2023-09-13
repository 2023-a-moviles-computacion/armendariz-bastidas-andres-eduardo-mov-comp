package com.example.firebase.data.dao

import com.example.firebase.data.entity.Carro

interface CarroDAO: GenericDAO<Carro, Int> {

    fun getAllCarrosByCodeCar(concesionarioCode: Int,onSuccess: (ArrayList<Carro>) -> Unit)

}