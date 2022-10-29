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
    @OptIn(ExperimentalStdlibApi::class)
    fun findShortestPath(startNode: Int, endNode: Int) : List<Int> {
        if (startNode == endNode) return listOf()

        // Dijkstra's algorithm
        val distances = MutableList(size) { Int.MAX_VALUE }
        val previous = MutableList(size) { -1 }
        val visited = MutableList(size) { false }
        // Distance from startNode to startNode is 0
        distances[startNode] = 0
        var currentNode = startNode
        // While there are unvisited nodes
        // Find the closest unvisited node
        while (currentNode != endNode) {
            // If all nodes are visited, but the endNode is not reached, return empty list
            if (visited[currentNode])
                return listOf()

            // Mark current node as visited
            visited[currentNode] = true
            // Get neighbours of current node
            val neighbours = getNeighbours(currentNode)
            // Update distances to neighbours
            // If the distance to the neighbour is shorter than the current distance
            // Update the distance and set the previous node
            for (neighbour in neighbours) {
                val newDistance = distances[currentNode] + matrix[currentNode][neighbour]
                if (newDistance < distances[neighbour]) {
                    distances[neighbour] = newDistance
                    previous[neighbour] = currentNode
                }
            }

            // Find next node to visit
            // Set current node to the closest unvisited node
            var closestDistance = Int.MAX_VALUE
            for (i in 0 until size) {
                if (!visited[i] && distances[i] < closestDistance) {
                    closestDistance = distances[i]
                    currentNode = i
                }
            }
        }
        // Reconstruct path
        val path = mutableListOf<Int>()
        var node = endNode
        // While there are previous nodes
        // Add current node to path
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