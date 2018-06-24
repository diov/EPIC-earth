package io.github.diov.epicearth.earth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.diov.epicearth.data.EarthData
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class EarthViewModel(application: Application) : AndroidViewModel(application) {
    private val earthDataLiveData by lazy {
        MutableLiveData<List<EarthData>>()
    }

    private val earthOptionLiveData by lazy {
        MutableLiveData<EarthOption>()
    }

    private val settingSource: EarthSettingLocalSource = EarthSettingLocalSource(application)
    private val dataRemoteSource: EarthDataRemoteSource = EarthDataRemoteSource()

    fun getEarthOptionLiveData(): LiveData<EarthOption> {
        loadEarthOption()
        return earthOptionLiveData
    }

    fun getEarthDataLiveData(): LiveData<List<EarthData>> {
        fetchEarthData()
        return earthDataLiveData
    }

    private fun loadEarthOption() {
        val option = settingSource.loadEarthOption()
        earthOptionLiveData.value = option
    }

    private fun fetchEarthData() {
        try {
            val option = earthOptionLiveData.value
            option?.let {
                launch(UI) {
                    val data = dataRemoteSource.fetchEarthData(it).await()
                    earthDataLiveData.value = data
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
