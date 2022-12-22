package com.example.musicplay

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.net.toUri
import com.example.musicplay.databinding.ActivityPlayerBinding
import com.gauravk.audiovisualizer.visualizer.BarVisualizer
import java.lang.Exception

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private var currentPosition: Int = -1
    private lateinit var runnable: Runnable
    private val handler = Handler()

    companion object{
        var mediaPlayer = MyMediaPlayer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Now Playing")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.songNameTV.setSelected(true)

        mediaPlayer.setOnCompletionListener {
            playSong(true)
        }

        currentPosition = intent.getIntExtra("Position", 0)
        if (mediaPlayer.getDataSource() != MainActivity.songsDataArrayList[currentPosition])
        {
            playSong(true, currentPosition)
        }
        else
        {
            playSong(true, currentPosition, true)
        }

        binding.playBT.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.playBT.setImageResource(R.drawable.ic_play)
            }
            else{
                mediaPlayer.start()
                binding.playBT.setImageResource(R.drawable.ic_pause)
            }
        }

        binding.nextBT.setOnClickListener {
            playSong(true)
        }

        binding.prevBT.setOnClickListener {
            playSong(false)

        }

        binding.forwardBT.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition+10000)
        }

        binding.rewindBT.setOnClickListener {
            mediaPlayer.seekTo(mediaPlayer.currentPosition-10000)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.start()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar!!.progress)
            }

        })
    }

    fun playSong(next: Boolean, position: Int? = null, playing: Boolean? = null){
        if(position != null){
            currentPosition = position
        }
        else {
            if (next == true) {
                currentPosition += 1
                if (currentPosition >= MainActivity.songsNameArrayList.size) {
                    currentPosition = 0
                }
            } else {
                currentPosition -= 1
                if (currentPosition < 0) {
                    currentPosition = MainActivity.songsNameArrayList.size - 1
                }
            }
        }

        if(playing == false || playing == null) {
            startAnimation(binding.playerIV)
            binding.songNameTV.text = MainActivity.songsNameArrayList[currentPosition]
            mediaPlayer.reset()
            mediaPlayer.setDataSource(MainActivity.songsDataArrayList[currentPosition])
            mediaPlayer.prepare()
            mediaPlayer.start()
            initializeSeekBar()
            binding.playBT.setImageResource(R.drawable.ic_pause)
        }
        else
        {
            startAnimation(binding.playerIV)
            binding.songNameTV.text = MainActivity.songsNameArrayList[currentPosition]
            initializeSeekBar()
            binding.playBT.setImageResource(R.drawable.ic_pause)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.barVisualizer.setAudioSessionId(mediaPlayer.audioSessionId)
    }

    override fun onPause() {
        binding.barVisualizer.release()
        super.onPause()

    }

    fun startAnimation(imageView: ImageView){
        val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
        animator.setDuration(2000)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator)
        animatorSet.start()
    }

    fun initializeSeekBar(){
        binding.seekBar.max = mediaPlayer.duration
        runnable = Runnable {
            binding.seekBar.progress = mediaPlayer.currentPosition
            binding.startTV.text = timeConverter(mediaPlayer.currentPosition)
            val diff = mediaPlayer.duration - mediaPlayer.currentPosition
            binding.stopTV.text = timeConverter(diff)
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

}



