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
                withContext(Dispatchers.Default) { Model.switchLights(action.light) }
                Model.lights()
            }

            Model.lightsList
        }
        is LightAction.Lights -> {

            CoroutineScope(window.asCoroutineDispatcher()).launch {
                Model.lights()
            }
            Model.lightsList
        }
    }
}
