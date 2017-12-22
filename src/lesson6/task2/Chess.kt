@file:Suppress("UNUSED_PARAMETER")


package lesson6.task2

import lesson6.task1.*
import lesson3.task1.powInt

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        val listCol = listOf<String>("a", "b", "c", "d", "e", "f", "g", "h")
        return if (inside()) listCol[column - 1] + row.toString()
        else ""
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    val listCol = listOf<String>("a", "b", "c", "d", "e", "f", "g", "h")
    var col = 0
    return try {

        (0..(listCol.size - 1))
                .filter { notation[0].toString() == listCol[it] }
                .forEach { col = it + 1 }
        Square(col, (notation[1].toString()).toInt())
    } catch (e: IllegalArgumentException) {
        Square(0, 0)
    }
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    return if (start == end) 0
    else
        if (start.column == end.column || start.row == end.row) 1
        else 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    return if (rookMoveNumber(start, end) == 0) listOf(start)
    else if (rookMoveNumber(start, end) == 1) listOf(start, end)
    else listOf(start, Square(start.column, end.row), end)
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    val p = end.row + end.column
    val m = end.row - end.column
    val sp = start.row + start.column
    val sm = start.row - start.column
    return if (start == end) 0
    else if (Math.abs(sm) % 2 != Math.abs(m) % 2) -1
    else if (p == sp || sm == m) 1
    else 2
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */

fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val pS = Point(start.column.toDouble(), start.row.toDouble())
    val pE = Point(end.column.toDouble(), end.row.toDouble())
    when {
        bishopMoveNumber(start, end) == -1 -> return listOf()
        bishopMoveNumber(start, end) == 0 -> return listOf(start)
        bishopMoveNumber(start, end) == 1 -> return listOf(start, end)
        else -> {
            try {
                val LineSP: Line = Line(pS, Math.PI / 4)
                val LineSM: Line = Line(pS, 3 * Math.PI / 4)
                val LineEP: Line = Line(pE, Math.PI / 4)
                val LineEM: Line = Line(pE, 3 * Math.PI / 4)
                val cR = LineSP.crossPoint(LineEM)
                val cL = LineSM.crossPoint(LineEP)
                if (cR.x.toInt() in 1..8 && cR.y.toInt() in 1..8)
                    return listOf(start, Square(Math.round(cR.x).toInt(), Math.round(cR.y).toInt()), end)
                else return listOf(start, Square(Math.round(cL.x).toInt(), Math.round(cL.y).toInt()), end)
            } catch (e: IllegalArgumentException) {
            }
            return listOf()
        }
    }

}


/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if(!start.inside() || !end.inside()) throw IllegalArgumentException()
    else
    return Math.max(Math.abs(end.column - start.column),Math.abs(end.row - start.row))
}
/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val Trajec = mutableListOf(start)
    var column = start.column
    var row = start.row
    var dC = end.column - column
    var dR = end.row - row
    var dowV = if (dC == 0) 0 else dC / Math.abs(dC)
    var horiVector = if (dR == 0) 0 else dR / Math.abs(dR)
    while (end.column - column != 0 && end.row - row != 0) {
        column += dowV
        row += horiVector
        Trajec.add(Square(column, row))
    }
    dC = end.column - column
    dR = end.row - row
    when {
        dC == 0 && dR == 0 -> return Trajec
        dC == 0 -> { dowV = 0; horiVector = dR / Math.abs(dR) }
        else -> { dowV = (dC) / Math.abs(dC); horiVector = 0 }
    }
    for (i in 1..Math.max(Math.abs(dC), Math.abs(dR)))
        Trajec.add(Square(column + dowV * i, row + horiVector * i))
    return Trajec
}


/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
class Coordinate {
    private data class Vertex(val name: Square) {
        val neighbors = mutableSetOf<Vertex>()
    }

    private val vertices = mutableMapOf<Square, Vertex>()

    private operator fun get(name: Square) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: Square) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        second.neighbors.add(first)
    }

    fun connect(first: Square, second: Square) = connect(this[first], this[second])

    fun neighbors(name: Square) = vertices[name]?.neighbors?.map { it.name } ?: listOf()

    fun bfs(start: Square, finish: Square) = bfs(vertices[start]!!, vertices[finish]!!)

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = mutableListOf<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue[0]
            queue.removeAt(0)
            val distance = visited[next]!! + 1
            var newNB: Square
            for (i in 0..7) {
                newNB = Square(next.name.column + 2 * powInt(-1, i / 2) * (i / 4 - 1) + powInt(-1, i) * (i / 4),
                                  next.name.row + 2 * powInt(-1, i / 2) * (i / 4) + powInt(-1, i) * (i / 4 - 1))
                if (newNB.inside() && vertices[newNB] !in visited) {
                    this.addVertex(newNB)
                    this.connect(next, vertices[newNB]!!)
                    visited.put(vertices[newNB]!!, distance)
                    queue.add(vertices[newNB]!!)
                    if (vertices[newNB] == finish) return distance
                }
            }
        }
        return -1
    }
}

fun knightMoveNumber(start: Square, end: Square): Int = when {
    !(start.inside() && end.inside()) -> throw IllegalArgumentException()
    start == end -> 0
    else -> {
        val g = Coordinate()
        g.addVertex(start)
        g.addVertex(end)
        g.bfs(start, end)
    }
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = when {
    !(start.inside() && end.inside()) -> throw IllegalArgumentException()
    start == end -> listOf(start)
    else -> {
        val g = Coordinate()
        g.addVertex(start)
        g.addVertex(end)
        val trajectory = mutableListOf(end)
        var tempSquare = end
        for (i in 0 until g.bfs(start, end)) {
            tempSquare = g.neighbors(tempSquare)[0]
            trajectory.add(tempSquare)
        }
        trajectory.reverse()
        trajectory
    }
}
