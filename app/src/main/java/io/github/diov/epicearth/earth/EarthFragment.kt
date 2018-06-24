package io.github.diov.epicearth.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.diov.epicearth.R
import io.github.diov.epicearth.data.EarthData
import io.github.diov.epicearth.data.EarthOption

class EarthFragment : Fragment() {

    companion object {
        fun newInstance() = EarthFragment()
    }

    private lateinit var viewModel: EarthViewModel
    private var option: EarthOption? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.earth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EarthViewModel::class.java)

        viewModel.getEarthOptionLiveData().observe(this, Observer { option ->
            this.option = option
            setupBottomAppBar(option)
        })
        viewModel.getEarthDataLiveData().observe(this, Observer { dataList ->
            setupEarthPreview(dataList)
        })
    }

    private fun setupBottomAppBar(option: EarthOption?) {
        // TODO: show option bottom sheet dialog
        println(option)
    }

    private fun setupEarthPreview(dataList: List<EarthData>?) {
        if (null == dataList || dataList.isEmpty()) {
            println("no data")
        }
        option?.let {
            val earthData = dataList?.get(0)
            val imageUrl = earthData?.getImageUrl(it)
            println(imageUrl)

        }
    }
}
