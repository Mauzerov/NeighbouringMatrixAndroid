package com.mauzerov.neighbouringmatrix

class Matrix(size: Int) {
    private val matrix: MutableList<MutableList<Boolean>> = MutableList(size) { MutableList(size) { false } }

    init {

    }

    operator fun set(x: Int, y: Int, value: Boolean) {
        matrix[x][y] = value
    }

    operator fun get(row: Int, column: Int): Boolean {
        return matrix[row][column]
    }
}