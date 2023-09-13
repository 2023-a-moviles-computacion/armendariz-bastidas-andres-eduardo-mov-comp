package com.example.firebase

import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.data.entity.Carro

class RcVwAdapterCarro(
    private val parentContext: ListCarro,
    private val list: ArrayList<Carro>
): RecyclerView.Adapter<RcVwAdapterCarro.MyViewHolder>() {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val codeTextView: TextView
        val parentCodeTextView: TextView

        val marcaTextView: TextView
        val fecha_elaboracionTextView: TextView
        val precioTextView: TextView
        val colorTextView: TextView
        val mesesTextView: TextView



        init {
            codeTextView = view.findViewById(R.id.tv_carro_code)
            parentCodeTextView = view.findViewById(R.id.tv_carro_concesionario_code)

            marcaTextView = view.findViewById(R.id.tv_carro_marca)
            fecha_elaboracionTextView = view.findViewById(R.id.tv_carro_fecha)
            precioTextView = view.findViewById(R.id.tv_carro_precio)
            colorTextView = view.findViewById(R.id.tv_carro_color)
            mesesTextView = view.findViewById(R.id.tv_carro_meses)

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
                inflater.inflate(R.menu.menu_carro, menu)

                parentContext.setSelectedComponentCode(list[adapterPosition].code)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_carro,
                parent,
                false
            )

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val carro = this.list[position]

        holder.codeTextView.text = carro.code.toString()
        holder.parentCodeTextView.text = carro.deviceCode.toString()

        holder.marcaTextView.text = carro.marca
        holder.fecha_elaboracionTextView.text = carro.fecha_elaboracion.toString()
        holder.precioTextView.text = carro.precio.toString()
        holder.colorTextView.text = carro.color_subjetivo.toString()
        holder.mesesTextView.text = carro.meses_plazo_pagar.toString()

    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}
