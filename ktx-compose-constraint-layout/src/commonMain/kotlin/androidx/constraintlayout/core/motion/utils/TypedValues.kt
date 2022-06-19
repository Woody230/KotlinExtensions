/*
 * Copyright (C) 2021 The Android Open Source Project
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
package androidx.constraintlayout.core.motion.utils

/**
 * Provides an interface to values used in KeyFrames and in
 * Starting and Ending Widgets
 */
open interface TypedValues {
    /**
     * Used to set integer values
     *
     * @param id
     * @param value
     * @return true if it accepted the value
     */
    fun setValue(id: Int, value: Int): Boolean

    /**
     * Used to set float values
     *
     * @param id
     * @param value
     * @return true if it accepted the value
     */
    fun setValue(id: Int, value: Float): Boolean

    /**
     * Used to set String values
     *
     * @param id
     * @param value
     * @return true if it accepted the value
     */
    fun setValue(id: Int, value: String?): Boolean

    /**
     * Used to set boolean values
     *
     * @param id
     * @param value
     * @return true if it accepted the value
     */
    fun setValue(id: Int, value: Boolean): Boolean
    fun getId(name: String?): Int
    open interface AttributesType {
        companion object {
            val NAME: String = "KeyAttributes"
            val TYPE_CURVE_FIT: Int = 301
            val TYPE_VISIBILITY: Int = 302
            val TYPE_ALPHA: Int = 303
            val TYPE_TRANSLATION_X: Int = 304
            val TYPE_TRANSLATION_Y: Int = 305
            val TYPE_TRANSLATION_Z: Int = 306
            val TYPE_ELEVATION: Int = 307
            val TYPE_ROTATION_X: Int = 308
            val TYPE_ROTATION_Y: Int = 309
            val TYPE_ROTATION_Z: Int = 310
            val TYPE_SCALE_X: Int = 311
            val TYPE_SCALE_Y: Int = 312
            val TYPE_PIVOT_X: Int = 313
            val TYPE_PIVOT_Y: Int = 314
            val TYPE_PROGRESS: Int = 315
            val TYPE_PATH_ROTATE: Int = 316
            val TYPE_EASING: Int = 317
            val TYPE_PIVOT_TARGET: Int = 318
            val S_CURVE_FIT: String = "curveFit"
            val S_VISIBILITY: String = "visibility"
            val S_ALPHA: String = "alpha"
            val S_TRANSLATION_X: String = "translationX"
            val S_TRANSLATION_Y: String = "translationY"
            val S_TRANSLATION_Z: String = "translationZ"
            val S_ELEVATION: String = "elevation"
            val S_ROTATION_X: String = "rotationX"
            val S_ROTATION_Y: String = "rotationY"
            val S_ROTATION_Z: String = "rotationZ"
            val S_SCALE_X: String = "scaleX"
            val S_SCALE_Y: String = "scaleY"
            val S_PIVOT_X: String = "pivotX"
            val S_PIVOT_Y: String = "pivotY"
            val S_PROGRESS: String = "progress"
            val S_PATH_ROTATE: String = "pathRotate"
            val S_EASING: String = "easing"
            val S_CUSTOM: String = "CUSTOM"
            val S_FRAME: String = "frame"
            val S_TARGET: String = "target"
            val S_PIVOT_TARGET: String = "pivotTarget"
            val KEY_WORDS: Array<String> = arrayOf(
                S_CURVE_FIT,
                S_VISIBILITY,
                S_ALPHA,
                S_TRANSLATION_X,
                S_TRANSLATION_Y,
                S_TRANSLATION_Z,
                S_ELEVATION,
                S_ROTATION_X,
                S_ROTATION_Y,
                S_ROTATION_Z,
                S_SCALE_X,
                S_SCALE_Y,
                S_PIVOT_X,
                S_PIVOT_Y,
                S_PROGRESS,
                S_PATH_ROTATE,
                S_EASING,
                S_CUSTOM,
                S_FRAME,
                S_TARGET,
                S_PIVOT_TARGET
            )

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            @kotlin.jvm.JvmStatic
            fun getId(name: String?): Int {
                when (name) {
                    S_CURVE_FIT -> return TYPE_CURVE_FIT
                    S_VISIBILITY -> return TYPE_VISIBILITY
                    S_ALPHA -> return TYPE_ALPHA
                    S_TRANSLATION_X -> return TYPE_TRANSLATION_X
                    S_TRANSLATION_Y -> return TYPE_TRANSLATION_Y
                    S_TRANSLATION_Z -> return TYPE_TRANSLATION_Z
                    S_ELEVATION -> return TYPE_ELEVATION
                    S_ROTATION_X -> return TYPE_ROTATION_X
                    S_ROTATION_Y -> return TYPE_ROTATION_Y
                    S_ROTATION_Z -> return TYPE_ROTATION_Z
                    S_SCALE_X -> return TYPE_SCALE_X
                    S_SCALE_Y -> return TYPE_SCALE_Y
                    S_PIVOT_X -> return TYPE_PIVOT_X
                    S_PIVOT_Y -> return TYPE_PIVOT_Y
                    S_PROGRESS -> return TYPE_PROGRESS
                    S_PATH_ROTATE -> return TYPE_PATH_ROTATE
                    S_EASING -> return TYPE_EASING
                    S_FRAME -> return TYPE_FRAME_POSITION
                    S_TARGET -> return TYPE_TARGET
                    S_PIVOT_TARGET -> return TYPE_PIVOT_TARGET
                }
                return -1
            }

            fun getType(name: Int): Int {
                when (name) {
                    TYPE_CURVE_FIT, TYPE_VISIBILITY, TYPE_FRAME_POSITION -> return INT_MASK
                    TYPE_ALPHA, TYPE_TRANSLATION_X, TYPE_TRANSLATION_Y, TYPE_TRANSLATION_Z, TYPE_ELEVATION, TYPE_ROTATION_X, TYPE_ROTATION_Y, TYPE_ROTATION_Z, TYPE_SCALE_X, TYPE_SCALE_Y, TYPE_PIVOT_X, TYPE_PIVOT_Y, TYPE_PROGRESS, TYPE_PATH_ROTATE -> return FLOAT_MASK
                    TYPE_EASING, TYPE_TARGET, TYPE_PIVOT_TARGET -> return STRING_MASK
                }
                return -1
            }
        }
    }

    open interface CycleType {
        companion object {
            val NAME: String = "KeyCycle"
            val TYPE_CURVE_FIT: Int = 401
            val TYPE_VISIBILITY: Int = 402
            val TYPE_ALPHA: Int = 403
            val TYPE_TRANSLATION_X: Int = AttributesType.TYPE_TRANSLATION_X
            val TYPE_TRANSLATION_Y: Int = AttributesType.TYPE_TRANSLATION_Y
            val TYPE_TRANSLATION_Z: Int = AttributesType.TYPE_TRANSLATION_Z
            val TYPE_ELEVATION: Int = AttributesType.TYPE_ELEVATION
            val TYPE_ROTATION_X: Int = AttributesType.TYPE_ROTATION_X
            val TYPE_ROTATION_Y: Int = AttributesType.TYPE_ROTATION_Y
            val TYPE_ROTATION_Z: Int = AttributesType.TYPE_ROTATION_Z
            val TYPE_SCALE_X: Int = AttributesType.TYPE_SCALE_X
            val TYPE_SCALE_Y: Int = AttributesType.TYPE_SCALE_Y
            val TYPE_PIVOT_X: Int = AttributesType.TYPE_PIVOT_X
            val TYPE_PIVOT_Y: Int = AttributesType.TYPE_PIVOT_Y
            val TYPE_PROGRESS: Int = AttributesType.TYPE_PROGRESS
            val TYPE_PATH_ROTATE: Int = 416
            val TYPE_EASING: Int = 420
            val TYPE_WAVE_SHAPE: Int = 421
            val TYPE_CUSTOM_WAVE_SHAPE: Int = 422
            val TYPE_WAVE_PERIOD: Int = 423
            val TYPE_WAVE_OFFSET: Int = 424
            val TYPE_WAVE_PHASE: Int = 425
            val S_CURVE_FIT: String = "curveFit"
            val S_VISIBILITY: String = "visibility"
            val S_ALPHA: String = AttributesType.S_ALPHA
            val S_TRANSLATION_X: String = AttributesType.S_TRANSLATION_X
            val S_TRANSLATION_Y: String = AttributesType.S_TRANSLATION_Y
            val S_TRANSLATION_Z: String = AttributesType.S_TRANSLATION_Z
            val S_ELEVATION: String = AttributesType.S_ELEVATION
            val S_ROTATION_X: String = AttributesType.S_ROTATION_X
            val S_ROTATION_Y: String = AttributesType.S_ROTATION_Y
            val S_ROTATION_Z: String = AttributesType.S_ROTATION_Z
            val S_SCALE_X: String = AttributesType.S_SCALE_X
            val S_SCALE_Y: String = AttributesType.S_SCALE_Y
            val S_PIVOT_X: String = AttributesType.S_PIVOT_X
            val S_PIVOT_Y: String = AttributesType.S_PIVOT_Y
            val S_PROGRESS: String = AttributesType.S_PROGRESS
            val S_PATH_ROTATE: String = "pathRotate"
            val S_EASING: String = "easing"
            val S_WAVE_SHAPE: String = "waveShape"
            val S_CUSTOM_WAVE_SHAPE: String = "customWave"
            val S_WAVE_PERIOD: String = "period"
            val S_WAVE_OFFSET: String = "offset"
            val S_WAVE_PHASE: String = "phase"
            val KEY_WORDS: Array<String> = arrayOf(
                S_CURVE_FIT,
                S_VISIBILITY,
                S_ALPHA,
                S_TRANSLATION_X,
                S_TRANSLATION_Y,
                S_TRANSLATION_Z,
                S_ELEVATION,
                S_ROTATION_X,
                S_ROTATION_Y,
                S_ROTATION_Z,
                S_SCALE_X,
                S_SCALE_Y,
                S_PIVOT_X,
                S_PIVOT_Y,
                S_PROGRESS,
                S_PATH_ROTATE,
                S_EASING,
                S_WAVE_SHAPE,
                S_CUSTOM_WAVE_SHAPE,
                S_WAVE_PERIOD,
                S_WAVE_OFFSET,
                S_WAVE_PHASE
            )

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    S_CURVE_FIT -> return TYPE_CURVE_FIT
                    S_VISIBILITY -> return TYPE_VISIBILITY
                    S_ALPHA -> return TYPE_ALPHA
                    S_TRANSLATION_X -> return TYPE_TRANSLATION_X
                    S_TRANSLATION_Y -> return TYPE_TRANSLATION_Y
                    S_TRANSLATION_Z -> return TYPE_TRANSLATION_Z
                    S_ROTATION_X -> return TYPE_ROTATION_X
                    S_ROTATION_Y -> return TYPE_ROTATION_Y
                    S_ROTATION_Z -> return TYPE_ROTATION_Z
                    S_SCALE_X -> return TYPE_SCALE_X
                    S_SCALE_Y -> return TYPE_SCALE_Y
                    S_PIVOT_X -> return TYPE_PIVOT_X
                    S_PIVOT_Y -> return TYPE_PIVOT_Y
                    S_PROGRESS -> return TYPE_PROGRESS
                    S_PATH_ROTATE -> return TYPE_PATH_ROTATE
                    S_EASING -> return TYPE_EASING
                }
                return -1
            }

            fun getType(name: Int): Int {
                when (name) {
                    TYPE_CURVE_FIT, TYPE_VISIBILITY, TYPE_FRAME_POSITION -> return INT_MASK
                    TYPE_ALPHA, TYPE_TRANSLATION_X, TYPE_TRANSLATION_Y, TYPE_TRANSLATION_Z, TYPE_ELEVATION, TYPE_ROTATION_X, TYPE_ROTATION_Y, TYPE_ROTATION_Z, TYPE_SCALE_X, TYPE_SCALE_Y, TYPE_PIVOT_X, TYPE_PIVOT_Y, TYPE_PROGRESS, TYPE_PATH_ROTATE, TYPE_WAVE_PERIOD, TYPE_WAVE_OFFSET, TYPE_WAVE_PHASE -> return FLOAT_MASK
                    TYPE_EASING, TYPE_TARGET, TYPE_WAVE_SHAPE -> return STRING_MASK
                }
                return -1
            }
        }
    }

    open interface TriggerType {
        companion object {
            val NAME: String = "KeyTrigger"
            val VIEW_TRANSITION_ON_CROSS: String = "viewTransitionOnCross"
            val VIEW_TRANSITION_ON_POSITIVE_CROSS: String = "viewTransitionOnPositiveCross"
            val VIEW_TRANSITION_ON_NEGATIVE_CROSS: String = "viewTransitionOnNegativeCross"
            val POST_LAYOUT: String = "postLayout"
            val TRIGGER_SLACK: String = "triggerSlack"
            val TRIGGER_COLLISION_VIEW: String = "triggerCollisionView"
            val TRIGGER_COLLISION_ID: String = "triggerCollisionId"
            val TRIGGER_ID: String = "triggerID"
            val POSITIVE_CROSS: String = "positiveCross"
            val NEGATIVE_CROSS: String = "negativeCross"
            val TRIGGER_RECEIVER: String = "triggerReceiver"
            val CROSS: String = "CROSS"
            val KEY_WORDS: Array<String> = arrayOf(
                VIEW_TRANSITION_ON_CROSS,
                VIEW_TRANSITION_ON_POSITIVE_CROSS,
                VIEW_TRANSITION_ON_NEGATIVE_CROSS,
                POST_LAYOUT,
                TRIGGER_SLACK,
                TRIGGER_COLLISION_VIEW,
                TRIGGER_COLLISION_ID,
                TRIGGER_ID,
                POSITIVE_CROSS,
                NEGATIVE_CROSS,
                TRIGGER_RECEIVER,
                CROSS
            )
            val TYPE_VIEW_TRANSITION_ON_CROSS: Int = 301
            val TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS: Int = 302
            val TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS: Int = 303
            val TYPE_POST_LAYOUT: Int = 304
            val TYPE_TRIGGER_SLACK: Int = 305
            val TYPE_TRIGGER_COLLISION_VIEW: Int = 306
            val TYPE_TRIGGER_COLLISION_ID: Int = 307
            val TYPE_TRIGGER_ID: Int = 308
            val TYPE_POSITIVE_CROSS: Int = 309
            val TYPE_NEGATIVE_CROSS: Int = 310
            val TYPE_TRIGGER_RECEIVER: Int = 311
            val TYPE_CROSS: Int = 312

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    VIEW_TRANSITION_ON_CROSS -> return TYPE_VIEW_TRANSITION_ON_CROSS
                    VIEW_TRANSITION_ON_POSITIVE_CROSS -> return TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS
                    VIEW_TRANSITION_ON_NEGATIVE_CROSS -> return TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS
                    POST_LAYOUT -> return TYPE_POST_LAYOUT
                    TRIGGER_SLACK -> return TYPE_TRIGGER_SLACK
                    TRIGGER_COLLISION_VIEW -> return TYPE_TRIGGER_COLLISION_VIEW
                    TRIGGER_COLLISION_ID -> return TYPE_TRIGGER_COLLISION_ID
                    TRIGGER_ID -> return TYPE_TRIGGER_ID
                    POSITIVE_CROSS -> return TYPE_POSITIVE_CROSS
                    NEGATIVE_CROSS -> return TYPE_NEGATIVE_CROSS
                    TRIGGER_RECEIVER -> return TYPE_TRIGGER_RECEIVER
                    CROSS -> return TYPE_CROSS
                }
                return -1
            }
        }
    }

    open interface PositionType {
        companion object {
            val NAME: String = "KeyPosition"
            val S_TRANSITION_EASING: String = "transitionEasing"
            val S_DRAWPATH: String = "drawPath"
            val S_PERCENT_WIDTH: String = "percentWidth"
            val S_PERCENT_HEIGHT: String = "percentHeight"
            val S_SIZE_PERCENT: String = "sizePercent"
            val S_PERCENT_X: String = "percentX"
            val S_PERCENT_Y: String = "percentY"
            val TYPE_TRANSITION_EASING: Int = 501
            val TYPE_DRAWPATH: Int = 502
            val TYPE_PERCENT_WIDTH: Int = 503
            val TYPE_PERCENT_HEIGHT: Int = 504
            val TYPE_SIZE_PERCENT: Int = 505
            val TYPE_PERCENT_X: Int = 506
            val TYPE_PERCENT_Y: Int = 507
            val TYPE_CURVE_FIT: Int = 508
            val TYPE_PATH_MOTION_ARC: Int = 509
            val TYPE_POSITION_TYPE: Int = 510
            val KEY_WORDS: Array<String> = arrayOf(
                S_TRANSITION_EASING,
                S_DRAWPATH,
                S_PERCENT_WIDTH,
                S_PERCENT_HEIGHT,
                S_SIZE_PERCENT,
                S_PERCENT_X,
                S_PERCENT_Y
            )

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    S_TRANSITION_EASING -> return TYPE_TRANSITION_EASING
                    S_DRAWPATH -> return TYPE_DRAWPATH
                    S_PERCENT_WIDTH -> return TYPE_PERCENT_WIDTH
                    S_PERCENT_HEIGHT -> return TYPE_PERCENT_HEIGHT
                    S_SIZE_PERCENT -> return TYPE_SIZE_PERCENT
                    S_PERCENT_X -> return TYPE_PERCENT_X
                    S_PERCENT_Y -> return TYPE_PERCENT_Y
                }
                return -1
            }

            fun getType(name: Int): Int {
                when (name) {
                    TYPE_CURVE_FIT, TYPE_FRAME_POSITION -> return INT_MASK
                    TYPE_PERCENT_WIDTH, TYPE_PERCENT_HEIGHT, TYPE_SIZE_PERCENT, TYPE_PERCENT_X, TYPE_PERCENT_Y -> return FLOAT_MASK
                    TYPE_TRANSITION_EASING, TYPE_TARGET, TYPE_DRAWPATH -> return STRING_MASK
                }
                return -1
            }
        }
    }

    open interface MotionType {
        companion object {
            val NAME: String = "Motion"
            val S_STAGGER: String = "Stagger"
            val S_PATH_ROTATE: String = "PathRotate"
            val S_QUANTIZE_MOTION_PHASE: String = "QuantizeMotionPhase"
            val S_EASING: String = "TransitionEasing"
            val S_QUANTIZE_INTERPOLATOR: String = "QuantizeInterpolator"
            val S_ANIMATE_RELATIVE_TO: String = "AnimateRelativeTo"
            val S_ANIMATE_CIRCLEANGLE_TO: String = "AnimateCircleAngleTo"
            val S_PATHMOTION_ARC: String = "PathMotionArc"
            val S_DRAW_PATH: String = "DrawPath"
            val S_POLAR_RELATIVETO: String = "PolarRelativeTo"
            val S_QUANTIZE_MOTIONSTEPS: String = "QuantizeMotionSteps"
            val S_QUANTIZE_INTERPOLATOR_TYPE: String = "QuantizeInterpolatorType"
            val S_QUANTIZE_INTERPOLATOR_ID: String = "QuantizeInterpolatorID"
            val KEY_WORDS: Array<String> = arrayOf(
                S_STAGGER,
                S_PATH_ROTATE,
                S_QUANTIZE_MOTION_PHASE,
                S_EASING,
                S_QUANTIZE_INTERPOLATOR,
                S_ANIMATE_RELATIVE_TO,
                S_ANIMATE_CIRCLEANGLE_TO,
                S_PATHMOTION_ARC,
                S_DRAW_PATH,
                S_POLAR_RELATIVETO,
                S_QUANTIZE_MOTIONSTEPS,
                S_QUANTIZE_INTERPOLATOR_TYPE,
                S_QUANTIZE_INTERPOLATOR_ID
            )
            val TYPE_STAGGER: Int = 600
            val TYPE_PATH_ROTATE: Int = 601
            val TYPE_QUANTIZE_MOTION_PHASE: Int = 602
            val TYPE_EASING: Int = 603
            val TYPE_QUANTIZE_INTERPOLATOR: Int = 604
            val TYPE_ANIMATE_RELATIVE_TO: Int = 605
            val TYPE_ANIMATE_CIRCLEANGLE_TO: Int = 606
            val TYPE_PATHMOTION_ARC: Int = 607
            val TYPE_DRAW_PATH: Int = 608
            val TYPE_POLAR_RELATIVETO: Int = 609
            val TYPE_QUANTIZE_MOTIONSTEPS: Int = 610
            val TYPE_QUANTIZE_INTERPOLATOR_TYPE: Int = 611
            val TYPE_QUANTIZE_INTERPOLATOR_ID: Int = 612

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            @kotlin.jvm.JvmStatic
            fun getId(name: String?): Int {
                when (name) {
                    S_STAGGER -> return TYPE_STAGGER
                    S_PATH_ROTATE -> return TYPE_PATH_ROTATE
                    S_QUANTIZE_MOTION_PHASE -> return TYPE_QUANTIZE_MOTION_PHASE
                    S_EASING -> return TYPE_EASING
                    S_QUANTIZE_INTERPOLATOR -> return TYPE_QUANTIZE_INTERPOLATOR
                    S_ANIMATE_RELATIVE_TO -> return TYPE_ANIMATE_RELATIVE_TO
                    S_ANIMATE_CIRCLEANGLE_TO -> return TYPE_ANIMATE_CIRCLEANGLE_TO
                    S_PATHMOTION_ARC -> return TYPE_PATHMOTION_ARC
                    S_DRAW_PATH -> return TYPE_DRAW_PATH
                    S_POLAR_RELATIVETO -> return TYPE_POLAR_RELATIVETO
                    S_QUANTIZE_MOTIONSTEPS -> return TYPE_QUANTIZE_MOTIONSTEPS
                    S_QUANTIZE_INTERPOLATOR_TYPE -> return TYPE_QUANTIZE_INTERPOLATOR_TYPE
                    S_QUANTIZE_INTERPOLATOR_ID -> return TYPE_QUANTIZE_INTERPOLATOR_ID
                }
                return -1
            }
        }
    }

    open interface Custom {
        companion object {
            val NAME: String = "Custom"
            val S_INT: String = "integer"
            val S_FLOAT: String = "float"
            val S_COLOR: String = "color"
            val S_STRING: String = "string"
            val S_BOOLEAN: String = "boolean"
            val S_DIMENSION: String = "dimension"
            val S_REFERENCE: String = "refrence"
            val KEY_WORDS: Array<String> = arrayOf(
                S_FLOAT,
                S_COLOR,
                S_STRING,
                S_BOOLEAN,
                S_DIMENSION,
                S_REFERENCE
            )
            val TYPE_INT: Int = 900
            val TYPE_FLOAT: Int = 901
            val TYPE_COLOR: Int = 902
            val TYPE_STRING: Int = 903
            val TYPE_BOOLEAN: Int = 904
            val TYPE_DIMENSION: Int = 905
            val TYPE_REFERENCE: Int = 906

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    S_INT -> return TYPE_INT
                    S_FLOAT -> return TYPE_FLOAT
                    S_COLOR -> return TYPE_COLOR
                    S_STRING -> return TYPE_STRING
                    S_BOOLEAN -> return TYPE_BOOLEAN
                    S_DIMENSION -> return TYPE_DIMENSION
                    S_REFERENCE -> return TYPE_REFERENCE
                }
                return -1
            }
        }
    }

    open interface MotionScene {
        companion object {
            val NAME: String = "MotionScene"
            val S_DEFAULT_DURATION: String = "defaultDuration"
            val S_LAYOUT_DURING_TRANSITION: String = "layoutDuringTransition"
            val TYPE_DEFAULT_DURATION: Int = 600
            val TYPE_LAYOUT_DURING_TRANSITION: Int = 601
            val KEY_WORDS: Array<String> = arrayOf(
                S_DEFAULT_DURATION,
                S_LAYOUT_DURING_TRANSITION
            )

            fun getType(name: Int): Int {
                when (name) {
                    TYPE_DEFAULT_DURATION -> return INT_MASK
                    TYPE_LAYOUT_DURING_TRANSITION -> return BOOLEAN_MASK
                }
                return -1
            }

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    S_DEFAULT_DURATION -> return TYPE_DEFAULT_DURATION
                    S_LAYOUT_DURING_TRANSITION -> return TYPE_LAYOUT_DURING_TRANSITION
                }
                return -1
            }
        }
    }

    open interface TransitionType {
        companion object {
            val NAME: String = "Transitions"
            val S_DURATION: String = "duration"
            val S_FROM: String = "from"
            val S_TO: String = "to"
            val S_PATH_MOTION_ARC: String = "pathMotionArc"
            val S_AUTO_TRANSITION: String = "autoTransition"
            val S_INTERPOLATOR: String = "motionInterpolator"
            val S_STAGGERED: String = "staggered"
            val S_TRANSITION_FLAGS: String = "transitionFlags"
            val TYPE_DURATION: Int = 700
            val TYPE_FROM: Int = 701
            val TYPE_TO: Int = 702
            val TYPE_PATH_MOTION_ARC: Int = PositionType.TYPE_PATH_MOTION_ARC
            val TYPE_AUTO_TRANSITION: Int = 704
            val TYPE_INTERPOLATOR: Int = 705
            val TYPE_STAGGERED: Int = 706
            val TYPE_TRANSITION_FLAGS: Int = 707
            val KEY_WORDS: Array<String> = arrayOf(
                S_DURATION,
                S_FROM,
                S_TO,
                S_PATH_MOTION_ARC,
                S_AUTO_TRANSITION,
                S_INTERPOLATOR,
                S_STAGGERED,
                S_FROM,
                S_TRANSITION_FLAGS
            )

            fun getType(name: Int): Int {
                when (name) {
                    TYPE_DURATION, TYPE_PATH_MOTION_ARC -> return INT_MASK
                    TYPE_FROM, TYPE_TO, TYPE_INTERPOLATOR, TYPE_TRANSITION_FLAGS -> return STRING_MASK
                    TYPE_STAGGERED -> return FLOAT_MASK
                }
                return -1
            }

            /**
             * Method to go from String names of values to id of the values
             * IDs are use for efficiency
             *
             * @param name the name of the value
             * @return the id of the vlalue or -1 if no value exist
             */
            fun getId(name: String?): Int {
                when (name) {
                    S_DURATION -> return TYPE_DURATION
                    S_FROM -> return TYPE_FROM
                    S_TO -> return TYPE_TO
                    S_PATH_MOTION_ARC -> return TYPE_PATH_MOTION_ARC
                    S_AUTO_TRANSITION -> return TYPE_AUTO_TRANSITION
                    S_INTERPOLATOR -> return TYPE_INTERPOLATOR
                    S_STAGGERED -> return TYPE_STAGGERED
                    S_TRANSITION_FLAGS -> return TYPE_TRANSITION_FLAGS
                }
                return -1
            }
        }
    }

    open interface OnSwipe {
        companion object {
            val DRAG_SCALE: String = "dragscale"
            val DRAG_THRESHOLD: String = "dragthreshold"
            val MAX_VELOCITY: String = "maxvelocity"
            val MAX_ACCELERATION: String = "maxacceleration"
            val SPRING_MASS: String = "springmass"
            val SPRING_STIFFNESS: String = "springstiffness"
            val SPRING_DAMPING: String = "springdamping"
            val SPRINGS_TOP_THRESHOLD: String = "springstopthreshold"
            val DRAG_DIRECTION: String = "dragdirection"
            val TOUCH_ANCHOR_ID: String = "touchanchorid"
            val TOUCH_ANCHOR_SIDE: String = "touchanchorside"
            val ROTATION_CENTER_ID: String = "rotationcenterid"
            val TOUCH_REGION_ID: String = "touchregionid"
            val LIMIT_BOUNDS_TO: String = "limitboundsto"
            val MOVE_WHEN_SCROLLAT_TOP: String = "movewhenscrollattop"
            val ON_TOUCH_UP: String = "ontouchup"
            val ON_TOUCH_UP_ENUM: Array<String> = arrayOf(
                "autoComplete",
                "autoCompleteToStart",
                "autoCompleteToEnd",
                "stop",
                "decelerate",
                "decelerateAndComplete",
                "neverCompleteToStart",
                "neverCompleteToEnd"
            )
            val SPRING_BOUNDARY: String = "springboundary"
            val SPRING_BOUNDARY_ENUM: Array<String> = arrayOf(
                "overshoot",
                "bounceStart",
                "bounceEnd",
                "bounceBoth"
            )
            val AUTOCOMPLETE_MODE: String = "autocompletemode"
            val AUTOCOMPLETE_MODE_ENUM: Array<String> = arrayOf(
                "continuousVelocity",
                "spring"
            )
            val NESTED_SCROLL_FLAGS: String = "nestedscrollflags"
            val NESTED_SCROLL_FLAGS_ENUM: Array<String> = arrayOf(
                "none",
                "disablePostScroll",
                "disableScroll",
                "supportScrollUp"
            )
        }
    }

    companion object {
        val S_CUSTOM: String = "CUSTOM"
        val BOOLEAN_MASK: Int = 1
        val INT_MASK: Int = 2
        val FLOAT_MASK: Int = 4
        val STRING_MASK: Int = 8
        val TYPE_FRAME_POSITION: Int = 100
        val TYPE_TARGET: Int = 101
    }
}