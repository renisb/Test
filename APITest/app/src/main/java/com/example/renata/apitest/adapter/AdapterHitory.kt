package com.example.renata.apitest.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.renata.apitest.R
import com.example.renata.apitest.activity.CarStatusActivity
import com.example.renata.apitest.model.Car

class AdapterHitory(val cars: List<Car>,private val context1: Context) :
RecyclerView.Adapter<AdapterHitory.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_history, parent, false)
        return MyViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = cars[position]
        holder.statusPayment1.text = if (item.paid == true) "Pago" else "--"
        holder.time1.text = item.time
        holder.itemView.setOnClickListener{
            CarStatusActivity.car = item
            context1.startActivity(Intent(context1, CarStatusActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    inner class MyViewHolder : RecyclerView.ViewHolder {

        val statusPayment1: TextView
        val time1: TextView

        constructor(view: View) : super(view) {
            statusPayment1 = itemView.findViewById(R.id.txtStatusPayment1)
            time1 = itemView.findViewById(R.id.txtTime1)
        }
    }
}