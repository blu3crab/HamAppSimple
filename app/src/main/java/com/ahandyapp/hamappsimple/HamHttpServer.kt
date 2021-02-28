package com.ahandyapp.hamappsimple

import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
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

    private var hamMessage = HamMessage()

    // establish HTTP server for HAM GET & POST
    fun establishHttpServer(ham_simple_layout: ViewGroup, textHR: TextView) {
//        fun establishHttpServer(dashboardViewModel: DashboardViewModel) {
        embeddedServer(Netty, 8080) {
            install(ContentNegotiation) {
                gson {}
            }
            routing {
                Log.d(TAG, "embeddedServer routing rcv'd")
                get("/about") {
//                    call.respond(mapOf("message" to "Hello world"))
                    val appMap = mapOf("appTitle" to "HeartActivityMonitor", "version" to "24JAN2021-16:31", "token" to "HAMster")
                    call.respond(appMap)
                    Log.d(TAG, "embeddedServer /about appMap keys: ${appMap.keys}")
                    Log.d(TAG, "embeddedServer /about appMap values: ${appMap.values}")
                    for (appMapKey in appMap.keys) {
                        appMap[appMapKey]?.let { it1 -> Log.d(TAG, "$appMapKey: $it1") }
                    }
                }
                post("/heartRate") {
                    val text: String = call.receiveText()
                    Log.d(TAG, "embeddedServer /heartRate post-receive -> $text")
                    call.respondText(HttpStatusCode.OK.toString())

                    // decode incoming HR metrics
                    hamMessage = Gson().fromJson<HamMessage>(text, HamMessage::class.java)
                    Log.d(TAG, "JSON timestamp, heartRate -> ${hamMessage.timestamp}, ${hamMessage.heartRate?.get(0)}")
                    // set heart rate view text
                    textHR.setText(hamMessage.heartRate?.get(0).toString())
                    ham_simple_layout.invalidate()
                }
            }

        }.start(wait = false)

    }
}