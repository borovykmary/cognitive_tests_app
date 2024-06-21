package com.example.cognittiveassesmenttests

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast


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

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message0)
        var counter: Int = 0
        var mediaCounter = 0
        val randomValue = (0..1).random()

    val playButton = view.findViewById<ImageView>(R.id.play_button)

            playButton.setOnClickListener {
                if (counter < 3) {

                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                        counter++
                    } else {
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        if(mediaCounter == 0) {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_start)
                            mediaCounter++
                        } else {
                            if(randomValue < 0.5) {
                                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message0)
                            } else {
                                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message1)
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "You cannot listen to audio anymore", Toast.LENGTH_SHORT).show()
                }
            }
        }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


}
