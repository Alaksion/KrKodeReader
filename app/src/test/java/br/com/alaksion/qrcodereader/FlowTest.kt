package br.com.alaksion.qrcodereader

import app.cash.turbine.test
import br.com.alaksion.qrcodereader.success.DummyClass
import com.google.common.truth.Truth
import io.mockk.coEvery
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Test

class StateFlowTest {

    @Test
    fun `Test a flow using turbine`() = runTest {
        val flow = MutableStateFlow("")
        /**
         * Initialize Test scope
         * */
        flow.test {
            flow.update { "1" }
            flow.update { "2" }
            flow.update { "3" }

            // Expect an emission
            val item = awaitItem()

            Truth.assertThat(item).isEqualTo("")
            // Cancel the test scope and don't consume remaining events
            cancelAndIgnoreRemainingEvents()
        }

        // Test continues after the flow test scope is canceled
        Truth.assertThat(false).isFalse()
    }

    @Test
    fun `Test a flow with no events`() = runTest {
        val flow = flow<Int> {
            print("i don't have any events")
        }
        /**
         * Initialize Test scope
         * */
        flow.test {
            /**
             * To test a flow that has no emission cancelling the scope is not needed
             * because the flow completion is considered a separate event.
             *
             * await complete throws an exception if the origin flow throws an exception or
             * a event is emitted instead of the flow completion.
             * */
            awaitComplete()
        }
        // Test continues after the flow test scope is canceled
        Truth.assertThat(false).isFalse()
    }

    @Test
    fun `Test only one value from a set of emissions`() = runTest {
        val flow = flow<Int> {
            emit(1)
            emit(2)
            emit(3)
        }

        flow.test {
            /**
             * Ignore the first two emissions
             * */
            skipItems(2)
            /**
             * Capture desired emission
             * */
            val value = awaitItem()

            Truth.assertThat(value).isEqualTo(3)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test stateflows updating to the same value`() = runTest {
        /**
         * StateFlows are distinct until changed by default, so emitting the same value multiple times won't update
         * */
        val flow = MutableStateFlow(1)
        flow.update { 1 }
        flow.update { 1 }

        flow.test {
            val event = awaitItem()
            awaitItem()

            Truth.assertThat(event).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test stateflows updating to the same value with data classes`() = runTest {
        /**
         * StateFlows are distinct until changed by default, so emitting the same value multiple times won't update.
         * Updating a data class with the same parameter won't work as well, because the default implementation of
         * isEqual will return true, so the flow won't update.
         * */
        val flow = MutableStateFlow(DummyClass("HELLO", 1))
        flow.update { DummyClass("HELLO", 1) }
        flow.update { DummyClass("HELLO", 1) }

        flow.test {
            val event = awaitItem()
            awaitItem()

            Truth.assertThat(event).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test flows and expect more emissions than the flow emits`() = runTest {
        val flow = flow {
            emit(1)
        }

        flow.test {
            /**
             * Flow only emits one value, expect more than 1 value will make the test scope suspend
             * for 60 seconds until the test fails with an exception.
             * This is the most common error i've faced so far, if a test takes more than 2-3 seconds
             * to run you can safely assume the emissions you are waiting for were not launched.
             * */
            awaitItem()
            awaitItem()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test exception throwing flows`() = runTest {
        val flow = flow {
            emit(1)
            throw NullPointerException()
        }

        flow.test {
            /**
             * Exceptions can be captured inside the test scope. You can also verify that n values were
             * emitted before the exception launched.
             * You can't expect more items after the exception since the flow will be canceled when the
             * exception is thrown.
             * */
            awaitItem()
            val exception = awaitError()

            Truth.assertThat(exception).isInstanceOf(java.lang.NullPointerException::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test Shared flows with same emissions`() = runTest {
        val flow = MutableSharedFlow<Int>()

        flow.test {
            flow.emit(1)
            flow.emit(1)

            awaitItem()
            awaitItem()

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Test awaitEvent or awaitItem`() = runTest {
        val flow = MutableStateFlow("")

        flow.test {
            flow.update { "1" }
            flow.update { "2" }
            flow.update { "3" }

            /**
             * awaitEvent captures the next emission as an event. Events can be represented by 3 subclasses of Event:
             * Error, Item, Completion.
             *
             * Error stands for an exception that terminated the flow
             * Item stands for a regular emission
             * Completion stands for the finalization of the flow.
             *
             * */
            val item = awaitEvent()
            val item2 = awaitEvent()

            /**
             * Await item captures the next emission as direct value of T.
             * If the next emission is not Event.Item throws an exception.
             * */
            val item3 = awaitItem()

            print("$item $item2 $item3")
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `Test cancelAndIgnore or cancelAndConsume`() = runTest {
        val flow = MutableStateFlow("")

        flow.test {
            flow.update { "1" }
            flow.update { "2" }
            flow.update { "3" }

            awaitItem()
            awaitItem()
            cancelAndIgnoreRemainingEvents()

            /**
             * CancelAndConsumeRemainingEvents will cancel the test scope, consume the remaining events
             * and return a list containing the events consumed.
             * */
//            Truth.assertThat(cancelAndConsumeRemainingEvents().size).isEqualTo(4)

            /**
             * CancelAndIgnoreRemainingEvents will cancel the test scope and ignore remaining events
             * they will not be consumed.
             * */
        }
    }
}