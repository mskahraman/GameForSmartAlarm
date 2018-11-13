package com.example.mehmetsabir.puzzlegamekotlin

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import java.util.*


class HelperPuzzle {

    private var list: java.util.ArrayList<Int>? = null
    private var bundleIntent: Intent? = null
    fun swap(context: Context, currentPosition: Int, swap: Int) {

        val newPosition = PuzzleActivity.tileList!![currentPosition + swap]
        PuzzleActivity.tileList!![currentPosition + swap] = PuzzleActivity.tileList!![currentPosition]
        PuzzleActivity.tileList!![currentPosition] = newPosition
        display(context, this.bundleIntent!!)

        if (PuzzleActivity().isSolved()) Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show()
    }

    fun display(context: Context, bundleIntent: Intent) {
        this.bundleIntent = bundleIntent
        val buttons = java.util.ArrayList<Button>()
        var button: Button
        list = getResId(context, bundleIntent.extras.getString("level"), bundleIntent.extras.getInt("numberOfPicture"));
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

        PuzzleActivity.mGridView?.adapter = CustomAdapter(
            buttons,
            PuzzleActivity.mColumnWidth,
            PuzzleActivity.mColumnHeight
        )
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


    fun moveTiles(context: Context, direction: String, position: Int) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else if (direction == PuzzleActivity.down)
                swap(context, position, PuzzleActivity.COLUMNS)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Upper-center tiles
        } else if (position > 0 && position < PuzzleActivity.COLUMNS - 1) {
            if (direction == PuzzleActivity.left)
                swap(context, position, -1)
            else if (direction == PuzzleActivity.down)
                swap(context, position, PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Upper-right-corner tile
        } else if (position == PuzzleActivity.COLUMNS - 1) {
            if (direction == PuzzleActivity.left)
                swap(context, position, -1)
            else if (direction == PuzzleActivity.down)
                swap(context, position, PuzzleActivity.COLUMNS)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Left-side tiles
        } else if (position > PuzzleActivity.COLUMNS - 1 && position < PuzzleActivity.DIMENSIONS - PuzzleActivity.COLUMNS &&
            position % PuzzleActivity.COLUMNS == 0
        ) {
            if (direction == PuzzleActivity.up)
                swap(context, position, -PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else if (direction == PuzzleActivity.down)
                swap(context, position, PuzzleActivity.COLUMNS)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Right-side AND bottom-right-corner tiles
        } else if (position == PuzzleActivity.COLUMNS * 2 - 1 || position == PuzzleActivity.COLUMNS * 3 - 1) {
            if (direction == PuzzleActivity.up)
                swap(context, position, -PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.left)
                swap(context, position, -1)
            else if (direction == PuzzleActivity.down) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= PuzzleActivity.DIMENSIONS - PuzzleActivity.COLUMNS - 1)
                    swap(
                        context, position,
                        PuzzleActivity.COLUMNS
                    )
                else
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Bottom-left corner tile
        } else if (position == PuzzleActivity.DIMENSIONS - PuzzleActivity.COLUMNS) {
            if (direction == PuzzleActivity.up)
                swap(context, position, -PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Bottom-center tiles
        } else if (position < PuzzleActivity.DIMENSIONS - 1 && position > PuzzleActivity.DIMENSIONS - PuzzleActivity.COLUMNS) {
            if (direction == PuzzleActivity.up)
                swap(context, position, -PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.left)
                swap(context, position, -1)
            else if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show()

            // Center tiles
        } else {
            if (direction == PuzzleActivity.up)
                swap(context, position, -PuzzleActivity.COLUMNS)
            else if (direction == PuzzleActivity.left)
                swap(context, position, -1)
            else if (direction == PuzzleActivity.right)
                swap(context, position, 1)
            else
                swap(context, position, PuzzleActivity.COLUMNS)
        }
    }

    companion object {
        private var INSTANCE: HelperPuzzle? = null

        val instance: HelperPuzzle
            get() {
                if (INSTANCE == null) {
                    INSTANCE = HelperPuzzle()
                }
                return INSTANCE as HelperPuzzle
            }
    }
}