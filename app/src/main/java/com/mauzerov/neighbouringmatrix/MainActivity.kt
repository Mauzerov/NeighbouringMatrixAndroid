package com.mauzerov.neighbouringmatrix

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.mauzerov.neighbouringmatrix.databinding.ActivityMainBinding
import org.graphstream.ui.android.AndroidFullGraphRenderer
import org.graphstream.ui.android_viewer.AndroidViewer
import org.graphstream.ui.android_viewer.DefaultView
import org.graphstream.ui.layout.Layouts
import org.graphstream.ui.view.Viewer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel = MainActivityViewModel(this)
    private lateinit var viewer: Viewer

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        System.setProperty("org.graphstream.ui", "android");

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewer = AndroidViewer(null, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD)
        viewer.enableAutoLayout(Layouts.newLayoutAlgorithm())


        binding.placeholder.addView((
            viewer.addView(AndroidViewer.DEFAULT_VIEW_ID,
                AndroidFullGraphRenderer().apply {
                    setContext(this@MainActivity)
                }) as DefaultView).apply {
            }
        )

        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId != BR.neighbouring && propertyId != BR._all)
                    return@onPropertyChanged
                viewer.replayGraph(viewModel.generateGraph())
                viewer.getView(AndroidViewer.DEFAULT_VIEW_ID).camera.resetView()
            }
        })

//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.notifyPropertyChanged(BR._all)
//            binding.swipeRefresh.isRefreshing = false
//        }
    }
}