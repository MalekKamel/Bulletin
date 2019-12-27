package com.sha.bulletin

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue


object ColorHelper {

    fun accentColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
        val color: Int = a.getColor(0, 0)
        a.recycle()
        return color
    }
}