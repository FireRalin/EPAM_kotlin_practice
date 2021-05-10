package com.etu.epam_practice

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.etu.epam_practice.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var runnable: Runnable
    private var handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mediaplayer = MediaPlayer.create(this,R.raw.morg)

        binding.seekbar.progress = 0
        binding.seekbar.max = mediaplayer.duration



        binding.stopStarButton.setOnClickListener{
            val temp = null
            if (!mediaplayer.isPlaying){
                mediaplayer.start()
                binding.stopStarButton.setImageResource(R.drawable.ic_baseline_pause_40)
            }else{
                mediaplayer.pause()
                binding.stopStarButton.setImageResource(R.drawable.ic_baseline_play_arrow_40)
            }
        }

        binding.previosButton.setOnClickListener{
            val temp = null
        }

        binding.nextButton.setOnClickListener{
            val temp = null
        }

        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    mediaplayer.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        fun createTimeLabel(time: Int): String{
            var timeLabel = ""
            var min = time/1000/60
            var sec = time/1000%60

            timeLabel = "$min:"
            if(sec<10) timeLabel += "0"
            timeLabel += sec

            return timeLabel
        }

        runnable = Runnable {
            binding.seekbar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
            var elapsedTime = createTimeLabel(mediaplayer.currentPosition)
            binding.elapsedTimeLabel.text = elapsedTime
            var remainingTime = createTimeLabel(mediaplayer.duration - mediaplayer.currentPosition)
            binding.remainingTimeLabel.text = "-$remainingTime"
        }
        handler.postDelayed(runnable, 1000)




        mediaplayer.setOnCompletionListener {
            binding.stopStarButton.setImageResource(R.drawable.ic_baseline_play_arrow_40)
            binding.seekbar.progress = 0
        }



    }
}