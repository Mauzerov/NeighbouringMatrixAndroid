package com.mauzerov.neighbouringmatrix

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class MainActivityViewModel : BaseObservable() {
    private val matrixSize = 50

    private val matrix = Matrix(matrixSize)

    @get:Bindable
    var neighbouring: Boolean
        get() = matrix[indexI, indexJ]
        set(value) = matrix.set(indexI, indexJ, value)

    @Bindable
    var indexI = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR.indexI)
            notifyPropertyChanged(BR.neighbouring)
        }

    @Bindable
    var indexJ = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR.indexJ)
            notifyPropertyChanged(BR.neighbouring)
        }

    @Bindable
    var startNode = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR.path)
        }

    @Bindable
    var endNode = 0
        set(value) {
            field = value.coerceIn(0, matrixSize - 1)
            notifyPropertyChanged(BR.path)
        }


    @get:Bindable
    val path: List<String>
        get() {
            val path = matrix.findPath(startNode, endNode)
            return if (path.isNotEmpty()) {
                path.map { "($it)" }
            } else {
                listOf("No path found")
            }
        }

    fun log() {
        Log.d("TAG", "log: $indexI $indexJ")
    }
}
