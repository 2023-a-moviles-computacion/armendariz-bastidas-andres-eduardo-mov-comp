package com.example.examen_ib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "Examen.db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEdificio =
            "CREATE TABLE t_edificio(" +
                    "idEdificio INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "direccion TEXT NOT NULL," +
                    "agua_potable TEXT NOT NULL," +
                    "valor_predial TEXT NOT NULL);"

        val scriptSQLCrearTablaDepartamento =
            "CREATE TABLE t_departamento(" +
                    "idDepartamento INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre_inquilino TEXT NOT NULL," +
                    "alquiler TEXT NOT NULL," +
                    "fecha_contrato TEXT NOT NULL," +
                    "estacionamiento TEXT NOT NULL, " +
                    "IDEdificio INTEGER NOT NULL," +
                    "FOREIGN KEY(IDEdificio) REFERENCES t_edificio(idEdificio));"

        db?.execSQL(scriptSQLCrearTablaEdificio)
        db?.execSQL(scriptSQLCrearTablaDepartamento)
    }

    // Se ejecuta cuando la version cambia
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS t_cancion")
        db?.execSQL("DROP TABLE IF EXISTS t_departamento")
        onCreate(db)
    }
}