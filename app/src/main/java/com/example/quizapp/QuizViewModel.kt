package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class QuizState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val timeRemaining: Int = 5,
    val isFinished: Boolean = false,
    val answeredQuestions: List<Pair<Question, String>> = emptyList()
)

class QuizViewModel : ViewModel() {
    var state by mutableStateOf(QuizState())
        private set

    fun startQuiz(topicId: String) {
        val topic = quizTopics.find { it.id == topicId }
        if (topic == null) return
        val shuffled = topic.questions.shuffled()
        state = QuizState(questions = shuffled)
    }

    fun onAnswerSelected(answer: String) {
        val currentQuestion = state.questions[state.currentQuestionIndex]
        val updatedAnswers = state.answeredQuestions + Pair(currentQuestion, answer)
        val newScore = if (answer == currentQuestion.correctAnswer) state.score + 1 else state.score
        val nextIndex = state.currentQuestionIndex + 1

        state = if (nextIndex >= state.questions.size) {
            state.copy(
                score = newScore,
                answeredQuestions = updatedAnswers,
                isFinished = true
            )
        } else {
            state.copy(
                score = newScore,
                currentQuestionIndex = nextIndex,
                answeredQuestions = updatedAnswers,
                timeRemaining = 5
            )
        }
    }

    fun onTimerTick() {
        if (state.timeRemaining > 0) {
            state = state.copy(timeRemaining = state.timeRemaining - 1)
        } else {
            onAnswerSelected("")
        }
    }

    fun resetQuiz() {
        state = QuizState()
    }
}