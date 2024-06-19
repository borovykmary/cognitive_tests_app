package com.example.cognittiveassesmenttests.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.R

class TestRecordAdapterAdminMA(
    private val dataList: List<Map<String, Any>>,
    private val onConfirm: (data: Map<String, Any>) -> Unit
) : RecyclerView.Adapter<TestRecordAdapterAdminMA.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.address)
        val address1: TextView = itemView.findViewById(R.id.address1)
        val address1Repeat: TextView = itemView.findViewById(R.id.address1Repeat)
        val addressRepeat: TextView = itemView.findViewById(R.id.addressRepeat)
        val animals: TextView = itemView.findViewById(R.id.animals)
        val date: TextView = itemView.findViewById(R.id.date)
        val day: TextView = itemView.findViewById(R.id.day)
        val month: TextView = itemView.findViewById(R.id.month)
        val name: TextView = itemView.findViewById(R.id.name)
        val name1: TextView = itemView.findViewById(R.id.name1)
        val name1Repeat: TextView = itemView.findViewById(R.id.name1Repeat)
        val nameRepeat: TextView = itemView.findViewById(R.id.nameRepeat)
        val time: TextView = itemView.findViewById(R.id.time)
        val year: TextView = itemView.findViewById(R.id.year)
        val input1: Spinner = itemView.findViewById(R.id.input1)
        val input2: Spinner = itemView.findViewById(R.id.input2)
        val input3: Spinner = itemView.findViewById(R.id.input3)
        val input4: Spinner = itemView.findViewById(R.id.input4)
        val input5: Spinner = itemView.findViewById(R.id.input5)
        val confirmButton: Button = itemView.findViewById(R.id.confirm_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin_ma, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.address.text = "Address: " + data["Address"] as String
        holder.address1.text = "Address1: " + data["Address1"] as String
        holder.address1Repeat.text = "Address1R: " + data["Address1Repeat"] as String
        holder.addressRepeat.text = "AddressR: " + data["AddressRepeat"] as String
        holder.animals.text = "Animals: " + data["Animals"] as String
        holder.date.text = "Date: " + data["Date"] as String
        holder.day.text = "Day: " + data["Day"] as String
        holder.month.text = "Month: " + data["Month"] as String
        holder.name.text = "Name: " + data["Name"] as String
        holder.name1.text = "Name1: " + data["Name1"] as String
        holder.name1Repeat.text = "Name1R: " + data["Name1Repeat"] as String
        holder.nameRepeat.text = "NameR: " + data["NameRepeat"] as String
        holder.time.text = "Time: " + data["Time"] as String
        holder.year.text = "Year: " + data["Year"] as String

        // Create an ArrayAdapter using the integer array and a default spinner layout
        val adapter1 = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.input1_options,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        holder.input1.adapter = adapter1
        // Set the default value
        holder.input1.setSelection(0)

        // Repeat the above steps for the other spinners
        val adapter2 = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.input2_options,
            android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.input2.adapter = adapter2
        holder.input2.setSelection(0)

        val adapter3 = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.input3_options,
            android.R.layout.simple_spinner_item
        )
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.input3.adapter = adapter3
        holder.input3.setSelection(0)

        val adapter4 = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.input4_options,
            android.R.layout.simple_spinner_item
        )
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.input4.adapter = adapter4
        holder.input4.setSelection(0)

        val adapter5 = ArrayAdapter.createFromResource(
            holder.itemView.context,
            R.array.input5_options,
            android.R.layout.simple_spinner_item
        )
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.input5.adapter = adapter5
        holder.input5.setSelection(0)

        holder.confirmButton.setOnClickListener {
            onConfirm(data)
        }

    }

    override fun getItemCount() = dataList.size
}