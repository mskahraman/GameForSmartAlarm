package com.example.mehmetsabir.puzzlegamekotlin

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.solver.widgets.Helper
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.Button
import java.util.*
import kotlin.collections.ArrayList

class PuzzleActivity : AppCompatActivity() {

    private var list: java.util.ArrayList<Int>? = null
    var level: String? = null
    var numberOfPicture: Int? = 0
    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        bundle = intent.extras
            getS()
            init()
            scramble()
            setDimensions()

    }

    private fun getS() {

        level = bundle?.getString("level")
        numberOfPicture = bundle?.getInt("numberOfPicture")

    }


    fun getResId(context: Context, level: String, randomNumber: Int): java.util.ArrayList<Int> {
        val images: java.util.ArrayList<Int> = java.util.ArrayList<Int>();
        for (i in 1..9) {
            val randomDrawableIndexWithTwoDigits: String = String.format(Locale.ENGLISH, "%02d", randomNumber)
            val indexWithTwoDigits: String = String.format(Locale.ENGLISH, "%02d", i)
            images.add(
                context.resources.getIdentifier(
                    "ic_" + level + "_" +
                            randomDrawableIndexWithTwoDigits + "_" +
                            indexWithTwoDigits, "drawable", context.packageName
                )
            )
        }
        return images
    }


    fun display(context: Context) {
        val buttons = java.util.ArrayList<Button>()
        var button: Button
//        level?.let {
        list = getResId(context, "easy", 1);
        for (i in PuzzleActivity.tileList?.indices!!) {
            button = Button(context)

            if (PuzzleActivity.tileList!![i] == "0")
                button.setBackgroundResource(list!![0])
            else if (PuzzleActivity.tileList!![i] == "1")
                button.setBackgroundResource(list!![1])
            else if (PuzzleActivity.tileList!![i] == "2")
                button.setBackgroundResource(list!![2])
            else if (PuzzleActivity.tileList!![i] == "3")
                button.setBackgroundResource(list!![3])
            else if (PuzzleActivity.tileList!![i] == "4")
                button.setBackgroundResource(list!![4])
            else if (PuzzleActivity.tileList!![i] == "5")
                button.setBackgroundResource(list!![5])
            else if (PuzzleActivity.tileList!![i] == "6")
                button.setBackgroundResource(list!![6])
            else if (PuzzleActivity.tileList!![i] == "7")
                button.setBackgroundResource(list!![7])
            else if (PuzzleActivity.tileList!![i] == "8")
                button.setBackgroundResource(list!![8])


            buttons.add(button)
        }

        PuzzleActivity.mGridView?.adapter = CustomAdapter(buttons,
            PuzzleActivity.mColumnWidth,
            PuzzleActivity.mColumnHeight
        )
        //  }
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
                display(applicationContext)
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
