package com.example.onedrive

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private val direcList: List<Directorio>,
    private val contexto: MainActivity,
    private val recycler: RecyclerView

) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var onItemClick : ((Directorio) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_elemento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagen = direcList[position].imagenDirectorio
        val nombre = direcList[position].nombreDirectorio
        val almacenamiento = direcList[position].almacenamientoDirectorio
        val fecha = direcList[position].fechaDirectorio
        holder.setData(imagen, nombre, almacenamiento, fecha)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(direcList[position])
        }
    }

    override fun getItemCount(): Int {
        return direcList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView
        private val textView: TextView
        private val textView2: TextView
        private val textView3: TextView

        init {
            imageView = itemView.findViewById(R.id.imDirectorio)
            textView = itemView.findViewById(R.id.tvnombreDirectorio)
            textView2 = itemView.findViewById(R.id.tvalmacenamientoDirectorio)
            textView3 = itemView.findViewById(R.id.tvfechaDirectorio)
        }

        fun setData(imagen: Int, nombre: String?, almacenamiento: String?, fecha: String?) {
            imageView.setImageResource(imagen)
            textView.text = nombre
            textView2.text = almacenamiento
            textView3.text = fecha
        }
    }
}