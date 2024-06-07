package com.example.cognittiveassesmenttests

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognittiveassesmenttests.SDMTTest.SDMTInfoActivity
import com.example.cognittiveassesmenttests.MiniAceTest.MiniAceInfoActivity
import com.example.cognittiveassesmenttests.SDMTTest.SDMTInfoActivity
import com.example.cognittiveassesmenttests.cardsTest.CardInfoActivity
import com.example.cognittiveassesmenttests.helpers.blurBitmap
import com.example.cognittiveassesmenttests.helpers.drawableToBitmap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.imageView3)
        val bitmap = drawableToBitmap(R.drawable.bg_gradient_home, requireContext())
        val blurredBitmap = blurBitmap(bitmap, requireContext(), 0.1f, 25f)
        imageView.setImageBitmap(blurredBitmap)
        var MiniAceButton = view.findViewById<Button>(R.id.MiniAceDetailsButton)
        MiniAceButton.setOnClickListener {
            val intent = Intent(activity, MiniAceInfoActivity::class.java)
            startActivity(intent)
        }
        var cardsButton = view.findViewById<Button>(R.id.CardSortDetailsButton)
        cardsButton.setOnClickListener {
            val intent = Intent(activity, CardInfoActivity::class.java)
            startActivity(intent)
        }
        var SymbolsDigitButton = view.findViewById<Button>(R.id.SymbolDigitDetailsButton)
        SymbolsDigitButton.setOnClickListener {
            val intent = Intent(activity, SDMTInfoActivity::class.java)
            startActivity(intent)
        }
    }
}

