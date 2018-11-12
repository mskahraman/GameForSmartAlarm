package com.example.mehmetsabir.puzzlegamekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import java.util.*

class ChooseActivity : AppCompatActivity() {

    private var radioGroup : RadioGroup? = null
    private var btnOk :  Button? = null
    private var btnCancel :  Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        openDialogScreen()
    }

    private fun openDialogScreen() {


        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.alertlayout, null)

        radioGroup = view.findViewById(R.id.rbtnGrpLevel)
        btnOk = view.findViewById(R.id.btnOkay)
        btnCancel = view.findViewById(R.id.btnCancel)

        val alert = AlertDialog.Builder(this)
        alert.setView(view)
        alert.setCancelable(false)
        val dialog = alert.create()

        btnOk?.setOnClickListener {

            changeLevel(radioGroup?.checkedRadioButtonId!!)

            dialog.cancel()
        }
        btnCancel?.setOnClickListener {
            dialog.cancel()
        }


        dialog.show()

    }

    private fun changeLevel(position : Int)  {

        val intent : Intent = Intent(this@ChooseActivity,PuzzleActivity::class.java)
        var level : String? = null
        var numberOfPicture : Int? = 1
        val r = Random()

        when (position) {
            R.id.rbEasy -> {
                level="easy"
                numberOfPicture = r.nextInt(4) + 1
            }
            R.id.rbMedium ->{
                level="medium"
                numberOfPicture = r.nextInt(4) + 1
            }
            R.id.rbHard ->{
                level="hard"
                numberOfPicture = r.nextInt(4) + 1
            }
            else -> {
                   level="easy"
               Toast.makeText(this@ChooseActivity,"Seçilmedi", Toast.LENGTH_LONG).show()
            }
        }

        intent.putExtra("level",level)
        intent.putExtra("numberOfPicture",numberOfPicture)
        startActivity(intent)
    }
}
