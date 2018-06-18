package io.github.diov.epicearth.earth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.diov.epicearth.R.id
import io.github.diov.epicearth.R.layout

class EarthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.earth_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(id.container, EarthFragment.newInstance())
                .commitNow()
        }
    }
}
