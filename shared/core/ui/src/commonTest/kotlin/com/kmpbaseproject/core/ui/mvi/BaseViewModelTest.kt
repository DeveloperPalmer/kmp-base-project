package com.kmpbaseproject.core.ui.mvi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
)

private sealed interface TestIntent {
  data class Query(val text: String) : TestIntent
  data class Input(val text: String) : TestIntent, BlockingViewIntent
}

private class TestViewModel(dispatcher: CoroutineDispatcher) : BaseViewModel<TestState, TestIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<TestState, Nothing>(
    initialState = TestState(),
    buildSettings = {
      eventLoopDispatcher = { dispatcher }
      intentLaunchingDispatcher = { dispatcher }
    },
  )

  override suspend fun Syntax<TestState, Nothing>.handle(viewIntent: TestIntent) {
    when (viewIntent) {
      is TestIntent.Query -> reduce { state.copy(handled = state.handled + viewIntent.text) }
      is TestIntent.Input -> reduce { state.copy(handled = state.handled + viewIntent.text) }
    }
  }
}
