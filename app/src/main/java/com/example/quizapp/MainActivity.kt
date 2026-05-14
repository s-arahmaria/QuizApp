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
            QuizScreen(
                viewModel = viewModel,
                onFinished = {
                    navController.navigate("result") {
                        popUpTo("quiz") { inclusive = true }
                    }
                },
                onHome = {
                    viewModel.resetQuiz()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("result") {
            ResultScreen(
                score = viewModel.state.score,
                total = viewModel.state.questions.size,
                topicId = viewModel.state.currentTopicId,
                answeredQuestions = viewModel.state.answeredQuestions,
                onPlayAgain = {
                    navController.navigate("quiz") {
                        popUpTo("quiz") { inclusive = true }
                    }
                    viewModel.startQuiz(viewModel.state.currentTopicId)
                },
                onHome = {
                    viewModel.resetQuiz()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
