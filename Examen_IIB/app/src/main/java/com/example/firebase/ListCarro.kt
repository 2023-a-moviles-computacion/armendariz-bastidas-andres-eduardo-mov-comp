package com.example.firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.dao.DAOFactory
import com.example.firebase.data.entity.Carro

class ListCarro : AppCompatActivity() {
    private var selectedCarroCode: Int? = null
    private var selectedConcesionarioCode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_carro)

        selectedConcesionarioCode = intent.getIntExtra("selectedConcesionarioCode", 0)

        val componentRecyclerView = findViewById<RecyclerView>(R.id.rv_carro)
        val deviceCodeTextView = findViewById<TextView>(R.id.tv_parent_concesionario_code)
        val deviceNameTextView = findViewById<TextView>(R.id.tv_parent_concesionario_nombre)
        val creationButton = findViewById<Button>(R.id.btn_create_carro)

        // Setting component device code when it is ready
        DAOFactory.factory.getConcesionarioDAO().read(
            selectedConcesionarioCode!!,
            onSuccess = { device ->
                deviceCodeTextView.text = device.code.toString()
                deviceNameTextView.text = device.nombre
            }
        )

        // Setting the Recycler View when the data is ready
        DAOFactory.factory.getCarroDAO().getAllCarrosByCodeCar(
            selectedConcesionarioCode!!,
            onSuccess = { components ->
                initializeRecyclerView(components, componentRecyclerView)
                registerForContextMenu(componentRecyclerView)
            }
        )

        creationButton.setOnClickListener {
            val intent = Intent(this, CreationCarro::class.java)
            intent.putExtra("selectedConcesionarioCode", selectedConcesionarioCode)
            startActivity(intent)
        }
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_carro_edit -> {
                val intent = Intent(this, EditionCarro::class.java)
                intent.putExtra("selectedCarroCode", selectedCarroCode)
                startActivity(intent)
                return true
            }

            R.id.menu_carro_delete -> {
                openDeleteDialog()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun setSelectedComponentCode(carroCode: Int) {
        selectedCarroCode = carroCode
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Estas seguro de eliminar este departamento?")

        builder.setPositiveButton("Yes") { _, _ ->
            DAOFactory.factory.getCarroDAO().delete(
                selectedCarroCode!!,
                onSuccess = {
                    DAOFactory.factory.getCarroDAO().getAllCarrosByCodeCar(
                        selectedConcesionarioCode!!,
                        onSuccess = { components ->
                            initializeRecyclerView(components, findViewById(R.id.rv_carro))
                        }
                    )
                }
            )
            Toast.makeText(this, "Departamento Eliminado", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView(
        list: ArrayList<Carro>,
        recyclerView: RecyclerView
    ) {
        val adapter = RcVwAdapterCarro(this, list)

        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}