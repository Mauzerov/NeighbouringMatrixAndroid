package com.mauzerov.neighbouringmatrix

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class MainActivityViewModel : BaseObservable() {
    private val matrixSize = 50

    private val matrix = Matrix(matrixSize, DijkstraPathFinder)


    @get:Bindable
    var neighbouring: Int
    get() = matrix[indexI, indexJ]
       set(value) {
            matrix[indexI, indexJ] = value
            notifyPropertyChanged(BR.path)
        }

    @Bindable
    var indexI = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var indexJ = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var startNode = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @Bindable
    var endNode = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR._all)
        }

    @get:Bindable
    val path: List<String>
    get() {
            val path = matrix.findShortestPath(startNode, endNode)
            return if (path.isNotEmpty()) {
                path.map { "($it)" }
            } else {
                listOf("No path found")
            }
        }
}
