package de.irishaderer.nouri

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Steht in der MainActivity, damit es überall immer verfügbar ist (Kontext).
    //Das Objekt wird hier definiert mit Angabe des Typs. Es wird ein Default Wert zugewiesen. Da dieser null ist, muss der Typ angegeben werden, weil sonst kein SharedPrefrences zugewiesen werden kann (Logik haha). Shared Preferences kann aber nicht null sein, daher:
    //"lateinit" sagt: ich kann das noch nicht initialisieren, aber das werde ich schnellstmöglich. Schaltet safety feature aus. DAnn kann das Objekt kurz null sein, bis es initialisiert wird.
    lateinit var sharedPrefNahrung: SharedPreferences

    // Variablen für Buttons für die ganze Klasse; hier werden die Werte aus der Datei noch nicht gelesen, weil Gründe. Wird dann direkt beim Start neu gesetzt
    val textPortion = "Portionen"
    var anzahlGemuese = 0
    var anzahlObst = 0
    var anzahlFleisch = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Hier wird die Variable dann neu gesetzt mit SharedPreferences. Speichern: Datei benennen normalerweise als Variable, jetzt hier hardcoded.
        sharedPrefNahrung = getSharedPreferences(
                "sharedPrefNahrung", Context.MODE_PRIVATE
        )
        //Werte aus der Speicherdatei sharedPrefNahrung lesen und setzen
        anzahlGemuese = sharedPrefNahrung.getInt("Gemuese", 0)
        anzahlObst = sharedPrefNahrung.getInt("Obst", 0)
        anzahlFleisch = sharedPrefNahrung.getInt("Fleisch", 0)

        ausgeben(tvAusgabeGemuese, anzahlGemuese)
        ausgeben(tvAusgabeObst, anzahlObst)
        ausgeben(tvAusgabeFleisch, anzahlFleisch)

        //Buttons
        buGemuesePlus.setOnClickListener {
            zaehlButton(anzahlGemuese, tvAusgabeGemuese, true)
        }
        //hier keine Funktion zur Berechnung benutzt, weil es Zeitaufwand ist, Funktionen zu nutzen und diese nur aus einer Zeile besteht.
        buGemueseMinus.setOnClickListener {
            zaehlButton(anzahlGemuese, tvAusgabeGemuese, false)
        }

        buObstPlus.setOnClickListener {
            zaehlButton(anzahlObst, tvAusgabeObst, true)
        }

        buObstMinus.setOnClickListener {
            zaehlButton(anzahlObst, tvAusgabeObst, false)
        }

        buFleischPlus.setOnClickListener {
            zaehlButton(anzahlFleisch, tvAusgabeFleisch, true)
        }

        buFleischMinus.setOnClickListener {
            zaehlButton(anzahlFleisch, tvAusgabeFleisch, false)
        }


    } //OnCreate Ende

    //Lebenszyklus App: Pause, wenn die App nicht mehr im Vordergrund ist
    //Speichern, sobald die App nicht mehr im Vordergrund ist (nicht erst beim Stopp)
    override fun onPause() {
        super.onPause()


        // Variable definiert, die die Variable "SharedPref" aufruft mit einem Edit-Modus. Dann diese Variable wieder aufrufen und reinreschreiben
        val editorNahrung = sharedPrefNahrung.edit()
        editorNahrung.putInt("Gemuese", anzahlGemuese)
        editorNahrung.putInt("Obst", anzahlObst)
        editorNahrung.putInt("Fleisch", anzahlFleisch)
        //Eine Variable editorNahrung ist ein Objekt, das eine Funktion (zB edit) aufrufen kann. Wenn ich apply aufrufen will, brauche ich ein Objekt, mit dem ich die Funktion aufrufen kann, das weiß, was damit zu tun ist. sharedPref ist oben der Name für die SharedPreferences Variable
        editorNahrung.apply()
        sharedPrefNahrung.getInt("Gemuese", 0)
    }

    //Funktion Addieren: mit Return, weil der Wert sonst im Nirvana verschwindet. Und den Datentyp vom Return hinter der Klammer angeben. Außerhalb von oncreate, weil man Funktionen nicht schachteln kann
    fun addiere(a: Int, b: Int): Int {
        val anzahl = a + b
        return anzahl
    }


    fun ausgeben(tvAusgabe: TextView, anzahl: Int) {
        tvAusgabe.text = "%s".format(anzahl)
    }

    fun zaehlButton(anz: Int, tvAnz: TextView, plus: Boolean) {                     //return weggemacht, weil ich den Wert oben nicht mehr brauche; dann auch den Typ des return nicht mehr angeben
        val anzahl = when {
            plus -> addiere(anz, 1)
            // heißt: if plus == true
            anz > 0 -> addiere(anz, -1)
            else -> return
        }

        when (tvAnz) {
            tvAusgabeObst -> anzahlObst = anzahl
            tvAusgabeGemuese -> anzahlGemuese = anzahl
            else -> anzahlFleisch = anzahl
        }
        ausgeben(tvAnz, anzahl)

    } //Parameter, die man in eine Funktion gibt, sind automatisch values und unveränderbar. An die FUnktion wird eine Kopie der Variablen gegeben, die nicht mehr die gleiche ist, wie sie oben definiert wurde.
    // Mit "this." kann man auf Dinge in der Klasse zugreifen, die höher sind als die Funktion. Also kann man hier die Variable von oben benutzen


}


