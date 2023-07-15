package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class ActualizarDepartamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_departamento)

        val idCancion = verDepartamentos.idCancionSeleccionada
        var cancion = DbDepartamento(null, "", "", "", "", 0, this)
        cancion = cancion.getCancionById(idCancion)

        var id = findViewById<EditText>(R.id.et_upd_iddepartamento)
        id.setText(cancion.getidDepartamento().toString())
        var nombre = findViewById<EditText>(R.id.et_upd_nombreDepartamento)
        nombre.setText(cancion.getnombreInquilino())
        var autor = findViewById<EditText>(R.id.et_upd_alquilerDepartamento)
        autor.setText(cancion.getAlquiler())
        var calificacion = findViewById<EditText>(R.id.et_upd_fechaContrato)
        calificacion.setText(cancion.getFechaContrato())
        var fecha = findViewById<EditText>(R.id.et_upd_estacionamiento)
        fecha.setText(cancion.getEstacionamiento())
        var fk = findViewById<EditText>(R.id.et_upd_id_edificio)
        fk.setText(cancion.getidEdificio().toString())

        val btn_actualizar_cancion = findViewById<Button>(R.id.btn_upd_EDIFICIO)
        btn_actualizar_cancion.setOnClickListener {
            cancion.setnombreInquilino(nombre.text.toString())
            cancion.setAlquiler(autor.text.toString())
            cancion.setFechaContrato(calificacion.text.toString())
            cancion.setEstacionamiento(fecha.text.toString())
            cancion.setidEdificio(fk.text.toString().toInt())
            val resultado = cancion.updateCancion()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        var id = findViewById<EditText>(R.id.et_upd_iddepartamento)
        id.setText("")
        var nombre = findViewById<EditText>(R.id.et_upd_nombreDepartamento)
        nombre.setText("")
        var autor = findViewById<EditText>(R.id.et_upd_alquilerDepartamento)
        autor.setText("")
        var calificacion = findViewById<EditText>(R.id.et_upd_fechaContrato)
        calificacion.setText("")
        var fecha = findViewById<EditText>(R.id.et_upd_estacionamiento)
        fecha.setText("")
        var fk = findViewById<EditText>(R.id.et_upd_id_edificio)
        fk.setText("")
        id.requestFocus()
    }
}