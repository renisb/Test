package com.example.renata.apitest.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renata.apitest.MainActivity
import com.example.renata.apitest.adapter.AdapterHitory
import com.example.renata.apitest.api.JsonPlacerHolderApi
import com.example.renata.apitest.R
import com.example.renata.apitest.fragment.PaymentFragment
import com.example.renata.apitest.model.Car
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class HistoryActivity : AppCompatActivity() {
    private lateinit var adapterHistory: AdapterHitory
    var cars = mutableListOf<Car>()
    lateinit var carHistory : List<Car>
    companion object{
        var plate: String =""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initRecyclerView()
        txtNumberLincence.text ="Placa "+plate

        img_CarStatus.setOnClickListener {
            //callFragmentExample()
            //startActivity(Intent(this, PaymentFragment::class.java))
            MainActivity.page = 1
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnMenu.setOnClickListener {
            //drawer_layout.openDrawer(GravityCompat.START)
            //MainActivity.page = 0
            startActivity(Intent(this, NavMenu::class.java))
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
        val call: Call<List<Car>> = jsonPlacerHolderApi.getCars(plate)

        call!!.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                //text_view_result1.text = "Renata3"
                if (response.isSuccessful) {
                    Log.i("info", "renis " + response.code())
                }
                carHistory = response.body()!!
                cars.clear()
                cars.addAll(carHistory)
                adapterHistory.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.i("info", "renis " + t.message)
                showError()
            }
        })

    }

    /*private fun callFragmentExample() {
        val bundle = bundleOf(
            "plate" to plate
        )
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PaymentFragment>(androidx.fragment.R.id.fragment_container_view_tag, args = bundle)
        }
    }*/


    private fun initRecyclerView() {
        adapterHistory = AdapterHitory(cars,this)
        rcHistory.layoutManager = LinearLayoutManager(this)
        rcHistory.adapter = adapterHistory
    }
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}