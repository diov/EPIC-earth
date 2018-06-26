package io.github.diov.epicearth.earth

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
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
import io.github.diov.epicearth.wallpaper.EpicWallpaperService
import kotlinx.android.synthetic.main.earth_fragment.earthBottomAppBar
import kotlinx.android.synthetic.main.earth_fragment.earthFloatingActionButton
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

        earthFloatingActionButton.setOnClickListener {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)

            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(context, EpicWallpaperService::class.java)
            )

            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

            if (intent.resolveActivity(context?.packageManager) != null) {
                startActivity(intent)
            } else {
                // TODO: show friendly message.
            }

        }
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

