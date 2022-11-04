package com.mauzerov.neighbouringmatrix

class Matrix(val size: Int, private val finder: PathFinder) {
    val used = MutableList(size) { false }

    // 2D array of Costs
    private val matrix: MutableList<MutableList<Int>> =
        MutableList(size) { MutableList(size) { 0 } }

    fun isUsed(node: Int): Boolean {
        return matrix[node].any { it != 0 } || matrix.any { it[node] != 0 }
    }

    init {

    }

    operator fun set(x: Int, y: Int, value: Int) {
        if (x != y) matrix[x][y] = value
        used[x] = value != 0
        used[y] = value != 0
    }
    operator fun set(x: Int?, y: Int?, value: Int) {
        if (x == null || y == null) return
        this[x, y] = value
    }


    operator fun get(row: Int, column: Int): Int? {
        if (row == column)
            return null
        return matrix[row][column]
    }

    operator fun get(row: Int?, column: Int?): Int? {
        return row?.let { r ->
            column?.let { c ->
                this[r, c]
            }
        }
    }

    fun findConnectedNodes(node: Int): List<Int> {
        // Get all possible paths of a node
        return matrix[node]
            .withIndex()
            .filter { it.value != 0 }
            .map { it.index }
    }

    fun findShortestPath(startNode:Int, endNode: Int) : List<Int> =
        if (startNode != endNode) finder(this, startNode, endNode) else listOf()
}