package com.example.examen_ib

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog


class Edificio : AppCompatActivity() {
    companion object{
        var idAlbumSeleccionado = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edificio)
        showListViewAlbum()

        val nombre = findViewById<EditText>(R.id.editText_nombreedificio)
        nombre.requestFocus()
        val autor = findViewById<EditText>(R.id.editText_direccionEdificio)
        val fecha = findViewById<EditText>(R.id.editText_agua)
        val estadoFav = findViewById<EditText>(R.id.editText_predio)

        val btnInsertar = findViewById<Button>(R.id.btn_insertarAlb)
        btnInsertar.setOnClickListener {
            val album = DbEdificio(
                null,
                nombre.text.toString(),
                autor.text.toString(),
                fecha.text.toString(),
                estadoFav.text.toString(),
                this
            )
            val resultado = album.insertAlbum()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewAlbum()
            } else {
                Toast.makeText(this, "ERROR EN INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        val nombre = findViewById<EditText>(R.id.editText_nombreedificio)
        nombre.setText("")
        val autor = findViewById<EditText>(R.id.editText_direccionEdificio)
        autor.setText("")
        val fecha = findViewById<EditText>(R.id.editText_agua)
        fecha.setText("")
        val estado = findViewById<EditText>(R.id.editText_predio)
        estado.setText("")
        nombre.requestFocus()
    }

    fun showListViewAlbum() {
        // ListView Canciones
        val album = DbEdificio(null, "", "", "", "", this)
        val listView = findViewById<ListView>(R.id.listview_album)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            album.showAlbumes()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_edificio, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idAlbumSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_edificio -> {
                irActividad(ActualizarEdificio::class.java)
                return true
            }
            R.id.mi_eliminar_edificio -> {
                abrirDialogo()
                return true
            }
            R.id.mi_verdepartamentos -> {
                irActividad(verDepartamentos::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar este álbum?")
        builder.setPositiveButton(
            "SI",
            DialogInterface.OnClickListener { dialog, which ->
                val album = DbEdificio(null, "", "", "", "", this)
                val resultado = album.deleteAlbum(idAlbumSeleccionado)
                if (resultado > 0) {
                    Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                    showListViewAlbum()
                } else {
                    Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                }
            }
        )
        builder.setNegativeButton(
            "NO",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}