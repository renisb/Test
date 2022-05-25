package com.example.renata.apitest.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.renata.apitest.api.JsonPlacerHolderApi
import com.example.renata.apitest.R
import com.example.renata.apitest.activity.HistoryActivity
import com.example.renata.apitest.model.Paid
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PaymentFragment : Fragment() {
    lateinit var progressBar: ProgressBar
    var plate: String = ""
    //RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_payment, container, false)
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        view.etLicense1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (validaPlaca(s)) {
                    btnPayment.isEnabled = true
                    Log.i("INFO", "renis 3 ")
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (validaPlaca(s)) {
                    btnPayment.isEnabled = true
                    btnExit.isEnabled = true
                    btnExit.setImageDrawable(resources.getDrawable(R.drawable.img_btn_enable_exit))
                    txtVer.isEnabled = true
                    plate = etLicense1.text.toString().toUpperCase()
                    Log.i("INFO", "renis 2 ")
                }
                Log.i("INFO", "renis 1 "+ s +" "+(s.length-1) +" "+if (validaPlaca(s) == true) btnPayment.isEnabled=true else btnPayment.isEnabled=false)
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        view.btnPayment.setOnClickListener {
            conection("Pago")
        }

        view.btnExit.setOnClickListener {
            conection("Saida")
        }

        view.txtVer.setOnClickListener {
            HistoryActivity.plate = etLicense1.text.toString().uppercase()
            this.context?.startActivity(Intent(context, HistoryActivity::class.java))
        }

        return view
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
                println("${s[i]} correto" )
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
                println("${s[i]} correto" )
            }
        }
        return valido
    }
    fun conection(typeConnection: String){

        val dialogo = getActivity()?.let { Dialog(it) }
        dialogo!!.setContentView(R.layout.confirm_payment)
        val txtAlertPlateDialog= dialogo.findViewById<TextView>(R.id.txtAlertPlate)
        txtAlertPlateDialog.text = plate
        val buttonConfirm = dialogo.findViewById<Button>(R.id.btnAlertConfirm)
        if (typeConnection == "Saida") {
            buttonConfirm.text = "LIBERAR SAÍDA"
            val txt1= dialogo.findViewById<TextView>(R.id.textView)
            txt1.setText("Confirma a saída do veículo da placa abaixo?")
        }
        buttonConfirm.setOnClickListener {
            dialogo.dismiss()
            val dialogo1 = getActivity()?.let { Dialog(it) }
            dialogo1!!.setContentView(R.layout.confirm_payment1)
            progressBar = dialogo1.findViewById(R.id.progressBar)
            val dialogo2 = getActivity()?.let { Dialog(it) }
            dialogo2!!.setContentView(R.layout.confirm_payment2)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://parking-lot-to-pfz.herokuapp.com/parking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val jsonPlacerHolderApi = retrofit.create(JsonPlacerHolderApi::class.java)
            if (typeConnection == "Saida") plate = plate + "/out" else plate = plate + "/pay"
            val call: Call<Paid> = jsonPlacerHolderApi.postTest(plate)
            val imgConf = dialogo2.findViewById<ImageView>(R.id.imgConfirm2)
            if (typeConnection == "Saida") {
                val txtDialog2 = dialogo2.findViewById<TextView>(R.id.txtAlertBack2)
                txtDialog2.text = "Saída Liberada"
            }
            call!!.enqueue(object : Callback<Paid> {
                override fun onResponse(call: Call<Paid>, response: Response<Paid>) {
                    if (response.isSuccessful) {
                        Log.i("info", "renis " + response.code())
                        dialogo1.dismiss()

                        imgConf.setOnClickListener {
                            dialogo2.dismiss()
                        }
                        dialogo2.show()
                    }
                    if (!response.isSuccessful){
                        Log.i("info", "renis sem sucesso" + response.code())
                        dialogo1.dismiss()
                        imgConf.setImageDrawable(resources.getDrawable(R.drawable.ic_close))
                        imgConf.setColorFilter(resources.getColor(R.color.parking_menu))
                        imgConf.scaleType = ImageView.ScaleType.CENTER_CROP
                        val txtAlertback2= dialogo2.findViewById<TextView>(R.id.txtAlertBack2)
                        txtAlertback2.text = "Não foi possivel confirmar o pagamento!"
                        imgConf.setOnClickListener {
                            dialogo2.dismiss()
                        }
                        dialogo2.show()
                    }

                }
                override fun onFailure(call: Call<Paid>, t: Throwable) {
                    Log.i("info", "renis " +t.message)
                    //showError()
                }
            })

            dialogo1.create()
            dialogo1.show()
        }
        val txtAlertBack = dialogo.findViewById<TextView>(R.id.txtAlertBack)
        txtAlertBack.setOnClickListener {
            dialogo.dismiss()
        }
        dialogo.show()
    }


/*private fun showError() {
    Toast.makeText(this@PaymentFragment, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
}*/
}