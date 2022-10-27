package com.mauzerov.neighbouringmatrix

class Matrix(val size: Int) {
    // 2D array of Costs
    private val matrix: MutableList<MutableList<Int>> =
        MutableList(size) { MutableList(size) { 0 } }

    init {

    }

    operator fun set(x: Int, y: Int, value: Int) {
        if (x != y)
            matrix[x][y] = value
    }

    operator fun get(row: Int, column: Int): Int {
        return matrix[row][column]
    }

    private fun getNeighbours(node: Int): List<Int> {
        val neighbours = mutableListOf<Int>()
        for (i in 0 until size) {
            if (matrix[node][i] != 0)
                neighbours.add(i)
        }
        return neighbours
    }

    fun findShortestPath(startNode: Int, endNode: Int) : List<Int> {
        // Dijkstra's algorithm
        val distances = MutableList(size) { Int.MAX_VALUE }
        val previous = MutableList(size) { -1 }
        val visited = MutableList(size) { false }
        // Distance from startNode to startNode is 0
        distances[startNode] = 0
        var currentNode = startNode
        while (currentNode != endNode) {

            visited[currentNode] = true
            val neighbours = getNeighbours(currentNode)
            for (neighbour in neighbours) {
                val newDistance = distances[currentNode] + matrix[currentNode][neighbour]
                if (newDistance < distances[neighbour]) {
                    distances[neighbour] = newDistance
                    previous[neighbour] = currentNode
                }
            }
            var minDistance = Int.MAX_VALUE
            for (i in 0 until size) {
                if (!visited[i] && distances[i] < minDistance) {
                    minDistance = distances[i]
                    currentNode = i
                }
            }
        }
        // Reconstruct path
        val path = mutableListOf<Int>()
        var node = endNode
        while (node != -1) {
            path.add(node)
            node = previous[node]
        }
        return path.reversed()
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