package com.ahandyapp.hamappsimple

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class HamMessage(
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("msgSequence")
        var msgSequence: String? = null,
        @SerializedName("timestamp")
        var timestamp: String? = null,
        @SerializedName("heartRate")
        var heartRate: IntArray? = null)
{
    //data class HamMetricRaw(val incomingJson: String) {
    // {"title":"Heart Rate Metrics",
    // "msgSequence":2520,
    // "isTest":true,
    // "timestamp":"xx:yy",
    // "records":[1,2,3,4],
    // "heartRate":[91,91,90,90,90,89,88,88]}
}
