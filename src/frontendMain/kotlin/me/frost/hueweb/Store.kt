package me.frost.hueweb

import io.kvision.redux.RAction
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch


sealed class LightAction : RAction {
    object Lights : LightAction()
    data class SwitchLight(val light: Light) : LightAction()
}

fun lightsReducer(state: List<Light>, action: LightAction): List<Light> {
    return when (action) {
        is LightAction.SwitchLight -> {

            CoroutineScope(Dispatchers.Default).launch {
                println("foooooo")
                Model.switchLights(action.light)
            }

            println("bar")
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
