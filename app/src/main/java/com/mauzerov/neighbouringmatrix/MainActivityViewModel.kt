package com.mauzerov.neighbouringmatrix

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class MainActivityViewModel : BaseObservable() {
    @get:Bindable val isLoading = false

    private val matrixSize = 50

    private val matrix = Matrix(matrixSize, DijkstraPathFinder)


    @get:Bindable
    var neighbouring: Int?
        get() = matrix[indexI, indexJ]
        set(value) {
            indexI?.let { i ->
                indexJ?.let { j ->
                    matrix[i, j] = value ?: 0
                    notifyPropertyChanged(BR.path)
                }
            }
        }

    @Bindable
    var indexI: Int? = 0
        set(value) {
            field = value?.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var indexJ: Int? = 0
        set(value) {
            field = value?.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var startNode: Int? = 0
        set(value) {
            field = value?.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var endNode: Int? = 0
        set(value) {
            field = value?.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @get:Bindable
    val path: List<String>
    get() {
            if (startNode == null || endNode == null)
                return listOf("No path found")

            val path = matrix.findShortestPath(startNode!!, endNode!!)
            return if (path.isNotEmpty()) {
                path.map { "($it)" }
            } else {
                listOf("No path found")
            }
        }

    fun refresh() {
        notifyPropertyChanged(BR._all)
    }
}
