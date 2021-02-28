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

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ham_simple_layout: ViewGroup = findViewById(R.id.ham_simple_layout)

//        var height : Int = ham_simple_layout.measuredHeight
//        var width : Int = ham_simple_layout.measuredWidth
//        Log.d(TAG, "ham_simple_layout h/w: $height / $width")

        // find layout dimensions
        ham_simple_layout.post {
            // height & width for instance
            val height = ham_simple_layout.measuredHeight
            val width = ham_simple_layout.measuredWidth
            // kotlin println logcat labeled I/System.out
            //println("println fragment h/w: $height / $width")
            // Log logcat labeled as D/MainActivity:
            Log.d(TAG, "ham_simple_layout h/w: $height / $width")
        }
        // get HR view text
        val textHR: TextView = findViewById(R.id.text_hr)
        textHR.setText("44")
        //textHR.draw()

        // establish HAM HTTP server to rcv HR on POST & send metadata on GET
        val hamHttpServer: HamHttpServer = HamHttpServer()
        hamHttpServer.establishHttpServer(ham_simple_layout, textHR)

        ///////////////////////////////////////////////////////////////////////
        // draw heart shape
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
//        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        // rectangle positions
        var left = 0
        var top = 400
        var right = 1000
        var bottom = 1000

        // draw rectangle shape to canvas
        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds(left, top, right, bottom)
//        shapeDrawable.getPaint().setColor(Color.parseColor("#FF0000"))
        shapeDrawable.getPaint().setColor(Color.RED)
        shapeDrawable.draw(canvas)

        // oval positions
        left = 100
        top = 500
        right = 600
        bottom = 800

        // draw oval shape to canvas
        shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.setBounds(left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009191"))
        shapeDrawable.draw(canvas)

        // set bitmap as background to ImageView
        val imageView: ImageView = findViewById(R.id.imageV)
        imageView.background = BitmapDrawable(getResources(), bitmap)
    }
    //override fun onActivity
}