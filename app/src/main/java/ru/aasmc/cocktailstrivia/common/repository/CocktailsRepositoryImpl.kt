package ru.aasmc.cocktailstrivia.common.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.aasmc.cocktailstrivia.common.network.Cocktail
import ru.aasmc.cocktailstrivia.common.network.CocktailsApi
import ru.aasmc.cocktailstrivia.common.network.CocktailsContainer

class CocktailsRepositoryImpl(private val api: CocktailsApi) : CocktailsRepository {

    private var getAlcoholicCall: Call<CocktailsContainer>? = null

    override fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>) {
        getAlcoholicCall?.cancel()
        getAlcoholicCall = api.getAlcoholic()
        getAlcoholicCall?.enqueue(wrapCallback(callback))
    }

    private fun wrapCallback(callback: RepositoryCallback<List<Cocktail>, String>) =
        object : Callback<CocktailsContainer> {
            override fun onResponse(call: Call<CocktailsContainer>?,
                                    response: Response<CocktailsContainer>?) {
                if (response != null && response.isSuccessful) {
                    val cocktailsContainer = response.body()
                    if (cocktailsContainer != null) {
                        callback.onSuccess(cocktailsContainer.drinks ?: emptyList())
                        return
                    }
                }
                callback.onError("Couldn't get cocktails")
            }

            override fun onFailure(call: Call<CocktailsContainer>?, t: Throwable?) {
                callback.onError("Couldn't get cocktails")
            }
        }
}