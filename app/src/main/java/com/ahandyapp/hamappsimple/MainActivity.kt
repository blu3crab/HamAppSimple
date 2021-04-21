///////////////////////////////////////////////////////////////////////////////
// MainActivity.kt by M.A.Tucker 01JAN2021
//
package com.ahandyapp.hamappsimple


import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowMetrics
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ham_simple_layout: ViewGroup = findViewById(R.id.ham_simple_layout)

        var isBigDisplay = setViewModel(this, ham_simple_layout)

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

    fun setViewModel(activity: Activity, ham_simple_layout: ViewGroup): Boolean {
        val textHR: TextView = ham_simple_layout.findViewById(R.id.text_hr)
        val textTimestamp: TextView = ham_simple_layout.findViewById(R.id.text_timestamp)
        // test if phone or tablet sized screen display
        var isBigDisplay = isBigDisplay(activity)
        // assign text size based on screen size
        if (isBigDisplay) {
//            textHR.textSize = resources.getDimensionPixelSize(R.dimen.hr_textsize_tablet).toFloat()
//            textTimestamp.textSize = resources.getDimensionPixelSize(R.dimen.hr_textsize_tablet).toFloat()
            textHR.textSize = 144.0F
            textTimestamp.textSize = 80.0F
        }
        else {
//            textHR.textSize = resources.getDimensionPixelSize(R.dimen.hr_textsize_phone).toFloat()
//            textTimestamp.textSize = resources.getDimensionPixelSize(R.dimen.hr_textsize_phone).toFloat()
            textHR.textSize = 80.0F
            textTimestamp.textSize = 48.0F
        }
        Log.d(TAG, "isBigDisplay -> HR text size: ${textHR.textSize}, clock text size: ${textTimestamp.textSize}")

        return isBigDisplay
    }
    // test if phone or tablet sized screen display
    fun isBigDisplay(activity: Activity): Boolean {
        var testResult = false
        var (widthPixel, heightPixel, diagonalInches) = getScreenDimensions(activity)

        if (diagonalInches > 7.0) testResult = true
        Log.d(TAG, "isBigDisplay -> ${testResult}")
        return testResult
    }

    fun getScreenDimensions(activity: Activity): Triple<Int, Int, Double>
    {
        var widthPixel = 0
        var heightPixel = 0
        val displayMetrics = DisplayMetrics()
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        widthPixel = displayMetrics.widthPixels
        heightPixel = displayMetrics.heightPixels

        val yInches = heightPixel / displayMetrics.ydpi
        val xInches = widthPixel / displayMetrics.xdpi
        val diagonalInches = Math.sqrt((xInches * xInches + yInches * yInches).toDouble())
        Log.d(TAG, "isBigDisplay -> pixel width/height: ${widthPixel}/${heightPixel} with diag inches: ${diagonalInches}" )
        return Triple(widthPixel, heightPixel, diagonalInches)
    }

}
