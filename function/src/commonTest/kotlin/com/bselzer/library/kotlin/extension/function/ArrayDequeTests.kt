package com.bselzer.library.kotlin.extension.function

import com.bselzer.library.kotlin.extension.function.collection.removeFirst
import kotlin.test.*

class ArrayDequeTests
{
    private companion object
    {
        val DEQUE_LIST = listOf(1, 5, 9, 1, 2, 0, -1, 2, 4, 9)
    }

    @Test
    fun removeFirst_WithOverCapacity()
    {
        // Arrange
        val deque = ArrayDeque<Int>().apply { addAll(DEQUE_LIST) }

        // Act
        val output = deque.removeFirst(5)

        // Assert
        assertEquals(5, output.size)
        assertContentEquals(listOf(1, 5, 9, 1, 2), output)
    }

    @Test
    fun removeFirst_WithExactCapacity()
    {
        // Arrange
        val deque = ArrayDeque<Int>().apply { addAll(DEQUE_LIST) }

        // Act
        val output = deque.removeFirst(10)

        // Assert
        assertEquals(10, output.size)
        assertContentEquals(listOf(1, 5, 9, 1, 2, 0, -1, 2, 4, 9), output)
    }

    @Test
    fun removeFirst_WithUnderCapacity()
    {
        // Arrange
        val deque = ArrayDeque<Int>().apply { addAll(DEQUE_LIST) }

        // Act
        val exception = assertFails { deque.removeFirst(11) }

        // Assert
        assertIs<NoSuchElementException>(exception)
    }
}