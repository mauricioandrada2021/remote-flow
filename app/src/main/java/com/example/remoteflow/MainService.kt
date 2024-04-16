package com.example.remoteflow

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.remoteflowlib.RemoteSharedFlow
import com.example.remoteflowlib.remoteSharedFlow

class MainService: Service() {

    private val tag = "Main Service"

    private lateinit var remoteSharedFlow: RemoteSharedFlow

    override fun onCreate() {
        super.onCreate()
        remoteSharedFlow = remoteSharedFlow()
        remoteSharedFlow.flow {
            println("$tag $it")
            remoteSharedFlow.emit("$tag Received: $it")
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return remoteSharedFlow.asBinder()
    }
}