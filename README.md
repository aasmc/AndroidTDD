# CocktailsTrivia educational app

This app is based on the book "Android test driven development by tutorials".

It's main goal is to build simple app with the TDD approach focusing on Unit tests only. 
No integration or end-to-end tests are performed in the app. 

It's based on Chapters 5 and 6 of the book. 

## Functionality:

Loads information and questions related to alcoholic cocktails from:
```text
https://www.thecocktaildb.com/api/json/v1/1/
```
Allows the user to answer a question about a cocktail. If answered correctly, increases the 
high score for current game. If the user makes more than 3 consecutive correct answers, the score for the answer
doubles. In case of 3 wrong answers - the game is over. 

## Networking
Data is loaded from the api with Retrofit and OkHttpClient. 

## Persistence:
Data is persisted in SharedPreferences.

## Tests
Mockito is used for mocking and spying on objects. 
Functionality of the app was implemented with TDD, i.e. first there were tests (specifically unit-tests),
and then the functionality.

InstantTaskExecutorRule is used for testing LiveData and ViewModel to allow for sequential invocation of
methods.

