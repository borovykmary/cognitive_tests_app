package com.example.cognittiveassesmenttests.helpers
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.core.content.ContextCompat

fun drawableToBitmap(drawableId: Int, context: Context): Bitmap {
    val drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun blurBitmap(bitmap: Bitmap, context: Context, scaleSize: Float = 0.1f, blurRadius: Float = 25f): Bitmap {
    // Scale down the image
    val width = (bitmap.width * scaleSize).toInt()
    val height = (bitmap.height * scaleSize).toInt()
    val smallBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

    // Create a new bitmap to use for the blurred result
    val outputBitmap = Bitmap.createBitmap(smallBitmap)

    // Create a RenderScript
    val rs = RenderScript.create(context)

    // Create an allocation for Renderscript to work with
    val input = Allocation.createFromBitmap(rs, smallBitmap)
    val output = Allocation.createFromBitmap(rs, outputBitmap)

    // Create a blur script
    val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    // Set the blur radius
    script.setRadius(blurRadius)

    // Set the input and output for the script
    script.setInput(input)
    script.forEach(output)

    // Copy the output to the blurred bitmap
    output.copyTo(outputBitmap)

    // Release memory allocations
    input.destroy()
    output.destroy()
    script.destroy()
    rs.destroy()

    // Scale the image back to its original size
    return Bitmap.createScaledBitmap(outputBitmap, bitmap.width, bitmap.height, true)
}