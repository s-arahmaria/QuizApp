package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizApp()
        }
    }
}

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    val viewModel: QuizViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onTopicSelected = { topicId ->
                    viewModel.startQuiz(topicId)
                    navController.navigate("quiz")
                }
            )
        }
        composable("quiz") {
            // QuizScreen goes here
        }
        composable("result") {
            // ResultScreen goes here
        }
    }
}