package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*


class Departamento : AppCompatActivity() {
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento)

        val nombre = findViewById<EditText>(R.id.editText_nombreInquilino)
        nombre.requestFocus()
        val autor = findViewById<EditText>(R.id.editText_alquiler)
        val calificacion = findViewById<EditText>(R.id.editText_fechaContrato)
        val fecha = findViewById<EditText>(R.id.editText_estacionamiento)
        val idAlbum = findViewById<EditText>(R.id.editText_idEdificio)

        val btnInsertar = findViewById<Button>(R.id.btn_insert)
        btnInsertar.setOnClickListener {
            val cancion: DbDepartamento = DbDepartamento(
                null,
                nombre.text.toString(),
                autor.text.toString(),
                calificacion.text.toString(),
                fecha.text.toString(),
                idAlbum.text.toString().toInt(),
                this
            )
            val resultado = cancion.insertCancion()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        val nombre = findViewById<EditText>(R.id.editText_nombreInquilino)
        nombre.setText("")
        val autor = findViewById<EditText>(R.id.editText_alquiler)
        autor.setText("")
        val calificacion = findViewById<EditText>(R.id.editText_fechaContrato)
        calificacion.setText("")
        val fecha = findViewById<EditText>(R.id.editText_estacionamiento)
        fecha.setText("")
        val idAlbum = findViewById<EditText>(R.id.editText_idEdificio)
        idAlbum.setText("")
        nombre.requestFocus()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_departamento, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_edificio -> {
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar_edificio -> {
                //abrirDialogo()
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}