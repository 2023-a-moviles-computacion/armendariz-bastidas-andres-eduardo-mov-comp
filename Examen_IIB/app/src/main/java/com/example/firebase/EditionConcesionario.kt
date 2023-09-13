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
import com.example.firebase.data.entity.Concesionario
import java.time.LocalDate

class EditionConcesionario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edition_concesionario)

        val codePlainText = findViewById<TextView>(R.id.edit_concesionario_code)
        val nombrePlainText = findViewById<EditText>(R.id.edit_concesionario_nombre)
        val fecha_inaguracionPlainText = findViewById<EditText>(R.id.edit_concesionario_fecha)
        val porcentajePlainText= findViewById<EditText>(R.id.edit_concesionario_porcentaje)
        val cantEmpleadosPlainText = findViewById<EditText>(R.id.edit_concesionario_empleados)
        val editButton = findViewById<Button>(R.id.btn_confirm_concesionario_edition)


        val selectedConcesionarioCode = intent.getIntExtra("selectedConcesionarioCode", 0)

        // Setting device data when it is ready
        DAOFactory.factory.getConcesionarioDAO().read(
            selectedConcesionarioCode,
            onSuccess = { device ->
                codePlainText.setText(device.code.toString())
                nombrePlainText.setText(device.nombre)
                fecha_inaguracionPlainText.setText(device.fecha_inaguracion.toString())
                porcentajePlainText.setText(device.porcentaje_personas_satisfechas.toString())
                cantEmpleadosPlainText.setText(device.cantidad_empleados.toString())
            }
        )

        editButton.setOnClickListener {
            openEditionDialog(
                Concesionario(
                    selectedConcesionarioCode,
                    nombrePlainText.text.toString(),
                    LocalDate.parse(fecha_inaguracionPlainText.text.toString()),
                    porcentajePlainText.text.toString().toDouble(),
                    cantEmpleadosPlainText.text.toString().toInt()
                )
            )
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    private fun openEditionDialog(editedDevice: Concesionario) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar")
        builder.setMessage("Estas seguro de editar este Edificio?")

        builder.setPositiveButton("Si") { _, _ ->
            DAOFactory.factory.getConcesionarioDAO().update(editedDevice)
            Toast.makeText(this, "Edificio Editado", Toast.LENGTH_SHORT).show()
            openActivity(ListConcesionario::class.java)
        }

        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}