package id.yanuar.moovis.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import android.support.annotation.LayoutRes
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by Yanuar Arifin
 * halo@yanuar.id
 */

fun <R, L1, L2> LiveData<L1>.zip(other: LiveData<L2>, onChange: (L1, L2) -> R): MediatorLiveData<R> {

    var source1emitted = false
    var source2emitted = false

    val result = MediatorLiveData<R>()

    val mergeF = {
        val source1Value = this.value
        val source2Value = other.value

        if (source1emitted && source2emitted) {
            result.value = onChange.invoke(source1Value!!, source2Value!!)
        }
    }

    result.addSource(this) { source1emitted = true; mergeF.invoke() }
    result.addSource(other) { source2emitted = true; mergeF.invoke() }

    return result
}

fun Float.toPx(context: Context?) = Math.round(TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context?.resources?.displayMetrics
))

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}