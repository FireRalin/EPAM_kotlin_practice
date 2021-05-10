package com.etu.epam_practice

import android.R.raw
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.etu.epam_practice.databinding.ActivityMainBinding
import java.lang.reflect.Field
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var runnable: Runnable
    private var handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var currentSong = 0

        val fields: Array<Field> = R.raw::class.java.getFields()
        var songsList: MutableList<Pair<Int, String>> = mutableListOf()
        fields.forEach {field ->
            Log.d("Knames", String.format(
                "name=\"%s\", id=0x%08x",
                field.name, field.getInt(field))
            )
            songsList.add(field.getInt(field) to field.name)
        }

        val drawables: Array<Field> = R.drawable::class.java.getFields()
        var coversList: MutableList<Pair<Int, String>> = mutableListOf()
        drawables.forEach {field ->
            Log.d("COVER names", String.format(
                "name=\"%s\", id=0x%08x",
                field.name, field.getInt(field))
            )
            if (field.name.endsWith("_cover")) {
                coversList.add(field.getInt(field) to field.name)
            }
        }
        var mediaplayer = MediaPlayer.create(this,songsList[currentSong].first)

        fun changeSong(info: Pair<Int, String>, isPlaying: Boolean = false){
            mediaplayer = MediaPlayer.create(this, info.first)

            binding.seekbar.progress = 0
            binding.seekbar.max = mediaplayer.duration
            binding.textView.text = info.second
            binding.imageView.setImageResource(coversList[currentSong].first)

            mediaplayer.setOnCompletionListener {
                if (currentSong < songsList.size - 1){
                    changeSong(songsList[++currentSong], true)
                }
                else{
                    binding.stopStarButton.setImageResource(R.drawable.ic_baseline_play_arrow_40)
                    binding.seekbar.progress = 0
                }

            }
            if (isPlaying)
                mediaplayer.start()
        }

        binding.seekbar.progress = 0
        binding.seekbar.max = mediaplayer.duration
        binding.textView.text = songsList[currentSong].second
        binding.imageView.setImageResource(coversList[currentSong].first)

        mediaplayer.setOnCompletionListener {
            if (currentSong < songsList.size - 1){
                changeSong(songsList[++currentSong], true)
            }
            else{
                binding.stopStarButton.setImageResource(R.drawable.ic_baseline_play_arrow_40)
                binding.seekbar.progress = 0
            }
        }



        binding.stopStarButton.setOnClickListener{
            if (!mediaplayer.isPlaying){
                mediaplayer.start()
                binding.stopStarButton.setImageResource(R.drawable.ic_baseline_pause_40)
            }else{
                mediaplayer.pause()
                binding.stopStarButton.setImageResource(R.drawable.ic_baseline_play_arrow_40)
            }
        }

        binding.previosButton.setOnClickListener{
            if (currentSong > 0){
                currentSong--
                if (!mediaplayer.isPlaying){
                    changeSong(songsList[currentSong], false)
                }else{
                    mediaplayer.pause()
                    changeSong(songsList[currentSong], true)
                }
            }

        }

        binding.nextButton.setOnClickListener{
            if (currentSong < songsList.size-1){
                currentSong++
                if (!mediaplayer.isPlaying){
                    changeSong(songsList[currentSong], false)
                }else{
                    mediaplayer.pause()
                    changeSong(songsList[currentSong], true)
                }
            }
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




    }
}