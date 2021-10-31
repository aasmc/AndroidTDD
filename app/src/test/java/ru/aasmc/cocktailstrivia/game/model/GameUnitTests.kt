package ru.aasmc.cocktailstrivia.game.model

import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

class GameUnitTests {

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

    /**
     * The game should delegate answering logic to a [Question] class.
     */
    @Test
    fun whenAnswering_ShouldDelegateToQuestion() {
        val question = mock<Question>()
        val game = Game(listOf(question))

        game.answer(question, "OPTION")

        verify(question, times(1)).answer(eq("OPTION"))
    }

    @Test
    fun whenAnsweringCorrectly_shouldIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(true)

        val score = mock<Score>()
        val game = Game(listOf(question), score)
        game.answer(question, "OPTION")

        verify(score).increment()
    }

    @Test
    fun whenAnsweringIncorrectly_ShouldNotIncrementCurrentScore() {
        val question = mock<Question>()
        whenever(question.answer(anyString())).thenReturn(false)
        val score = mock<Score>()
        val game = Game(listOf(question), score)
        game.answer(question, "OPTION")

        verify(score, never()).increment()
    }
}















