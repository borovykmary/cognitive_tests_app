package com.example.cognittiveassesmenttests

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.cognittiveassesmenttests.helpers.DrawingView
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MiniAceTest4.newInstance] factory method to
 * create an instance of this fragment.
 */
class MiniAceTest4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mini_ace_test4, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawingView = view.findViewById<DrawingView>(R.id.drawingView)

        val downloadButton = view.findViewById<Button>(R.id.downloadButton)
        val clearButton = view.findViewById<Button>(R.id.clearButton)

        clearButton.setOnClickListener {
            drawingView.clearCanvas()
        }
        val bitmap = drawingView.saveCanvas()


        // Save the bitmap to the device's storage when the download button is clicked
        if (bitmap != null) {
            // Save the bitmap to the device's storage when the download button is clicked
            downloadButton.setOnClickListener {
                val filename = "drawing.png"
                val uri = saveImage(bitmap, filename, requireContext())
                Toast.makeText(requireContext(), "Image saved to $uri", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun saveImage(bitmap: Bitmap, filename: String, context: Context): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, filename, null)
    return Uri.parse(path)

}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MiniAceTest4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MiniAceTest4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}