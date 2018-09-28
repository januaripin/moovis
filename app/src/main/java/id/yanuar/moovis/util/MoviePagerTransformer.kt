package id.yanuar.moovis.util

import android.support.v4.view.ViewPager
import android.view.View


class MoviePagerTransformer(private val maxOffsetX: Int) : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        (page.parent as ViewPager).let {
            val centerXInViewPager = (page.left - it.scrollX) + page.measuredWidth / 2
            val offsetX = centerXInViewPager - it.measuredWidth / 2
            val offsetRate = offsetX.toFloat() * 0.38f / it.measuredWidth
            val scale = 1 - Math.abs(offsetRate)
            if (scale > 0) {
                page.scaleX = scale
                page.scaleY = scale
                page.translationX = -maxOffsetX * offsetRate
            }
        }
    }
}