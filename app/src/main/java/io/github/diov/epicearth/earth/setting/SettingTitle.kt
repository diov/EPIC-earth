package io.github.diov.epicearth.earth.setting

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import io.github.diov.epicearth.R
import io.github.diov.epicearth.helper.dpToPx

/**
 * Created by Dio_V on 2018/6/24.
 * Copyright © 2018 diov.github.io. All rights reserved.
 */

class SettingTitle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val constraintSet = ConstraintSet()

    private val titleView = AppCompatTextView(context)
    private val submitView = AppCompatTextView(context)
    private var submitListener: OnSubmitListener? = null
    private val padding = context.dpToPx(16)

    init {
        setPaddingRelative(padding, 0, padding, 0)

        setupTitleView()
        setupSubmitView()
        setupConstraint()
    }

    private fun setupTitleView() {
        titleView.id = View.generateViewId()
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        titleView.setTextColor(Color.BLACK)
        val textLayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        addView(titleView, textLayoutParams)
    }

    private fun setupSubmitView() {
        submitView.id = View.generateViewId()
        submitView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        submitView.setText(android.R.string.ok)
        submitView.setTextColor(ContextCompat.getColor(context, R.color.secondaryDarkColor))
        submitView.setPaddingRelative(padding, 0, padding, 0)
        val submitLayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        submitView.setOnClickListener {
            submitListener?.invoke()
        }
        addView(submitView, submitLayoutParams)
    }

    private fun setupConstraint() {
        constraintSet.clone(this)

        constraintSet.connect(titleView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(titleView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(titleView.id, ConstraintSet.START, id, ConstraintSet.START)

        constraintSet.connect(submitView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(submitView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(submitView.id, ConstraintSet.END, id, ConstraintSet.END)

        constraintSet.applyTo(this)
    }

    fun setTitle(@StringRes titleId: Int) {
        titleView.setText(titleId)
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setSubmitListener(listener: OnSubmitListener) {
        this.submitListener = listener
    }
}

typealias OnSubmitListener = (() -> Unit)
