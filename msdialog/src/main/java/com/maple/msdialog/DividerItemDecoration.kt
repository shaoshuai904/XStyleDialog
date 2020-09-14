package com.maple.msdialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maple.msdialog.utils.DensityUtils.dp2px

/**
 * 分割线
 *
 * @author : shaoshuai
 * @date ：2020/5/18
 */
class DividerItemDecoration(
        private val leftPadding: Int = 10,// 距离左侧的距离
        private val mDividerHeight: Int = 1,// 分割线高度
        private val mDivider: Drawable = ColorDrawable(Color.parseColor("#e6e9ee")),// 分割线
        private val mOrientation: Int = LinearLayoutManager.VERTICAL,// 方向
        private val skipLastItems: Int = 1 // 跳过几个item不画分割线，默认只在item中间画线
) : RecyclerView.ItemDecoration() {

    constructor(context: Context, leftDp: Float, divDp: Float) : this(leftDp.dp2px(context), divDp.dp2px(context))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDividerHeight)
        } else {
            outRect.set(0, 0, mDividerHeight, 0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    // 画竖直方向的分割线
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + leftPadding //左侧偏移量
        val right = parent.width - parent.paddingRight
        // 最后一个item 底部不画分割线 parent.childCount -1
        for (i in 0 until parent.childCount - skipLastItems) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDividerHeight
            // int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    // 画水平方向的分割线
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        for (i in 0 until parent.childCount - skipLastItems) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

}