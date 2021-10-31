package ru.aasmc.cocktailstrivia.game.model

import org.junit.Assert
import org.junit.Test

class GameUnitTests {

    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore() {
        val game = Game(emptyList(), 0)

        game.incrementScore()

        Assert.assertEquals("Current score should have been 1", 1, game.currentScore)
    }

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldAlsoIncrementHighScore() {
        val game = Game(emptyList(), 0)

        game.incrementScore()

        Assert.assertEquals(1, game.highestScore)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore() {
        val game = Game(emptyList(), 10)

        game.incrementScore()

        Assert.assertEquals(10, game.highestScore)
    }

    @Test
    fun whenGettingNextQuestion_shouldReturnIt() {
        val question1 = Question("CORRECT", "INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        val nextQuestion = game.nextQuestion()

        Assert.assertSame(question1, nextQuestion)
    }

    @Test
    fun whenGettingNextQuestion_withoutMoreQuestions_shouldReturnNull() {
        val question1 = Question("CORRECT", "INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        game.nextQuestion()
        val nextQuestion = game.nextQuestion()

        Assert.assertNull(nextQuestion)
    }
}