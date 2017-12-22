@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.*
import kotlin.collections.*


/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var m = 0.0
    var i = 0
    while (i < v.size) {
        m = sqrt(m * m + v[i] * v[i])
        i += 1
    }
    return m
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var k = 0.0
    var m = 0.0
    if (list.isEmpty()) return 0.0
    else {
        for (i in 0..(list.size - 1)) {
            k += list[i]
        }
        m = k / list.size

    }
    return m

}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double>{
    val m = mean(list)
    for (i in 0 until list.size) list[i] -= m
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    if (a.isEmpty() or b.isEmpty()) return 0.0
    else
        for (i in 0..(a.size - 1)) {
            c += a[i] * b[i]
        }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var q = 0.0
    if (p.isEmpty()) return 0.0
    for (i in 0..(p.size - 1)) {
            q += p[i] * pow(x, i.toDouble())
        }
    return q

}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1 until list.size) list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var list: MutableList<Int> = mutableListOf()
    var m = n
    var i = 2
    while (m > 1) {
        if (m % i == 0) {
            list.add(i)
            m = m / i
        } else i++
    }

    return list
}


/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String {
    var list: List<Int> = factorize(n)
    var k = list[0]
    var a: String = "$k"
    if (list.size == 1) return a
    else {
        for (i in 1..(list.size - 1)) {
            var b = list[i]
            a += "*$b"
        }
        return a
    }

}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var tempN = n
    do {
        list.add(0, newN % base)
        tempN /= base
    } while (tempN != 0)
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val keys = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    val tempN = convert(n, base).map { key[it] }
    return tempN.joinToString("")
}



/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var d = 0
    for (i in 0..(digits.size - 1)) {
        d += (digits[i] * (Math.pow(base.toDouble(), (digits.size - i - 1).toDouble()))).toInt()
    }
    return d
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int = TODO()

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String = TODO()

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var list: MutableList<Int> = mutableListOf()
    var list1: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0)
    var m = n
    var i = 0
    while (m > 0) {
        list.add(i, Math.floorMod(m, 10))
        m = Math.floorDiv(m, 10)
        i++
    }
    for (i in 0..(list.size - 1)) {
        list1[i] = list[i]
    }

    var dv: String = ""
    var hc: String = ""
    var last: String = ""
    var ht: String = ""
    var hn: String = ""
    var first: String = ""
    var hcn: String = ""
    var htn: String = ""
    var output: String = ""

    if (list.size == 1) {
        if (list1[1] == 1) {

            when (list1[0]) {
                0 -> last = "десять"
                1 -> last = "одиннадцать"
                2 -> last = "двенадцать"
                3 -> last = "тринадцать"
                4 -> last = "четырнадцать"
                5 -> last = "пятнадцать"
                6 -> last = "шестнадцать"
                7 -> last = "семнадцать"
                8 -> last = "восемнадцать"
                9 -> last = "девятнадцать"
            }
        } else {
            when (list1[0]) {
                0 -> dv = ""
                1 -> dv = " один"
                2 -> dv = " два"
                3 -> dv = " три"
                4 -> dv = " четыре"
                5 -> dv = " пять"
                6 -> dv = " шесть"
                7 -> dv = " семь"
                8 -> dv = " восемь"
                9 -> dv = " девять"
            }
            when (list1[1]) {
                0 -> hc = ""
                2 -> hc = " двадцать"
                3 -> hc = " тридцать"
                4 -> hc = " сорок"
                5 -> hc = " пятьдесят"
                6 -> hc = " шестьдесят"
                7 -> hc = " семьдесят"
                8 -> hc = " восемьдесят"
                9 -> hc = " девяносто"
            }
        }
        return dv.trim()
    } else
        if (list.size == 2) {
            if (list1[1] == 1) {

                when (list1[0]) {
                    0 -> last = "десять"
                    1 -> last = "одиннадцать"
                    2 -> last = "двенадцать"
                    3 -> last = "тринадцать"
                    4 -> last = "четырнадцать"
                    5 -> last = "пятнадцать"
                    6 -> last = "шестнадцать"
                    7 -> last = "семнадцать"
                    8 -> last = "восемнадцать"
                    9 -> last = "девятнадцать"
                }
            } else {
                when (list1[0]) {
                    0 -> dv = ""
                    1 -> dv = " один"
                    2 -> dv = " два"
                    3 -> dv = " три"
                    4 -> dv = " четыре"
                    5 -> dv = " пять"
                    6 -> dv = " шесть"
                    7 -> dv = " семь"
                    8 -> dv = " восемь"
                    9 -> dv = " девять"
                }
                when (list1[1]) {
                    0 -> hc = ""
                    2 -> hc = " двадцать"
                    3 -> hc = " тридцать"
                    4 -> hc = " сорок"
                    5 -> hc = " пятьдесят"
                    6 -> hc = " шестьдесят"
                    7 -> hc = " семьдесят"
                    8 -> hc = " восемьдесят"
                    9 -> hc = " девяносто"
                }
            }
            if (list[1] == 1) return last.trim()
            else return (hc + dv).trim()
        } else
            if (list.size == 3) {
                if (list1[1] == 1) {

                    when (list1[0]) {
                        0 -> last = " десять"
                        1 -> last = " одиннадцать"
                        2 -> last = " двенадцать"
                        3 -> last = " тринадцать"
                        4 -> last = " четырнадцать"
                        5 -> last = " пятнадцать"
                        6 -> last = " шестнадцать"
                        7 -> last = " семнадцать"
                        8 -> last = " восемнадцать"
                        9 -> last = " девятнадцать"
                    }
                } else {
                    when (list1[0]) {
                        0 -> dv = ""
                        1 -> dv = " один"
                        2 -> dv = " два"
                        3 -> dv = " три"
                        4 -> dv = " четыре"
                        5 -> dv = " пять"
                        6 -> dv = " шесть"
                        7 -> dv = " семь"
                        8 -> dv = " восемь"
                        9 -> dv = " девять"
                    }
                    when (list1[1]) {
                        0 -> hc = ""
                        2 -> hc = " двадцать"
                        3 -> hc = " тридцать"
                        4 -> hc = " сорок"
                        5 -> hc = " пятьдесят"
                        6 -> hc = " шестьдесят"
                        7 -> hc = " семьдесят"
                        8 -> hc = " восемьдесят"
                        9 -> hc = " девяносто"
                    }
                }

                when (list1[2]) {
                    0 -> ht = ""
                    1 -> ht = "сто"
                    2 -> ht = "двести"
                    3 -> ht = "триста"
                    4 -> ht = "четыреста"
                    5 -> ht = "пятьсот"
                    6 -> ht = "шестьсот"
                    7 -> ht = "семьсот"
                    8 -> ht = "восемьсот"
                    9 -> ht = "девятьсот"
                }
                if (list[1] == 1) return (ht + last).trim()
                else if (list[1] == 0) return (ht + dv).trim()
                else return (ht + hc + dv).trim()
            } else {
                if (list.size == 4) {

                    if (list1[1] == 1)
                        when (list1[0]) {
                            0 -> last = " десять"
                            1 -> last = " одиннадцать"
                            2 -> last = " двенадцать"
                            3 -> last = " тринадцать"
                            4 -> last = " четырнадцать"
                            5 -> last = " пятнадцать"
                            6 -> last = " шестнадцать"
                            7 -> last = " семнадцать"
                            8 -> last = " восемнадцать"
                            9 -> last = " девятнадцать"
                        }
                    else {
                        when (list1[0]) {
                            0 -> dv = ""
                            1 -> dv = " один"
                            2 -> dv = " два"
                            3 -> dv = " три"
                            4 -> dv = " четыре"
                            5 -> dv = " пять"
                            6 -> dv = " шесть"
                            7 -> dv = " семь"
                            8 -> dv = " восемь"
                            9 -> dv = " девять"
                        }
                        when (list1[1]) {
                            0 -> hc = ""
                            2 -> hc = " двадцать"
                            3 -> hc = " тридцать"
                            4 -> hc = " сорок"
                            5 -> hc = " пятьдесят"
                            6 -> hc = " шестьдесят"
                            7 -> hc = " семьдесят"
                            8 -> hc = " восемьдесят"
                            9 -> hc = " девяносто"
                        }
                    }

                    when (list1[2]) {
                        0 -> ht = ""
                        1 -> ht = " сто"
                        2 -> ht = " двести"
                        3 -> ht = " триста"
                        4 -> ht = " четыреста"
                        5 -> ht = " пятьсот"
                        6 -> ht = " шестьсот"
                        7 -> ht = " семьсот"
                        8 -> ht = " восемьсот"
                        9 -> ht = " девятьсот"
                    }


                    if (list1[4] == 1) {
                        when (list1[3]) {
                            0 -> first = " десять тысяч"
                            1 -> first = " одиннадцать тысяч"
                            2 -> first = " двенадцать тысяч"
                            3 -> first = " тринадцать тысяч"
                            4 -> first = " четырнадцать тысяч"
                            5 -> first = " пятнадцать тысяч"
                            6 -> first = " шестнадцать тысяч"
                            7 -> first = " семнадцать тысяч"
                            8 -> first = " восемнадцать тысяч"
                            9 -> first = " девятнадцать тысяч"
                        }
                    } else {
                        when (list1[3]) {
                            0 -> hn = "тысяч"
                            1 -> hn = "одна тысяча"
                            2 -> hn = "две тысячи"
                            3 -> hn = "три тысячи"
                            4 -> hn = "четыре тысячи"
                            5 -> hn = "пять тысяч"
                            6 -> hn = "шесть тысяч"
                            7 -> hn = "семь тысяч"
                            8 -> hn = "восемь тысяч"
                            9 -> hn = "девять тысяч"
                        }
                        when (list1[4]) {
                            0 -> hcn = ""
                            2 -> hcn = "двадцать"
                            3 -> hcn = "тридцать"
                            4 -> hcn = "сорок"
                            5 -> hcn = "пятьдесят"
                            6 -> hcn = "шестьдесят"
                            7 -> hcn = "семьдесят"
                            8 -> hcn = "восемьдесят"
                            9 -> hcn = "девяносто"
                        }
                    }
                    if (list[1] == 1) (hcn + hn + ht + last).trim()
                    else return (hcn + hn + ht + hc + dv).trim()
                } else


                    if (list.size == 5) {
                        if (list1[1] == 1) {

                            when (list1[0]) {
                                0 -> last = " десять"
                                1 -> last = " одиннадцать"
                                2 -> last = " двенадцать"
                                3 -> last = " тринадцать"
                                4 -> last = " четырнадцать"
                                5 -> last = " пятнадцать"
                                6 -> last = " шестнадцать"
                                7 -> last = " семнадцать"
                                8 -> last = " восемнадцать"
                                9 -> last = " девятнадцать"
                            }
                        } else {
                            when (list1[0]) {
                                0 -> dv = ""
                                1 -> dv = " один"
                                2 -> dv = " два"
                                3 -> dv = " три"
                                4 -> dv = " четыре"
                                5 -> dv = " пять"
                                6 -> dv = " шесть"
                                7 -> dv = " семь"
                                8 -> dv = " восемь"
                                9 -> dv = " девять"
                            }
                            when (list1[1]) {
                                0 -> hc = ""
                                2 -> hc = " двадцать"
                                3 -> hc = " тридцать"
                                4 -> hc = " сорок"
                                5 -> hc = " пятьдесят"
                                6 -> hc = " шестьдесят"
                                7 -> hc = " семьдесят"
                                8 -> hc = " восемьдесят"
                                9 -> hc = " девяносто"
                            }
                        }

                        when (list1[2]) {
                            0 -> ht = ""
                            1 -> ht = " сто"
                            2 -> ht = " двести"
                            3 -> ht = " триста"
                            4 -> ht = " четыреста"
                            5 -> ht = " пятьсот"
                            6 -> ht = " шестьсот"
                            7 -> ht = " семьсот"
                            8 -> ht = " восемьсот"
                            9 -> ht = " девятьсот"
                        }

                        if (list1[4] == 1) {
                            when (list1[3]) {
                                0 -> first = " десять тысяч"
                                1 -> first = " одиннадцать тысяч"
                                2 -> first = " двенадцать тысяч"
                                3 -> first = " тринадцать тысяч"
                                4 -> first = " четырнадцать тысяч"
                                5 -> first = " пятнадцать тысяч"
                                6 -> first = " шестнадцать тысяч"
                                7 -> first = " семнадцать тысяч"
                                8 -> first = " восемнадцать тысяч"
                                9 -> first = " девятнадцать тысяч"
                            }
                        } else {
                            when (list1[3]) {
                                0 -> hn = " тысяч"
                                1 -> hn = " одна тысяча"
                                2 -> hn = " две тысячи"
                                3 -> hn = " три тысячи"
                                4 -> hn = " четыре тысячи"
                                5 -> hn = " пять тысяч"
                                6 -> hn = " шесть тысяч"
                                7 -> hn = " семь тысяч"
                                8 -> hn = " восемь тысяч"
                                9 -> hn = " девять тысяч"
                            }
                            when (list1[4]) {
                                0 -> hcn = ""
                                2 -> hcn = "двадцать"
                                3 -> hcn = "тридцать"
                                4 -> hcn = "сорок"
                                5 -> hcn = "пятьдесят"
                                6 -> hcn = "шестьдесят"
                                7 -> hcn = "семьдесят"
                                8 -> hcn = "восемьдесят"
                                9 -> hcn = "девяносто"
                            }
                        }

                        if ((list[1] == 1) and (list[4] == 1))
                            output = (first + ht + last).trim()
                        else
                            if ((list[1] != 1) and (list[4] == 1))
                                output = (first + ht + hc + dv).trim()
                            else
                                if ((list[1] != 1) and (list[4] != 1))
                                    output = (hcn + hn + ht + hc + dv).trim()
                                else
                                    output = (hcn + hn + ht + last).trim()
                        return output
                    } else {
                        if (list1[1] == 1) {

                            when (list1[0]) {
                                0 -> last = " десять"
                                1 -> last = " одиннадцать"
                                2 -> last = " двенадцать"
                                3 -> last = " тринадцать"
                                4 -> last = " четырнадцать"
                                5 -> last = " пятнадцать"
                                6 -> last = " шестнадцать"
                                7 -> last = " семнадцать"
                                8 -> last = " восемнадцать"
                                9 -> last = " девятнадцать"
                            }
                        } else {
                            when (list1[0]) {
                                0 -> dv = ""
                                1 -> dv = " один"
                                2 -> dv = " два"
                                3 -> dv = " три"
                                4 -> dv = " четыре"
                                5 -> dv = " пять"
                                6 -> dv = " шесть"
                                7 -> dv = " семь"
                                8 -> dv = " восемь"
                                9 -> dv = " девять"
                            }
                            when (list1[1]) {
                                0 -> hc = ""
                                2 -> hc = " двадцать"
                                3 -> hc = " тридцать"
                                4 -> hc = " сорок"
                                5 -> hc = " пятьдесят"
                                6 -> hc = " шестьдесят"
                                7 -> hc = " семьдесят"
                                8 -> hc = " восемьдесят"
                                9 -> hc = " девяносто"
                            }
                        }

                        when (list1[2]) {
                            0 -> ht = ""
                            1 -> ht = " сто"
                            2 -> ht = " двести"
                            3 -> ht = " триста"
                            4 -> ht = " четыреста"
                            5 -> ht = " пятьсот"
                            6 -> ht = " шестьсот"
                            7 -> ht = " семьсот"
                            8 -> ht = " восемьсот"
                            9 -> ht = " девятьсот"
                        }

                        if (list1[4] == 1) {
                            when (list1[3]) {
                                0 -> first = " десять тысяч"
                                1 -> first = " одиннадцать тысяч"
                                2 -> first = " двенадцать тысяч"
                                3 -> first = " тринадцать тысяч"
                                4 -> first = " четырнадцать тысяч"
                                5 -> first = " пятнадцать тысяч"
                                6 -> first = " шестнадцать тысяч"
                                7 -> first = " семнадцать тысяч"
                                8 -> first = " восемнадцать тысяч"
                                9 -> first = " девятнадцать тысяч"
                            }
                        } else {
                            when (list1[3]) {
                                0 -> hn = " тысяч"
                                1 -> hn = " одна тысяча"
                                2 -> hn = " две тысячи"
                                3 -> hn = " три тысячи"
                                4 -> hn = " четыре тысячи"
                                5 -> hn = " пять тысяч"
                                6 -> hn = " шесть тысяч"
                                7 -> hn = " семь тысяч"
                                8 -> hn = " восемь тысяч"
                                9 -> hn = " девять тысяч"
                            }
                            when (list1[4]) {
                                0 -> hcn = ""
                                2 -> hcn = " двадцать"
                                3 -> hcn = " тридцать"
                                4 -> hcn = " сорок"
                                5 -> hcn = " пятьдесят"
                                6 -> hcn = " шестьдесят"
                                7 -> hcn = " семьдесят"
                                8 -> hcn = " восемьдесят"
                                9 -> hcn = " девяносто"
                            }
                        }
                        when (list1[5]) {
                            0 -> htn = ""
                            1 -> htn = "сто"
                            2 -> htn = "двести"
                            3 -> htn = "триста"
                            4 -> htn = "четыреста"
                            5 -> htn = "пятьсот"
                            6 -> htn = "шестьсот"
                            7 -> htn = "семьсот"
                            8 -> htn = "восемьсот"
                            9 -> htn = "девятьсот"
                        }
                        if ((list[1] == 1) and (list[4] == 1))
                            output = (htn + first + ht + last).trim()
                        else
                            if ((list[1] != 1) and (list[4] == 1))
                                output = (htn + first + ht + hc + dv).trim()
                            else
                                if ((list[1] != 1) and (list[4] != 1))
                                    output = (htn + hcn + hn + ht + hc + dv).trim()
                                else
                                    output = (htn + hcn + hn + ht + last).trim()
                    }
                return output
            }
}
