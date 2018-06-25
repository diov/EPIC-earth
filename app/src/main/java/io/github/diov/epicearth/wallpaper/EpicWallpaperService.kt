package io.github.diov.epicearth.wallpaper

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.github.diov.epicearth.data.EarthOption
import io.github.diov.epicearth.data.EarthSetting
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.data.source.remote.EarthDataRemoteSource
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

    override fun onCreate() {
        super.onCreate()

        setting = EarthSettingLocalSource(this@EpicWallpaperService).loadEarthSetting()
        option = setting.generateOption()
        // TODO: add JobScheduler
    }

    override fun onDestroy() {
        // TODO: remove JobScheduler

        super.onDestroy()
    }

    override fun onCreateEngine(): Engine {
        val engine = EpicWallpaperEngine()
        this.engine = engine
        return engine
    }

    inner class EpicWallpaperEngine : WallpaperService.Engine() {
        private val region: Rect = Rect()
        private val paint: Paint = Paint()

        init {
            paint.isFilterBitmap = true
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
                val list = EarthDataRemoteSource().fetchEarthData(option).await()
                val imageUrl = list[0].getRealImageUrl(option)
                Picasso.Builder(this@EpicWallpaperService).build().load(imageUrl).into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) = Unit

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                        bitmap?.let {
                            drawWallpaper(bitmap)
                        }
                    }
                })
            }
        }

        private fun drawWallpaper(bitmap: Bitmap) {
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
