package com.example.quizapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onFinished: () -> Unit,
    onHome: () -> Unit
) {
    val state = viewModel.state
    val question = state.questions.getOrNull(state.currentQuestionIndex) ?: return
    var showQuitDialog by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.currentQuestionIndex) {
        selectedAnswer = null
    }

    LaunchedEffect(state.currentQuestionIndex, isPaused) {
        while (!viewModel.state.isFinished && !isPaused) {
            kotlinx.coroutines.delay(1000L)
            if (!isPaused) viewModel.onTimerTick()
        }
    }

    LaunchedEffect(state.isFinished) {
        if (state.isFinished) onFinished()
    }

    // Quit warning dialog
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = {
                showQuitDialog = false
                isPaused = false
            },
            title = { Text("Quit Quiz?") },
            text = { Text("Are you sure you want to leave? Your progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    showQuitDialog = false
                    viewModel.resetQuiz()
                    onHome()
                }) {
                    Text("Yes, Quit", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showQuitDialog = false
                    isPaused = false
                }) {
                    Text("Keep Playing")
                }
            }
        )
    }

    // Timer colour logic
    val timerColour = when {
        state.timeRemaining <= 5 -> Color.Red
        else -> MovieBlue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(
                top = 64.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.quizapp_logo),
                contentDescription = "QuizApp logo",
                modifier = androidx.compose.ui.Modifier.height(75.dp),
                contentScale = ContentScale.Fit
            )

            // Circle timer
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(3.dp, timerColour, CircleShape)
            ) {
                Text(
                    text = String.format("%02d", state.timeRemaining),
                    color = timerColour,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // X button
            TextButton(onClick = {
                showQuitDialog = true
                isPaused = true
            }) {
                Text(
                    text = "✕",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Image if question has one
        question.imageRes?.let { res ->
            Image(
                painter = painterResource(id = res),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.85f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Question text
        Text(
            text = question.questionText.uppercase(),
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Answer buttons
        question.options.forEach { option ->
            val isSelected = selectedAnswer == option
            Button(
                onClick = {
                    if (selectedAnswer == null) {
                        selectedAnswer = option
                        viewModel.onAnswerSelected(option)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MovieBlue else Color(0xFFe6e5e1)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Progress bar — 5 segments
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(state.questions.size) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            when {
                                index < state.currentQuestionIndex -> Color(0xFFc7ed9f)
                                index == state.currentQuestionIndex -> Color(0xFF9acc66)
                                else -> Color.LightGray
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun ResultScreen(
    score: Int,
    total: Int,
    topicId: String,
    answeredQuestions: List<Pair<Question, String>>,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit
) {
    if (answeredQuestions.isEmpty()) return

    val correctGreen = Color(0xFF9acc66)
    val wrongRed = Color(0xFFE57373)

    val (message, iconRes) = when (topicId) {
        "movies" -> when (score) {
            5 -> Pair("You belong in Hollywood! A true cinephile.", R.drawable.perfect_movie)
            3, 4 -> Pair("Not bad, not bad. You've clearly spent some quality time at the cinema!", R.drawable.good_movie)
            1, 2 -> Pair("Have you ever actually watched a film before? 👀", R.drawable.okay_movie)
            else -> Pair("Did you guess with your eyes closed?", R.drawable.zero_movie)
        }
        "trivia" -> when (score) {
            5 -> Pair("Are you even human?! Genuinely impressive.", R.drawable.perfect_trivia)
            3, 4 -> Pair("Pretty solid! You clearly paid attention in school... sometimes.", R.drawable.good_trivia)
            1, 2 -> Pair("Well... at least you tried.", R.drawable.okay_trivia)
            else -> Pair("Zero. Absolutely zero.", R.drawable.zero_trivia)
        }
        else -> Pair("Nice work!", R.drawable.perfect_trivia)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Header with message and icon
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$score / $total correct",
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        HorizontalDivider(color = Color.LightGray)

        // Question breakdown
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }
            items(answeredQuestions) { (question, userAnswer) ->
                val isCorrect = userAnswer == question.correctAnswer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFe6e5e1), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (isCorrect) "✓" else "✗",
                        color = if (isCorrect) correctGreen else wrongRed,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = question.questionText,
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
        }

        HorizontalDivider(color = Color.LightGray)

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onHome,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFe6e5e1)
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Home", color = Color.Black)
            }
            Button(
                onClick = onPlayAgain,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MovieBlue
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Play Again", color = Color.White)
            }
        }
    }
}
