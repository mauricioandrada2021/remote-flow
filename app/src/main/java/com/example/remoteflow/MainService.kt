/*
 * Copyright (c) 2024.
 * This file is part of RemoteFlow.
 *
 * RemoteFlow is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * RemoteFlow is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with RemoteFlow. If not, see <https://www.gnu.org/licenses/>.
 */

package com.example.remoteflow

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.remoteflowlib.RemoteSharedFlow
import com.example.remoteflowlib.remoteSharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainService: Service() {

    private val tag = "Main Service"

    private lateinit var remoteSharedFlow: RemoteSharedFlow
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        remoteSharedFlow = remoteSharedFlow()
        coroutineScope.launch {
            remoteSharedFlow.flow().collect {
                println("$tag $it")
                remoteSharedFlow.emit("$tag Received: $it")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return remoteSharedFlow.asBinder()
    }
}