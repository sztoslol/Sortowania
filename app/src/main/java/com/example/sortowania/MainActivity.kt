package com.example.sortowania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

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

//Funkcja losująca losową liste
fun losuj(size: Int): MutableList<Int> {
    val random = Random()
    return List(size) { random.nextInt(1000) }.toMutableList()
}

//Funkcja licząca roznice miedzy pomiarami
fun calcTime(t1 : Long, t2 : Long) : Long
{
    return t2 - t1
}

//================================Sortowanie Rabina-Karpa=====================================
fun sortowanie_Rabin_Karp(arr: IntArray): IntArray {
    val base = 101
    val mod = 1000000007
    var n = arr.size
    var pow = 1
    var maxVal = arr.maxOrNull()!!
    while (maxVal / pow > 0) {
        val count = IntArray(10)
        for (i in arr.indices) {
            val digit = arr[i] / pow % 10
            count[digit]++
        }
        for (i in 1 until 10) {
            count[i] += count[i - 1]
        }
        val temp = IntArray(n)
        for (i in n - 1 downTo 0) {
            val digit = arr[i] / pow % 10
            temp[count[digit] - 1] = arr[i]
            count[digit]--
        }
        for (i in arr.indices) {
            arr[i] = temp[i]
        }
        pow *= 10
    }
    return arr
}

//==================================Sortowanie Bruteforce=======================================
fun sortowanie_Bruteforce(arr: IntArray): IntArray {
    val n = arr.size
    for (i in 0 until n) {
        for (j in i+1 until n) {
            if (arr[i] > arr[j]) {
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }
    }
    return arr
}

//===============================Sortowanie Knutha-Morrisa-Pratta==================================
fun kmpSort(arr: IntArray) {
    val n = arr.size
    val aux = IntArray(n)
    var lo = 0
    var hi = n - 1
    var d = 0
    var m = 1

    while (hi >= m) {
        m *= 10
        d++
    }

    for (i in 1..d) {
        val count = IntArray(10)

        for (j in lo..hi) {
            val digit = (arr[j] / m) % 10
            count[digit]++
        }

        for (j in 1 until 10) {
            count[j] += count[j - 1]
        }

        for (j in hi downTo lo) {
            val digit = (arr[j] / m) % 10
            aux[count[digit] - 1] = arr[j]
            count[digit]--
        }

        for (j in lo..hi) {
            arr[j] = aux[j - lo]
        }

        m /= 10
    }
}

//===============================Sortowanie Knutha-Morrisa-Pratta==================================
fun sortowanie_BM(arr: IntArray): IntArray {
    val n = arr.size
    val maxVal = arr.maxOrNull() ?: 0
    val counts = IntArray(maxVal + 1)
    for (x in arr) {
        counts[x]++
    }
    var i = 0
    for (x in 0..maxVal) {
        for (j in 0 until counts[x]) {
            arr[i++] = x
        }
    }
    return arr
}
