package io.github.diov.epicearth.wallpaper

import android.database.ContentObserver
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import io.github.diov.epicearth.Constant
import io.github.diov.epicearth.R
import io.github.diov.epicearth.data.source.local.EarthDataLocalSource
import io.github.diov.epicearth.data.source.local.EarthSettingLocalSource
import io.github.diov.epicearth.helper.JobSchedulerHelper
import io.github.diov.epicearth.helper.random
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.lang.Exception

/**
 * Created by Dio_V on 2018/6/26.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class EpicWallpaperService : WallpaperService() {

    private lateinit var engine: EpicWallpaperEngine
    private var isDestroyed: Boolean = false

    override fun onCreate() {
        super.onCreate()

        isDestroyed = false
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
        private var wallpaperUrl: String? = null

        private val contentObservable: ContentObserver = object : ContentObserver(Handler()) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                val needUpdate = !isPreview
                fetchAndDraw(needUpdate)
            }
        }
        private val schedulerHelper = JobSchedulerHelper(this@EpicWallpaperService)

        init {
            paint.isFilterBitmap = true
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            contentResolver.registerContentObserver(Constant.LATEST_UPDATE_URL, false, contentObservable)
            if (!isPreview) {
                val setting = EarthSettingLocalSource(this@EpicWallpaperService).loadEarthSetting()
                schedulerHelper.startFetchJob(setting)
            }
        }

        override fun onDestroy() {
            contentResolver.unregisterContentObserver(contentObservable)
            if (!isPreview) {
                schedulerHelper.stopFetchJob()
            }
            super.onDestroy()
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

        private fun fetchAndDraw(forceUpdate: Boolean = false) {
            launch(UI) {
                var imageUrl = wallpaperUrl
                if (forceUpdate || imageUrl.isNullOrEmpty()) {
                    wallpaperUrl = loadRandomUrl()
                    imageUrl = wallpaperUrl
                }
                Log.i("WallpaperService==>", "previous image: $imageUrl")
                if (imageUrl.isNullOrEmpty()) {
                    picasso.load(R.mipmap.earth_placeholder)
                        .into(bitmapTarget)
                } else {
                    picasso.load(imageUrl)
                        .into(bitmapTarget)
                }
            }
        }

        private suspend fun loadRandomUrl(): String? {
            val earth = EarthDataLocalSource(this@EpicWallpaperService).loadLatestEarth()
            return earth.random()?.imageUrl
        }

        private fun drawWallpaper(bitmap: Bitmap) {
            if (isDestroyed || !isVisible) {
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
