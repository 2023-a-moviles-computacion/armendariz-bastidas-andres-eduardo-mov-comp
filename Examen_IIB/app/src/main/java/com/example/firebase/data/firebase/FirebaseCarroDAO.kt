package com.example.firebase.data.firebase

import com.example.firebase.data.dao.CarroDAO
import com.example.firebase.data.dao.DAOFactory
import com.example.firebase.data.entity.Carro
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class FirebaseCarroDAO: CarroDAO {

    private val db = Firebase.firestore
    private val concesionarioCollectionReference = db.collection("Edificios")

    override fun getAllCarrosByCodeCar(
        concesionarioCode: Int,
        onSuccess: (ArrayList<Carro>) -> Unit
    ) {
        concesionarioCollectionReference
            .document(concesionarioCode.toString())
            .collection("Departamentos")
            .get()
            .addOnSuccessListener { documents ->
                val carros = ArrayList<Carro>()

                for (document in documents) {
                    carros.add(
                        Carro(
                            code = document.id.split("/").last().toInt(),
                            deviceCode = concesionarioCode,
                            marca = document.getString("marca")!!,
                            fecha_elaboracion = document.getString("fecha_elaboracion")!!,
                            precio = document.getString("precio")!!,
                            color_subjetivo = document.getString("color_subjetivo")!!,
                            meses_plazo_pagar = document.getString("meses_plazo_pagar")!!
                        )
                    )
                }

                onSuccess(carros)
            }
    }

    override fun create(entity: Carro) {
        val carro = hashMapOf(
            "marca" to entity.marca,
            "fecha_elaboracion" to  entity.fecha_elaboracion.toString(),
            "precio" to entity.precio,
            "color_subjetivo" to entity.color_subjetivo,
            "meses_plazo_pagar" to entity.meses_plazo_pagar,
        )

        concesionarioCollectionReference
            .document(entity.deviceCode.toString())
            .collection("Departamentos")
            .document(entity.code.toString()).set(carro)
    }

    override fun read(code: Int, onSuccess: (Carro) -> Unit) {
        DAOFactory.factory.getConcesionarioDAO().getAllConcesionarios { documents ->
            for (document in documents) {
                val db = Firebase.firestore
                val carroCollectionReference = db.collection(
                    "Edificios/${document.code}/Departamentos"
                )

                carroCollectionReference
                    .get()
                    .addOnSuccessListener { documentsCarros ->
                        for (documentCarro in documentsCarros) {
                            if (documentCarro.id.toInt() == code) {
                                onSuccess(
                                    Carro(
                                        documentCarro.id.toInt(),
                                        document.code,
                                        documentCarro.getString("marca")!!,
                                        documentCarro.getString("fecha_elaboracion")!!,
                                        documentCarro.getString("precio")!!,
                                        documentCarro.getString("color_subjetivo")!!,
                                        documentCarro.getString("meses_plazo_pagar")!!
                                    )
                                )
                            }
                        }
                    }
            }
        }
    }

    override fun update(entity: Carro) {
        val carro = hashMapOf(
            "marca" to entity.marca,
            "fecha_elaboracion" to  entity.fecha_elaboracion.toString(),
            "precio" to entity.precio,
            "color_subjetivo" to entity.color_subjetivo,
            "meses_plazo_pagar" to entity.meses_plazo_pagar,
        )

        concesionarioCollectionReference
            .document(entity.deviceCode.toString())
            .collection("Departamentos")
            .document(entity.code.toString()).set(carro)
    }

    override fun delete(code: Int, onSuccess: (Unit) -> Unit) {
        DAOFactory.factory.getConcesionarioDAO().getAllConcesionarios { documents ->
            for (document in documents) {
                val db = Firebase.firestore
                val carroCollectionReference = db.collection(
                    "Edificios/${document.code}/Departamentos"
                )

                carroCollectionReference
                    .get()
                    .addOnSuccessListener { documentsCarros ->
                        for (documentCarro in documentsCarros) {
                            if (documentCarro.id.toInt() == code) {
                                val carroReference = carroCollectionReference
                                    .document(code.toString())

                                carroReference.delete().addOnSuccessListener {
                                    onSuccess(Unit)
                                }
                            }
                        }
                    }
            }
        }
    }

    override fun getRandomCode(onSuccess: (Int) -> Unit) {
        var identificador = Date().time.toInt()
        if(identificador<0){
            identificador*=-1
        }
        onSuccess(identificador)
    }

}