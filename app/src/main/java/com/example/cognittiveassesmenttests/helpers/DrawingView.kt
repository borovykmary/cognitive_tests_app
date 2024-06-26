package com.example.cognittiveassesmenttests.helpers

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * This class represents a custom view for drawing.
 * It includes functionality for drawing on the view, clearing the canvas, and saving the canvas as a bitmap.
 */
class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mDrawPath: Path = Path()
    private var mDrawPaint: Paint = Paint()
    private var mCanvasPaint: Paint = Paint(Paint.DITHER_FLAG)
    private var mDrawCanvas: Canvas? = null
    private var mCanvasBitmap: Bitmap? = null

    init {
        setupDrawing()
    }

    private fun setupDrawing() {
        mDrawPaint.color = Color.BLACK
        mDrawPaint.isAntiAlias = true
        mDrawPaint.strokeWidth = 10f
        mDrawPaint.style = Paint.Style.STROKE
        mDrawPaint.strokeJoin = Paint.Join.ROUND
        mDrawPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mDrawCanvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)
        canvas.drawPath(mDrawPath, mDrawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                mDrawPath.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                mDrawCanvas!!.drawPath(mDrawPath, mDrawPaint)
                mDrawPath.reset()
            }
            else -> return false
        }
        invalidate()
        return true
    }

    fun clearCanvas() {
        if (mDrawCanvas != null) {
            mDrawCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            invalidate()
        }
    }

    fun saveCanvas(): Bitmap {
    // Create a blank bitmap with the same dimensions as the view
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        // Draw the bitmap that is being used as the drawing surface onto the canvas
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)


        return bitmap
    }
}