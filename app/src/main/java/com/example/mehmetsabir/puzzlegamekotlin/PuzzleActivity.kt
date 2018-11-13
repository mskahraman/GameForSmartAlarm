package com.example.mehmetsabir.puzzlegamekotlin

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.view.WindowManager
import java.util.*

class PuzzleActivity() : AppCompatActivity() {


    var level: String? = null
    var numberOfPicture: Int? = 0
    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        getS()
        init()
        scramble()
        setDimensions()

    }

    fun getS() {
        bundle = intent.extras
        level = bundle?.getString("level")
        numberOfPicture = bundle?.getInt("numberOfPicture")

    }


    private fun init() {

        mGridView = findViewById<GestureDetectGridView>(R.id.grid)
        mGridView?.numColumns = COLUMNS
        tileList = arrayOfNulls<String>(DIMENSIONS)
        for (i in 0 until DIMENSIONS) {
            tileList!![i] = i.toString()
        }
    }

    private fun scramble() {
        var index: Int
        var temp: String
        val random = Random()

        for (i in tileList?.size!! - 1 downTo 1) {
            index = random.nextInt(i + 1)
            temp = tileList!![index]!!
            tileList!![index] = tileList!![i]
            tileList!![i] = temp
        }
    }

    private fun setDimensions() {


        val vto = mGridView?.getViewTreeObserver()
        vto?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onGlobalLayout() {
                mGridView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                val displayWidth = mGridView?.measuredWidth
                val displayHeight = mGridView?.measuredHeight

                val statusbarHeight = getStatusBarHeight(applicationContext)
                val requiredHeight = displayHeight!! - statusbarHeight

                mColumnWidth = displayWidth!! / COLUMNS
                mColumnHeight = requiredHeight / COLUMNS
                HelperPuzzle.instance.display(applicationContext, intent)
            }
        })
    }


    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
            "status_bar_height", "dimen",
            "android"
        )

        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }

        return result
    }


    fun isSolved(): Boolean {
        var solved = false

        for (i in tileList?.indices!!) {
            if (tileList!![i] == i.toString()) {
                solved = true
            } else {
                solved = false
                break
            }
        }

        return solved
    }

    companion object {

        val COLUMNS = 3
        val DIMENSIONS = 3 * COLUMNS
        const val up = "up"
        const val down = "down"
        const val left = "left"
        const val right = "right"
        var tileList: Array<String?>? = null
        var mGridView: GestureDetectGridView? = null
        var mColumnWidth: Int = 0
        var mColumnHeight: Int = 0
    }
}
