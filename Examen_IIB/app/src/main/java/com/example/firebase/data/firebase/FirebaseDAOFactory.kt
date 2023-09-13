package com.example.firebase.data.firebase

import com.example.firebase.data.dao.CarroDAO
import com.example.firebase.data.dao.DAOFactory
import com.example.firebase.data.dao.ConcesionarioDAO

class FirebaseDAOFactory: DAOFactory() {

    override fun getConcesionarioDAO(): ConcesionarioDAO {
        return FirebaseConcesionarioDAO()
    }

    override fun getCarroDAO(): CarroDAO {
        return FirebaseCarroDAO()
    }

}