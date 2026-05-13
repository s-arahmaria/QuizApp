package com.example.quizapp

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val imageRes: Int? = null
)

data class QuizTopic(
    val id: String,
    val title: String,
    val emoji: String,
    val questions: List<Question>
)

val movieQuestions = listOf(
    Question(
        questionText = "Who directed The Godfather?",
        options = listOf("Martin Scorsese", "Francis Ford Coppola", "Stanley Kubrick", "Mario Puzo"),
        correctAnswer = "Francis Ford Coppola",
        imageRes = R.drawable.the_godfather
    ),
    Question(
        questionText = "What fictitious island is Jurassic Park set on?",
        options = listOf("Isla Sorna", "Muertes Archipelago", "Isla Pena", "Isla Nublar"),
        correctAnswer = "Isla Nublar",
        imageRes = R.drawable.jurassic_park
    ),
    Question(
        questionText = "\"Luke, there is another Skywalker\" were the last words of which Jedi?",
        options = listOf("Obi-Wan Kenobi", "Mace Windu", "Yoda", "Ahsoka Tano"),
        correctAnswer = "Yoda",
        imageRes = R.drawable.star_wars
    ),
    Question(
        questionText = "Which movie is this quote from? \"Carpe diem. Seize the day, boys. Make your lives extraordinary.\"",
        options = listOf("Good Will Hunting", "Dead Poets Society", "Ferris Bueller's Day Off", "The Breakfast Club"),
        correctAnswer = "Dead Poets Society"
    ),
    Question(
        questionText = "Which Harry Potter film is this scene from?",
        options = listOf("Goblet of Fire", "Order of the Phoenix", "Prisoner of Azkaban", "Half Blood Prince"),
        correctAnswer = "Prisoner of Azkaban",
        imageRes = R.drawable.harry_potter_scene
    )
)

val triviaQuestions = listOf(
    Question(
        questionText = "How many bones are in the adult human body?",
        options = listOf("196", "206", "216", "226"),
        correctAnswer = "206",
        imageRes = R.drawable.skeleton
    ),
    Question(
        questionText = "Who won the 2022 FIFA World Cup?",
        options = listOf("France", "Brazil", "Argentina", "England"),
        correctAnswer = "Argentina",
        imageRes = R.drawable.world_cup
    ),
    Question(
        questionText = "Which artist has the most streamed song on Spotify of all time?",
        options = listOf("Drake", "Ed Sheeran", "The Weeknd", "Taylor Swift"),
        correctAnswer = "Ed Sheeran",
        imageRes = R.drawable.spotify
    ),
    Question(
        questionText = "Which country invented Croissants?",
        options = listOf("France", "Austria", "Belgium", "Switzerland"),
        correctAnswer = "Austria",
        imageRes = R.drawable.croissant
    ),
    Question(
        questionText = "What show features the Bluth family?",
        options = listOf("Curb Your Enthusiasm", "Arrested Development", "It's Always Sunny in Philadelphia", "30 Rock"),
        correctAnswer = "Arrested Development"
    )
)

val quizTopics = listOf(
    QuizTopic(
        id = "movies",
        title = "Movies",
        emoji = "🎬",
        questions = movieQuestions
    ),
    QuizTopic(
        id = "trivia",
        title = "Trivia",
        emoji = "🧠",
        questions = triviaQuestions
    )
)