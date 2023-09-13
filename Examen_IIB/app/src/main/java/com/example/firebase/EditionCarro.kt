package com.example.firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebase.data.dao.DAOFactory
import com.example.firebase.data.entity.Carro
import java.time.LocalDate

class EditionCarro : AppCompatActivity() {
    private var selectedCarroCode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edition_carro)

        val codePlainText = findViewById<TextView>(R.id.edit_carro_code)
        val parentDevicePlainText = findViewById<TextView>(R.id.edit_carro_concesionario_code)

        val marcaPlainText = findViewById<EditText>(R.id.edit_carro_marca)
        val fecha_elaboracionPlainText = findViewById<EditText>(R.id.edit_carro_fecha)
        val precioPlainText = findViewById<EditText>(R.id.edit_carro_precio)
        val colorPlainText = findViewById<EditText>(R.id.edit_carro_color)
        val mesesPlainText = findViewById<EditText>(R.id.edit_carro_meses)

        val editButton = findViewById<Button>(R.id.btn_confirm_carro_edition)

        var selectedComponent: Carro? = null
        selectedCarroCode = intent.getIntExtra("selectedCarroCode", 0)

        // Setting component data when it is ready
        DAOFactory.factory.getCarroDAO().read(
            selectedCarroCode!!,
            onSuccess = { component ->
                selectedComponent = component

                codePlainText.setText(selectedComponent!!.code.toString())
                parentDevicePlainText.setText(selectedComponent!!.deviceCode.toString())

                marcaPlainText.setText(selectedComponent!!.marca)
                fecha_elaboracionPlainText.setText(selectedComponent!!.fecha_elaboracion.toString())
                precioPlainText.setText(selectedComponent!!.precio.toString())
                colorPlainText.setText(selectedComponent!!.color_subjetivo.toString())
                mesesPlainText.setText(selectedComponent!!.meses_plazo_pagar.toString())

                /* RECOGE ALGO?*/

            }
        )

        editButton.setOnClickListener {
            openEditionDialog(
                Carro(
                    selectedCarroCode!!,
                    selectedComponent!!.deviceCode,
                    marcaPlainText.text.toString(),
                    fecha_elaboracionPlainText.text.toString(),
                    precioPlainText.text.toString(),
                    colorPlainText.text.toString(),
                    mesesPlainText.text.toString()
                ),
                selectedComponent!!.deviceCode
            )
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openEditionDialog(editedComponent: Carro, selectedConcesionarioCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar")
        builder.setMessage("Estas seguro de editar este Departamento?")

        builder.setPositiveButton("Si") { _, _ ->
            DAOFactory.factory.getCarroDAO().update(editedComponent)
            Toast.makeText(this, "Departamento Editado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListCarro::class.java)
            intent.putExtra("selectedConcesionarioCode", selectedConcesionarioCode)
            startActivity(intent)
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

}