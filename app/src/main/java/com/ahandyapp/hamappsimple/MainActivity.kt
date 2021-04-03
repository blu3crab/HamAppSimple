///////////////////////////////////////////////////////////////////////////////
// MainActivity.kt by M.A.Tucker 01JAN2021
//
package com.ahandyapp.hamappsimple


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup

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
