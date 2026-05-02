package com.example.planer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.planer.data.repository.*
import com.example.planer.security.BiometricManager
import com.example.planer.ui.screens.*
import com.example.planer.ui.theme.PlanerTheme
import com.example.planer.data.deeplink.*
import androidx.fragment.app.FragmentActivity
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import android.content.Intent
import android.net.Uri

class MainActivity : FragmentActivity() {

    private lateinit var taskRepository: TaskRepository
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var userRepository: UserRepository
    private lateinit var biometricManager: BiometricManager

    private lateinit var router: DeepLinkRouter // 👈 ВАЖНО

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        taskRepository = TaskRepository(this)
        categoryRepository = CategoryRepository(this)
        userRepository = UserRepository(this)
        biometricManager = BiometricManager(this)

        router = DeepLinkRouter() // 👈 НЕ val

        // cold start
        intent?.data?.toString()?.let {
            router.handle(it)
        }

        setContent {
            PlanerTheme {
                PlanerApp(
                    taskRepository = taskRepository,
                    categoryRepository = categoryRepository,
                    userRepository = userRepository,
                    biometricManager = biometricManager,
                    router = router
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        intent.data?.toString()?.let {
            router.handle(it)
        }
    }
}

@Composable
fun PlanerApp(
    taskRepository: TaskRepository,
    categoryRepository: CategoryRepository,
    userRepository: UserRepository,
    biometricManager: BiometricManager,
    router: DeepLinkRouter
) {
    val navController = rememberNavController()
    val destination by router.currentDestination.collectAsState()
    val destinationState by router.currentDestination.collectAsState()

    // 🔥 DEEP LINK NAVIGATION
    LaunchedEffect(destinationState) {

        when (val dest = destinationState) {

            is Destination.Home -> {
                navController.navigate("list") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }

            is Destination.Detail -> {
                val id = dest.id

                navController.navigate("detail/$id") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }

            is Destination.Catalog -> {
                navController.navigate("list") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }

            is Destination.Invite -> {
                val invite = destination as Destination.Invite
                val token = invite.token

                navController.navigate("invite/$token") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }

            is Destination.Notifications -> {
                navController.navigate("profile") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("list") },
                    icon = { Text("📋") },
                    label = { Text("Задачі") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("add") },
                    icon = { Text("➕") },
                    label = { Text("Додати") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Text("👤") },
                    label = { Text("Профіль") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("completed") },
                    icon = { Text("✔") },
                    label = { Text("Виконані") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("stats") },
                    icon = { Text("📊") },
                    label = { Text("Статистика") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("security") },
                    icon = { Text("🔐") },
                    label = { Text("Безпека") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("debug") },
                    icon = { Text("🐞") },
                    label = { Text("Debug") }
                )
            }
        }
    ) { innerPadding ->   // ✅ ВАЖЛИВО: правильна назва

        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding) // ✅ тепер збігається
        ) {

            composable("list") {
                TaskListScreen(navController, taskRepository)
            }

            composable("detail/{taskId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("taskId") ?: ""
                TaskDetailScreen(id, navController, taskRepository)
            }

            composable("add") {
                AddTaskScreen(navController, taskRepository)
            }

            composable("profile") {
                ProfileScreen(navController, userRepository)
            }

            composable("completed") {
                CompletedTasksScreen(taskRepository)
            }

            composable("stats") {
                StatisticsScreen(taskRepository)
            }

            composable("security") {
                SecuritySettingsScreen(navController, biometricManager)
            }

            composable("debug") {
                DebugScreen(router)
            }

            composable("invite/{token}") { backStackEntry ->
                val token = backStackEntry.arguments?.getString("token") ?: ""
                InviteScreen(token, navController)
            }
        }
    }
}