 package one.empty3.feature.app.maxSdk29.pro.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import one.empty3.feature.app.maxSdk29.pro.FragmentSuperClass
import one.empty3.feature.app.maxSdk29.pro.R

 class MainActivityFragmentsFragment : FragmentSuperClass() {

    companion object {
        fun newInstance() = MainActivityFragmentsFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)
        if (imageView == null) imageView = activity.findViewById(R.id.currentImageView)
        return inflate
    }

}