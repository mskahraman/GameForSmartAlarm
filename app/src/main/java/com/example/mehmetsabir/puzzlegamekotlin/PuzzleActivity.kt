package com.example.mehmetsabir.puzzlegamekotlin

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.Toast
import java.util.*

class PuzzleActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        init()
        scramble()
        setDimensions()
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

    private fun display(context: Context) {
        val buttons = ArrayList<Button>()
        var button: Button

        for (i in tileList?.indices!!) {
            button = Button(context)

            if (tileList!![i] == "0")
                button.setBackgroundResource(R.drawable.pigeon_piece1)
            else if (tileList!![i] == "1")
                button.setBackgroundResource(R.drawable.pigeon_piece2)
            else if (tileList!![i] == "2")
                button.setBackgroundResource(R.drawable.pigeon_piece3)
            else if (tileList!![i] == "3")
                button.setBackgroundResource(R.drawable.pigeon_piece4)
            else if (tileList!![i] == "4")
                button.setBackgroundResource(R.drawable.pigeon_piece5)
            else if (tileList!![i] == "5")
                button.setBackgroundResource(R.drawable.pigeon_piece6)
            else if (tileList!![i] == "6")
                button.setBackgroundResource(R.drawable.pigeon_piece7)
            else if (tileList!![i] == "7")
                button.setBackgroundResource(R.drawable.pigeon_piece8)
            else if (tileList!![i] == "8")
                button.setBackgroundResource(R.drawable.pigeon_piece9)

            buttons.add(button)
        }

        mGridView?.adapter = CustomAdapter(buttons, mColumnWidth, mColumnHeight)
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

        private val COLUMNS = 3
        private val DIMENSIONS = COLUMNS * COLUMNS
        const val up = "up"
        const val down = "down"
        const val left = "left"
        const val right = "right"
        private var tileList: Array<String?>? = null
        private var mGridView: GestureDetectGridView? = null
        private var mColumnWidth: Int = 0
        private var mColumnHeight: Int = 0

        fun swap(context: Context, currentPosition: Int, swap: Int) {
            val newPosition = tileList!![currentPosition + swap]
            tileList!![currentPosition + swap] = tileList!![currentPosition]
            tileList!![currentPosition] = newPosition
            PuzzleActivity().display(context)

            if (PuzzleActivity().isSolved()) Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show()
        }

        fun moveTiles(context: Context, direction: String, position: Int) {

            // Upper-left-corner tile
            if (position == 0) {

                if (direction == right)
                    swap(context, position, 1)
                else if (direction == down)
                    swap(context, position, COLUMNS)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Upper-center tiles
            } else if (position > 0 && position < COLUMNS - 1) {
                if (direction == left)
                    swap(context, position, -1)
                else if (direction == down)
                    swap(context, position, COLUMNS)
                else if (direction == right)
                    swap(context, position, 1)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Upper-right-corner tile
            } else if (position == COLUMNS - 1) {
                if (direction == left)
                    swap(context, position, -1)
                else if (direction == down)
                    swap(context, position, COLUMNS)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Left-side tiles
            } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0
            ) {
                if (direction == up)
                    swap(context, position, -COLUMNS)
                else if (direction == right)
                    swap(context, position, 1)
                else if (direction == down)
                    swap(context, position, COLUMNS)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Right-side AND bottom-right-corner tiles
            } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
                if (direction == up)
                    swap(context, position, -COLUMNS)
                else if (direction == left)
                    swap(context, position, -1)
                else if (direction == down) {

                    // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                    // right-corner tile.
                    if (position <= DIMENSIONS - COLUMNS - 1)
                        swap(
                            context, position,
                            COLUMNS
                        )
                    else
                        Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Bottom-left corner tile
            } else if (position == DIMENSIONS - COLUMNS) {
                if (direction == up)
                    swap(context, position, -COLUMNS)
                else if (direction == right)
                    swap(context, position, 1)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Bottom-center tiles
            } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
                if (direction == up)
                    swap(context, position, -COLUMNS)
                else if (direction == left)
                    swap(context, position, -1)
                else if (direction == right)
                    swap(context, position, 1)
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

                // Center tiles
            } else {
                if (direction == up)
                    swap(context, position, -COLUMNS)
                else if (direction == left)
                    swap(context, position, -1)
                else if (direction == right)
                    swap(context, position, 1)
                else
                    swap(context, position, COLUMNS)
            }
        }
    }
}
