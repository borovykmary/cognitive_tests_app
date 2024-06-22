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

/**
 * This fragment represents the second test in the MiniAce series.
 * It includes functionality for playing audio files and limiting the number of times they can be played.
 * The layout for this fragment is defined in `R.layout.fragment_mini_ace_test2`.
 */
class MiniAceTest2 : Fragment() {

    private lateinit var mediaPlayer: MediaPlayer

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
        var counter: Int = 0
        var mediaCounter = 0
        val randomValue = (0..3).random()
        mediaPlayer = MediaPlayer()

    val playButton = view.findViewById<ImageView>(R.id.play_button)

        playButton.setOnClickListener {
            if (counter < 3) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                }

                if(mediaCounter == 0) {
                    when (randomValue) {
                        1 -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_1_start)
                        }
                        2 -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_2_start)
                        }
                        else -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_3_start)
                        }
                    }
                    mediaCounter++
                } else {
                    when (randomValue) {
                        1 -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_1_end)
                        }
                        2 -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_2_end)
                        }
                        else -> {
                            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.voice_message_pair_3_end)
                        }
                    }
                }
                mediaPlayer.start()
                counter++
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
