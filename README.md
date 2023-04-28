# Rabin-Karp
1. Pierwszym krokiem jest wygenerowanie haszów dla wszystkich suffixów danego tekstu. Hasz jest liczbą całkowitą, 
    która reprezentuje wartość tekstową danego fragmentu tekstu. Można to zrobić na kilka sposobów, ale jednym z 
    najpopularniejszych jest wykorzystanie funkcji skrótu, takiej jak SHA-256.
```kotlin
    fun generateHashes(text: String): MutableList<Int> {
      val hashes = mutableListOf<Int>()
      for (i in text.indices) {
          hashes.add(text.substring(i).hashCode())
      }
      return hashes
    }
```
  2. Następnie sortujemy hasze, korzystając z algorytmu sortowania przez wstawianie (insertion sort).
```kotlin
    fun insertionSort(hashes: MutableList<Int>): MutableList<Int> {
       for (i in 1 until hashes.size) {
           val key = hashes[i]
           var j = i - 1
           while (j >= 0 && key < hashes[j]) {
               hashes[j + 1] = hashes[j]
               j--
           }
           hashes[j + 1] = key
       }
       return hashes
   }
```
 3. W końcowej fazie algorytmu, wykorzystujemy posortowane hasze do odtworzenia oryginalnego tekstu.
 
```kotlin 
    fun rebuildText(text: String, sortedHashes: MutableList<Int>): MutableList<String> {
        val sortedText = mutableListOf<String>()
        for (h in sortedHashes) {
            for (i in text.indices) {
                if (text.substring(i).hashCode() == h) {
                    sortedText.add(text.substring(i))
                    break
                }
            }
        }
        return sortedText
    }
```
 <br>
 Algorytm sortowania Rabin-Karpa wykorzystuje tzw. "rolling hash" do generowania haszy dla fragmentów tekstu. W tym przypadku, 
 funkcja hashująca używana jest do obliczenia hasza dla kolejnych podciągów tekstu, a następnie sortuje je w kolejności rosnącej. 
 W ten sposób, można uzyskać posortowaną listę wszystkich suffixów tekstu.
   
# Bruteforce
1. Algorytm sortowania brute force, zwany również algorytmem sortowania bąbelkowego, polega na porównywaniu każdej 
   pary sąsiednich elementów w nieposortowanej tablicy i zamianie ich miejscami, jeśli są w złej kolejności.
  
```kotlin
  fun bruteForceSort(arr: IntArray): IntArray {
      val n = arr.size
      for (i in 0 until n) {
          for (j in 0 until n-i-1) {
              if (arr[j] > arr[j+1]) {
                  arr[j] = arr[j+1].also { arr[j+1] = arr[j] }
              }
          }
      }
      return arr
  }
```
2. Pierwsza pętla for iteruje po wszystkich elementach w tablicy, podczas gdy druga pętla for iteruje po elementach po prawej stronie obecnego elementu.
3. Porównanie dwóch elementów polega na sprawdzeniu, czy element po lewej stronie jest większy od elementu po prawej stronie. Jeśli tak, 
   zamieniamy ich pozycje w tablicy.
4. W każdym przejściu zewnętrznej pętli for przesuwamy się o jedną pozycję w prawo, aby zapewnić, że ostatni element będzie posortowany.
5. Powtarzamy ten proces aż do momentu, gdy cała tablica zostanie posortowana.
6. Złożoność obliczeniowa algorytmu sortowania brute force wynosi O(n^2), co oznacza, że dla dużej ilości elementów w tablicy, 
   czas sortowania może być bardzo długi.

<br>
Algorytm sortowania brute force jest bardzo łatwy do zrozumienia i zaimplementowania, ale jest mało efektywny dla dużych tablic.
Istnieją bardziej zaawansowane algorytmy sortowania, takie jak QuickSort, MergeSort czy HeapSort, które mają lepszą złożoność obliczeniową i 
działają szybciej dla dużych tablic.

# Knutha-Morrisa-Pratta
Algorytm Knutha-Morrisa-Pratta (KMP) to algorytm wyszukiwania wzorca w tekście. Może być również wykorzystywany do sortowania, ale nie jest to jego
główne zastosowanie. Algorytm KMP działa w dwóch fazach: budowaniu tablicy prefiksowej i wyszukiwaniu wzorca w tekście.

1.Faza budowania tablicy prefiksowej:
  - Zaczynamy od zdefiniowania tablicy **`pi`** o długości równej długości wzorca **`p`**.
  - Ustawiamy **`pi[0] = 0`** oraz **`j = 0`**.
  - Dla każdej pozycji **`i`** w **`p`** wykonujemy następujące kroki:
    + Jeśli **`p[i]`** jest równy **`p[j]`**, to **`pi[i] = j+1`** oraz **`j = j+1`**.
    + W przeciwnym razie, jeśli **`j`** jest równe 0, to **`pi[i] = 0`**.
    + W przeciwnym razie, ustawiamy **`j`** na **`pi[j-1]`** i powtarzamy proces.

```kotlin
  fun buildPi(p: String): IntArray {
      val m = p.length
      val pi = IntArray(m) { 0 }
      var j = 0
      for (i in 1 until m) {
          while (j > 0 && p[i] != p[j]) {
              j = pi[j - 1]
          }
          if (p[i] == p[j]) {
              j++
          }
          pi[i] = j
      }
      return pi
  }
```

2. Faza wyszukiwania wzorca w tekście:
  - Zaczynamy od porównania pierwszego znaku tekstu **`t`** z pierwszym znakiem wzorca **`p`**.
  - Dla każdej pozycji i w t wykonujemy następujące kroki:
    + Jeśli **`t[i]`** jest równy **`p[j]`**, to zwiększamy **`i`** i **`j`** o 1.
    + W przeciwnym razie, jeśli **`j`** jest równe 0, to przesuwamy się o jeden znak w tekście **`t`**.
    + W przeciwnym razie, ustawiamy **`j`** na **`pi[j-1]`**.

```kotlin
  fun kmpSearch(p: String, t: String): Int {
      val m = p.length
      val n = t.length
      val pi = buildPi(p)
      var j = 0
      for (i in 0 until n) {
          while (j > 0 && t[i] != p[j]) {
              j = pi[j - 1]
          }
          if (t[i] == p[j]) {
              j++
          }
          if (j == m) {
              return i - m + 1
          }
      }
      return -1
  }
```

W powyższym kodzie funkcja **`kmp_search`** przyjmuje wzorzec **`p`** oraz tekst **`t`**, a następnie zwraca indeks pierwszego wystąpienia wzorca w 
tekście lub **`-1`**, jeśli wzorzec nie występuje.<br>

W pierwszej fazie budujemy tablicę prefiksową **`pi`** dla wzorca **`p`** za pomocą funkcji **`build_pi`**.<br>

W drugiej fazie porównujemy znaki tekstu t z kolejnymi znakami wzorca p i przesuwamy wzorzec w razie potrzeby, korzystając z tablicy prefiksowej **`pi`**. 
Jeśli znaki są równe, to przesuwamy oba wskaźniki o jeden. W przeciwnym razie przesuwamy wskaźnik wzorca na pozycję wskazaną przez wartość z tablicy prefiksowej,
a wskaźnik tekstu pozostaje w swoim miejscu.<br>

Gdy wskaźnik wzorca dotrze do końca, oznacza to, że znaleźliśmy wzorzec w tekście i zwracamy indeks 
pierwszego wystąpienia wzorca w tekście. Jeśli nie udało się znaleźć wzorca, zwracamy wartość **`-1`**.<br>

Algorytm KMP ma złożoność czasową O(m + n), gdzie m to długość wzorca, a n to długość tekstu.

# Boyera-Moore'a

1. Algorytm Boyera-Moore'a jest algorytmem wyszukiwania wzorca w tekście, który wykorzystuje heurystyki w celu przyspieszenia procesu wyszukiwania.
```kotlin
  fun boyerMoore(text: String, pattern: String): Int {
      val m = pattern.length
      val n = text.length
      if (m == 0) {
          return 0
      }
      val badChar = generateBadCharTable(pattern, m)
      val goodSuffix = generateGoodSuffixTable(pattern, m)
      var i = 0
      while (i <= n - m) {
          var j = m - 1
          while (j >= 0 && pattern[j] == text[i+j]) {
              j--
          }
          if (j < 0) {
              return i
          } else {
              i += maxOf(goodSuffix[j], j - (badChar[text[i+j].toInt()] ?: -1))
          }
      }
      return -1
  }
```
2. Algorytm Boyera-Moore'a wykorzystuje dwie tablice: tablicę błędnych znaków i tablicę dobrych sufiksów. Tablica błędnych znaków przechowuje 
   informacje o tym, ile pozycji trzeba przesunąć wzorzec w prawo, gdy wystąpi błąd dopasowania. Tablica dobrych sufiksów przechowuje informacje 
   o tym, jak daleko można przesunąć wzorzec w prawo, gdy wystąpi dobry dopasowanie.
3.Pierwsza pętla while iteruje po tekście do momentu, gdy pozostaje mniej niż m znaków do przeszukania. Wewnątrz pętli while jest druga pętla while, 
  która porównuje każdy znak w tekście z odpowiadającym mu znakiem w wzorcu, począwszy od końca wzorca.
4. Jeśli dopasowanie nie jest prawidłowe, algorytm wykorzystuje tablicę błędnych znaków, aby określić, jak daleko należy przesunąć wzorzec w prawo. 
   Jeśli dopasowanie jest prawidłowe, algorytm wykorzystuje tablicę dobrych sufiksów, aby określić, jak daleko można przesunąć wzorzec w prawo, aby uzyskać 
   kolejne dopasowanie.
5. Algorytm wykonuje przesunięcie wzorca w prawo o maksymalną wartość spośród wartości obliczonych na podstawie tablicy błędnych znaków i tablicy dobrych sufiksów.
6. Złożoność czasowa algorytmu Boyera-Moore'a wynosi O(n + m), co oznacza, że czas działania algorytmu jest proporcjonalny do sumy długości wzorca i tekstu. 
   W przypadku wyszukiwania wielu wzorców, złożoność czasowa może wzrosnąć do O(k(n + m)), gdzie k jest liczbą wzorców.
   
   
Algorytm Boyera-Moore'a jest jednym z najbardziej efektywnych algorytmów wyszukiwania wzorców, zwłaszcza gdy wzorzec jest długi i ma wiele powtarzających się 
znaków. Algorytm ten jest często stosowany w wyszukiwaniu tekstu w plikach, przetwarzaniu napisów i analizie danych.
