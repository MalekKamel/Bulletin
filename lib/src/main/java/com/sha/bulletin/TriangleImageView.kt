package com.sha.bulletin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Triangle shape to be used for [Bulletin]'s icon container.
 */
class TriangleImageView: AppCompatImageView {

    var color = -1
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        createTriangle(
                canvas,
                width,
                DisplayUtil.dp2px(context, 10f))
    }

    private fun createTriangle(canvas: Canvas, width: Int, height: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (color == -1) color = BulletinConfig.iconSetup.containerColorRes
        paint.color = color
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width / 2.toFloat(), height.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

}