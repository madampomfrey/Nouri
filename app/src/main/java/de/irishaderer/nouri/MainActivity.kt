package de.irishaderer.nouri

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.irishaderer.nouri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPrefNahrung: SharedPreferences

    val textPortion = "Portionen"
    var anzahlGemuese = 0
    var anzahlObst = 0
    var anzahlFleisch = 0
    var anzahlSport = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefNahrung = getSharedPreferences(
                "sharedPrefNahrung", Context.MODE_PRIVATE
        )
        anzahlGemuese = sharedPrefNahrung.getInt("Gemuese", 0)
        anzahlObst = sharedPrefNahrung.getInt("Obst", 0)
        anzahlFleisch = sharedPrefNahrung.getInt("Fleisch", 0)
        anzahlSport = sharedPrefNahrung.getInt("Sport", 0)

        ausgeben(binding.tvAusgabeGemuese, anzahlGemuese)
        ausgeben(binding.tvAusgabeObst, anzahlObst)
        ausgeben(binding.tvAusgabeFleisch, anzahlFleisch)
        ausgeben(binding.tvAusgabeSport, anzahlSport)

        binding.buGemuesePlus.setOnClickListener {
            zaehlButton(anzahlGemuese, binding.tvAusgabeGemuese, true)
        }
        binding.buGemueseMinus.setOnClickListener {
            zaehlButton(anzahlGemuese, binding.tvAusgabeGemuese, false)
        }

        binding.buObstPlus.setOnClickListener {
            zaehlButton(anzahlObst, binding.tvAusgabeObst, true)
        }

        binding.buObstMinus.setOnClickListener {
            zaehlButton(anzahlObst, binding.tvAusgabeObst, false)
        }

        binding.buFleischPlus.setOnClickListener {
            zaehlButton(anzahlFleisch, binding.tvAusgabeFleisch, true)
        }

        binding.buFleischMinus.setOnClickListener {
            zaehlButton(anzahlFleisch, binding.tvAusgabeFleisch, false)
        }

        binding.buSportPlus.setOnClickListener {
            zaehlButton(anzahlSport, binding.tvAusgabeSport, true)
        }

        binding.buSportMinus.setOnClickListener {
            zaehlButton(anzahlSport, binding.tvAusgabeSport, false)
        }
    }

    override fun onPause() {
        super.onPause()

        val editorNahrung = sharedPrefNahrung.edit()
        editorNahrung.putInt("Gemuese", anzahlGemuese)
        editorNahrung.putInt("Obst", anzahlObst)
        editorNahrung.putInt("Fleisch", anzahlFleisch)
        editorNahrung.putInt("Sport", anzahlSport)
        editorNahrung.apply()
    }

    fun addiere(a: Int, b: Int): Int {
        val anzahl = a + b
        return anzahl
    }

    fun ausgeben(tvAusgabe: TextView, anzahl: Int) {
        tvAusgabe.text = "%s".format(anzahl)
    }

    fun zaehlButton(anz: Int, tvAnz: TextView, plus: Boolean) {
        val anzahl = when {
            plus -> addiere(anz, 1)
            anz > 0 -> addiere(anz, -1)
            else -> return
        }

        when (tvAnz) {
            binding.tvAusgabeObst -> anzahlObst = anzahl
            binding.tvAusgabeGemuese -> anzahlGemuese = anzahl
            binding.tvAusgabeFleisch -> anzahlFleisch = anzahl
            binding.tvAusgabeSport -> anzahlSport = anzahl
        }
        ausgeben(tvAnz, anzahl)
    }
}
