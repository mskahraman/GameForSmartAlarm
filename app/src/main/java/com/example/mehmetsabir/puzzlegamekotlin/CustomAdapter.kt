package com.example.mehmetsabir.puzzlegamekotlin

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import java.util.*

class CustomAdapter(buttons: ArrayList<Button>, columnWidth: Int, columnHeight: Int) : BaseAdapter() {

    private var mButtons: ArrayList<Button>? = null
    private var mColumnWidth: Int = 0
    private var mColumnHeight: Int = 0

    init {
        mButtons = buttons
        mColumnWidth =columnWidth
        mColumnHeight = columnHeight

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val button: Button = if (convertView == null) {
            mButtons?.get(position)!!
        } else {
            convertView as Button
        }

        val params = android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight)
        button.layoutParams = params

        return button
    }

    override fun getItem(position: Int): Any {
        return mButtons?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
       return mButtons?.size!!
    }
}