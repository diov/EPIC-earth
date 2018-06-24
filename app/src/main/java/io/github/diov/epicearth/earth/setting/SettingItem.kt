package io.github.diov.epicearth.earth.setting

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import io.github.diov.epicearth.helper.dpToPx

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class SettingItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val constraintSet = ConstraintSet()

    private val iconView = AppCompatImageView(context)
    private val titleView = AppCompatTextView(context)
    private val switcher = SwitchCompat(context)
    private var settingChangedListener: OnSettingChangeListener? = null
    private val padding = context.dpToPx(PADDING)

    init {
        setPaddingRelative(padding, 0, padding, 0)

        setupIconView()
        setupTitleView()
        setupSwitch()
        setupConstraint()
    }

    private fun setupIconView() {
        iconView.visibility = View.GONE
        iconView.id = View.generateViewId()
        val iconLayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        addView(iconView, iconLayoutParams)
    }

    private fun setupTitleView() {
        titleView.id = View.generateViewId()
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
        titleView.setTextColor(Color.BLACK)
        val textLayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        addView(titleView, textLayoutParams)
    }

    private fun setupSwitch() {
        switcher.id = View.generateViewId()
        val switcherLayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        switcher.setOnCheckedChangeListener { _, b ->
            settingChangedListener?.invoke(b)
        }
        addView(switcher, switcherLayoutParams)
    }

    private fun setupConstraint() {
        constraintSet.clone(this)

        constraintSet.connect(iconView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(iconView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(iconView.id, ConstraintSet.START, id, ConstraintSet.START)

        constraintSet.connect(titleView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(titleView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(titleView.id, ConstraintSet.START, iconView.id, ConstraintSet.END, padding)
        constraintSet.setGoneMargin(titleView.id, ConstraintSet.START, 0)

        constraintSet.connect(switcher.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(switcher.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(switcher.id, ConstraintSet.END, id, ConstraintSet.END)

        constraintSet.applyTo(this)
    }

    fun setIcon(@DrawableRes iconId: Int) {
        iconView.setImageResource(iconId)
        iconView.visibility = View.VISIBLE
    }

    fun setIcon(icon: Drawable) {
        iconView.setImageDrawable(icon)
        iconView.visibility = View.VISIBLE
    }

    fun setTitle(@StringRes titleId: Int) {
        titleView.setText(titleId)
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setDefaultValue(enable: Boolean) {
        switcher.isChecked = enable
    }

    fun setSettingChangedListener(listener: OnSettingChangeListener) {
        this.settingChangedListener = listener
    }

    companion object {
        private const val PADDING = 16
    }
}

typealias OnSettingChangeListener = ((value: Boolean) -> Unit)
