package com.ahandyapp.hamappsimple

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ham_simple_layout: ViewGroup = findViewById(R.id.ham_simple_layout)

        // establish HAM HTTP server to rcv HR on POST & send metadata on GET
        val hamHttpServer: HamHttpServer = HamHttpServer()
        hamHttpServer.establishHttpServer(ham_simple_layout)

//        val timer = Timer("stale timer check", true)
//        timer.schedule(8000) {
//            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//            val currentDate = sdf.format(Date())
//            Log.d(TAG, "=============stale timer check schedule -> $currentDate")
//        }
//        timer.scheduleAtFixedRate(8000, 8000) {
//            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
//            val currentDate = sdf.format(Date())
//            Log.d(TAG, "=============stale timer check fixed rate -> $currentDate")
//        }
    }
}
