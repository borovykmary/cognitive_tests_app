package com.example.cognittiveassesmenttests

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class MiniAceTest2 : Fragment() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mini_ace_test2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message)

    val playButton = view.findViewById<ImageView>(R.id.play_button)
    playButton.setOnClickListener {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        } else {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message)
        }
    }
}
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


    }
