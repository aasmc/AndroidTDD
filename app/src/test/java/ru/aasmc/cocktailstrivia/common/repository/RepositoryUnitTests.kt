package ru.aasmc.cocktailstrivia.common.repository

import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import ru.aasmc.cocktailstrivia.common.network.CocktailsApi

class RepositoryUnitTests {

    private lateinit var repository: CocktailsRepository
    private lateinit var api: CocktailsApi
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        api = mock()
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit())
            .thenReturn(sharedPreferencesEditor)

        repository = CocktailsRepositoryImpl(api, sharedPreferences)
    }

    @Test
    fun saveScore_shouldSaveToSharedPreferences() {
        val score = 100
        repository.saveHighScore(score)

        // use inOrder to check that subsequent verifications are executed in the exact order
        inOrder(sharedPreferencesEditor) {
            verify(sharedPreferencesEditor).putInt(any(), eq(score))
            verify(sharedPreferencesEditor).apply()
        }
    }

    @Test
    fun getScore_shouldGetFromSharedPreferences() {
        repository.getHighScore()
        verify(sharedPreferences).getInt(any(), any())
    }

}








