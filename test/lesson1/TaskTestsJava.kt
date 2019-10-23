package lesson1

import org.junit.jupiter.api.Tag
import kotlin.test.Test


class TaskTestsJava : AbstractTaskTests() {

    @Test
    @Tag("Easy")
    fun testSortTimes() {
        try {
            sortTimes { inputName, outputName -> JavaTasks.sortTimes(inputName, outputName) }
        } catch (e: NotImplementedError) {}
    }

    @Test
    @Tag("Normal")
    fun testSortAddresses() {
        try {
            sortAddresses { inputName, outputName -> JavaTasks.sortAddresses(inputName, outputName) }
        } catch (e: NotImplementedError) {}
    }

    @Test
    @Tag("Normal")
    fun testSortTemperatures() {
        sortTemperatures { inputName, outputName -> JavaTasks.sortTemperatures(inputName, outputName) }
    }

    @Test
    @Tag("Normal")
    fun testSortSequence() {
        sortSequence { inputName, outputName -> JavaTasks.sortSequence(inputName, outputName) }
    }

    @Test
    @Tag("Easy")
    fun testMergeArrays() {
        mergeArrays { first, second -> JavaTasks.mergeArrays(first, second) }
    }
}
