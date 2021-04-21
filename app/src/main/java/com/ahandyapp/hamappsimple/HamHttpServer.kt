///////////////////////////////////////////////////////////////////////////////
// HamHttpServer.kt by M.A.Tucker 01JAN2021
///////////////////////////////////////////////////////////////////////////////
package com.ahandyapp.hamappsimple

import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


class HamHttpServer {
    private val TAG = "HamHttpServer"

    val TEST_TEXT_ENABLED = false
    val TEST_HR_TEXT = "123"
    val TEST_CLOCK_TEXT = "12:34:56"
    val TEST_TEXT_COLOR = Color.YELLOW

    val DEFAULT_HR_TEXT = "???"
    val DEFAULT_CLOCK_TEXT = "hh:mm:ss"
    val DEFAULT_TEXT_COLOR = Color.WHITE

    private var hamMessage = HamMessage()

    // establish HTTP server for HAM GET & POST
    fun establishHttpServer(ham_simple_layout: ViewGroup) {
        val textHR: TextView = ham_simple_layout.findViewById(R.id.text_hr)
        val textTimestamp: TextView = ham_simple_layout.findViewById(R.id.text_timestamp)
        textHR.setText(DEFAULT_HR_TEXT)
        textTimestamp.setText(DEFAULT_CLOCK_TEXT)
        textHR.setTextColor(DEFAULT_TEXT_COLOR)
        textTimestamp.setTextColor(DEFAULT_TEXT_COLOR)
        if (TEST_TEXT_ENABLED) {
            textHR.setText(TEST_HR_TEXT)
            textTimestamp.setText(TEST_CLOCK_TEXT)
            textHR.setTextColor(TEST_TEXT_COLOR)
            textTimestamp.setTextColor(TEST_TEXT_COLOR)
        }

        var toggle = -1

        embeddedServer(Netty, 8080) {
            install(ContentNegotiation) {
                gson {}
            }
            routing {
                Log.i(TAG, "embeddedServer routing rcv'd")
                get("/about") {
//                    call.respond(mapOf("message" to "Hello world"))
                    val appMap = mapOf("appTitle" to "HeartActivityMonitor", "version" to "24JAN2021-16:31", "token" to "HAMster")
                    call.respond(appMap)
                    Log.i(TAG, "embeddedServer /about appMap keys: ${appMap.keys}")
                    Log.i(TAG, "embeddedServer /about appMap values: ${appMap.values}")
                    for (appMapKey in appMap.keys) {
                        appMap[appMapKey]?.let { it1 -> Log.i(TAG, "$appMapKey: $it1") }
                    }
                }
                post("/heartRate") {
                    val text: String = call.receiveText()
                    Log.i(TAG, "embeddedServer /heartRate post-receive -> $text")
                    call.respondText(HttpStatusCode.OK.toString())

                    // decode incoming HR metrics
                    hamMessage = Gson().fromJson<HamMessage>(text, HamMessage::class.java)
                    Log.i(TAG, "JSON timestamp, heartRate -> ${hamMessage.timestamp}, ${hamMessage.heartRate?.get(0)}")
                    // set heart rate view text
                    var heartRateCount = hamMessage.heartRate?.size
                    if (heartRateCount != null) {
                        var offbody = false
                        for (i in 0..heartRateCount-1) {
                            if (hamMessage.heartRate?.get(heartRateCount - 1) == 0) offbody = true;
                        }
                        if (!offbody) {
                            textHR.text = hamMessage.heartRate?.get(heartRateCount - 1).toString()
                        }
                        else {
                            textHR.text = "---"
                        }
                        Log.i(TAG, "embeddedServer /heartRate count -> $heartRateCount")
                    }
                    // set timestamp text
                    textTimestamp.text = hamMessage.timestamp
                    ham_simple_layout.invalidate()
                }
            }

        }.start(wait = false)
    }
}