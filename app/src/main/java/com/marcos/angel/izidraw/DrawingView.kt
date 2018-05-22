package com.marcos.angel.izidraw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View

class DrawingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var paint: Paint
    var path2: Path
    var bitmap: Bitmap
    var canvas: Canvas
    var U: Int
    //var width: Int
    //var height: Int

    init {
        var width = getContext().resources.displayMetrics.widthPixels
        var height = getContext().resources.displayMetrics.heightPixels
        U = width/20
        paint = Paint()
        paint.isDither = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 12F
        path2 = Path()
        bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444)
        canvas = Canvas(bitmap)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.GRAY
        paint.strokeWidth = 1F
        for(i in 0..width step U) {
            canvas?.drawLine(i.toFloat(), 0F, i.toFloat(), height.toFloat(), paint)
        }
        for(i in 0..height step U) {
            canvas?.drawLine(0F, i.toFloat(), width.toFloat(), i.toFloat(),paint)
        }
        paint.color = Color.BLACK
        paint.strokeWidth = 12F
        canvas?.drawPath(path2, paint)
        invalidate()
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        canvas.drawPath(path2, paint)
        var prevX = e?.x
        var prevY = e?.y

        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                path2.moveTo(e?.x, e?.y)
                prevX = e?.x
                prevY = e?.y
                //path2.lineTo(e?.x, e?.y)
            }
            MotionEvent.ACTION_MOVE -> {
                //path2.quadTo(prevX!!, prevY!!, e?.x, e?.y)
                //path2.lineTo(e?.x, e?.y)

            }
            MotionEvent.ACTION_UP -> {
                path2.lineTo(e?.x, e?.y)
                //path2.reset()
            }
        }
        invalidate()
        return true
    }

}