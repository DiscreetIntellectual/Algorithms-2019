@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File
import kotlin.math.max
import kotlin.math.min

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val dynamic =
        MutableList(first.length + 1) { MutableList(second.length + 1) { 0 } }
    for (i in 1..first.length) {
        for (j in 1..second.length) {
            if (first[i - 1] == second[j - 1])
                dynamic[i][j] = dynamic[i - 1][j - 1] + 1
            else
                dynamic[i][j] = max(dynamic[i - 1][j], dynamic[i][j - 1])
        }
    }
    val ans = StringBuilder()
    var i = first.length
    var j = second.length
    while (i > 0 && j > 0) {
        when {
            first[i - 1] == second[j - 1] -> {
                ans.append(first[i - 1])
                i--
                j--
            }
            dynamic[i - 1][j] == dynamic[i][j] -> i--
            else -> j--
        }
    }
    return ans.reverse().toString()
}
// Трудоемкость - O(first.length * second.length)
// Ресурсоемкость - O(first.length * second.length)

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {

    val dynamic = MutableList(list.size) { 1 }
    val last = MutableList(list.size) { -1 }

    for (i in list.indices) {
        for (j in 0 until i) {
            if (list[j] < list[i] && dynamic[j] + 1 > dynamic[i]) {
                dynamic[i] = dynamic[j] + 1
                last[i] = j
            }
        }
    }

    var index = -1
    val ans = mutableListOf<Int>()
    var length = 0

    for (i in list.indices) {
        if (dynamic[i] > length) {
            length = dynamic[i]
            index = i
        }
    }

    while (index != -1) {
        ans.add(list[index])
        index = last[index]
    }

    return ans.asReversed()

}
// Трудоемкость - O(list.size ^ 2)
// Ресурсоемкость - O(list.size)

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */

fun shortestPathOnField(inputName: String): Int {
    val text = File(inputName).readLines()
    val dynamic
            = MutableList(text.size + 1) { MutableList(text[0].split(" ").size + 1) { Int.MAX_VALUE } }
    dynamic[0][0] = 0

    var i = 1
    var j = 1
    for (line in text) {
        j = 1
        for (number in line.split(" ")) {
            dynamic[i][j] =
                min(dynamic[i - 1][j - 1], min(dynamic[i - 1][j], dynamic[i][j - 1])) + number.toInt()
            j++
        }
        i++
    }

    return dynamic[i - 1][j - 1]
}
// Трудоемкость - O(n * m)
// Ресурсоемкость - O(n * m)

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5
