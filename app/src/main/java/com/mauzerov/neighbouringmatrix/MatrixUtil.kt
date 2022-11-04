package com.mauzerov.neighbouringmatrix

import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph


fun Matrix.generateGraph(): Graph {
    val graph: Graph = SingleGraph("${System.currentTimeMillis()}")

    graph.isStrict = false
    // TODO: Improve rendering style
    // TODO: Access themes.xml And Set Background acordingly

    graph.setAttribute("ui.stylesheet",
        "edge {size:20px; arrow-shape: arrow; arrow-size: 70px, 40px; shape: line;} \n" +
        "node { fill-color: red; text-size: 60px; size: 90px; text-style: bold; text-alignment: left;}\n"
//        "node, edge, graph, sprite {  text-size: 20px; text-color: #ffffff; text-alignment: under; text-background-mode: rounded-box; text-background-color: #000000; text-padding: 5px, 5px; }"
    )

//    graph.setAttribute("ui.quality")
//    graph.setAttribute("ui.antialias")
//    graph.setAttribute("ui.screenshot", "graph.png")

    for (i in 0 until size) {
        if (!used[i]) continue;
        graph.addNode(i.toString())
            .setAttribute("ui.label", i.toString())
    }

    for (i in 0 until size) {
        for (j in 0 until size) {
            if (i != j && (this[i, j] ?: 0) != 0) {
                val edge = graph.addEdge("$i-$j", i.toString(), j.toString(), true)
                edge?.setAttribute("ui.label", this[i, j].toString())
            }
        }
    }
    return graph
}
