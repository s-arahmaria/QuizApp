package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                     Greeting(
                         name = "",
                         modifier = Modifier.padding(innerPadding)
                     )
                    val homeScreen = HomeScreenComponents(
                        "Welcome to QuizApp, there are loads of challenges to select from" +
                                "With each question, theres a 5 seconds timer, so make haste"
                    );
                    homeScreen.Main("Placeholder::not sure yet")
                }
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
    val homeScreen = HomeScreenComponents("");
    QuizAppTheme {
        Greeting("Android")
    }

    homeScreen.Main("Placeholder::not sure yet")
}

