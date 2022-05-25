package com.example.renata.apitest.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.renata.apitest.R
import com.example.renata.apitest.api.JsonPlacerHolderApi
import com.example.renata.apitest.model.Entrance
import com.example.renata.apitest.model.Paid
import com.example.renata.apitest.model.Plate
import kotlinx.android.synthetic.main.fragment_parking.*
import kotlinx.android.synthetic.main.fragment_parking.view.*
import kotlinx.android.synthetic.main.fragment_payment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ParkingFragment : Fragment() {
    var plate: String = ""
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_parking, container, false)
        val view = inflater.inflate(R.layout.fragment_parking, container, false)

        view.etLicense.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                /*if (validaPlaca(s)) {

                    Log.i("INFO", "renis 2 ")
                }*/
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (validaPlaca(s)) {
                    btnConfirm.isEnabled = true
                    plate = etLicense.text.toString().toUpperCase()
                    Log.i("INFO", "renis 2 ")
                }
                Log.i("INFO", "renis 1 " + if (validaPlaca(s) == true) btnConfirm.isEnabled = true else btnConfirm.isEnabled = false)
                // Log.i("INFO", "renis 1 "+ s +" "+(s.length-1) +" "+if (validaPlaca(s) == true && s.length == 8) txtAlertError.visibility = View.GONE else txtAlertError.visibility = View.VISIBLE)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        view.btnConfirm.setOnClickListener {
           //conection()
        }
        return view
    }

    fun conection() {
        /**
         * erro quando coloca a expressao : https://parking-lot-to-pfz.herokuapp.com/parking
         */
        val dialogo1 = getActivity()?.let { Dialog(it) }
        dialogo1!!.setContentView(R.layout.confirm_payment1)
        progressBar = dialogo1.findViewById(R.id.progressBar)
        val txtDialog1 = dialogo1.findViewById<TextView>(R.id.txtAlertBack1)
        txtDialog1.text = "Registrando..."
        val dialogo2 = getActivity()?.let { Dialog(it) }
        dialogo2!!.setContentView(R.layout.confirm_payment2)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
        //if (typeConnection == "Saida") plate = plate + "/out" else plate = plate + "/pay"
        val p = Plate(plate)
        val call: Call<Entrance> = jsonPlacerHolderApi.postEntrance(p)
        val imgConf = dialogo2.findViewById<ImageView>(R.id.imgConfirm2)
        val txtDialog2 = dialogo2.findViewById<TextView>(R.id.txtAlertBack2)
        txtDialog2.text = "REGISTRADO!"
        call!!.enqueue(object : Callback<Entrance> {
            override fun onResponse(call: Call<Entrance>, response: Response<Entrance>) {
                if (response.isSuccessful) {
                    Log.i("info", "renis " + response.code())
                    dialogo1.dismiss()

                    imgConf.setOnClickListener {
                        dialogo2.dismiss()
                    }
                    dialogo2.show()
                }
                if (!response.isSuccessful) {
                    Log.i("info", "renis sem sucesso" + response.code())
                    dialogo1.dismiss()
                    imgConf.setImageDrawable(resources.getDrawable(R.drawable.ic_close))
                    imgConf.setColorFilter(resources.getColor(R.color.parking_menu))
                    val txtAlertback2 = dialogo2.findViewById<TextView>(R.id.txtAlertBack2)
                    txtAlertback2.text = "Não foi possível registrar!"
                    imgConf.setOnClickListener {
                        dialogo2.dismiss()
                    }
                    dialogo2.show()
                }

            }

            override fun onFailure(call: Call<Entrance>, t: Throwable) {
                Log.i("info", "renis " + t.message)
                //showError()
            }
        })
        dialogo1.create()
        dialogo1.show()
    }

    fun validaPlaca(s: CharSequence): Boolean {
        var valido = true
        if (s.length != 8) {
            valido = false
        }
        for (i in s.indices) {
            println(s[i])
            if (i <= 2) {
                if (!s[i].isLetter()) {
                    valido = false
                    println("${s[i]} é numero  falso" + s.subSequence(0, i).toString() + " " + i)
                }
                println("${s[i]} correto")
            }
            if (i == 3) {
                if (s[3] != '-') {
                    valido = false
                    println("${s[i]} é numero  falso" + s.subSequence(0, i).toString() + " " + i)

                }
            }
            if (i >= 4) {
                if (!(s[i] >= '0' && s[i] <= '9')) {
                    valido = false
                    println("${s[i]} é letra " + s.subSequence(4, i).toString() + " " + i)
                }
                println("${s[i]} correto")
            }
        }
        return valido
    }
}
