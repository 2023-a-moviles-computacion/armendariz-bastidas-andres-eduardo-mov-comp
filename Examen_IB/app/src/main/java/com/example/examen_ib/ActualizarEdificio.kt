package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class ActualizarEdificio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_edificio)

        val idAlbum = Edificio.idAlbumSeleccionado
        var album = DbEdificio(null, "", "", "", "", this)
        album = album.getAlbumById(idAlbum)

        var id = findViewById<EditText>(R.id.et_upd_idEdificio)
        id.setText(album.getidEdificio().toString())
        var nombre = findViewById<EditText>(R.id.et_upd_nombre)
        nombre.setText(album.getnombre())
        var autor = findViewById<EditText>(R.id.et_upd_direccion)
        autor.setText(album.getdireccion())
        var fecha = findViewById<EditText>(R.id.et_upd_agua)
        fecha.setText(album.getaguaPotable())
        var estado = findViewById<EditText>(R.id.et_upd_predio)
        estado.setText(album.getValorPredial())

        val btn_actualizarAlbum = findViewById<Button>(R.id.btn_upd_edificio)
        btn_actualizarAlbum.setOnClickListener {
            // Album actualizado
            album.setnombre(nombre.text.toString())
            album.setDireccion(autor.text.toString())
            album.setAguaPotable(fecha.text.toString())
            album.setvalorPredial(estado.text.toString())
            val resultado = album.updateAlbum()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        val id = findViewById<EditText>(R.id.et_upd_idEdificio)
        id.setText("")
        val nombre = findViewById<EditText>(R.id.et_upd_nombre)
        nombre.setText("")
        val autor = findViewById<EditText>(R.id.et_upd_direccion)
        autor.setText("")
        val fecha = findViewById<EditText>(R.id.et_upd_agua)
        fecha.setText("")
        val estado = findViewById<EditText>(R.id.et_upd_predio)
        estado.setText("")
        id.requestFocus()
    }

}