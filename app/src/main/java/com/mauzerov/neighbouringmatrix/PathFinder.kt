package com.mauzerov.neighbouringmatrix

interface PathFinder {
    // Return a list of nodes making up the shortest path from startNode to endNode in the given matrix
    // or an empty list if no path exists
    operator fun invoke(matrix: Matrix, startNode: Int, endNode: Int) : List<Int>
}

object DFSPathFinder : PathFinder {
    override fun invoke(matrix: Matrix, startNode: Int, endNode: Int) : List<Int> = with(matrix) {
        val visited = MutableList(size) { false }
        val stack   = mutableListOf<Int>()
        val path    = mutableListOf<Int>()

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

                val connections = findConnectedNodes(current)
                for (connection in connections) {
                    if (!visited[connection]) {
                        stack.add(connection)
                    }
                }
            }
        }
        return path
    }
}

// Dijkstra's algorithm implementation
object DijkstraPathFinder: PathFinder {
    override fun invoke(matrix: Matrix, startNode: Int, endNode: Int): List<Int> = with(matrix) {
        val distances = MutableList(size) { Int.MAX_VALUE }
        val previous  = MutableList(size) { -1 }
        val visited   = MutableList(size) { false }
        // Distance from startNode to startNode is 0
        distances[startNode] = 0
        var currentNode = startNode
        // While there are unvisited nodes
        // Find the closest unvisited node
        while (currentNode != endNode) {
            if (visited[currentNode]) return listOf()
            // Mark current node as visited
            visited[currentNode] = true
            // Get connections of current node
            val connections = findConnectedNodes(currentNode)
            // Update distances to connection
            // If the distance to the connection is shorter than the current distance
            // Update the distance and set the previous node
            for (connection in connections) {
                val newDistance = distances[currentNode] + this[currentNode, connection]!!
                if (newDistance < distances[connection]) {
                    distances[connection] = newDistance
                    previous[connection]  = currentNode
                }
            }
            // Find next node to visit
            // Set current node to the closest unvisited node
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
        // While there are previous nodes
        // Add current node to path
        while (node != -1) {
            path.add(node)
            node = previous[node]
        }
        return path.reversed()
    }
}