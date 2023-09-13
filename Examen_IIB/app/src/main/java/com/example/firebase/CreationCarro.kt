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

class CreationCarro : AppCompatActivity() {
    private var selectedConcesionarioCode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation_carro)

        selectedConcesionarioCode = intent.getIntExtra("selectedConcesionarioCode", 0)

        val codeConcesionarioPlainText = findViewById<TextView>(R.id.create_car_code_concesionario)

        val marcaPlainText = findViewById<EditText>(R.id.create_car_marca)
        val fecha_elaboracionPlainText = findViewById<EditText>(R.id.create_car_fecha)
        val precioPlainText = findViewById<EditText>(R.id.create_car_precio)
        val colorPlainText = findViewById<EditText>(R.id.create_car_color)
        val mesesPlainText = findViewById<EditText>(R.id.create_car_meses)

        val createButton = findViewById<Button>(R.id.btn_confirm_car_create)

        codeConcesionarioPlainText.setText(selectedConcesionarioCode.toString())

        // Opening creation dialog when next code is here
        createButton.setOnClickListener {
            DAOFactory.factory.getCarroDAO().getRandomCode(
                onSuccess = { code ->
                    openCreationDialog(
                        Carro(
                            code,
                            selectedConcesionarioCode!!,
                            marcaPlainText.text.toString(),
                            fecha_elaboracionPlainText.text.toString(),
                            precioPlainText.text.toString(),
                            colorPlainText.text.toString(),
                            mesesPlainText.text.toString()
                        ),
                        selectedConcesionarioCode!!
                    )
                }
            )
        }

    }



    @SuppressLint("NotifyDataSetChanged")
    private fun openCreationDialog(newCarro: Carro, selectedConcesionarioCode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear")
        builder.setMessage("Estas seguro de crear este Departamento?")

        builder.setPositiveButton("Si") { _, _ ->
            DAOFactory.factory.getCarroDAO().create(newCarro)
            Toast.makeText(this, "Departamento Creado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListCarro::class.java)
            intent.putExtra("selectedConcesionarioCode", selectedConcesionarioCode)
            startActivity(intent)

        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()

    }

}