/*
 * Copyright (C) 2018 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets

import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour

/**
 * Class to represent a chain by its main elements.
 */
class ChainHead(var first: ConstraintWidget, private val mOrientation: Int, isRtl: Boolean) {
    var firstVisibleWidget: ConstraintWidget? = null
        protected set
    var last: ConstraintWidget? = null
        protected set
    var lastVisibleWidget: ConstraintWidget? = null
        protected set
    var head: ConstraintWidget? = null
        protected set
    var firstMatchConstraintWidget: ConstraintWidget? = null
        protected set
    var lastMatchConstraintWidget: ConstraintWidget? = null
        protected set
    var mWeightedMatchConstraintsWidgets: ArrayList<ConstraintWidget>? = null
    var mWidgetsCount = 0
    var mWidgetsMatchCount = 0
    var totalWeight = 0f
        protected set
    var mVisibleWidgets = 0
    var mTotalSize = 0
    var mTotalMargins = 0
    var mOptimizable = false
    private var mIsRtl = false
    var mHasUndefinedWeights = false
    protected var mHasDefinedWeights = false
    var mHasComplexMatchWeights = false
    protected var mHasRatio = false
    private var mDefined = false
    private fun defineChainProperties() {
        val offset = mOrientation * 2
        var lastVisited = first
        mOptimizable = true

        // TraverseChain
        var widget = first
        var next: ConstraintWidget? = first
        var done = false
        while (!done) {
            mWidgetsCount++
            widget.mNextChainWidget[mOrientation] = null
            widget.mListNextMatchConstraintsWidget[mOrientation] = null
            if (widget.visibility != ConstraintWidget.GONE) {
                mVisibleWidgets++
                if (widget.getDimensionBehaviour(mOrientation) != DimensionBehaviour.MATCH_CONSTRAINT) {
                    mTotalSize += widget.getLength(mOrientation)
                }
                mTotalSize += widget.mListAnchors[offset]?.margin ?: 0
                mTotalSize += widget.mListAnchors[offset + 1]?.margin ?: 0
                mTotalMargins += widget.mListAnchors[offset]?.margin ?: 0
                mTotalMargins += widget.mListAnchors[offset + 1]?.margin ?: 0
                // Visible widgets linked list.
                if (firstVisibleWidget == null) {
                    firstVisibleWidget = widget
                }
                lastVisibleWidget = widget

                // Match constraint linked list.
                if (widget.mListDimensionBehaviors[mOrientation] == DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (widget.mResolvedMatchConstraintDefault[mOrientation] == ConstraintWidget.MATCH_CONSTRAINT_SPREAD || widget.mResolvedMatchConstraintDefault[mOrientation] == ConstraintWidget.MATCH_CONSTRAINT_RATIO || widget.mResolvedMatchConstraintDefault[mOrientation] == ConstraintWidget.MATCH_CONSTRAINT_PERCENT) {
                        mWidgetsMatchCount++ // Note: Might cause an issue if we support MATCH_CONSTRAINT_RATIO_RESOLVED in chain optimization. (we currently don't)
                        val weight = widget.mWeight[mOrientation]
                        if (weight > 0) {
                            totalWeight += widget.mWeight[mOrientation]
                        }
                        if (isMatchConstraintEqualityCandidate(widget, mOrientation)) {
                            if (weight < 0) {
                                mHasUndefinedWeights = true
                            } else {
                                mHasDefinedWeights = true
                            }
                            if (mWeightedMatchConstraintsWidgets == null) {
                                mWeightedMatchConstraintsWidgets = ArrayList()
                            }
                            mWeightedMatchConstraintsWidgets!!.add(widget)
                        }
                        if (firstMatchConstraintWidget == null) {
                            firstMatchConstraintWidget = widget
                        }
                        if (lastMatchConstraintWidget != null) {
                            lastMatchConstraintWidget!!.mListNextMatchConstraintsWidget[mOrientation] = widget
                        }
                        lastMatchConstraintWidget = widget
                    }
                    if (mOrientation == ConstraintWidget.HORIZONTAL) {
                        if (widget.mMatchConstraintDefaultWidth != ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                            mOptimizable = false
                        } else if (widget.mMatchConstraintMinWidth != 0 || widget.mMatchConstraintMaxWidth != 0) {
                            mOptimizable = false
                        }
                    } else {
                        if (widget.mMatchConstraintDefaultHeight != ConstraintWidget.MATCH_CONSTRAINT_SPREAD) {
                            mOptimizable = false
                        } else if (widget.mMatchConstraintMinHeight != 0 || widget.mMatchConstraintMaxHeight != 0) {
                            mOptimizable = false
                        }
                    }
                    if (widget.dimensionRatio != 0.0f) {
                        //TODO: Improve (Could use ratio optimization).
                        mOptimizable = false
                        mHasRatio = true
                    }
                }
            }
            if (lastVisited !== widget) {
                lastVisited.mNextChainWidget[mOrientation] = widget
            }
            lastVisited = widget

            // go to the next widget
            val nextAnchor = widget.mListAnchors[offset + 1]?.target
            if (nextAnchor != null) {
                next = nextAnchor.owner
                if (next.mListAnchors[offset]?.target == null
                    || next.mListAnchors[offset]?.target?.owner !== widget
                ) {
                    next = null
                }
            } else {
                next = null
            }
            if (next != null) {
                widget = next
            } else {
                done = true
            }
        }
        if (firstVisibleWidget != null) {
            mTotalSize -= firstVisibleWidget!!.mListAnchors[offset]?.margin ?: 0
        }
        if (lastVisibleWidget != null) {
            mTotalSize -= lastVisibleWidget!!.mListAnchors[offset + 1]?.margin ?: 0
        }
        last = widget
        if (mOrientation == ConstraintWidget.HORIZONTAL && mIsRtl) {
            head = last
        } else {
            head = first
        }
        mHasComplexMatchWeights = mHasDefinedWeights && mHasUndefinedWeights
    }

    fun define() {
        if (!mDefined) {
            defineChainProperties()
        }
        mDefined = true
    }

    companion object {
        /**
         * Returns true if the widget should be part of the match equality rules in the chain
         *
         * @param widget      the widget to test
         * @param orientation current orientation, HORIZONTAL or VERTICAL
         * @return
         */
        private fun isMatchConstraintEqualityCandidate(widget: ConstraintWidget, orientation: Int): Boolean {
            return widget.visibility != ConstraintWidget.GONE && widget.mListDimensionBehaviors[orientation] == DimensionBehaviour.MATCH_CONSTRAINT && (widget.mResolvedMatchConstraintDefault[orientation] == ConstraintWidget.MATCH_CONSTRAINT_SPREAD
                    || widget.mResolvedMatchConstraintDefault[orientation] == ConstraintWidget.MATCH_CONSTRAINT_RATIO)
        }
    }

    /**
     * Initialize variables, then determine visible widgets, the head of chain and
     * matched constraint widgets.
     *
     * @param first       first widget in a chain
     * @param orientation orientation of the chain (either Horizontal or Vertical)
     * @param isRtl       Right-to-left layout flag to determine the actual head of the chain
     */
    init {
        mIsRtl = isRtl
    }
}