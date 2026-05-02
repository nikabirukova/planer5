package com.example.planer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planer.data.deeplink.DeepLinkRouter

@Composable
fun DebugScreen(router: DeepLinkRouter) {

    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("DEBUG SCREEN WORKS")

        Text("Debug Deep Link", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Enter URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                router.handle(url)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                url = "myapp://tasks/1"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test: Task Detail")
        }

        Button(
            onClick = {
                url = "myapp://tasks?filter=completed"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test: Filter")
        }
    }
}