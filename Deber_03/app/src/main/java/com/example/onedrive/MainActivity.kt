package com.example.onedrive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaDirectorios = arrayListOf<Directorio>()
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Aplicaciones Web","16 GB","Mar 20, 2022"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Aplicaciones Móviles","25 GB","Feb 10, 2023"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Ingeniería de Software","10 GB","Jun 11, 2022"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Recuperación de la información","4 GB","Feb 03, 2023"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Compiladores y Lenguajes","10 GB","Jun 06, 2020"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Inteligencia Artificial","40 GB","Feb 03, 2021"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Estructura de datos","10 GB","Jun 06, 2020"))
        listaDirectorios.add(Directorio(R.drawable.ic_baseline_folder_24,"Algoritmos","12 GB","Jun 12, 2020"))

        val recyclerView = findViewById<RecyclerView>(R.id.rvDirectorio)
        initRecyclerView(listaDirectorios, recyclerView)
    }

    private fun initRecyclerView(listaDirectorios: ArrayList<Directorio>, recyclerView: RecyclerView) {
        val adapter = Adapter(listaDirectorios,this,recyclerView)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
        adapter.onItemClick = {
            val intent = Intent(this, ArchivoActivity::class.java)
            startActivity(intent)
        }
    }
}