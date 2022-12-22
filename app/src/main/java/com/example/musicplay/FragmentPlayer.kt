package com.example.musicplay

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplay.databinding.FragmentPlayerBinding
import kotlin.random.Random

class FragmentPlayer : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val mediaMetadataRetriever = MediaMetadataRetriever()

        binding.songIconIV.setOnClickListener {
            if (PlayerActivity.mediaPlayer.isPlaying) {
                binding.songIconIV.setImageResource(R.drawable.ic_play)
                PlayerActivity.mediaPlayer.pause()
            } else {
                binding.songIconIV.setImageResource(R.drawable.ic_pause)
                PlayerActivity.mediaPlayer.start()
            }
        }
        if (PlayerActivity.mediaPlayer.isPlaying) {
            binding.songIconIV.setImageResource(R.drawable.ic_pause)
            runnable = Runnable {
                binding.seekBar.max = PlayerActivity.mediaPlayer.duration
                binding.seekBar.progress = PlayerActivity.mediaPlayer.currentPosition

                mediaMetadataRetriever.setDataSource(PlayerActivity.mediaPlayer.getDataSource())
                binding.songNameTV.setText(
                    "${
                        mediaMetadataRetriever.extractMetadata(
                            MediaMetadataRetriever.METADATA_KEY_ARTIST
                        )
                    }: ${mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)}"
                )
                handler.postDelayed(runnable, 1000)
            }
            handler.postDelayed(runnable, 1000)
        }

        binding.songCardView.setOnClickListener {
            if (binding.songNameTV.text != "Nothing is playing right now...") {
                val intent = Intent(context, PlayerActivity::class.java)
                intent.putExtra(
                    "Data",
                    MainActivity.songsDataArrayList[MainActivity.songsDataArrayList.indexOf(
                        PlayerActivity.mediaPlayer.getDataSource()
                    )]
                )
                intent.putExtra(
                    "Name",
                    MainActivity.songsNameArrayList[MainActivity.songsDataArrayList.indexOf(
                        PlayerActivity.mediaPlayer.getDataSource()
                    )]
                )
                intent.putExtra(
                    "Position",
                    MainActivity.songsDataArrayList.indexOf(PlayerActivity.mediaPlayer.getDataSource())
                )

                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}