package com.example.examen_ib

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbEdificio(
    // Atributos
    private var idEdificio: Int?,
    private var nombre: String,
    private var direccion: String,
    private var aguaPotable: String,
    private var valorPredial: String,
    private val context: Context?
) {
    init {
        idEdificio
        nombre
        direccion
        aguaPotable
        valorPredial
        context
    }

    fun setidEdificio(idEdificio: Int) {
        this.idEdificio = idEdificio
    }

    fun setnombre(nombre: String) {
        this.nombre = nombre
    }

    fun setDireccion(direccion: String) {
        this.direccion = direccion
    }

    fun setAguaPotable(aguaPotable: String) {
        this.aguaPotable = aguaPotable
    }

    fun setvalorPredial(valorPredial: String) {
        this.valorPredial = valorPredial
    }

    fun getidEdificio(): Int? {
        return idEdificio
    }

    fun getnombre(): String {
        return nombre
    }

    fun getdireccion(): String {
        return direccion
    }

    fun getaguaPotable(): String {
        return aguaPotable
    }

    fun getValorPredial(): String {
        return valorPredial
    }

    fun insertAlbum(): Long {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre", this.nombre)
        values.put("direccion", this.direccion)
        values.put("agua_potable", this.aguaPotable)
        values.put("valor_predial", this.valorPredial)

        return db.insert("t_edificio", null, values)
    }

    fun showAlbumes(): ArrayList<DbEdificio> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var lista = ArrayList<DbEdificio>()
        var edificio: DbEdificio
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_edificio", null)

        if (cursor.moveToFirst()) {
            do {
                edificio = DbEdificio(null, "", "", "", "", null)

                edificio.setidEdificio(cursor.getString(0).toInt())
                edificio.setnombre(cursor.getString(1))
                edificio.setDireccion(cursor.getString(2))
                edificio.setAguaPotable(cursor.getString(3))
                edificio.setvalorPredial(cursor.getString(4))
                lista.add(edificio)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return lista
    }

    fun getAlbumById(id: Int): DbEdificio{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var edificio = DbEdificio(null, "", "", "", "", this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_edificio WHERE idEdificio = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                edificio.setidEdificio(cursor.getString(0).toInt())
                edificio.setnombre(cursor.getString(1))
                edificio.setDireccion(cursor.getString(2))
                edificio.setAguaPotable(cursor.getString(3))
                edificio.setvalorPredial(cursor.getString(4))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return edificio
    }

    fun updateAlbum(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre", this.nombre)
        values.put("direccion", this.direccion)
        values.put("agua_potable", this.aguaPotable)
        values.put("valor_predial", this.valorPredial)

        return db.update("t_edificio", values, "idEdificio="+this.idEdificio, null)
    }

    fun deleteAlbum(id: Int): Int{
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete("t_edificio", "idEdificio="+(id+1), null)
    }

    override fun toString(): String {
        val salida =
            "Núm. edificio: ${idEdificio}\n" +
                    "Nombre: ${nombre}\n" +
                    "Dirección: ${direccion}\n" +
                    "¿Tiene Agua Potable?: ${aguaPotable}\n" +
                    "Valor predial: ${valorPredial}"

        return salida
    }
}