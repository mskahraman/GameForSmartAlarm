package com.example.mehmetsabir.puzzlegamekotlin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.solver.widgets.Helper
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridView

class GestureDetectGridView : GridView {

    private var gDetector: GestureDetector? = null
    private var mFlingConfirmed = false
    private var mTouchX: Float = 0.toFloat()
    private var mTouchY: Float = 0.toFloat()
    private val SWIPE_MIN_DISTANCE = 100
    private val SWIPE_MAX_OFF_PATH = 100
    private val SWIPE_THRESHOLD_VELOCITY = 100

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context)
    }


    private fun init(context: Context) {
        gDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(event: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                velocityY: Float
            ): Boolean {
                val position = this@GestureDetectGridView.pointToPosition(Math.round(e1.x), Math.round(e1.y))

                if (Math.abs(e1.y - e2.y) > SWIPE_MAX_OFF_PATH) {
                    if (Math.abs(e1.x - e2.x) > SWIPE_MAX_OFF_PATH || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false
                    }
                    if (e1.y - e2.y > SWIPE_MIN_DISTANCE) {
                        HelperPuzzle.instance.moveTiles(context, PuzzleActivity.up, position)
                    } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE) {
                        HelperPuzzle.instance.moveTiles(context, PuzzleActivity.down, position)
                    }
                } else {
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false
                    }
                    if (e1.x - e2.x > SWIPE_MIN_DISTANCE) {
                        HelperPuzzle.instance.moveTiles(context, PuzzleActivity.left, position)
                    } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE) {
                       HelperPuzzle.instance.moveTiles(context, PuzzleActivity.right, position)
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.actionMasked
        gDetector?.onTouchEvent(ev)

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.x
            mTouchY = ev.y
        } else {

            if (mFlingConfirmed) {
                return true
            }

            val dX = Math.abs(ev.x - mTouchX)
            val dY = Math.abs(ev.y - mTouchY)
            if (dX > SWIPE_MIN_DISTANCE || dY > SWIPE_MIN_DISTANCE) {
                mFlingConfirmed = true
                return true
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return gDetector?.onTouchEvent(ev)!!
    }

}