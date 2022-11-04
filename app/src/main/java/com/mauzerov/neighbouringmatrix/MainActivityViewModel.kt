package com.mauzerov.neighbouringmatrix

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.graphstream.graph.Graph

@OptIn(ExperimentalStdlibApi::class)
class MainActivityViewModel(private val context: Context) : BaseObservable() {
    @get:Bindable val isLoading = false

    private val matrixSize = 50
    private val matrix = Matrix(matrixSize, DijkstraPathFinder)

    fun generateGraph() : Graph {
        return matrix.generateGraph()
    }

    @get:Bindable("indexI", "indexJ")
    var neighbouring: Int?
        get() = matrix[indexI, indexJ]
        set(value) {
            if (value == null) return
            matrix[indexI, indexJ] = value
            notifyPropertyChanged(BR.neighbouring)
        }

    @Bindable
    var indexI: Int? = null
        set(value) {
            field = value?.coerceIn(0..<matrixSize)
            notifyPropertyChanged(BR.indexI)
        }

    @Bindable
    var indexJ: Int? = null
        set(value) {
            field = value?.coerceIn(0..<matrixSize)
            notifyPropertyChanged(BR.indexJ)
        }

    @Bindable
    var startNode: Int? = null
        set(value) {
            field = value?.coerceIn(0..<matrixSize)
            notifyPropertyChanged(BR.startNode)
        }

    @Bindable
    var endNode: Int? = null
        set(value) {
            field = value?.coerceIn(0..<matrixSize)
            notifyPropertyChanged(BR.endNode)
        }

    @get:Bindable("indexI", "indexJ", "startNode", "endNode", "neighbouring")
    val path: List<String>
    get() {
            if (startNode == null || endNode == null)
                return listOf(context.getString(R.string.no_path))

            val path = matrix.findShortestPath(startNode!!, endNode!!)
            return if (path.isNotEmpty()) {
                path.map { "($it)" }
            } else {
                listOf(context.getString(R.string.no_path))
            }
        }

    fun refresh() {
        notifyPropertyChanged(BR._all)
    }
}
