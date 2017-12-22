@file:Suppress("UNUSED_PARAMETER")
package lesson7.task2

import lesson7.task1.Cell
import lesson7.task1.Matrix
import lesson7.task1.MatrixImpl
import lesson7.task1.createMatrix
import java.util.*

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val re = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            re[i, j] = matrix[j, i]
        }
    }
    return re
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (width != other.width || height != other.height) throw IllegalArgumentException()
    if (width < 1 || height < 1) return this
    val re = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            re[i, j] = this[i, j] + other[i, j]
        }
    }
    return re
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */

fun generateSpiral(height: Int, width: Int): Matrix<Int> {
    val re = MatrixImpl(height, width, 0)
    
    var count = 1
    for (i in 0 until Math.min(height, width) - Math.min(height, width) / 2) {
        for (j in 0..3) {
            when (j) {
                0 -> for (k in i until width - i){ re[i, k] = count; count++ }
                1 -> for (k in i + 1 until height - i) { re[k, width - 1 - i] = count; count++ }
                2 -> for (k in width - 2 - i downTo i) { re[height - 1 - i, k] = count; count++ }
                3 -> for (k in height - 2 - i downTo i + 1) { re[k, i] = count; count++ }
            }
            if (re[height / 2, width / 2 - (width + 1) % 2] != 0) break
        }
    }
    return re
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> {
    val re = MatrixImpl(height, width, 1)
    for (i in 1 until Math.min(height, width) - Math.min(height, width) / 2) {
        for (j in i until width - i) re[i, j] = i + 1
        for (j in i + 1 until height - i) re[j, width - 1 - i] = i + 1
        for (j in width - 2 - i downTo i) re[height - 1 - i, j] = i + 1
        for (j in height - 2 - i downTo i + 1) re[j, i] = i + 1
    }
    return re
}

/**
 * Сложная
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> {
    val re = MatrixImpl(height, width, 0)
    var count = 1
    for (i in 0..height + width - 2) for (j in 0 until  height) {
        if (j > height - 1 ||  i - j !in 0 until width) continue
        re[j, i - j] = count
        count++
    }
    return re
}

/**
 * Средняя
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> = when {
    matrix.height != matrix.width -> throw IllegalArgumentException()
    matrix.height < 1 || matrix.width < 1 -> matrix
    else -> {
        val re = MatrixImpl(matrix.height, matrix.width, matrix[0, 0])
        for (i in 0 until re.height) for (j in 0 until re.width) re[i,j] = matrix[matrix.height - 1 - j, i]
        re
    }
}

/**
 * Сложная
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun isLatinSquare(matrix: Matrix<Int>): Boolean = when {
    matrix.height != matrix.width || matrix.height < 1 -> false
    else -> {
        var re = true
        val set = mutableSetOf<Int>()
        for (i in 1..matrix.height) set.add(i)
        for (i in 0 until matrix.height) {
            val row = mutableSetOf<Int>()
            val column = mutableSetOf<Int>()
            for (j in 0 until matrix.width) { row.add(matrix[i, j]); column.add(matrix[j,i]) }
            if (row != set || column != set) { re = false; break }
        }
        re
    }
}

/**
 * Средняя
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> = when {
    matrix.height == 1 && matrix.width == 1 -> MatrixImpl(1, 1, 0)
    else -> {
        val re = MatrixImpl(matrix.height, matrix.width, 0)
        for (i in 0 until matrix.height) for (j in 0 until matrix.width) {
            var sum = 0
            for (k in -1..1) for (l in -1..1)
                if (i + k in 0 until matrix.height && j + l in 0 until matrix.width && (k != 0 || l != 0))
                    sum += matrix[i + k, j + l]
            re[i, j] = sum
        }
        re
    }
}

/**
 * Средняя
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val Mrows = mutableListOf<Int>()
    val Mcolumns = mutableListOf<Int>()
    for (i in 0 until matrix.height) {
        var log = true
        for (j in 0 until matrix.width) if (matrix[i, j] != 0) { log = false; break }
        if (log) Mrows.add(i)
    }
    for (j in 0 until matrix.width) {
        var log = true
        for (i in 0 until matrix.height) if (matrix[i, j] != 0) { log = false; break }
        if (log) Mcolumns.add(j)
    }
    return Holes(Mrows, Mcolumns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> {
    val re = MatrixImpl(matrix.height, matrix.width, 0)
    for (i in 0 until matrix.height) for (j in 0 until matrix.width) {
        var sum = 0
        for (k in 0..i) for (l in 0..j) sum += matrix[k, l]
        re[i, j] = sum
    }
    return re
}

/**
 * Сложная
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (i in 0..lock.height - key.height) for (j in 0..lock.width - key.width) {
        var Key = true
        for (k in 0 until key.height) {
            var log = false
            for (l in 0 until key.width) if (lock[k + i, l + j] + key[k, l] != 1) { log = true; break }
            if (log) { Key = false; break }
        }
        if (Key) return Triple(true, i, j)
    }
    return Triple(false, 0, 0)
}
/**
 * Простая
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    for (i in 0 until this.height) for (j in 0 until this.width) this[i, j] = -this[i, j]
    return this
}

/**
 * Средняя
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> = when {
    this.width != other.height -> throw IllegalArgumentException()
    else -> {
        val re = MatrixImpl(this.height, other.width, 0)
        for (i in 0 until this.height) for (j in 0 until other.width) {
            var element = 0
            for (k in 0 until this.width) element += this[i, k] * other[k, j]
            re[i, j] = element
        }
        re
    }
}

/**
 * Сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */
fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    var Maxtrix = MatrixImpl(matrix.height, matrix.width, 0)
    for (i in 0 until Maxtrix.height) for (j in 0 until Maxtrix.width) { Maxtrix[i, j] = matrix[i, j]; Maxtrix[matrix[i, j]] = Cell(i, j) }
    var num = Maxtrix.height * Maxtrix.width
    for (i in 0 until moves.size) {
        if (moves[i] !in 1 until num) throw IllegalStateException()
        else {
            val deltaColumn = Math.abs(Maxtrix[moves[i]].column - Maxtrix[0].column)
            val deltaRow = Math.abs(Maxtrix[moves[i]].row - Maxtrix[0].row)
            if (deltaColumn == 1 && deltaRow == 0 || deltaRow == 1 && deltaColumn == 0) {
                Maxtrix[Maxtrix[0]] = moves[i]
                Maxtrix[Maxtrix[moves[i]]] = 0
                val k = Maxtrix[0]
                Maxtrix[0] = Maxtrix[moves[i]]
                Maxtrix[moves[i]] = k // create Temp
            }
            else throw IllegalStateException()
        }
    }
    return Maxtrix
}

/**
 * Очень сложная
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {
    val s0 = listOf('d', 'l', 'l', 'u', 'r')
    val s1 = listOf('u', 'l', 'l', 'd', 'r')
    val s2 = listOf('r', 'u', 'u', 'l', 'd')
    var trajectory = listOf<Int>()
    val Maxtrix = MatrixImpl(matrix.height, matrix.width, 0)
    for (i in 0 until Maxtrix.height) for (j in 0 until Maxtrix.width) { Maxtrix[i, j] = matrix[i, j]; Maxtrix[matrix[i, j]] = Cell(i, j) }
    for (l in 0 until matrix.height - 2) {
        trajectory += Maxtrix.change(List(Maxtrix[0].row - l) { 'u' } + List(Maxtrix[0].column) { 'l' })
        for (k in l * Maxtrix.width + 1..(l + 1) * Maxtrix.width) {
            if (Maxtrix[k].column < (k - 1) % Maxtrix.width) {
                for (j in 0 until Maxtrix[0].column - Maxtrix[k].column) {
                    trajectory += Maxtrix.change(List(Maxtrix[k].row - Maxtrix[0].row) { 'd' } +
                            List(Maxtrix[0].column - Maxtrix[k].column) { 'l' } + if (Maxtrix[k].row == l + 1) 'd' else 'u')
                    trajectory += Maxtrix.change(List((k - 1) % Maxtrix.width - Maxtrix[0].column)
                                                      { 'r' } + List(Maxtrix[0].row - l) { 'u' })
                }
            }
            if (Maxtrix[k].column != (k - 1) % Maxtrix.width) {
                trajectory += Maxtrix.change(List(Maxtrix[k].row - Maxtrix[0].row) { 'd' } +
                                             List(Maxtrix[k].column - Maxtrix[0].column) { 'r' })
                val tempS = if (Maxtrix[k].row == l) s0 else s1
                for (i in k - l * Maxtrix.width..Maxtrix[k].column) trajectory += Maxtrix.change(tempS)
            }
            if (Maxtrix[k].row != l && k % Maxtrix.width != 0) {
                trajectory += Maxtrix.change(if (Maxtrix[0].column > Maxtrix[k].column) listOf('u', 'l', 'd')
                                       else List(Maxtrix[k].row - l) { 'd' })
                for (i in 0 until Maxtrix[k].row - l) trajectory += Maxtrix.change(s2)
                trajectory += Maxtrix.change(listOf('r', 'u'))
            }
        }
        trajectory += Maxtrix.change(listOf('d', 'l', 'l'))
        if (Maxtrix[(l + 1) * Maxtrix.width].row == l) continue
        val deltaRow = Maxtrix[(l + 1) * Maxtrix.width].row - Maxtrix[0].row
        val deltaColumn = Maxtrix[(l + 1) * Maxtrix.width].column - Maxtrix[0].column
        val partOfTrajectory = List(deltaRow) { 'd' } + List(deltaColumn) { 'r' } +
                               List(deltaRow) { 'u' } + List(deltaColumn) { 'l' }
        for (k in 1..deltaRow + deltaColumn) trajectory += Maxtrix.change(partOfTrajectory)
        trajectory += Maxtrix.change(listOf('u', 'r', 'd', 'r', 'u', 'l', 'l', 'd'))
    }
    trajectory += Maxtrix.change(List(Maxtrix[0].column) { 'l' })
    for (l in 0 until Maxtrix.width - 2) {
        var k = Maxtrix.width * (Maxtrix.height - 1) + 1 + l
        for (j in 0..1) {
            if (Maxtrix[k].column < j + l)
                trajectory += Maxtrix.change(listOf('d', 'l', 'u', 'r', 'd', 'r', 'u', 'l', 'l', 'd', 'r', 'u'))
            if (Maxtrix[k].column != j + l) {
                trajectory += Maxtrix.change(List(Maxtrix[k].row - Maxtrix[0].row) { 'd' } +
                                             List(Maxtrix[k].column - Maxtrix[0].column) { 'r' })
                val tempS = if (Maxtrix[k].row == Maxtrix.height - 2) s0 else s1
                for (i in j + l until Maxtrix[k].column) trajectory += Maxtrix.change(tempS)
            }
            if (Maxtrix[k].row == Maxtrix.height - 1) {
                trajectory += Maxtrix.change(if (Maxtrix[0].column > Maxtrix[k].column) listOf('u', 'l', 'd', 'r', 'u')
                                       else listOf('d', 'r', 'u'))
            }
            k -= Maxtrix.width
        }
        trajectory += Maxtrix.change(s0)
    }
    while (Maxtrix[Maxtrix.width * Maxtrix.height - 1] != Cell(Maxtrix.height - 1, Maxtrix.width - 1))
        trajectory += Maxtrix.change(listOf('r', 'd', 'l', 'u'))
    trajectory += Maxtrix.change(if (Maxtrix[Maxtrix.height - 1, Maxtrix.width - 2] > Maxtrix[Maxtrix.height - 2, Maxtrix.width - 1])
        listOf('d', 'l', 'u', 'r', 'd', 'r', 'u', 'l', 'l', 'd', 'r', 'r', 'u', 'l', 'd', 'r') else listOf('d', 'r'))
    return trajectory
}

fun MatrixImpl<Int>.change(moves: List<Char>): MutableList<Int> {
    val trajectory = mutableListOf<Int>()
    for (i in 0 until moves.size) {
        var sColumn = 0
        var sRow = 0
        when (moves[i]) {
            'u' -> sRow = -1    
            'd' -> sRow = 1     
            'l' -> sColumn = -1 
            'r' -> sColumn = 1  
            else -> throw IllegalArgumentException()
        }
        if (this[0].row + sRow !in 0 until this.height || this[0].column + sColumn !in 0 until this.width)
            throw IllegalArgumentException()
        val digit = this[this[0].row + sRow, this[0].column + sColumn]
        trajectory.add(digit)
        this[this[0]] = digit
        this[this[digit]] = 0
        this[0] = this[digit]
        this[digit] = Cell(this[0].row - sRow, this[0].column - sColumn)
    }
    return trajectory
}
