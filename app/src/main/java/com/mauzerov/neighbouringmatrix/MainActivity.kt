package com.mauzerov.neighbouringmatrix

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue

import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.mauzerov.neighbouringmatrix.databinding.ActivityMainBinding
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.android.AndroidFullGraphRenderer
import org.graphstream.ui.android_viewer.AndroidViewer
import org.graphstream.ui.android_viewer.DefaultView
import org.graphstream.ui.layout.Layouts
import org.graphstream.ui.view.Viewer
import kotlin.streams.toList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel = MainActivityViewModel(this)
    private lateinit var viewer: Viewer
    private lateinit var graph: Graph

    val isGraphLoaded: Boolean get() = ::graph.isInitialized

    fun getThemeColor(@AttrRes res: Int): Int? {
        val typedValue = TypedValue()
        if (theme.resolveAttribute(res, typedValue, true)
        ) {
            return typedValue.data
        }
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        System.setProperty("org.graphstream.ui", "android")

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.isGraphLoaded = isGraphLoaded

        viewer = AndroidViewer(null, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD)
        viewer.enableAutoLayout(Layouts.newLayoutAlgorithm())

        binding.placeholder.addView((
            viewer.addView(AndroidViewer.DEFAULT_VIEW_ID,
                AndroidFullGraphRenderer().apply {
                    setContext(this@MainActivity)
                }) as DefaultView)
            .apply {
                getThemeColor(android.R.attr.colorBackground)?.let {
                    setBackgroundColor(it)
                }
                setVisible(false)
            }
        )

        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId != BR.neighbouring)
                    return@onPropertyChanged

                if (isGraphLoaded) {
                    val edges =  viewer.graphicGraph.edges().toList()
                    for (edge in edges) {
                        viewer.graphicGraph.removeEdge("${edge.node0.id}-${edge.node1.id}")
                    }

                    val nodes = viewer.graphicGraph.nodes().toList()
                    for (node in nodes) {
                        viewer.graphicGraph.removeNode(node.id)
                    }
                }

                graph = viewModel.generateGraph()
                binding.placeholder.visibility = android.view.View.VISIBLE
                getThemeColor(android.R.attr.colorBackground)?.let {
                    graph.setAttribute("ui.stylesheet", graph.getAttribute("ui.stylesheet") as String
                            + "\ngraph { fill-color: #${Integer.toHexString(argbToRgba(it))}; }")

                }
                getThemeColor(android.R.attr.colorForeground)?.let {
                    graph.setAttribute("ui.stylesheet", graph.getAttribute("ui.stylesheet") as String
                            + "\nedge { fill-color: #${Integer.toHexString(argbToRgba(it))}; }")
                }
                viewer.replayGraph(graph)
                viewer.getView(AndroidViewer.DEFAULT_VIEW_ID).apply{
                    camera.resetView()
                }
            }
        })

//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.notifyPropertyChanged(BR._all)
//            binding.swipeRefresh.isRefreshing = false
//        }
    }
}