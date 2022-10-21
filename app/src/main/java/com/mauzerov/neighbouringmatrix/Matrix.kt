package com.mauzerov.neighbouringmatrix

class Matrix(val size: Int) {
    private val matrix: MutableList<MutableList<Boolean>> = MutableList(size) { MutableList(size) { false } }

    init {

    }

    operator fun set(x: Int, y: Int, value: Boolean) {
        if (x != y)
            matrix[x][y] = value
    }

    operator fun get(row: Int, column: Int): Boolean {
        return matrix[row][column]
    }

    private fun getNeighbours(node: Int): List<Int> {
        val neighbours = mutableListOf<Int>()
        for (i in 0 until size) {
            if (matrix[node][i])
                neighbours.add(i)
        }
        return neighbours
    }

    fun findPath(startNode: Int, endNode: Int) : List<Int> {
        // DFS
        val visited = MutableList(size) { false }
        val stack = mutableListOf<Int>()
        val path = mutableListOf<Int>()

        stack.add(startNode)

        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.size - 1)
            if (current == endNode) {
                path.add(current)
                break
            }

            if (!visited[current]) {
                visited[current] = true
                path.add(current)

                val neighbours = getNeighbours(current)
                for (neighbour in neighbours) {
                    if (!visited[neighbour]) {
                        stack.add(neighbour)
                    }
                }
            }
        }

        return path
    }
}