package io.github.diov.epicearth.earth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.data.dao.EarthData
import io.github.diov.epicearth.data.source.local.EarthPreviousLocalSource
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class EarthViewModel(application: Application) : AndroidViewModel(application) {

    var earthSettingLiveData: MutableLiveData<EarthSetting>? = null
        get() {
            if (null == field) {
                field = MutableLiveData()
            }
            return field
        }

    var earthImageLiveData: MutableLiveData<String>? = null
        get() {
            if (null == field) {
                field = MutableLiveData()
            }
            return field
        }

    private val settingSource: EarthSettingLocalSource = EarthSettingLocalSource(application)
    private val previousSource: EarthPreviousLocalSource = EarthPreviousLocalSource(application)
    private val dataRemoteSource: EarthDataRemoteSource = EarthDataRemoteSource()
    private var option: EarthOption? = null

    fun initLiveData() {
        launch(UI) {
            val setting = settingSource.loadEarthSetting()
            val latestEarth = previousSource.loadLatestEarth()
            earthImageLiveData?.value = if (latestEarth.isEmpty()) "" else latestEarth[0].previewUrl
            updateEarthSetting(setting)
        }
    }

    fun storeEarthSetting(setting: EarthSetting) {
        settingSource.storeEarthSetting(setting)
        updateEarthSetting(setting)
    }

    private fun updateEarthSetting(setting: EarthSetting) {
        earthSettingLiveData?.value = setting

        val option = setting.generateOption()
        if (this.option?.colorType != option.colorType || this.option?.imageType != option.imageType) {
            fetchEarthData(option)
        }
        this.option = option
    }

    private fun fetchEarthData(option: EarthOption) {
        try {
            launch(UI) {
                val originData = dataRemoteSource.fetchEarthData(option)
                val dataList = originData.map {
                    EarthData.fromOriginData(it, option)
                }
                previousSource.storeEarthData(*dataList.toTypedArray())
                // TODO: remove hardcode position
                val previewImageUrl = dataList[0].previewUrl
                earthImageLiveData?.value = previewImageUrl
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        earthSettingLiveData = null
        earthImageLiveData = null
        super.onCleared()
    }
}
