package ru.aasmc.cocktailstrivia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.aasmc.cocktailstrivia.databinding.ActivityGameBinding
import ru.aasmc.cocktailstrivia.game.model.Question
import ru.aasmc.cocktailstrivia.game.model.Score
import ru.aasmc.cocktailstrivia.game.viewmodel.CocktailsGameViewModel
import ru.aasmc.cocktailstrivia.game.viewmodel.CocktailsGameViewModelFactory

class CocktailsGameActivity : AppCompatActivity() {

    private lateinit var viewModel: CocktailsGameViewModel

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as CocktailsApplication).repository
        val factory = (application as CocktailsApplication).gameFactory
        viewModel = ViewModelProvider(this, CocktailsGameViewModelFactory(repository, factory))
            .get(CocktailsGameViewModel::class.java)

        viewModel.getLoading().observe(this) {
            it?.let { loading -> showLoading(loading) }
        }
        viewModel.getError().observe(this) {
            it?.let { error -> showError(error) }
        }
        viewModel.getQuestion().observe(this) {
            showQuestion(it)
        }
        viewModel.getScore().observe(this) {
            it?.let { score -> showScore(score) }
        }
        viewModel.getGameOver().observe(this) {
            it?.let { isOver -> showGameOver(isOver) }
        }

        binding.nextButton.setOnClickListener { viewModel.nextQuestion() }

        viewModel.initGame()
    }

    private fun showScore(score: Score) {
        binding.scoreTextView.text = getString(R.string.game_score, score.current)
        binding.highScoreTextView.text = getString(R.string.game_highscore, score.highest)
    }

    private fun showQuestion(question: Question?) {
        if (question != null) {
            binding.mainGroup.visibility = View.VISIBLE

            if (question.answeredOption != null) {
                showAnsweredQuestion(question)
            } else {
                showUnansweredQuestion(question)
            }
        } else {
            binding.mainGroup.visibility = View.GONE
            binding.noResultsTextView.visibility = View.VISIBLE
            binding.questionResultImageView.visibility = View.GONE
        }
    }

    private fun showUnansweredQuestion(question: Question) {
        val options = question.getOptions()
        binding.firstOptionButton.text = options[0]
        binding.firstOptionButton.setOnClickListener { viewModel.answerQuestion(question, binding.firstOptionButton.text.toString()) }
        binding.firstOptionButton.isEnabled = true
        binding.secondOptionButton.text = options[1]
        binding.secondOptionButton.setOnClickListener { viewModel.answerQuestion(question, binding.secondOptionButton.text.toString()) }
        binding.secondOptionButton.isEnabled = true

        Glide.with(binding.cocktailImageView).load(question.imageUrl).into(binding.cocktailImageView)
        binding.questionResultImageView.visibility = View.GONE
    }

    private fun showAnsweredQuestion(question: Question) {
        binding.firstOptionButton.setOnClickListener(null)
        binding.firstOptionButton.isEnabled = false
        binding.secondOptionButton.setOnClickListener(null)
        binding.secondOptionButton.isEnabled = false
        if (question.isAnsweredCorrectly) {
            binding.questionResultImageView.setImageResource(R.drawable.ic_check_24dp)
        } else {
            binding.questionResultImageView.setImageResource(R.drawable.ic_error_24dp)
        }
        binding.questionResultImageView.visibility = View.VISIBLE
    }

    private fun showError(show: Boolean) {
        if (show) {
            binding.errorContainer.root.visibility = View.VISIBLE
            binding.errorContainer.retryButton.setOnClickListener { viewModel.initGame() }
        } else {
            binding.errorContainer.root.visibility = View.GONE
        }
    }

    private fun showLoading(show: Boolean) {
        binding.loadingContainer.root.visibility = if (show) View.VISIBLE else View.GONE
        binding.mainGroup.visibility = View.GONE
        binding.noResultsTextView.visibility = View.GONE
        binding.questionResultImageView.visibility = View.GONE
    }

    private fun showGameOver(show: Boolean) {
        if (show) {
            binding.gameOverContainer.root.visibility = View.VISIBLE
            binding.mainGroup.visibility = View.GONE
            binding.gameOverContainer.restartButton.setOnClickListener { viewModel.initGame() }
            binding.questionResultImageView.visibility = View.GONE
        } else {
            binding.gameOverContainer.root.visibility = View.GONE
        }
    }
}
