/*
 * Copyright (C) 2016 The Android Open Source Project
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
package androidx.constraintlayout.core.scout

import androidx.constraintlayout.core.widgets.WidgetContainer
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
import java.util.ArrayList

/**
 * Main entry for the Scout Inference engine.
 * All external access should be through this class
 * TODO support Stash / merge constraints table etc.
 */
object Scout {
    /**
     * Given a collection of widgets evaluates probability of a connection
     * and makes connections
     *
     * @param list collection of widgets to connect
     */
    /**
     * Recursive decent of widget tree inferring constraints on ConstraintWidgetContainer
     *
     * @param base
     */
    @JvmStatic
    fun inferConstraints(base: WidgetContainer?) {
        if (base == null) {
            return
        }
        if (base is ConstraintWidgetContainer && base.handlesInternalConstraints()) {
            return
        }
        val preX = base.x
        val preY = base.y
        base.x = 0
        base.y = 0
        for (constraintWidget in base.children) {
            if (constraintWidget is ConstraintWidgetContainer) {
                val container = constraintWidget
                if (!container.children.isEmpty()) {
                    inferConstraints(container)
                }
            }
        }
        val list = ArrayList(base.children)
        list.add(0, base)
        val widgets = list.toTypedArray()
        ScoutWidget.Companion.computeConstraints(ScoutWidget.Companion.create(widgets))
        base.x = preX
        base.y = preY
    }
}