package com.example.quizapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Cream = Color(0xFFFFF8F0)
val MovieBlue = Color(0xFF9fdaed)
val TriviaYellow = Color(0xFFfae87f)

@Composable
fun HomeScreen(onTopicSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with logo and settings
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.quizapp_logo),
                contentDescription = "QuizApp logo",
                modifier = Modifier.height(95.dp),
                contentScale = ContentScale.Fit
            )

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .offset(y = (-8).dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Heading
        Text(
            text = stringResource(R.string.home_heading),
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tagline
        Text(
            text = stringResource(R.string.tagline),
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Movies card
        TopicCard(
            title = stringResource(R.string.topic_movies),
            tagline = stringResource(R.string.movies_tagline),
            iconRes = R.drawable.icon_movies,
            backgroundColor = MovieBlue,
            onClick = { onTopicSelected("movies") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Trivia card
        TopicCard(
            title = stringResource(R.string.topic_trivia),
            tagline = stringResource(R.string.trivia_tagline),
            iconRes = R.drawable.icon_trivia,
            backgroundColor = TriviaYellow,
            onClick = { onTopicSelected("trivia") }
        )
    }
}

@Composable
fun TopicCard(
    title: String,
    tagline: String,
    iconRes: Int,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = tagline,
                    fontSize = 13.sp,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onTopicSelected = {})
}