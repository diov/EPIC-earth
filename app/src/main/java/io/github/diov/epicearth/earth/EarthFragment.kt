package io.github.diov.epicearth.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import io.github.diov.epicearth.R
import io.github.diov.epicearth.earth.setting.EarthSettingDialog
import kotlinx.android.synthetic.main.earth_fragment.earthBottomAppBar
import kotlinx.android.synthetic.main.earth_fragment.earthPreviewView

class EarthFragment : Fragment() {

    companion object {
        fun newInstance() = EarthFragment()
    }

    private lateinit var viewModel: EarthViewModel
    private var imageLiveData: LiveData<String>? = null

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
        imageLiveData = viewModel.earthImageLiveData
        imageLiveData?.observe(this, Observer { url ->
            setupEarthPreview(url)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.mipmap.earth_placeholder)
            .into(earthPreviewView)
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

    private fun setupEarthPreview(url: String?) {
        if (url.isNullOrEmpty()) {
            Picasso.get()
                .load(R.mipmap.earth_placeholder)
                .into(earthPreviewView)
        } else {
            Picasso.get()
                .load(url)
                .into(earthPreviewView)
        }
    }
}

