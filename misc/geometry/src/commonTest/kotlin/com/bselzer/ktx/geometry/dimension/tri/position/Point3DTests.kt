package com.bselzer.ktx.geometry.dimension.tri.position

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Point3DTests {
    @Test
    fun isDefault() {
        // Arrange
        val isDefault = Point3D(0.0, 0.0, 0.0)
        val isNotDefault = Point3D(1.0, 2.0, 3.0)

        // Act / Assert
        assertTrue(isDefault.isDefault)
        assertFalse(isNotDefault.isDefault)
    }
}