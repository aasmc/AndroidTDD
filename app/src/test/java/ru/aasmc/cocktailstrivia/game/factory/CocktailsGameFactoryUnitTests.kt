package ru.aasmc.cocktailstrivia.game.factory

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import ru.aasmc.cocktailstrivia.common.network.Cocktail
import ru.aasmc.cocktailstrivia.common.repository.CocktailsRepository
import ru.aasmc.cocktailstrivia.common.repository.RepositoryCallback
import ru.aasmc.cocktailstrivia.game.model.Game
import ru.aasmc.cocktailstrivia.game.model.Question

/**
 * This class tests a factory that builds a Game with questions, which will map
 * the cocktails returned by the API.
 */
class CocktailsGameFactoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var factory: CocktailsGameFactory

    private val cocktails = listOf(
        Cocktail("1", "Drink1", "image1"),
        Cocktail("2", "Drink2", "image2"),
        Cocktail("3", "Drink3", "image3"),
        Cocktail("4", "Drink4", "image4"),
    )

    @Before
    fun setup() {
        repository = mock()
        factory = CocktailsGameFactoryImpl(repository)
    }

    @Test
    fun buildGame_shouldGetCocktailsFromRepo() {
        factory.buildGame(mock())

        verify(repository).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldCallOnSuccess() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setupRepositoryWithCocktails(repository)

        factory.buildGame(callback)

        verify(callback).onSuccess(any())
    }

    /**
     * Stubs the repository.getAlcoholic() method to always return success with a list of
     * cocktails.
     */
    private fun setupRepositoryWithCocktails(repository: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback<List<Cocktail>, String> =
                it.getArgument(0)
            callback.onSuccess(cocktails)
        }.whenever(repository).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldCallOnError() {
        val callback = mock<CocktailsGameFactory.Callback>()
        setupRepositoryWithError(repository)
        factory.buildGame(callback)
        verify(callback).onError()
    }

    private fun setupRepositoryWithError(repository: CocktailsRepository) {
        doAnswer {
            val callback: RepositoryCallback<List<Cocktail>, String> =
                it.getArgument(0)
            callback.onError("Error")
        }.whenever(repository).getAlcoholic(any())
    }

    @Test
    fun buildGame_shouldGetHighScoreFromRepo() {
        setupRepositoryWithCocktails(repository)
        factory.buildGame(mock())
        verify(repository).getHighScore()
    }

    @Test
    fun buildGame_shouldBuildGameWithHighScore() {
        setupRepositoryWithCocktails(repository)
        val highScore = 100
        whenever(repository.getHighScore()).thenReturn(highScore)

        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) =
                Assert.assertEquals(highScore, game.score.highest)

            override fun onError() = Assert.fail()
        })
    }

    @Test
    fun buildGame_shouldBuildGameWithQuestions() {
        setupRepositoryWithCocktails(repository)
        factory.buildGame(object : CocktailsGameFactory.Callback {
            override fun onSuccess(game: Game) {
                cocktails.forEach {
                    assertQuestion(
                        game.nextQuestion(),
                        it.strDrink,
                        it.strDrinkThumb
                    )
                }
            }

            override fun onError() = Assert.fail()
        })
    }

    private fun assertQuestion(question: Question?, correctOption: String, imageUrl: String?) {
        Assert.assertNotNull(question)
        Assert.assertEquals(imageUrl, question?.imageUrl)
        Assert.assertEquals(correctOption, question?.correctOption)
        Assert.assertNotEquals(correctOption, question?.incorrectOption)
    }
}



























