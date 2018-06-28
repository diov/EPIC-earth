package io.github.diov.epicearth.wallpaper

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.github.diov.epicearth.R
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.data.source.local.EarthPreviousLocalSource
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.lang.Exception

/**
 * Created by Dio_V on 2018/6/26.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EpicWallpaperService : WallpaperService() {

    private lateinit var setting: EarthSetting
    private lateinit var option: EarthOption
    private lateinit var engine: EpicWallpaperEngine
    private var isDestroyed: Boolean = false

    override fun onCreate() {
        super.onCreate()

        isDestroyed = false
        setting = EarthSettingLocalSource(this@EpicWallpaperService).loadEarthSetting()
        option = setting.generateOption()
        Log.i("WallpaperService==>", "onCreate")
    }

    override fun onDestroy() {
        // TODO: remove JobScheduler

        isDestroyed = true
        Log.i("WallpaperService==>", "onDestroy")
        super.onDestroy()
    }

    override fun onCreateEngine(): Engine {
        Log.i("WallpaperService==>", "onCreateEngine")
        // TODO: add JobScheduler

        val engine = EpicWallpaperEngine()
        this.engine = engine
        return engine
    }

    inner class EpicWallpaperEngine : WallpaperService.Engine() {
        private val region: Rect = Rect()
        private val paint: Paint = Paint()
        private val picasso: Picasso = Picasso.Builder(this@EpicWallpaperService).build()
        private val bitmapTarget: Target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) = Unit

            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                bitmap?.let { drawWallpaper(it) }
            }
        }

        init {
            paint.isFilterBitmap = true
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)

            if (isVisible) {
                fetchAndDraw()
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)

            fetchAndDraw()
        }

        override fun onVisibilityChanged(visible: Boolean) {

            if (visible) {
                fetchAndDraw()
            }
        }

        private fun fetchAndDraw() {
            launch(UI) {
                val earth = EarthPreviousLocalSource(this@EpicWallpaperService).loadLatestEarth()
                val imageUrl = earth[0].imageUrl
                Log.i("WallpaperService==>", "previous image: $imageUrl")
                if (imageUrl.isEmpty()) {
                    picasso.load(R.mipmap.earth_placeholder)
                        .into(bitmapTarget)
                } else {
                    picasso.load(imageUrl)
                        .into(bitmapTarget)
                }
            }
        }

        private fun drawWallpaper(bitmap: Bitmap) {
            if (isDestroyed) {
                return
            }

            val canvas = surfaceHolder.lockCanvas()

            region.set(0, 0, canvas.width, canvas.height)
            if (region.width() > region.height()) {
                region.inset((region.width() - region.height()) / 2, 0)
            } else {
                region.inset(0, (region.height() - region.width()) / 2)
            }

            canvas.drawBitmap(bitmap, null, region, paint)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}
