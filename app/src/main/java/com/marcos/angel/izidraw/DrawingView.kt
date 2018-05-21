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


    var paint: Paint = Paint()
    var mPath: Path = Path()
    var circlePath: Path = Path()
    var mCanvas: Canvas = Canvas()
    lateinit var mBitmap: Bitmap
    var mBitmapPaint: Paint
    var circlePaint: Paint
    init {
        var display = getContext().resources.displayMetrics
        var width = display.widthPixels
        var height = display.heightPixels
        var paint: Paint = Paint()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)

        circlePaint = Paint()
        circlePaint.isAntiAlias = true
        circlePaint.color = Color.BLUE
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeJoin = Paint.Join.MITER
        circlePaint.strokeWidth = 4F
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 12F

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }


    fun start() {

    }

    fun update() {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
        canvas.drawBitmap(mBitmap, 0F, 0F, mBitmapPaint)
        canvas.drawPath(mPath, paint)
        canvas.drawPath(circlePath, circlePaint)
        }
    }

    var mX: Float = 0F
    var mY: Float = 0F
    val TOUCHTOLERANCE: Float = 4F

    private fun touch_start(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touch_move(x: Float, Y: Float) {
        var dx: Float = Math.abs(x - mX)
        var dy: Float = Math.abs(y - mY)
        if(dx >= TOUCHTOLERANCE || dy >= TOUCHTOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2)
            mX = x
            mY = y

            circlePath.reset()
            circlePath.addCircle(mX, mY, 30F, Path.Direction.CW)
        }
    }

    private fun touch_up() {
        mPath.lineTo(mX, mY)
        circlePath.reset()

        mCanvas.drawPath(mPath, paint)

        mPath.reset()
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        Log.d("TAG", "${e?.x} ${e?.y}")
        var x: Float? = e?.x
        var y: Float? = e?.y
        val DOWN = MotionEvent.ACTION_DOWN
        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                touch_start(x!!, y!!)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touch_move(x!!, y!!)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touch_up()
                invalidate()
            }
        }
        return true
    }
}