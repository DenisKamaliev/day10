package com.example.lesson10

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private lateinit var assetManager: AssetManager

    private var catSound: Int = 0
    private var chickenSound: Int = 0
    private var cowSound: Int = 0
    private var dogSound: Int = 0
    private var duckSound: Int = 0
    private var sheepSound: Int = 0

    private var streamID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catImage: ImageView = findViewById(R.id.image_cat)
        val chickenImage: ImageView = findViewById(R.id.image_chicken)
        val cowImage: ImageView = findViewById(R.id.image_cow)
        val dogImage: ImageView = findViewById(R.id.image_dog)
        val duckImage: ImageView = findViewById(R.id.image_duck)
        val sheepImage: ImageView = findViewById(R.id.image_sheep)

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        assetManager = assets
        catSound = loadSound("cat.mp3")
        chickenSound = loadSound("chicken.mp3")
        cowSound = loadSound("cow.mp3")
        dogSound = loadSound("dog.mp3")
        duckSound = loadSound("duck.mp3")
        sheepSound = loadSound("sheep.mp3")

        catImage.setOnClickListener { playSound(catSound) }
        chickenImage.setOnClickListener { playSound(chickenSound) }
        cowImage.setOnClickListener { playSound(cowSound) }
        dogImage.setOnClickListener { playSound(dogSound) }
        duckImage.setOnClickListener { playSound(duckSound) }
        sheepImage.setOnClickListener { playSound(sheepSound) }
    }

    override fun onPause() {
        super.onPause()

        soundPool.release()
    }

    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            streamID = soundPool.play(sound, 1F, 1F, 1, 0, 1F)
        }
        return streamID
    }

    private fun loadSound(fileName: String): Int {
        val afd: AssetFileDescriptor = try {
            application.assets.openFd(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Meow", "Не могу загрузить файл $fileName")

            return -1
        }
        return soundPool.load(afd, 1)
    }
}
