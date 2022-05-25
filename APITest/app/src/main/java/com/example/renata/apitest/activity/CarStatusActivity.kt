package com.example.renata.apitest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.renata.apitest.R
import com.example.renata.apitest.activity.HistoryActivity.Companion.plate
import com.example.renata.apitest.api.JsonPlacerHolderApi
import com.example.renata.apitest.fragment.PaymentFragment
import com.example.renata.apitest.model.Car
import com.example.renata.apitest.model.Entrance
import com.example.renata.apitest.model.Plate
import kotlinx.android.synthetic.main.activity_car_status.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarStatusActivity : AppCompatActivity() {
    companion object{
        var car : Car?= null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_status)

        txtNumberLincence1.text = car!!.plate
        txtTypeStatus.text = if (car!!.left == false) "Estacionado" else "Liberado"
        txtTime.text = car!!.time.toString()
        txtStatusPayment.text = if (car!!.paid == true) "Pago" else "--"

        img_CarStatus.setOnClickListener {
            val p: String = car!!.plate
            startActivity(Intent(this@CarStatusActivity, HistoryActivity::class.java).putExtra("p",plate))
        }
        btnMenu.setOnClickListener {
            //drawer_layout.openDrawer(GravityCompat.START)
            startActivity(Intent(this@CarStatusActivity, NavMenu::class.java))
        }

    }

    /*private fun callFragmentExample() {
        val bundle = bundleOf(
            "plate" to HistoryActivity.plate
        )
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PaymentFragment>(androidx.fragment.R.id.fragment_container_view_tag, args = bundle)
        }
    }*/
}

/**
 * val retrofit = Retrofit.Builder()
.baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking/")
.addConverterFactory(GsonConverterFactory.create())
.build()
val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
//val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance("AAA-4444")
val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance(Plate("AAA-4444"))
call!!.enqueue(object : Callback<Entrance>{
override fun onResponse(call: Call<Entrance>, response: Response<Entrance>) {
if (response.isSuccessful) {
textViewRenis.text = "Code "+response.code().toString()+
response.body()!!.entered_at+
response.body()!!.reservation+
response.body()!!.plate
Log.i("info", "renis " + response.code())
}
}

override fun onFailure(call: Call<Entrance>, t: Throwable) {
showError()
}

})
}
private fun showError() {
Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
}
 */


/// tanto para a funcao postExit como postPaid funciona essas formulas

/**
 * val retrofit = Retrofit.Builder()
.baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking/")
.addConverterFactory(GsonConverterFactory.create())
.build()
val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
//val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance("AAA-4444")
val call: Call<Paid> = jsonPlacerHolderApi.postExit("AAA-4444")
call!!.enqueue(object : Callback<Paid>{
override fun onResponse(call: Call<Paid>, response: Response<Paid>) {
if (response.isSuccessful) {
Log.i("info", "renis " + response.code())
textViewRenis.text = "Code "+response.code().toString()//+
//response.body()!!.number
Log.i("info", "renis " + response.code())
}
}

override fun onFailure(call: Call<Paid>, t: Throwable) {
Log.i("info", "renis " +t.message)
showError()
}

})
}
private fun showError() {
Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
}
 */

// postEntrace nao funciona
/*val retrofit = Retrofit.Builder()
            .baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
        //val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance("AAA-4444")
        val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance("AAA-4444")
        call!!.enqueue(object : Callback<Entrance>{
            override fun onResponse(call: Call<Entrance>, response: Response<Entrance>) {
                if (response.isSuccessful) {
                    Log.i("info", "renis " + response.code())
                    textViewRenis.text = "Code "+response.code().toString()//+
                            //response.body()!!.number
                    Log.i("info", "renis " + response.code())
                }
            }

            override fun onFailure(call: Call<Entrance>, t: Throwable) {
                Log.i("info", "renis " +t.message)
                showError()
            }

        })*/

/**
 * val plate = Plate("AAA-4444")
val gson = Gson()
val plateGson = gson.toJson(plate)
Log.i("info", "renis " + plateGson)
/*CoroutineScope(Dispatchers.IO).launch {
val http = HttpHelper().post(plateGson)
Log.i("info", "renis " + http[0])
}*/

val http = HttpHelper().post("AAA-4444")
Log.i("info", "renis 2 " + http.toString())

}
private fun showError() {
Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
}
 */