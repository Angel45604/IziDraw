package com.marcos.angel.izidraw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button

class DrawingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var paint: Paint
    var path1: Path
    var path2: Path
    var path3: Path
    var path4: Path
    var bitmap: Bitmap
    var canvas: Canvas
    var U: Int
    var clearPath: Int = 0
    var lines1: ArrayList<LineModel>
    var lines2: ArrayList<LineModel>
    //var width: Int
    //var height: Int

    init {
        var width = getContext().resources.displayMetrics.widthPixels
        var height = getContext().resources.displayMetrics.heightPixels
        //clearPath = false
        lines1 = ArrayList()
        lines2 = ArrayList()
        U = width/20
        paint = Paint()
        paint.isDither = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 12F
        path1 = Path()
        path2 = Path()
        path3 = Path()
        path4 = Path()
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)



    }

    fun angleTo (x1: Float, y1: Float, angle: Int, length: Int): DoubleArray {
        var xy = DoubleArray(2)
        xy[0] = (x1 + length * Math.sin(angle * Math.PI / 180))
        xy[1] = (y1 + length * Math.cos(angle + Math.PI / 180))
        return xy
    }


    fun m (x: Float, y: Float, x2: Float, y2: Float): Float {
        return Math.abs((y -y2) / (x - x2))
    }


    fun remove1() {
        path1.reset()
        lines1.clear()
    }

    fun remove2() {
        path2.reset()
        lines2.clear()
    }

    fun remove3() {
        path3.reset()
    }

    fun isHorizontal(x1: Float, x2: Float, y1: Float, y2: Float): Boolean {
        return (y1 == y2)
    }

    fun isVertical(x1: Float, x2: Float, y1: Float, y2: Float): Boolean {
        return (x1 == x2)
    }

    fun generateIsometric() {
        path4.moveTo(width / 2.toFloat(), height /2.toFloat())
        var prevX = width /2.toFloat()
        var prevY = height /2.toFloat()
        for(i in lines2) {
            var angle = 0
            var length = Math.pow(Math.pow(i.x2 - i.x1.toDouble(), 2.toDouble())
                    + Math.pow(i.y2 - i.y1.toDouble(), 2.toDouble()),
                    1/2.toDouble())
            if(isHorizontal(i.x1, i.x2, i.y1, i.y2)) {
                angle = 120
            } else if(isVertical(i.x1, i.x2, i.y1, i.y2)) {
                angle = 90
            }
            var ola = angleTo(prevX, prevY.toFloat(), angle, length.toInt())
            path4.lineTo(ola[0].toFloat(), ola[1].toFloat())
            Log.d("LENGTH", "${length /U}")
            Log.d("VERTEX", "prevX: ${prevX /U} prevY: ${prevY /U}")
            Log.d("VERTEX", "X: ${ola[0] /U} Y: ${ola[1] /U}")
            prevX = ola[0].toFloat()
            prevY = ola[1].toFloat()
            path4.moveTo(prevX, prevY)
        }
    }

    fun generateView() {
        for(i in lines1) {
            Log.d("LINES1", "$i")
            Log.d("Linea", "${i.x1 + (width / 2 - i.x1) + ((U * 18) - i.y1) / U}")
            path3.moveTo(i.x1, i.y1)
            path3.lineTo(i.x1 + (width / 2 - i.x1) + ((U * 18)  - i.y1), i.y1)

            path3.moveTo(i.x1 + (width / 2 - i.x1) + ((U * 18)  - i.y1), i.y1)
            path3.lineTo(i.x1 + (width / 2 - i.x1) + ((U * 18)  - i.y1), height.toFloat())
        }

        for(i in lines2) {
            path3.moveTo(i.x1, i.y1)
            path3.lineTo(width.toFloat(), i.y1)
        }
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
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 12F

        canvas?.drawLine(width/2.toFloat(), 0F, width/2.toFloat(), height.toFloat(), paint)
        canvas?.drawLine(0F, U * 18.toFloat(), width.toFloat(), U * 18.toFloat(), paint)
        canvas?.drawLine(width / 2.toFloat(), U*18.toFloat(), angleTo(width.toFloat(), height.toFloat(), 45, U * 11)[0].toFloat(), 0F, paint)
        canvas?.drawPath(path1, paint)
        canvas?.drawPath(path2, paint)
        canvas?.drawPath(path3, paint)
        canvas?.drawPath(path4, paint)
        if(clearPath != 0) {
            //path1.reset()
        }
        invalidate()
    }
    var prevX: Float = 0F
    var prevY: Float = 0F
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        //canvas.drawPath(path2, paint)


        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                 if(e?.x <= width/2.toFloat()) {
                     prevX = e?.x
                     prevY = e?.y
                     Log.d("START", "${e?.x} ${e?.y}")
                     Log.d("START", "${e?.x / U} ${e?.y / U}")
                     //path2.lineTo(e?.x, e?.y)
                 }else {
                    clearPath = 1
                }
                //path2.moveTo(e?.x, e?.y)

            }
            MotionEvent.ACTION_MOVE -> {
                //path2.quadTo(prevX!!, prevY!!, e?.x, e?.y)
                //path2.lineTo(e?.x, e?.y)

            }
            MotionEvent.ACTION_UP -> {
                //path2.lineTo(e?.x, e?.y)
                if(e?.x <= width/2.toFloat()) {
                        if(e?.y <= height/2.toFloat()) {
                            Log.d("PATH1", "PATH1")
                            Log.d("LINE TO", "xprev: ${Math.round(prevX!! / U)} yprev: ${Math.round(prevY!! / U)} x: ${Math.round(e?.x / U)} y: ${Math.round(e?.y / U)}")
                            lines1.add(LineModel(Math.round(prevX!! / U) * U.toFloat(), Math.round(prevY!! / U) * U.toFloat(),
                                    Math.round(e?.x / U) * U.toFloat(), Math.round(e?.y / U) * U.toFloat()))
                            path1.moveTo(Math.round(prevX!! / U) * U.toFloat(), Math.round(prevY!! / U) * U.toFloat())
                            path1.lineTo(Math.round(e?.x / U) * U.toFloat(), Math.round(e?.y / U) * U.toFloat())
                            Log.d("END", "X: ${e?.x}  Y: ${e?.y} PREV = X: ${prevX!!}  Y: ${prevY!!}")
                            Log.d("END", "${e?.x /U} ${e?.y / U} M=${m(prevX!!, prevY!!, e?.x, e?.y)}")
                        } else {
                            Log.d("PATH2", "PATH2")
                            Log.d("LINE TO", "xprev: ${Math.round(prevX!! / U)} yprev: ${Math.round(prevY!! / U)} x: ${Math.round(e?.x / U)} y: ${Math.round(e?.y / U)}")
                            lines2.add(LineModel(Math.round(prevX!! / U) * U.toFloat(), Math.round(prevY!! / U) * U.toFloat(),
                                    Math.round(e?.x / U) * U.toFloat(), Math.round(e?.y / U) * U.toFloat()))
                            path2.moveTo(Math.round(prevX!! / U) * U.toFloat(), Math.round(prevY!! / U) * U.toFloat())
                            path2.lineTo(Math.round(e?.x / U) * U.toFloat(), Math.round(e?.y / U) * U.toFloat())
                            Log.d("END", "X: ${e?.x}  Y: ${e?.y} PREV = X: ${prevX!!}  Y: ${prevY!!}")
                            Log.d("END", "${e?.x /U} ${e?.y / U} M=${m(prevX!!, prevY!!, e?.x, e?.y)}")

                        }
                    }
                //path2.reset()
            }
        }
        invalidate()
        return true
    }

}