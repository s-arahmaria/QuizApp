package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.QuizAppTheme

// So we'll want the homescreen to have this sort of structure
// 		Select General Quiz | Select Move Quiz
// So should it take references to composables ?, Remember seeing that 
// Nav thing, think it could help instead
// But instead, lets get two buttons to show on this screen first

class HomeScreenComponents(var info: String? = null) {
	@Composable
	fun Main(onTopicSelected: (String) -> Unit, modifier: Modifier = Modifier) {
			Column(
			modifier=modifier.fillMaxSize()
			.background(Color.Black),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
	){
			InfoSection()
			Button(
				onClick = { onTopicSelected("trivia") },
				shape = RoundedCornerShape(6.dp),
				modifier =
					modifier.fillMaxWidth(0.78f),

				){
				Text(text = "General Quiz")
			}
			Button(
				onClick = { onTopicSelected("movies") },
				shape = RoundedCornerShape(6.dp),
				modifier =
					modifier.fillMaxWidth(0.78f),


			){
				Text(text = "Movie Quiz")
			}
	}
	}

	// Could replace this whole thing with a logo or special font that 
	// just says -> Welcome to QuizApp
	@Composable
	private fun InfoSection(modifier: Modifier = Modifier){
		Text(
			text = buildAnnotatedString {
				append("Welcome to")
				withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)) {
					append("QuizApp")
				}

				append(", there are loads of challenges to select from")
				append("With each question")
				withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)) {
					append(", there's a 5 seconds timer.")
				}
				append("\n")
				append("Be quick. Make haste!")

				
			},
			modifier = modifier.padding(10.dp)
				.widthIn(max = 300.dp),
			color = Color.White,
		)

		

			Text(
				text = buildAnnotatedString{
					withStyle(style= SpanStyle(fontWeight = FontWeight.Bold)) {
						append( "Select from below")
					}
				},
				modifier = modifier.padding(10.dp),
				color = Color.White,
			)
		// }
	}

}
