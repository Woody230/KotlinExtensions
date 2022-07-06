import com.bselzer.ktx.compose.ui.layout.merge.IntMerger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IntMergerTests {
    companion object {
        val Merger = IntMerger()

        data class Wrapper(
            val value: Int = Merger.default
        )
    }

    @Test
    fun merge() {
        val wrapper = Wrapper()
        assertTrue { Merger.isDefault(wrapper.value) }
        assertEquals(456, Merger.safeMerge(456, wrapper.value))
        assertEquals(456, Merger.safeMerge(wrapper.value, 456))
    }
}