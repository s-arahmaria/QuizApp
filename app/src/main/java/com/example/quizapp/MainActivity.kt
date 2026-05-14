package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    QuizApp()
                }
            }
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
                }
            )
        }
        composable("result") {
            ResultScreen(
                score = viewModel.state.score,
                total = viewModel.state.questions.size,
                answeredQuestions = viewModel.state.answeredQuestions,
                onPlayAgain = {
                    viewModel.resetQuiz()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
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
    }
}
