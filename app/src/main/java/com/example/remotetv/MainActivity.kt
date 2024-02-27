package com.example.remotetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.connectsdk.discovery.DiscoveryManager
import com.example.remotetv.ui.theme.RemoteTVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DiscoveryManager.init(applicationContext)
        DeviceController().SetMainActivity(this)
        DeviceController().SetDevicePicker(this)
        setContent {
            RemoteTVTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp("Android", this)
                }
            }
        }
    }
}

@Composable
fun MainApp(name: String, mainActivity: MainActivity = MainActivity(), modifier: Modifier = Modifier) {
    Column {
        Button(
            onClick = { DeviceController().FindTV(mainActivity) },
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Find TV")
        }

        Row(
            Modifier.padding(top=16.dp).fillMaxWidth()
        ){
            Button(
                onClick = { DeviceController().TurnOnTV(mainActivity) },
                Modifier.padding(16.dp).weight(1f)
            ) {
                Text("Turn on")
            }

            Button(
                onClick = { DeviceController().TurnOffTV(mainActivity) },
                Modifier.padding(16.dp).weight(1f)
            ) {
                Text(text = "Turn off")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainApp() {
    RemoteTVTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainApp("Android")
        }
    }
}