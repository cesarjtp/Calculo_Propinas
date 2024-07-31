package com.example.calculo_propina

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(private val employees: MutableList<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val employeeNameTextView: TextView = view.findViewById(R.id.employeeNameTextView)
        val tipAmountTextView: TextView = view.findViewById(R.id.tipAmountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.employeeNameTextView.text = employee.name
        holder.tipAmountTextView.text = "Propina: $${employee.tipAmount}"
    }

    override fun getItemCount() = employees.size

    fun updateTips(totalTip: Double) {
        val tipPerEmployee = if (employees.isEmpty()) 0.0 else totalTip / employees.size
        employees.forEach { it.tipAmount = tipPerEmployee }
        notifyDataSetChanged()
    }
}