package com.example.examen_ib

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbDepartamento(
    //Atributos
    private var idDepartamento: Int?,
    private var nombreInquilino: String,
    private var alquiler: String,
    private var fecha_contrato: String,
    private var estacionamiento: String,
    private var idEdificio: Int,
    private val context: Context?
) {
    init {
        nombreInquilino
        alquiler
        fecha_contrato
        estacionamiento
        idEdificio
        context
    }

    fun setidDepartamento(idDepartamento: Int?) {
        this.idDepartamento = idDepartamento
    }

    fun setnombreInquilino(nombreInquilino: String) {
        this.nombreInquilino = nombreInquilino
    }

    fun setAlquiler(alquiler: String) {
        this.alquiler = alquiler
    }

    fun setFechaContrato(fecha_contrato: String) {
        this.fecha_contrato = fecha_contrato
    }

    fun setEstacionamiento(estacionamiento: String) {
        this.estacionamiento = estacionamiento
    }

    fun setidEdificio(idEdificio: Int) {
        this.idEdificio = idEdificio
    }

    fun getidDepartamento(): Int? {
        return idDepartamento
    }

    fun getidEdificio(): Int {
        return idEdificio
    }

    fun getnombreInquilino(): String {
        return nombreInquilino
    }

    fun getAlquiler(): String {
        return alquiler
    }

    fun getFechaContrato(): String {
        return fecha_contrato
    }

    fun getEstacionamiento(): String {
        return estacionamiento
    }

    fun insertCancion(): Long {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_inquilino", this.nombreInquilino)
        values.put("alquiler", this.alquiler)
        values.put("fecha_contrato", this.fecha_contrato)
        values.put("estacionamiento", this.estacionamiento)
        values.put("IDEdificio", this.idEdificio)

        return db.insert("t_departamento", null, values)
    }

    fun showCanciones(id: Int): ArrayList<DbDepartamento> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var listaDepartamentos = ArrayList<DbDepartamento>()
        var departamento: DbDepartamento
        var cursorDepa: Cursor? = null

        // Ver si el id: Int es diferente de null
        cursorDepa = db.rawQuery("SELECT * FROM t_departamento WHERE idDepartamento=${id+1}", null)

        if (cursorDepa.moveToFirst()) {
            do {
                departamento = DbDepartamento(null, "", "", "", "", 0, null)

                departamento.setidDepartamento(cursorDepa.getString(0).toInt())
                departamento.setnombreInquilino(cursorDepa.getString(1))
                departamento.setAlquiler(cursorDepa.getString(2))
                departamento.setFechaContrato(cursorDepa.getString(3))
                departamento.setEstacionamiento(cursorDepa.getString(4))
                departamento.setidEdificio(cursorDepa.getString(5).toInt())
                listaDepartamentos.add(departamento)
            } while (cursorDepa.moveToNext())
        }

        cursorDepa.close()
        return listaDepartamentos
    }

    fun getCancionById(id: Int): DbDepartamento{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var departamento = DbDepartamento(null, "", "", "", "",0, this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_departamento WHERE idDepartamento = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                departamento.setidDepartamento(cursor.getString(0).toInt())
                departamento.setnombreInquilino(cursor.getString(1))
                departamento.setAlquiler(cursor.getString(2))
                departamento.setFechaContrato(cursor.getString(3))
                departamento.setEstacionamiento(cursor.getString(4))
                departamento.setidEdificio(cursor.getString(5).toInt())
            } while (cursor.moveToNext())
        }

        cursor.close()
        return departamento
    }

    fun updateCancion(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_inquilino", this.nombreInquilino)
        values.put("alquiler", this.alquiler)
        values.put("fecha_contrato", this.fecha_contrato)
        values.put("estacionamiento", this.estacionamiento)
        values.put("IDEdificio", this.idEdificio)

        return db.update("t_departamento", values, "idDepartamento="+this.idDepartamento, null)
    }

    fun deleteCancion(id: Int): Int{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete("t_departamento", "idDepartamento="+(id+1), null)
    }

    override fun toString(): String {
        val salida =
            "Núm. Departamento: ${idDepartamento}\n" +
                    "Nombre Inquilino:: ${nombreInquilino}\n" +
                    "Alquiler: ${alquiler}\n" +
                    "Fecha Contrato: ${fecha_contrato} \n" +
                    "¿Tiene estacionamiento?: ${estacionamiento}\n" +
                    "Núm. Edificio: ${idEdificio}"

        return salida
    }
}