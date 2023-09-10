package com.example.onedrive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ArchivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivo)
        var listaArchivos = arrayListOf<Archivo>()
        listaArchivos.add(Archivo(R.drawable.ic_baseline_feed_24,"Tarea 1","25 MB","Feb 17, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_feed_24,"Tarea 2","75 MB","Jun 17, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_feed_24,"Tarea 3","80 MB","Apr 10, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_dataset_24,"Tarea 4","25 MB","Mar 17, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_description_24,"Tarea 5","75 MB","Jul 17, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_image_24,"Tarea 6","80 MB","Oct 10, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_feed_24,"Tarea 7","25 MB","Nov 17, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_feed_24,"Tarea 8","75 MB","Jun 27, 2022"))
        listaArchivos.add(Archivo(R.drawable.ic_baseline_width_wide_24,"Tarea 9","80 MB","Apr 12, 2022"))

        val recyclerView = findViewById<RecyclerView>(R.id.rvArchivo)
        initRecyclerView(listaArchivos, recyclerView)
    }

    private fun initRecyclerView(listaArchivos: ArrayList<Archivo>, recyclerView: RecyclerView) {
        val adapter = Adapter2(listaArchivos,this,recyclerView)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }
}