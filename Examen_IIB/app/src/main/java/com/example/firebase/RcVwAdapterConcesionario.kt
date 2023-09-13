package com.example.firebase

import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.entity.Concesionario

class RcVwAdapterConcesionario(
    private val parentContext: ListConcesionario,
    private val list: ArrayList<Concesionario>
): RecyclerView.Adapter<RcVwAdapterConcesionario.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val codeTextView: TextView
        val nombreTextView: TextView
        val fecha_inaguracionTextView: TextView
        val porcentajeTextView: TextView
        val cantEmpleadosTextView: TextView

        init {
            codeTextView = view.findViewById(R.id.tv_concesionario_code)
            nombreTextView = view.findViewById(R.id.tv_concesionario_nombre)
            fecha_inaguracionTextView = view.findViewById(R.id.tv_concesionario_fecha)
            porcentajeTextView = view.findViewById(R.id.tv_concesionario_porcentaje)
            cantEmpleadosTextView = view.findViewById(R.id.tv_concesionario_empleados)

            view.setOnCreateContextMenuListener(this)

            // Setting the view selection mode
            itemView.isClickable = true
            itemView.isLongClickable = true

            // Setting the style
            val typedValue = TypedValue()
            itemView.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
            itemView.setBackgroundResource(typedValue.resourceId)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            if (menu != null) {
                val inflater = MenuInflater(view?.context)
                inflater.inflate(R.menu.menu_concesionario, menu)

                parentContext.setSelectedDeviceCode(list[adapterPosition].code)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_concesionario,
                parent,
                false
            )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val concesionario = this.list[position]

        holder.codeTextView.text = concesionario.code.toString()
        holder.nombreTextView.text = concesionario.nombre
        holder.fecha_inaguracionTextView.text = concesionario.fecha_inaguracion.toString()
        holder.porcentajeTextView.text = concesionario.porcentaje_personas_satisfechas.toString()
        holder.cantEmpleadosTextView.text = concesionario.cantidad_empleados.toString()
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}