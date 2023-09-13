package com.example.firebase.data.dao

import com.example.firebase.data.entity.Concesionario

interface ConcesionarioDAO: GenericDAO<Concesionario, Int> {

    fun getAllConcesionarios(onSuccess: (ArrayList<Concesionario>) -> Unit)

}