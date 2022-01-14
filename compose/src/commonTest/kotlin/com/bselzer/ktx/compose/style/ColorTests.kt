package com.bselzer.ktx.compose.style

import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.style.Hex
import com.bselzer.ktx.compose.ui.style.color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTests {
    @Test
    fun hexNoAlpha() {
        // Arrange
        val hex = "3dab5a"

        // Act
        val color = Hex(hex).color()

        // Assert
        color.assertArgb(alpha = 255, red = 61, green = 171, blue = 90)
    }

    @Test
    fun hexWithAlpha() {
        // Arrange
        val hex = "053dab5a"

        // Act
        val color = Hex(hex).color()

        // Assert
        color.assertArgb(alpha = 5, red = 61, green = 171, blue = 90)
    }

    /**
     * Asserts that the color matches the expected [alpha], [red], [green], and [blue] 0-255 scaled values.
     */
    private fun Color.assertArgb(alpha: Int, red: Int, green: Int, blue: Int) {
        assertEquals(alpha.zeroToOneScaled(), this.alpha)
        assertEquals(red.zeroToOneScaled(), this.red)
        assertEquals(blue.zeroToOneScaled(), this.blue)
        assertEquals(green.zeroToOneScaled(), this.green)
    }

    /**
     * Scale the 0-255 RGB into a 0-1 scaled value.
     */
    private fun Int.zeroToOneScaled(): Float = (this / 255.0).toFloat()
}