package com.example.firebase.data.dao

import com.example.firebase.data.firebase.FirebaseDAOFactory

abstract class DAOFactory {

    companion object {
        var factory: DAOFactory = FirebaseDAOFactory()
    }

    abstract fun getConcesionarioDAO(): ConcesionarioDAO
    abstract fun getCarroDAO(): CarroDAO

}