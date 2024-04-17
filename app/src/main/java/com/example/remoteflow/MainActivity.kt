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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.remoteflow.theme.RemoterFlowTheme
import com.example.remoteflowlib.RemoteSharedFlow
import com.example.remoteflowlib.remoteSharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val tag = "Main Activity"

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private lateinit var remoteSharedFlow: RemoteSharedFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoterFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        remoteSharedFlow = remoteSharedFlow(
            this,
            "com.example.remoteflow",
            "com.example.remoteflow.MainService"
        )

        coroutineScope.launch {
            remoteSharedFlow.flow {
                println("$tag Response1: $it")
            }
        }

        coroutineScope.launch {
            remoteSharedFlow.flow {
                println("$tag Response2: $it")
            }
        }

        coroutineScope.launch {
            for (i in 1..100) {
                println("$tag sent: Hello there")
                remoteSharedFlow.emit("$tag Hello there")
                delay(3000)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RemoterFlowTheme {
        Greeting("Android")
    }
}