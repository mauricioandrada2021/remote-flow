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