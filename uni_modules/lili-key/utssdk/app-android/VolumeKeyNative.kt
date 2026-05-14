package uts.sdk.modules.liliKey

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import android.view.Window
import io.dcloud.uts.console

object VolumeKeyNative {
    private const val DEBUG = false
    private var originalCallback: Window.Callback? = null
    private var interceptCallback: Window.Callback? = null
    private var installedActivity: Activity? = null
    private var actionName: String = ""
    private var keyCodeExtra: String = ""
    private var actionCodeExtra: String = ""

    @JvmStatic
    fun install(activity: Activity, action: String, keyExtra: String, actionExtra: String): Boolean {
        actionName = action
        keyCodeExtra = keyExtra
        actionCodeExtra = actionExtra
        activity.runOnUiThread {
            try {
                val window = activity.window
                val current = window.callback
                if (interceptCallback != null && installedActivity === activity) {
                    if (DEBUG) console.log("lili-key-native: already installed")
                    return@runOnUiThread
                }
                originalCallback = current
                installedActivity = activity
                interceptCallback = VolumeWindowCallback(activity, current)
                window.callback = interceptCallback
                if (DEBUG) console.log("lili-key-native: installed callback=" + current.javaClass.name)
            } catch (error: Throwable) {
                if (DEBUG) console.log("lili-key-native: install failed " + error.message)
            }
        }
        return true
    }

    @JvmStatic
    fun uninstall(activity: Activity): Boolean {
        activity.runOnUiThread {
            try {
                val window = activity.window
                if (installedActivity === activity && interceptCallback != null && originalCallback != null) {
                    window.callback = originalCallback
                    if (DEBUG) console.log("lili-key-native: uninstalled")
                }
            } catch (error: Throwable) {
                if (DEBUG) console.log("lili-key-native: uninstall failed " + error.message)
            } finally {
                if (installedActivity === activity) {
                    installedActivity = null
                    interceptCallback = null
                    originalCallback = null
                }
            }
        }
        return true
    }

    private fun isVolumeKey(event: KeyEvent): Boolean {
        return event.keyCode == KeyEvent.KEYCODE_VOLUME_UP || event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
    }

    private fun emit(activity: Activity, event: KeyEvent) {
        if (actionName.isEmpty()) return
        val intent = Intent(actionName)
        intent.setPackage(activity.packageName)
        intent.putExtra(keyCodeExtra, event.keyCode)
        intent.putExtra(actionCodeExtra, event.action)
        activity.sendBroadcast(intent)
        if (DEBUG) console.log("lili-key-native: consumed key=" + event.keyCode + " action=" + event.action)
    }

    private class VolumeWindowCallback(
        private val activity: Activity,
        private val base: Window.Callback
    ) : Window.Callback by base {
        override fun dispatchKeyEvent(event: KeyEvent): Boolean {
            if (VolumeKeyNative.isVolumeKey(event)) {
                VolumeKeyNative.emit(activity, event)
                return true
            }
            return base.dispatchKeyEvent(event)
        }
    }
}
