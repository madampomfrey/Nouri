package de.irishaderer.nouri

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.irishaderer.nouri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var foodPrefs: SharedPreferences
    private lateinit var movementPrefs: SharedPreferences

    private var vegetableCount = 0
    private var fruitCount = 0
    private var meatCount = 0
    private var sportCount = 0

    // This variable is not used, but is kept to avoid changing functionality.
    private val textPortion = "Portionen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        foodPrefs = getSharedPreferences(FOOD_PREFS_NAME, MODE_PRIVATE)
        movementPrefs = getSharedPreferences(MOVEMENT_PREFS_NAME, MODE_PRIVATE)

        loadCounts()
        updateAllCountViews()

        // Setup button listeners
        setupCounterListeners(
            binding.buGemuesePlus,
            binding.buGemueseMinus,
            binding.tvAusgabeGemuese,
            { vegetableCount },
            { vegetableCount = it })
        setupCounterListeners(
            binding.buObstPlus,
            binding.buObstMinus,
            binding.tvAusgabeObst,
            { fruitCount },
            { fruitCount = it })
        setupCounterListeners(
            binding.buFleischPlus,
            binding.buFleischMinus,
            binding.tvAusgabeFleisch,
            { meatCount },
            { meatCount = it })
        setupCounterListeners(
            binding.buSportPlus,
            binding.buSportMinus,
            binding.tvAusgabeSport,
            { sportCount },
            { sportCount = it })
    }

    override fun onPause() {
        super.onPause()
        saveCounts()
    }

    private fun loadCounts() {
        vegetableCount = foodPrefs.getInt(KEY_VEGETABLE, 0)
        fruitCount = foodPrefs.getInt(KEY_FRUIT, 0)
        meatCount = foodPrefs.getInt(KEY_MEAT, 0)
        sportCount = movementPrefs.getInt(KEY_SPORT, 0)
    }

    private fun saveCounts() {
        foodPrefs.edit()
            .putInt(KEY_VEGETABLE, vegetableCount)
            .putInt(KEY_FRUIT, fruitCount)
            .putInt(KEY_MEAT, meatCount)
            .apply()

        movementPrefs.edit()
            .putInt(KEY_SPORT, sportCount)
            .apply()
    }

    private fun updateAllCountViews() {
        updateCountView(binding.tvAusgabeGemuese, vegetableCount)
        updateCountView(binding.tvAusgabeObst, fruitCount)
        updateCountView(binding.tvAusgabeFleisch, meatCount)
        updateCountView(binding.tvAusgabeSport, sportCount)
    }

    private fun setupCounterListeners(
        plusButton: Button,
        minusButton: Button,
        countView: TextView,
        getCount: () -> Int,
        setCount: (Int) -> Unit
    ) {
        plusButton.setOnClickListener {
            setCount(getCount() + 1)
            updateCountView(countView, getCount())
        }

        minusButton.setOnClickListener {
            val currentCount = getCount()
            if (currentCount > 0) {
                setCount(currentCount - 1)
                updateCountView(countView, getCount())
            }
        }
    }

    private fun updateCountView(view: TextView, count: Int) {
        view.text = count.toString()
    }

    companion object {
        private const val FOOD_PREFS_NAME = "sharedPrefNahrung"
        private const val MOVEMENT_PREFS_NAME = "sharedPrefBewegung"

        private const val KEY_VEGETABLE = "Gemüse"
        private const val KEY_FRUIT = "Obst"
        private const val KEY_MEAT = "Fleisch"
        private const val KEY_SPORT = "Sport"
    }
}
