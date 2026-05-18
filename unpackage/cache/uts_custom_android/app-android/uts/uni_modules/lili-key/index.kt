@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "SENSELESS_COMPARISON", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.liliKey
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.view.KeyEvent
import android.view.View
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
import java.lang.Runnable
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
open class VolumeKeyEvent (
    @JsonNotNull
    open var key: String,
    @JsonNotNull
    open var action: String,
    @JsonNotNull
    open var streamType: Number,
    @JsonNotNull
    open var volume: Number,
    @JsonNotNull
    open var previousVolume: Number,
    @JsonNotNull
    open var maxVolume: Number,
    @JsonNotNull
    open var timestamp: Number,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeKeyEvent", "uni_modules/lili-key/utssdk/app-android/index.uts", 10, 13)
    }
}
open class VolumeKeyResult (
    @JsonNotNull
    open var success: Boolean = false,
    @JsonNotNull
    open var code: String,
    @JsonNotNull
    open var message: String,
    open var data: UTSJSONObject? = null,
) : UTSObject(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeKeyResult", "uni_modules/lili-key/utssdk/app-android/index.uts", 19, 13)
    }
}
val volumeChangedAction = "android.media.VOLUME_CHANGED_ACTION"
val extraStreamType = "android.media.EXTRA_VOLUME_STREAM_TYPE"
val extraStreamValue = "android.media.EXTRA_VOLUME_STREAM_VALUE"
val extraPreviousStreamValue = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE"
val nativeKeyEventAction = "lili.volume.KEY_EVENT"
val nativeExtraKeyCode = "lili.volume.EXTRA_KEY_CODE"
val nativeExtraActionCode = "lili.volume.EXTRA_ACTION_CODE"
val debugEnabled = false
var volumeReceiver: VolumeKeyReceiver? = null
var volumeReceiverContext: Context? = null
var volumeCallback: ((event: VolumeKeyEvent) -> Unit)? = null
var latestEvent: VolumeKeyEvent? = null
var listening = false
var restoringVolume = false
var volumeKeyView: View? = null
var volumeKeyListener: VolumeKeyViewListener? = null
var volumeActivityWindowCallback: VolumeActivityWindowCallback? = null
var volumeActivityKeyCallback: VolumeActivityKeyCallback? = null
var lastNativeKeyCode: Number = -1
var lastNativeActionCode: Number = -1
var lastNativeEventAt: Number = 0
var lastNativeConsumedAt: Number = 0
fun volumeLog(message: String) {
    if (!debugEnabled) {
        return
    }
    console.log("lili-key:", message, " at uni_modules/lili-key/utssdk/app-android/index.uts:56")
}
fun keyActionText(actionCode: Int): String {
    if (actionCode == KeyEvent.ACTION_DOWN) {
        return "ACTION_DOWN"
    }
    if (actionCode == KeyEvent.ACTION_UP) {
        return "ACTION_UP"
    }
    if (actionCode == KeyEvent.ACTION_MULTIPLE) {
        return "ACTION_MULTIPLE"
    }
    return "ACTION_" + actionCode.toString()
}
fun keyCodeText(keyCode: Int): String {
    if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
        return "KEYCODE_VOLUME_UP"
    }
    if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
        return "KEYCODE_VOLUME_DOWN"
    }
    return "KEYCODE_" + keyCode.toString()
}
fun getActivity(): Activity {
    return UTSAndroid.getUniActivity() as Activity
}
fun getAudioManager(context: Context): AudioManager? {
    val manager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    return manager
}
fun makeResult(success: Boolean, code: String, message: String, data: UTSJSONObject? = null): VolumeKeyResult {
    return VolumeKeyResult(success = success, code = code, message = message, data = data)
}
fun eventToData(event: VolumeKeyEvent): UTSJSONObject {
    val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "uni_modules/lili-key/utssdk/app-android/index.uts", 85, 11))
    data["key"] = event.key
    data["action"] = event.action
    data["streamType"] = event.streamType
    data["volume"] = event.volume
    data["previousVolume"] = event.previousVolume
    data["maxVolume"] = event.maxVolume
    data["timestamp"] = event.timestamp
    return data
}
fun detectKey(volume: Number, previousVolume: Number): String {
    if (volume > previousVolume) {
        return "VOLUME_UP"
    }
    if (volume < previousVolume) {
        return "VOLUME_DOWN"
    }
    return "VOLUME_CHANGED"
}
fun dispatchNativeKeyEvent(keyCode: Int, actionCode: Int): Boolean {
    if (!listening) {
        volumeLog("native ignored because listener is stopped key=" + keyCodeText(keyCode) + " action=" + keyActionText(actionCode))
        return false
    }
    if (keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN) {
        return false
    }
    val actionText = if (actionCode == KeyEvent.ACTION_UP) {
        "UP"
    } else {
        "PRESS"
    }
    val streamType = AudioManager.STREAM_MUSIC
    var volume: Number = 0
    var maxVolume: Number = 0
    val manager = getAudioManager(getActivity())
    if (manager != null) {
        volume = manager!!.getStreamVolume(streamType)
        maxVolume = manager!!.getStreamMaxVolume(streamType)
    }
    val now = Date().getTime()
    val duplicateEvent = lastNativeKeyCode == keyCode && lastNativeActionCode == actionCode && now - lastNativeEventAt < 120
    volumeLog("native key event key=" + keyCodeText(keyCode) + " action=" + keyActionText(actionCode) + " duplicate=" + duplicateEvent.toString() + " volume=" + volume.toString(10) + "/" + maxVolume.toString(10))
    lastNativeKeyCode = keyCode
    lastNativeActionCode = actionCode
    lastNativeEventAt = now
    lastNativeConsumedAt = now
    val event = VolumeKeyEvent(key = if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
        "VOLUME_UP"
    } else {
        "VOLUME_DOWN"
    }
    , action = actionText, streamType = streamType, volume = volume, previousVolume = volume, maxVolume = maxVolume, timestamp = now)
    latestEvent = event
    if (!duplicateEvent && actionCode == KeyEvent.ACTION_UP && volumeCallback != null) {
        volumeLog("native callback dispatch key=" + event.key)
        volumeCallback!!(event)
    }
    volumeLog("native consumed key=" + keyCodeText(keyCode) + " action=" + keyActionText(actionCode))
    return true
}
open class VolumeKeyViewListener : View.OnKeyListener, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeKeyViewListener", "uni_modules/lili-key/utssdk/app-android/index.uts", 145, 7)
    }
    override fun onKey(view: View, keyCode: Int, event: KeyEvent): Boolean {
        volumeLog("view onKey key=" + keyCodeText(keyCode) + " action=" + keyActionText(event.getAction()))
        return dispatchNativeKeyEvent(keyCode, event.getAction())
    }
}
open class InstallKeyListenerRunnable : Runnable, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("InstallKeyListenerRunnable", "uni_modules/lili-key/utssdk/app-android/index.uts", 151, 7)
    }
    override fun run() {
        val activity = getActivity()
        val decorView = activity.getWindow().getDecorView()
        volumeLog("install view listener activity=" + activity.toString() + " decor=" + decorView.toString())
        if (volumeKeyView != null && volumeKeyView != decorView) {
            volumeKeyView!!.setOnKeyListener(null)
        }
        volumeKeyView = decorView
        volumeKeyListener = VolumeKeyViewListener()
        decorView.setFocusable(true)
        decorView.setFocusableInTouchMode(true)
        decorView.requestFocus()
        decorView.setOnKeyListener(volumeKeyListener)
        volumeLog("install view listener done focus=" + decorView.hasFocus().toString())
    }
}
open class RemoveKeyListenerRunnable : Runnable, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("RemoveKeyListenerRunnable", "uni_modules/lili-key/utssdk/app-android/index.uts", 168, 7)
    }
    override fun run() {
        if (volumeKeyView != null) {
            volumeLog("remove view listener")
            volumeKeyView!!.setOnKeyListener(null)
        }
        volumeKeyView = null
        volumeKeyListener = null
    }
}
fun consumeActivityVolumeEvent(params: UniActivityParams, event: KeyEvent?): Boolean {
    if (event == null) {
        volumeLog("activity callback event=null")
        return false
    }
    val keyCode = event!!.getKeyCode()
    volumeLog("activity callback key=" + keyCodeText(keyCode) + " action=" + keyActionText(event!!.getAction()))
    if (keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN) {
        return false
    }
    val consumed = dispatchNativeKeyEvent(keyCode, event!!.getAction())
    if (consumed) {
        params.result = true
        volumeLog("activity callback consumed result=true key=" + keyCodeText(keyCode))
    } else {
        volumeLog("activity callback not consumed key=" + keyCodeText(keyCode))
    }
    return consumed
}
open class VolumeActivityWindowCallback : UniActivityWindowCallback, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeActivityWindowCallback", "uni_modules/lili-key/utssdk/app-android/index.uts", 198, 7)
    }
    constructor() : super() {}
    override fun dispatchPreKeyEvent(params: UniActivityParams, event: KeyEvent?) {
        volumeLog("dispatchPreKeyEvent enter")
        consumeActivityVolumeEvent(params, event)
    }
    override fun dispatchKeyEvent(params: UniActivityParams, event: KeyEvent?) {
        volumeLog("dispatchKeyEvent enter")
        consumeActivityVolumeEvent(params, event)
    }
}
fun consumeActivityKeyEvent(params: UniActivityParams, keyCode: Int, event: KeyEvent?, source: String): Boolean {
    val actionCode = if (event == null) {
        KeyEvent.ACTION_DOWN
    } else {
        event!!.getAction()
    }
    volumeLog(source + " key=" + keyCodeText(keyCode) + " action=" + keyActionText(actionCode))
    if (keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN) {
        return false
    }
    val consumed = dispatchNativeKeyEvent(keyCode, actionCode)
    if (consumed) {
        params.result = true
        volumeLog(source + " consumed result=true key=" + keyCodeText(keyCode))
    }
    return consumed
}
open class VolumeActivityKeyCallback : UniActivityKeyEventCallback, IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeActivityKeyCallback", "uni_modules/lili-key/utssdk/app-android/index.uts", 224, 7)
    }
    constructor() : super() {}
    override fun onPreKeyDown(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        consumeActivityKeyEvent(params, keyCode, event, "onPreKeyDown")
    }
    override fun onKeyDown(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        consumeActivityKeyEvent(params, keyCode, event, "onKeyDown")
    }
    override fun onPreKeyUp(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        consumeActivityKeyEvent(params, keyCode, event, "onPreKeyUp")
    }
    override fun onKeyUp(params: UniActivityParams, keyCode: Int, event: KeyEvent?) {
        consumeActivityKeyEvent(params, keyCode, event, "onKeyUp")
    }
}
fun dispatchVolumeEvent(context: Context, intent: Intent) {
    volumeLog("broadcast volume changed received restoring=" + restoringVolume.toString())
    if (restoringVolume) {
        return
    }
    val streamType = intent.getIntExtra(extraStreamType, AudioManager.STREAM_MUSIC)
    val volume = intent.getIntExtra(extraStreamValue, -1)
    val previousVolume = intent.getIntExtra(extraPreviousStreamValue, volume)
    var maxVolume: Number = 0
    val manager = getAudioManager(context)
    if (manager != null) {
        maxVolume = manager!!.getStreamMaxVolume(streamType)
    }
    volumeLog("broadcast volume=" + volume.toString() + " previous=" + previousVolume.toString() + " stream=" + streamType.toString())
    val event = VolumeKeyEvent(key = detectKey(volume, previousVolume), action = "PRESS", streamType = streamType, volume = volume, previousVolume = previousVolume, maxVolume = maxVolume, timestamp = Date().getTime())
    latestEvent = event
    val nativeRecentlyConsumed = Date().getTime() - lastNativeConsumedAt < 1500
    if (volumeCallback != null && !nativeRecentlyConsumed) {
        volumeLog("broadcast callback dispatch key=" + event.key)
        volumeCallback!!(event)
    } else if (nativeRecentlyConsumed) {
        volumeLog("broadcast callback skipped because native event was consumed recently")
    }
    if (manager != null && previousVolume >= 0 && volume != previousVolume) {
        volumeLog("broadcast restore volume from=" + volume.toString() + " to=" + previousVolume.toString())
        restoringVolume = true
        manager!!.setStreamVolume(streamType, previousVolume, 0)
        setTimeout(fun(){
            restoringVolume = false
        }
        , 120)
    }
}
open class VolumeKeyReceiver : BroadcastReceiver(), IUTSSourceMap {
    override fun `__$getOriginalPosition`(): UTSSourceMapPosition? {
        return UTSSourceMapPosition("VolumeKeyReceiver", "uni_modules/lili-key/utssdk/app-android/index.uts", 282, 7)
    }
    override fun onReceive(context: Context, intent: Intent): Unit {
        val action = intent.getAction()
        volumeLog("receiver onReceive action=" + (if (action == null) {
            ""
        } else {
            action!!
        }
        ))
        if (action == nativeKeyEventAction) {
            val keyCode = intent.getIntExtra(nativeExtraKeyCode, -1)
            val actionCode = intent.getIntExtra(nativeExtraActionCode, -1)
            volumeLog("receiver native event key=" + keyCodeText(keyCode) + " action=" + keyActionText(actionCode))
            dispatchNativeKeyEvent(keyCode, actionCode)
            return
        }
        if (action != volumeChangedAction) {
            return
        }
        dispatchVolumeEvent(context, intent)
    }
}
fun unregisterVolumeReceiver() {
    if (volumeReceiver == null) {
        volumeReceiverContext = null
        return
    }
    try {
        val context = if (volumeReceiverContext == null) {
            getActivity().getApplicationContext()
        } else {
            volumeReceiverContext!!
        }
        context.unregisterReceiver(volumeReceiver)
        volumeLog("broadcast receiver unregistered")
    }
     catch (error: Throwable) {}
    volumeReceiver = null
    volumeReceiverContext = null
}
fun startVolumeKeyListener(callback: (event: VolumeKeyEvent) -> Unit): VolumeKeyResult {
    volumeLog("start requested listening=" + listening.toString())
    volumeCallback = callback
    if (listening) {
        try {
            val activity = getActivity()
            VolumeKeyNative.install(activity, nativeKeyEventAction, nativeExtraKeyCode, nativeExtraActionCode)
            activity.runOnUiThread(InstallKeyListenerRunnable())
        }
         catch (error: Throwable) {}
        return makeResult(true, "ALREADY_LISTENING", "音量键监听已启动", null)
    }
    try {
        val activity = getActivity()
        val nativeInstalled = VolumeKeyNative.install(activity, nativeKeyEventAction, nativeExtraKeyCode, nativeExtraActionCode)
        volumeLog("native kt interceptor install=" + nativeInstalled.toString())
        activity.runOnUiThread(InstallKeyListenerRunnable())
        volumeActivityWindowCallback = VolumeActivityWindowCallback()
        UTSAndroid.onActivityCallback(volumeActivityWindowCallback!!)
        volumeLog("activity callback registered")
        volumeActivityKeyCallback = VolumeActivityKeyCallback()
        UTSAndroid.onActivityCallback(volumeActivityKeyCallback!!)
        volumeLog("activity key callback registered")
        val filter = IntentFilter()
        filter.addAction(volumeChangedAction)
        filter.addAction(nativeKeyEventAction)
        unregisterVolumeReceiver()
        volumeReceiver = VolumeKeyReceiver()
        volumeReceiverContext = activity.getApplicationContext()
        volumeReceiverContext!!.registerReceiver(volumeReceiver, filter)
        listening = true
        volumeLog("broadcast receiver registered")
        return makeResult(true, "LISTENING", "音量键监听已启动", null)
    }
     catch (error: Throwable) {
        volumeLog("start failed " + JSON.stringify(error))
        unregisterVolumeReceiver()
        try {
            VolumeKeyNative.uninstall(getActivity())
        }
         catch (error: Throwable) {}
        if (volumeActivityWindowCallback != null) {
            UTSAndroid.offActivityCallback(volumeActivityWindowCallback!!)
        }
        volumeActivityWindowCallback = null
        if (volumeActivityKeyCallback != null) {
            UTSAndroid.offActivityCallback(volumeActivityKeyCallback!!)
        }
        volumeActivityKeyCallback = null
        listening = false
        return makeResult(false, "START_FAILED", "音量键监听启动失败", null)
    }
}
fun stopVolumeKeyListener(): VolumeKeyResult {
    volumeLog("stop requested listening=" + listening.toString())
    if (!listening) {
        volumeCallback = null
        return makeResult(true, "NOT_LISTENING", "音量键监听未启动", null)
    }
    unregisterVolumeReceiver()
    try {
        val nativeUninstalled = VolumeKeyNative.uninstall(getActivity())
        volumeLog("native kt interceptor uninstall=" + nativeUninstalled.toString())
    }
     catch (error: Throwable) {}
    if (volumeActivityWindowCallback != null) {
        UTSAndroid.offActivityCallback(volumeActivityWindowCallback!!)
        volumeLog("activity callback unregistered")
    }
    volumeActivityWindowCallback = null
    if (volumeActivityKeyCallback != null) {
        UTSAndroid.offActivityCallback(volumeActivityKeyCallback!!)
        volumeLog("activity key callback unregistered")
    }
    volumeActivityKeyCallback = null
    volumeCallback = null
    listening = false
    try {
        getActivity().runOnUiThread(RemoveKeyListenerRunnable())
    }
     catch (error: Throwable) {
        volumeKeyView = null
        volumeKeyListener = null
    }
    return makeResult(true, "STOPPED", "音量键监听已停止", null)
}
fun isVolumeKeyListening(): VolumeKeyResult {
    val data: UTSJSONObject = _uO("__\$originalPosition" to UTSSourceMapPosition("data", "uni_modules/lili-key/utssdk/app-android/index.uts", 400, 11))
    data["listening"] = listening
    return makeResult(true, if (listening) {
        "LISTENING"
    } else {
        "NOT_LISTENING"
    }
    , if (listening) {
        "音量键监听中"
    } else {
        "音量键监听未启动"
    }
    , data)
}
fun getLatestVolumeKeyEvent(): VolumeKeyResult {
    if (latestEvent == null) {
        return makeResult(false, "NO_EVENT", "暂无音量键事件", null)
    }
    return makeResult(true, "LATEST_EVENT", "已获取最近一次音量键事件", eventToData(latestEvent!!))
}
