/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.constraintlayout.core.state

class Registry {
    private val mCallbacks = HashMap<String, RegistryCallback>()
    fun register(name: String, callback: RegistryCallback) {
        mCallbacks[name] = callback
    }

    fun unregister(name: String, callback: RegistryCallback?) {
        mCallbacks.remove(name)
    }

    fun updateContent(name: String, content: String?) {
        val callback = mCallbacks[name]
        callback?.onNewMotionScene(content)
    }

    fun updateProgress(name: String, progress: Float) {
        val callback = mCallbacks[name]
        callback?.onProgress(progress)
    }

    fun currentContent(name: String): String? {
        val callback = mCallbacks[name]
        return callback?.currentMotionScene()
    }

    fun currentLayoutInformation(name: String): String? {
        val callback = mCallbacks[name]
        return callback?.currentLayoutInformation()
    }

    fun setDrawDebug(name: String, debugMode: Int) {
        val callback = mCallbacks[name]
        callback?.setDrawDebug(debugMode)
    }

    fun setLayoutInformationMode(name: String, mode: Int) {
        val callback = mCallbacks[name]
        callback?.setLayoutInformationMode(mode)
    }

    val layoutList: Set<String>
        get() = mCallbacks.keys

    fun getLastModified(name: String): Long {
        val callback = mCallbacks[name]
        return callback?.lastModified ?: Long.MAX_VALUE
    }

    fun updateDimensions(name: String, width: Int, height: Int) {
        val callback = mCallbacks[name]
        callback?.onDimensions(width, height)
    }

    companion object {
        val instance = Registry()
    }
}