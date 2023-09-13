package com.example.firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.dao.DAOFactory
import com.example.firebase.data.entity.Concesionario
import com.example.firebase.data.util.DataCarro
import com.example.firebase.data.util.DataConcesionario
import kotlinx.coroutines.*

class ListConcesionario : AppCompatActivity() {
    private var selectedConcesionarioCode: Int? = null
    var concesionariosList = ArrayList<Concesionario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_concesionario)

        val concesionarioRecyclerView = findViewById<RecyclerView>(R.id.rv_concesionario)
        val creationButton = findViewById<Button>(R.id.btn_create_concesionario)

        //Obtengo la data solo 1 vez
        DAOFactory.factory.getConcesionarioDAO().getAllConcesionarios(
            onSuccess = { concesionarios ->
                concesionariosList=concesionarios
                if(concesionariosList.isEmpty()){
                    initData()
                    openActivity(ListConcesionario::class.java)

                }
                initializeRecyclerView(concesionariosList, concesionarioRecyclerView)
                registerForContextMenu(concesionarioRecyclerView)
            }
        )



        creationButton.setOnClickListener {
            openActivity(CreationConcesionario::class.java)
        }
    }


    private fun initData() {
        for (concesionario in DataConcesionario.concesionariosData) {
            DAOFactory.factory.getConcesionarioDAO().create(concesionario)
        }

        for (carro in DataCarro.carroData) {
            DAOFactory.factory.getCarroDAO().create(carro)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_concesionario_ver -> {
                val intent = Intent(this, ListCarro::class.java)
                intent.putExtra("selectedConcesionarioCode", selectedConcesionarioCode)
                startActivity(intent)
                return true
            }

            R.id.menu_concesionario_edit -> {
                val intent = Intent(this, EditionConcesionario::class.java)
                intent.putExtra("selectedConcesionarioCode", selectedConcesionarioCode)
                startActivity(intent)
                return true
            }

            R.id.menu_concesionario_delete -> {
                openDeleteDialog()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun setSelectedDeviceCode(concesionarioCode: Int) {
        selectedConcesionarioCode = concesionarioCode
    }

    private fun openDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar")
        builder.setMessage("Estas seguro de eliminar este edificio?")

        builder.setPositiveButton("Si") { _, _ ->
            DAOFactory.factory.getConcesionarioDAO().delete(
                selectedConcesionarioCode!!,
                onSuccess = {
                    DAOFactory.factory.getConcesionarioDAO().getAllConcesionarios(
                        onSuccess = { concesionarios ->
                            initializeRecyclerView(concesionarios, findViewById(R.id.rv_concesionario))
                        }
                    )
                }
            )
            Toast.makeText(this, "Edificio Eliminado", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initializeRecyclerView(
        list: ArrayList<Concesionario>,
        recyclerView: RecyclerView
    ) {
        val adapter = RcVwAdapterConcesionario(this, list)

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