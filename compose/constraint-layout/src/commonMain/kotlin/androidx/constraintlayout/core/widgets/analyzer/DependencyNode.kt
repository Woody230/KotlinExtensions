/*
 * Copyright (C) 2019 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets.analyzer

open class DependencyNode(var run: WidgetRun) : Dependency {
    @JvmField
    var updateDelegate: Dependency? = null
    @JvmField
    var delegateToWidgetRun = false
    @JvmField
    var readyToSolve = false

    enum class Type {
        UNKNOWN, HORIZONTAL_DIMENSION, VERTICAL_DIMENSION, LEFT, RIGHT, TOP, BOTTOM, BASELINE
    }

    @JvmField
    var type = Type.UNKNOWN
    @JvmField
    var margin = 0
    @JvmField
    var value = 0
    @JvmField
    var marginFactor = 1
    @JvmField
    var marginDependency: DimensionDependency? = null
    @JvmField
    var resolved = false
    @JvmField
    var dependencies: MutableList<Dependency> = ArrayList()
    @JvmField
    var targets: MutableList<DependencyNode> = ArrayList()
    override fun toString(): String {
        return (run.widget.debugName + ":" + type + "("
                + (if (resolved) value else "unresolved") + ") <t=" + targets.size + ":d=" + dependencies.size + ">")
    }

    open fun resolve(value: Int) {
        if (resolved) {
            return
        }
        resolved = true
        this.value = value
        for (node in dependencies) {
            node.update(node)
        }
    }

    override fun update(node: Dependency) {
        for (target in targets) {
            if (!target.resolved) {
                return
            }
        }
        readyToSolve = true
        if (updateDelegate != null) {
            updateDelegate!!.update(this)
        }
        if (delegateToWidgetRun) {
            run.update(this)
            return
        }
        var target: DependencyNode? = null
        var numTargets = 0
        for (t in targets) {
            if (t is DimensionDependency) {
                continue
            }
            target = t
            numTargets++
        }
        if (target != null && numTargets == 1 && target.resolved) {
            if (marginDependency != null) {
                margin = if (marginDependency!!.resolved) {
                    marginFactor * marginDependency!!.value
                } else {
                    return
                }
            }
            resolve(target.value + margin)
        }
        if (updateDelegate != null) {
            updateDelegate!!.update(this)
        }
    }

    fun addDependency(dependency: Dependency) {
        dependencies.add(dependency)
        if (resolved) {
            dependency.update(dependency)
        }
    }

    fun name(): String {
        var definition = run.widget.debugName
        definition += if (type == Type.LEFT
            || type == Type.RIGHT
        ) {
            "_HORIZONTAL"
        } else {
            "_VERTICAL"
        }
        definition += ":" + type.name
        return definition ?: ""
    }

    fun clear() {
        targets.clear()
        dependencies.clear()
        resolved = false
        value = 0
        readyToSolve = false
        delegateToWidgetRun = false
    }
}