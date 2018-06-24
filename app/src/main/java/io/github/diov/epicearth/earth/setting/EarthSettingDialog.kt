package io.github.diov.epicearth.earth.setting

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.diov.epicearth.R
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.earth.EarthViewModel
import kotlinx.android.synthetic.main.setting_dialog.settingEnhancedMode
import kotlinx.android.synthetic.main.setting_dialog.settingImageQuality
import kotlinx.android.synthetic.main.setting_dialog.settingTitle
import kotlinx.android.synthetic.main.setting_dialog.settingWifiTrigger

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EarthSettingDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(viewModel: EarthViewModel): EarthSettingDialog {
            val dialog = EarthSettingDialog()
            dialog.viewModel = viewModel
            return dialog
        }
    }

    private lateinit var viewModel: EarthViewModel
    private var isShown = false
    private var setting: EarthSetting? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.setting_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.earthSettingLiveData?.observe(this, Observer {
            setting = it?.copy()
            setupTitle()
            setupEnhancedMode()
            setupImageQuality()
            setupWifiTrigger()
        })
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (isShown) {
            return
        }
        isShown = true
        super.show(manager, tag)
    }

    override fun dismiss() {
        isShown = false
        super.dismiss()
    }

    override fun onCancel(dialog: DialogInterface?) {
        isShown = false
        super.onCancel(dialog)
    }

    private fun setupTitle() {
        settingTitle.setTitle(R.string.setting_title)
        settingTitle.setSubmitListener {
            setting?.let {
                viewModel.storeEarthSetting(it)
                dismiss()
            }
        }
    }

    private fun setupEnhancedMode() {
        settingEnhancedMode.setTitle(R.string.setting_enhanced_mode)
        settingEnhancedMode.setIcon(R.drawable.ic_color_lens_24)
        val enableEnhanced = setting?.enhancedMode ?: false
        settingEnhancedMode.setDefaultValue(enableEnhanced)
        settingEnhancedMode.setSettingChangedListener {
            setting?.enhancedMode = it
        }
    }

    private fun setupImageQuality() {
        settingImageQuality.setTitle(R.string.setting_high_quality)
        settingImageQuality.setIcon(R.drawable.ic_image_24)
        val highQuality = setting?.highQuality ?: false
        settingImageQuality.setDefaultValue(highQuality)
        settingImageQuality.setSettingChangedListener {
            setting?.highQuality = it
        }
    }

    private fun setupWifiTrigger() {
        settingWifiTrigger.setTitle(R.string.setting_wifi_trigger)
        settingWifiTrigger.setIcon(R.drawable.ic_wifi_24)
        val wifiTrigger = setting?.wifiTrigger ?: false
        settingWifiTrigger.setDefaultValue(wifiTrigger)
        settingWifiTrigger.setSettingChangedListener {
            setting?.wifiTrigger = it
        }
    }
}
