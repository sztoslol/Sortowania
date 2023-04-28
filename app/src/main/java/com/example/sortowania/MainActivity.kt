package com.example.sortowania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Przypisanie elementow UI do zmiennych
        var wynik_rabin_karp = findViewById<TextView>(R.id.textView_sortowanie_wynik_KarpRabin)
        var wynik_bruteforce = findViewById<TextView>(R.id.textView_sortowanie_wynik_Bruteforce)
        var wynik_KMP = findViewById<TextView>(R.id.textView_sortowanie_wynik_KMP)
        var wynik_BM = findViewById<TextView>(R.id.textView_sortowanie_wynik_BM)

        var button_wykonaj = findViewById<Button>(R.id.button_wykonaj)

        var input_ile_razy = findViewById<EditText>(R.id.input_ile_razy)
        var input_ile_elementow = findViewById<EditText>(R.id.input_ile_elementow)
    }
}