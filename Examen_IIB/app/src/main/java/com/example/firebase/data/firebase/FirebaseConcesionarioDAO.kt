package com.example.firebase.data.firebase

import com.example.firebase.data.dao.ConcesionarioDAO
import com.example.firebase.data.entity.Concesionario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class FirebaseConcesionarioDAO: ConcesionarioDAO {

    private val db = Firebase.firestore
    private val concesionariosCollectionReference = db.collection("Edificios")

    override fun getAllConcesionarios(onSuccess: (ArrayList<Concesionario>) -> Unit) {
        concesionariosCollectionReference
            .get()
            .addOnSuccessListener { documents ->
                val concesionarios = ArrayList<Concesionario>()


                for (document in documents) {
                    concesionarios.add(
                        Concesionario(
                            code = document.id.split("/").last().toInt(),
                            nombre = document.getString("nombre")!!,
                            fecha_inaguracion = LocalDate.parse(document.getString("fecha_inaguracion")!!),
                            porcentaje_personas_satisfechas = document.getDouble("predial")!!,
                            cantidad_empleados =  document.getDouble("numero_departamentos")!!.toInt()
                        )

                    )
                }

                onSuccess(concesionarios)
            }
    }

    override fun create(entity: Concesionario) {
        val concesionario = hashMapOf(
            "nombre" to entity.nombre,
            "fecha_inaguracion" to entity.fecha_inaguracion.toString(),
            "predial" to entity.porcentaje_personas_satisfechas,
            "numero_departamentos" to entity.cantidad_empleados
        )

        concesionariosCollectionReference.document(entity.code.toString()).set(concesionario)
    }

    override fun read(code: Int, onSuccess: (Concesionario) -> Unit) {
        val concesionarioReference = concesionariosCollectionReference.document(code.toString())

        concesionarioReference
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val concesionario = Concesionario(
                        code,
                        document.data!!["nombre"].toString(),
                        LocalDate.parse(document.data!!["fecha_inaguracion"].toString()),
                        document.data!!["numero_departamentos"].toString().toDouble(),
                        document.data!!["predial"].toString().toDouble().toInt()
                    )

                    onSuccess(concesionario)
                }
            }
    }

    override fun update(entity: Concesionario) {
        val concesionario = hashMapOf(
            "nombre" to entity.nombre,
            "fecha_inaguracion" to entity.fecha_inaguracion.toString(),
            "numero_departamentos" to entity.porcentaje_personas_satisfechas,
            "predial" to entity.cantidad_empleados
        )

        concesionariosCollectionReference.document(entity.code.toString()).set(concesionario)
    }

    override fun delete(code: Int, onSuccess: (Unit) -> Unit) {
        val concesionarioReference = concesionariosCollectionReference.document(code.toString())

        concesionarioReference.delete().addOnSuccessListener {
            onSuccess(Unit)
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