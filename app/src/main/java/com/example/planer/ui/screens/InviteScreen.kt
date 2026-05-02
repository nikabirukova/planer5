package com.example.planer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InviteScreen(
    token: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Запрошення",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Token: $token")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("list")
        }) {
            Text("Прийняти")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(onClick = {
            navController.popBackStack()
        }) {
            Text("Відхилити")
        }
    }
}