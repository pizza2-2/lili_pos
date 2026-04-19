@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.uniRegisterRequestPermissionTips
import android.app.Activity
import android.graphics.Color
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.unicloud.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import io.dcloud.uts.permissionrequest.R
import java.lang.Runnable
import java.util.HashMap
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import io.dcloud.uniapp.extapi.createRequestPermissionListener as uni_createRequestPermissionListener
open class RequestPermissionTipsListener (
    open var onRequest: ((permissions: UTSArray<String>) -> Unit)? = null,
    open var onConfirm: ((permission: UTSArray<String>) -> Unit)? = null,
    open var onComplete: ((permissions: UTSJSONObject) -> Unit)? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RequestPermissionTipsListener", "uni_modules/uni-registerRequestPermissionTips/utssdk/interface.uts", 1, 13)
    }
}
typealias SetRequestPermissionTips = (tips: UTSJSONObject) -> Unit
var PermissionTipsView: View? = null
var permissionTips: HashMap<String, String> = HashMap<String, String>()
var permissionListener: RequestPermissionListener? = null
var listener: RequestPermissionTipsListener? = null
fun unregisterRequestPermissionTipsListener(e: RequestPermissionTipsListener?) {
    listener = null
    if (permissionListener != null) {
        permissionListener!!.stop()
        permissionListener = null
    }
    if (PermissionTipsView != null) {
        if (PermissionTipsView!!.getParent() != null) {
            PermissionTipsView!!.setAnimation(null)
            (PermissionTipsView!!.getParent() as ViewGroup).removeView(PermissionTipsView)
        }
        PermissionTipsView = null
    }
}
fun registerRequestPermissionTipsListener(l: RequestPermissionTipsListener?) {
    listener = l
    if (permissionListener == null) {
        permissionListener = uni_createRequestPermissionListener()
        permissionListener!!.onRequest(fun(permissions: UTSArray<String>){
            if (listener != null) {
                listener!!.onRequest?.invoke(permissions)
            }
        }
        )
        permissionListener!!.onConfirm(fun(permissions: UTSArray<String>){
            UTSAndroid.getUniActivity()!!.runOnUiThread(ConfirmRunnable(permissions))
        }
        )
        permissionListener!!.onComplete(fun(permissions: UTSArray<String>){
            UTSAndroid.getUniActivity()!!.runOnUiThread(CompleteRunnable(permissions))
        }
        )
    }
}
open class ConfirmRunnable : Runnable, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("ConfirmRunnable", "uni_modules/uni-registerRequestPermissionTips/utssdk/app-android/index.uts", 50, 7)
    }
    open lateinit var permissions: UTSArray<String>
    constructor(permissions: UTSArray<String>){
        this.permissions = permissions
    }
    override fun run() {
        var activity = UTSAndroid.getUniActivity()!!
        if (PermissionTipsView != null && PermissionTipsView!!.getParent() != null) {
            PermissionTipsView!!.setAnimation(null)
            (PermissionTipsView!!.getParent() as ViewGroup).removeView(PermissionTipsView)
        }
        if (this.permissions.length > 0) {
            try {
                PermissionTipsView = createPermissionWindow(activity, this.permissions)
                if (PermissionTipsView != null) {
                    (activity.findViewById(android.R.id.content) as ViewGroup).addView(PermissionTipsView!!)
                }
            }
             catch (e: Throwable) {
                console.log(e, " at uni_modules/uni-registerRequestPermissionTips/utssdk/app-android/index.uts:72")
            }
        }
        if (listener != null) {
            listener!!.onConfirm?.invoke(this.permissions)
        }
    }
}
open class CompleteRunnable : Runnable, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("CompleteRunnable", "uni_modules/uni-registerRequestPermissionTips/utssdk/app-android/index.uts", 76, 7)
    }
    open lateinit var permissions: UTSArray<String>
    constructor(permissions: UTSArray<String>){
        this.permissions = permissions
    }
    override fun run() {
        var activity = UTSAndroid.getUniActivity()!!
        if (PermissionTipsView != null) {
            PermissionTipsView!!.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.popupwindow_exit))
            (PermissionTipsView!!.getParent() as ViewGroup).removeView(PermissionTipsView!!)
            PermissionTipsView = null
        }
        if (listener != null) {
            var permissionStatus: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("permissionStatus", "uni_modules/uni-registerRequestPermissionTips/utssdk/app-android/index.uts", 89, 17))
            for(p in resolveUTSKeyIterator(this.permissions)){
                permissionStatus[p] = if (UTSAndroid.checkSystemPermissionGranted(UTSAndroid.getUniActivity()!!, _uA(
                    p
                ))) {
                    "grant"
                } else {
                    "denied"
                }
            }
            listener!!.onComplete?.invoke(permissionStatus)
        }
    }
}
val setRequestPermissionTips: SetRequestPermissionTips = fun(tips: UTSJSONObject){
    permissionTips.clear()
    for(k in resolveUTSKeyIterator(tips)){
        permissionTips.put(k, if (tips[k] != null) {
            tips[k].toString()
        } else {
            ""
        }
        )
    }
}
fun createPermissionWindow(activity: Activity, permissions: UTSArray<String>): ViewGroup? {
    var rootView = RelativeLayout(activity)
    rootView.setBackgroundColor(Color.TRANSPARENT)
    var backgroundView = LinearLayout(activity)
    backgroundView.setPadding(30, 0, 30, 30)
    backgroundView.setOrientation(1)
    backgroundView.setBackgroundResource(R.drawable.dcloud_permission_background)
    var permissionTipsList: UTSArray<String> = UTSArray<String>()
    for(p in resolveUTSKeyIterator(permissions)){
        if (permissionTips.containsKey(p) && permissionTipsList.indexOf(permissionTips.get(p)) == -1) {
            permissionTipsList.push(permissionTips.get(p)!!)
        }
    }
    for(p in resolveUTSKeyIterator(permissionTipsList)){
        var text = TextView(activity)
        text.setText(Html.fromHtml(p, Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING))
        text.setPadding(0, 30, 0, 0)
        text.setTextSize((5 * getScale()).toFloat())
        text.setTextColor(Color.BLACK)
        backgroundView.addView(text)
    }
    if (backgroundView.getChildCount() == 0) {
        return null
    }
    var rll = RelativeLayout.LayoutParams(-1, -2)
    rll.topMargin = (UTSAndroid.getStatusBarHeight() * getScale()).toInt()
    rll.leftMargin = 30
    rll.rightMargin = 30
    rll.bottomMargin = 30
    rootView.addView(backgroundView, rll)
    rootView.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.popupwindow_enter))
    return rootView
}
fun getScale(): Float {
    if (UTSAndroid.getUniActivity() != null) {
        return UTSAndroid.getUniActivity()!!.resources.displayMetrics.scaledDensity
    }
    return (0 as Number).toFloat()
}
