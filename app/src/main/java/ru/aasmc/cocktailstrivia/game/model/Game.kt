package ru.aasmc.cocktailstrivia.game.model

class Game(private val questions: List<Question>,
           highest: Int = 0) {
    var currentScore = 0
        private set

    var highestScore = highest
        private set

    private var questionIndex = -1

    fun incrementScore() {
        currentScore++
        if (currentScore > highestScore) {
            highestScore = currentScore
        }
    }

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

}