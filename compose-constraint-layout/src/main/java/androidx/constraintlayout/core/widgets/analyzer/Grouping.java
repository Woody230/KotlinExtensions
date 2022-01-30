/*
 * Copyright (C) 2020 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;

import java.util.ArrayList;

/**
 * Implements a simple grouping mechanism, to group interdependent widgets together.
 *
 * TODO: we should move towards a more leaner implementation -- this is more expensive as it could be.
 */
public class Grouping {

    private static final boolean DEBUG = false;
    private static final boolean DEBUG_GROUPING = false;


    public static boolean validInGroup(ConstraintWidget.DimensionBehaviour layoutHorizontal, ConstraintWidget.DimensionBehaviour layoutVertical,
                                       ConstraintWidget.DimensionBehaviour widgetHorizontal, ConstraintWidget.DimensionBehaviour widgetVertical) {
        boolean fixedHorizontal = widgetHorizontal == ConstraintWidget.DimensionBehaviour.FIXED || widgetHorizontal == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
                || (widgetHorizontal == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && layoutHorizontal != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        boolean fixedVertical = widgetVertical == ConstraintWidget.DimensionBehaviour.FIXED || widgetVertical == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
                || (widgetVertical == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && layoutVertical != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        if (fixedHorizontal || fixedVertical) {
            return true;
        }
        return false;
    }

    public static boolean simpleSolvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {

        if (DEBUG) {
            System.out.println("*** GROUP SOLVING ***");
        }
        ArrayList<ConstraintWidget> children = layout.getChildren();

        final int count = children.size();

        ArrayList<Guideline> verticalGuidelines = null;
        ArrayList<Guideline> horizontalGuidelines = null;
        ArrayList<HelperWidget> horizontalBarriers = null;
        ArrayList<HelperWidget> verticalBarriers = null;
        ArrayList<ConstraintWidget> isolatedHorizontalChildren = null;
        ArrayList<ConstraintWidget> isolatedVerticalChildren = null;

        for (int i = 0; i < count; i++) {
            ConstraintWidget child = children.get(i);
            if (!validInGroup(layout.getHorizontalDimensionBehaviour(), layout.getVerticalDimensionBehaviour(),
                    child.getHorizontalDimensionBehaviour(), child.getVerticalDimensionBehaviour())) {
                if (DEBUG) {
                    System.out.println("*** NO GROUP SOLVING ***");
                }
                return false;
            }
            if (child instanceof Flow) {
                return false;
            }
        }
        if (layout.mMetrics != null) {
            layout.mMetrics.grouping++;
        }
        for (int i = 0; i < count; i++) {
            ConstraintWidget child = children.get(i);
            if (!validInGroup(layout.getHorizontalDimensionBehaviour(), layout.getVerticalDimensionBehaviour(),
                    child.getHorizontalDimensionBehaviour(), child.getVerticalDimensionBehaviour())) {
                ConstraintWidgetContainer.measure(0, child, measurer, layout.mMeasure, BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            if (child instanceof Guideline) {
                Guideline guideline = (Guideline) child;
                if (guideline.getOrientation() == ConstraintWidget.HORIZONTAL) {
                    if (horizontalGuidelines == null) {
                        horizontalGuidelines = new ArrayList<>();
                    }
                    horizontalGuidelines.add(guideline);
                }
                if (guideline.getOrientation() == ConstraintWidget.VERTICAL) {
                    if (verticalGuidelines == null) {
                        verticalGuidelines = new ArrayList<>();
                    }
                    verticalGuidelines.add(guideline);
                }
            }
            if (child instanceof HelperWidget) {
                if (child instanceof Barrier) {
                    Barrier barrier = (Barrier) child;
                    if (barrier.getOrientation() == ConstraintWidget.HORIZONTAL) {
                        if (horizontalBarriers == null) {
                            horizontalBarriers = new ArrayList<>();
                        }
                        horizontalBarriers.add(barrier);
                    }
                    if (barrier.getOrientation() == ConstraintWidget.VERTICAL) {
                        if (verticalBarriers == null) {
                            verticalBarriers = new ArrayList<>();
                        }
                        verticalBarriers.add(barrier);
                    }
                } else {
                    HelperWidget helper = (HelperWidget) child;
                    if (horizontalBarriers == null) {
                        horizontalBarriers = new ArrayList<>();
                    }
                    horizontalBarriers.add(helper);
                    if (verticalBarriers == null) {
                        verticalBarriers = new ArrayList<>();
                    }
                    verticalBarriers.add(helper);
                }
            }
            if (child.mLeft.mTarget == null && child.mRight.mTarget == null
                    && !(child instanceof Guideline) && !(child instanceof Barrier)) {
                if (isolatedHorizontalChildren == null) {
                    isolatedHorizontalChildren = new ArrayList<>();
                }
                isolatedHorizontalChildren.add(child);
            }
            if (child.mTop.mTarget == null && child.mBottom.mTarget == null
                    && child.mBaseline.mTarget == null
                    && !(child instanceof Guideline) && !(child instanceof Barrier)) {
                if (isolatedVerticalChildren == null) {
                    isolatedVerticalChildren = new ArrayList<>();
                }
                isolatedVerticalChildren.add(child);
            }
        }
        ArrayList<WidgetGroup> allDependencyLists = new ArrayList<>();

        if (true || layout.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ArrayList<WidgetGroup> dependencyLists = allDependencyLists; //horizontalDependencyLists; //new ArrayList<>();

            if (verticalGuidelines != null) {
                for (Guideline guideline : verticalGuidelines) {
                    findDependents(guideline, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                }
            }
            if (horizontalBarriers != null) {
                for (HelperWidget barrier : horizontalBarriers) {
                    WidgetGroup group = findDependents(barrier, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                    barrier.addDependents(dependencyLists, ConstraintWidget.HORIZONTAL, group);
                    group.cleanup(dependencyLists);
                }
            }

            ConstraintAnchor left = layout.getAnchor(ConstraintAnchor.Type.LEFT);
            if (left.getDependents() != null) {
                for (ConstraintAnchor first : left.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                }
            }

            ConstraintAnchor right = layout.getAnchor(ConstraintAnchor.Type.RIGHT);
            if (right.getDependents() != null) {
                for (ConstraintAnchor first : right.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                }
            }

            ConstraintAnchor center = layout.getAnchor(ConstraintAnchor.Type.CENTER);
            if (center.getDependents() != null) {
                for (ConstraintAnchor first : center.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                }
            }

            if (isolatedHorizontalChildren != null) {
                for (ConstraintWidget widget : isolatedHorizontalChildren) {
                    findDependents(widget, ConstraintWidget.HORIZONTAL, dependencyLists, null);
                }
            }
        }

        if (true || layout.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ArrayList<WidgetGroup> dependencyLists = allDependencyLists; //verticalDependencyLists; //new ArrayList<>();

            if (horizontalGuidelines != null) {
                for (Guideline guideline : horizontalGuidelines) {
                    findDependents(guideline, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }
            if (verticalBarriers != null) {
                for (HelperWidget barrier : verticalBarriers) {
                    WidgetGroup group = findDependents(barrier, ConstraintWidget.VERTICAL, dependencyLists, null);
                    barrier.addDependents(dependencyLists, ConstraintWidget.VERTICAL, group);
                    group.cleanup(dependencyLists);
                }
            }

            ConstraintAnchor top = layout.getAnchor(ConstraintAnchor.Type.TOP);
            if (top.getDependents() != null) {
                for (ConstraintAnchor first : top.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }

            ConstraintAnchor baseline = layout.getAnchor(ConstraintAnchor.Type.BASELINE);
            if (baseline.getDependents() != null) {
                for (ConstraintAnchor first : baseline.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }

            ConstraintAnchor bottom = layout.getAnchor(ConstraintAnchor.Type.BOTTOM);
            if (bottom.getDependents() != null) {
                for (ConstraintAnchor first : bottom.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }

            ConstraintAnchor center = layout.getAnchor(ConstraintAnchor.Type.CENTER);
            if (center.getDependents() != null) {
                for (ConstraintAnchor first : center.getDependents()) {
                    findDependents(first.mOwner, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }

            if (isolatedVerticalChildren != null) {
                for (ConstraintWidget widget : isolatedVerticalChildren) {
                    findDependents(widget, ConstraintWidget.VERTICAL, dependencyLists, null);
                }
            }
        }
        // Now we may have to merge horizontal/vertical dependencies
        for (int i = 0; i < count; i++) {
            ConstraintWidget child = children.get(i);
            if (child.oppositeDimensionsTied()) {
                WidgetGroup horizontalGroup = findGroup(allDependencyLists, child.horizontalGroup);
                WidgetGroup verticalGroup = findGroup(allDependencyLists, child.verticalGroup);
                if (horizontalGroup != null && verticalGroup != null) {
                    if (DEBUG_GROUPING) {
                        System.out.println("Merging " + horizontalGroup + " to " + verticalGroup + " for " + child);
                    }
                    horizontalGroup.moveTo(ConstraintWidget.HORIZONTAL, verticalGroup);
                    verticalGroup.setOrientation(ConstraintWidget.BOTH);
                    allDependencyLists.remove(horizontalGroup);
                }
            }
            if (DEBUG_GROUPING) {
                System.out.println("Widget " + child + " => " + child.horizontalGroup + " : " + child.verticalGroup);
            }
        }

        if (allDependencyLists.size() <= 1) {
            return false;
        }

        if (DEBUG) {
            System.out.println("----------------------------------");
            System.out.println("-- Horizontal dependency lists:");
            System.out.println("----------------------------------");
            for (WidgetGroup list : allDependencyLists) {
                if (list.getOrientation() != ConstraintWidget.VERTICAL) {
                    System.out.println("list: " + list);
                }
            }
            System.out.println("----------------------------------");
            System.out.println("-- Vertical dependency lists:");
            System.out.println("----------------------------------");
            for (WidgetGroup list : allDependencyLists) {
                if (list.getOrientation() != ConstraintWidget.HORIZONTAL) {
                    System.out.println("list: " + list);
                }
            }
            System.out.println("----------------------------------");
        }

        WidgetGroup horizontalPick = null;
        WidgetGroup verticalPick = null;

        if (layout.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            int maxWrap = 0;
            WidgetGroup picked = null;
            for (WidgetGroup list : allDependencyLists) {
                if (list.getOrientation() == ConstraintWidget.VERTICAL) {
                    continue;
                }
                list.setAuthoritative(false);
                int wrap = list.measureWrap(layout.getSystem(), ConstraintWidget.HORIZONTAL);
                if (wrap > maxWrap) {
                    picked = list;
                    maxWrap = wrap;
                }
                if (DEBUG) {
                    System.out.println("list: " + list + " => " + wrap);
                }
            }
            if (picked != null) {
                if (DEBUG) {
                    System.out.println("Horizontal MaxWrap : " + maxWrap + " with group " + picked);
                }
                layout.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                layout.setWidth(maxWrap);
                picked.setAuthoritative(true);
                horizontalPick = picked;
            }
        }

        if (layout.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            int maxWrap = 0;
            WidgetGroup picked = null;
            for (WidgetGroup list : allDependencyLists) {
                if (list.getOrientation() == ConstraintWidget.HORIZONTAL) {
                    continue;
                }
                list.setAuthoritative(false);
                int wrap = list.measureWrap(layout.getSystem(), ConstraintWidget.VERTICAL);
                if (wrap > maxWrap) {
                    picked = list;
                    maxWrap = wrap;
                }
                if (DEBUG) {
                    System.out.println("      " + list + " => " + wrap);
                }
            }
            if (picked != null) {
                if (DEBUG) {
                    System.out.println("Vertical MaxWrap : " + maxWrap + " with group " + picked);
                }
                layout.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                layout.setHeight(maxWrap);
                picked.setAuthoritative(true);
                verticalPick = picked;
            }
        }
        return horizontalPick != null || verticalPick != null;
    }

    private static WidgetGroup findGroup(ArrayList<WidgetGroup> horizontalDependencyLists, int groupId) {
        final int count = horizontalDependencyLists.size();
        for (int i = 0; i < count; i++) {
            WidgetGroup group = horizontalDependencyLists.get(i);
            if (groupId == group.id) {
                return group;
            }
        }
        return null;
    }

    public static WidgetGroup findDependents(ConstraintWidget constraintWidget, int orientation, ArrayList<WidgetGroup> list, WidgetGroup group) {
        int groupId = -1;
        if (orientation == ConstraintWidget.HORIZONTAL) {
            groupId = constraintWidget.horizontalGroup;
        } else {
            groupId = constraintWidget.verticalGroup;
        }
        if (DEBUG_GROUPING) {
            System.out.println("--- find " + (orientation == ConstraintWidget.HORIZONTAL ? "Horiz" : "Vert") + " dependents of " + constraintWidget.getDebugName()
                    + " group " + group + " widget group id " + groupId);
        }
        if (groupId != -1 && (group == null || (groupId != group.id))) {
            // already in a group!
            if (DEBUG_GROUPING) {
                System.out.println("widget " + constraintWidget.getDebugName() + " already in group " + groupId + " group: " + group);
            }
            for (int i = 0; i < list.size(); i++) {
                WidgetGroup widgetGroup = list.get(i);
                if (widgetGroup.getId() == groupId) {
                    if (group != null) {
                        if (DEBUG_GROUPING) {
                            System.out.println("Move group " + group + " to " + widgetGroup);
                        }
                        group.moveTo(orientation, widgetGroup);
                        list.remove(group);
                    }
                    group = widgetGroup;
                    break;
                }
            }
        } else if (groupId != -1) {
            return group;
        }
        if (group == null) {
            if (constraintWidget instanceof HelperWidget) {
                HelperWidget helper = (HelperWidget) constraintWidget;
                groupId = helper.findGroupInDependents(orientation);
                if (groupId != -1) {
                    for (int i = 0; i < list.size(); i++) {
                        WidgetGroup widgetGroup = list.get(i);
                        if (widgetGroup.getId() == groupId) {
                            group = widgetGroup;
                            break;
                        }
                    }
                }
            }
            if (group == null) {
                group = new WidgetGroup(orientation);
            }
            if (DEBUG_GROUPING) {
                System.out.println("Create group " + group + " for widget " + constraintWidget.getDebugName());
            }
            list.add(group);
        }
        if (group.add(constraintWidget)) {
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                guideline.getAnchor().findDependents(guideline.getOrientation() == Guideline.HORIZONTAL ? ConstraintWidget.VERTICAL : ConstraintWidget.HORIZONTAL, list, group);
            }
            if (orientation == ConstraintWidget.HORIZONTAL) {
                constraintWidget.horizontalGroup = group.getId();
                if (DEBUG_GROUPING) {
                    System.out.println("Widget " + constraintWidget.getDebugName() + " H group is " + constraintWidget.horizontalGroup);
                }
                constraintWidget.mLeft.findDependents(orientation, list, group);
                constraintWidget.mRight.findDependents(orientation, list, group);
            } else {
                constraintWidget.verticalGroup = group.getId();
                if (DEBUG_GROUPING) {
                    System.out.println("Widget " + constraintWidget.getDebugName() + " V group is " + constraintWidget.verticalGroup);
                }
                constraintWidget.mTop.findDependents(orientation, list, group);
                constraintWidget.mBaseline.findDependents(orientation, list, group);
                constraintWidget.mBottom.findDependents(orientation, list, group);
            }
            constraintWidget.mCenter.findDependents(orientation, list, group);
        }
        return group;
    }
}
