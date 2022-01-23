package me.frost.hueweb

import io.kvision.redux.RAction
import kotlinx.browser.window
import kotlinx.coroutines.*


sealed class LightAction : RAction {
    object Lights : LightAction()
    data class SwitchLight(val light: Light) : LightAction()
}

fun lightsReducer(state: List<Light>, action: LightAction): List<Light> {
    return when (action) {
        is LightAction.SwitchLight -> {

            CoroutineScope(Dispatchers.Default).launch {
                withContext(Dispatchers.Default) { Model.switchLight(action.light) }
                Model.callLights()
            }

            Model.lights
        }
        is LightAction.Lights -> {

            CoroutineScope(window.asCoroutineDispatcher()).launch {
                Model.callLights()
            }
            Model.lights
        }
    }
}
