package ru.aasmc.cocktailstrivia.game.model

class Game(
    private val questions: List<Question>,
    val score: Score = Score(0)
) {
    private var questionIndex = -1

    private var questionsAnsweredCorrectlySequentially = 0
    private var questionsAnsweredIncorrectly = 0

    val isOver: Boolean
        get() = questionsAnsweredIncorrectly >= 3

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    fun answer(question: Question, option: String) {
        val result = question.answer(option)
        if (result) {
            questionsAnsweredCorrectlySequentially++
            score.increment()
            if (shouldGiveExtraPoint()) {
                score.increment()
            }
        } else {
            questionsAnsweredCorrectlySequentially = 0
            questionsAnsweredIncorrectly++
        }
    }

    private fun shouldGiveExtraPoint() = questionsAnsweredCorrectlySequentially > 3

}