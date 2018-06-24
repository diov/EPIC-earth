package io.github.diov.epicearth.earth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.diov.epicearth.data.EarthData
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class EarthViewModel(application: Application) : AndroidViewModel(application) {

    var earthDataLiveData: MutableLiveData<List<EarthData>>? = null
        get() {
            if (null == field) {
                field = MutableLiveData()
            }
            return field
        }

    var earthOptionLiveData: MutableLiveData<EarthOption>? = null
        get() {
            if (null == field) {
                field = MutableLiveData()
            }
            return field
        }

    var earthSettingLiveData: MutableLiveData<EarthSetting>? = null
        get() {
            if (null == field) {
                field = MutableLiveData()
            }
            return field
        }

    private val settingSource: EarthSettingLocalSource = EarthSettingLocalSource(application)
    private val dataRemoteSource: EarthDataRemoteSource = EarthDataRemoteSource()

    fun initLiveData() {
        val setting = settingSource.loadEarthSetting()
        updateEarthSetting(setting)
    }

    fun storeEarthSetting(setting: EarthSetting) {
        settingSource.storeEarthSetting(setting)
        updateEarthSetting(setting)
    }

    private fun updateEarthSetting(setting: EarthSetting) {
        earthSettingLiveData?.value = setting

        val option = setting.generateOption()
        if (earthOptionLiveData?.value?.colorType != option.colorType) {
            fetchEarthData(option)
        }
        earthOptionLiveData?.value = option
    }

    private fun fetchEarthData(option: EarthOption) {
        try {
            launch(UI) {
                val data = dataRemoteSource.fetchEarthData(option).await()
                earthDataLiveData?.value = data
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        earthDataLiveData = null
        earthOptionLiveData = null
        earthSettingLiveData = null
        super.onCleared()
    }
}
