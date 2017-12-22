@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
        if (height < 1 || width < 1) throw IllegalArgumentException()
        else MatrixImpl(height, width, e)

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val method = mutableMapOf<Cell, E>()
     private val key = MutableList(this.height * this.width) { Cell(0, 0) }

    init { for (i in 0 until height) for (j in 0 until width) method[Cell(i, j)] = e }

    override fun get(row: Int, column: Int): E = get(Cell(row, column))

    override fun get(cell: Cell): E = method[cell] ?: throw IllegalArgumentException()
    operator fun get(index: Int): Cell = key[index]
    override fun set(row: Int, column: Int, value: E) {
        if (row !in 0 until height || column !in 0 until width) throw IllegalArgumentException()
        else method[Cell(row, column)] = value
    }
    
    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)
    operator fun set(index: Int, element: Cell) { key[index] = element }

    override fun equals(other: Any?) = if (other is Matrix<*> && height == other.height && width == other.width) {
        var re = true
        for (i in 0 until height) for (j in 0 until width) if (other[i, j] != this[i, j]) {re = false; break}
        re
    }
    else false


    override fun hashCode(): Int {
        var sum = height
        sum = 31 * sum + width
        sum = 31 * sum + method.hashCode()
        return sum
    }

    override fun toString(): String {
        val r = mutableListOf<String>()
        r.add("[ ")
        for (row in 0 until height) {
            r.add("[")
            for (column in 0 until width) {
                r.add(this[row, column].toString())
                r.add(",")
            }
            r.removeAt(r.size - 1)
            r.add("] ")
        }
        r.add("]")
        return r.joinToString("")
    }
}
