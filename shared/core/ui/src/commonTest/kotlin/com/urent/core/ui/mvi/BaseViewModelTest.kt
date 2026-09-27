package com.urent.core.ui.mvi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseViewModelTest {
  @Test
  fun `handles every view intent`() = runTest {
    val viewModel = testViewModel()

    viewModel.dispatch(TestIntent.Query("a"))
    viewModel.dispatch(TestIntent.Query("ab"))

    assertEquals(listOf("a", "ab"), viewModel.state().handled)
    viewModel.destroy()
  }

  @Test
  fun `passes view intents to stream collected in onCreate`() = runTest {
    val viewModel = testViewModel()

    viewModel.dispatch(TestIntent.Query("a"))
    viewModel.dispatch(TestIntent.Query("ab"))
    advanceUntilIdle()

    assertEquals(listOf("ab"), viewModel.state().debounced)
    viewModel.destroy()
  }

  @Test
  fun `does not lose view intent dispatched right after creation`() = runTest {
    val viewModel = testViewModel()

    viewModel.dispatch(TestIntent.Query("a"))
    advanceUntilIdle()

    assertEquals(listOf("a"), viewModel.state().debounced)
    viewModel.destroy()
  }

  @Test
  fun `applies blocking view intent before dispatch returns`() = runTest {
    val viewModel = TestViewModel(StandardTestDispatcher(testScheduler))

    viewModel.dispatch(TestIntent.Query("a"))
    viewModel.dispatch(TestIntent.Input("b"))

    assertEquals(listOf("b"), viewModel.state().handled)
    viewModel.destroy()
  }
}

private fun TestScope.testViewModel() = TestViewModel(UnconfinedTestDispatcher(testScheduler))

private fun TestViewModel.state(): TestState = container.stateFlow.value

private data class TestState(
  val handled: List<String> = emptyList(),
  val debounced: List<String> = emptyList(),
)

private sealed interface TestIntent {
  data class Query(val text: String) : TestIntent
  data class Input(val text: String) : TestIntent, BlockingViewIntent
}

private class TestViewModel(dispatcher: CoroutineDispatcher) : BaseViewModel<TestState, TestIntent, Nothing>() {
  // Orbit runs on the test dispatcher, so debounce uses virtual time
  override val container = viewModelScope.orbitContainer<TestState, Nothing>(
    initialState = TestState(),
    buildSettings = {
      eventLoopDispatcher = { dispatcher }
      intentLaunchingDispatcher = { dispatcher }
    },
  ) {
    intents<TestIntent.Query>()
      .debounce(DEBOUNCE_MILLIS)
      .collect { intent -> reduce { state.copy(debounced = state.debounced + intent.text) } }
  }

  override suspend fun Syntax<TestState, Nothing>.handle(viewIntent: TestIntent) {
    when (viewIntent) {
      is TestIntent.Query -> reduce { state.copy(handled = state.handled + viewIntent.text) }
      is TestIntent.Input -> reduce { state.copy(handled = state.handled + viewIntent.text) }
    }
  }
}

private const val DEBOUNCE_MILLIS = 500L
