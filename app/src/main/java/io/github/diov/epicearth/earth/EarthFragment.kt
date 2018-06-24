package io.github.diov.epicearth.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.diov.epicearth.R
import io.github.diov.epicearth.data.EarthData
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.earth.setting.EarthSettingDialog
import kotlinx.android.synthetic.main.earth_fragment.earthBottomAppBar

class EarthFragment : Fragment() {

    companion object {
        fun newInstance() = EarthFragment()
    }

    private lateinit var viewModel: EarthViewModel
    private var optionLiveData: LiveData<EarthOption>? = null
    private var dataLiveData: LiveData<List<EarthData>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.earth_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EarthViewModel::class.java)

        viewModel.initLiveData()
        viewModel.earthSettingLiveData?.observe(this, Observer {
            setupBottomAppBar()
        })
        dataLiveData = viewModel.earthDataLiveData
        dataLiveData?.observe(this, Observer { dataList ->
            setupEarthPreview(dataList)
        })
    }

    private fun setupBottomAppBar() {
        earthBottomAppBar.setNavigationOnClickListener {
            showSettingDialog()
        }
    }

    private fun showSettingDialog() {
        val settingDialog: EarthSettingDialog = EarthSettingDialog.newInstance(viewModel)
        settingDialog.show(fragmentManager, "Setting Dialog")
    }

    private fun setupEarthPreview(dataList: List<EarthData>?) {
        if (null == dataList || dataList.isEmpty()) {
            println("no data")
        }
        optionLiveData?.value?.let {
            val earthData = dataList?.get(0)
            val imageUrl = earthData?.getImageUrl(it)
            println(imageUrl)

        }
    }
}
