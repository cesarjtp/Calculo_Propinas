package com.example.calculo_propina

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeAdapter: EmployeeAdapter
    private val employees = mutableListOf<Employee>()
    private var totalTip: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_main)

        recyclerView = findViewById(R.id.recyclerView)
        employeeAdapter = EmployeeAdapter(employees)
        recyclerView.adapter = employeeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addEmployeeButton).setOnClickListener {
            showAddEmployeeDialog()
        }

        findViewById<Button>(R.id.addTipButton).setOnClickListener {
            showAddTipDialog()
        }

        updateTipDistribution()
    }

    private fun showAddEmployeeDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Agregar Empleado")

        val input = EditText(this)
        input.hint = "Nombre del Empleado"
        builder.setView(input)

        builder.setPositiveButton("Agregar") { _, _ ->
            val name = input.text.toString()
            if (name.isNotEmpty()) {
                employees.add(Employee(name))
                employeeAdapter.notifyItemInserted(employees.size - 1)
                updateTipDistribution()
            }
        }
        builder.setNegativeButton("Cancelar", null)

        builder.show()
    }

    private fun showAddTipDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Propina")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        input.hint = "Monto de la Propina"
        builder.setView(input)

        builder.setPositiveButton("Agregar") { _, _ ->
            val tip = input.text.toString().toDoubleOrNull()
            if (tip != null) {
                totalTip += tip
                updateTipDistribution()
            }
        }
        builder.setNegativeButton("Cancelar", null)

        builder.show()
    }

    private fun updateTipDistribution() {
        employeeAdapter.updateTips(totalTip)
        findViewById<TextView>(R.id.tipDistributionTextView).text = "Distribución de Propinas: $totalTip"
    }
}