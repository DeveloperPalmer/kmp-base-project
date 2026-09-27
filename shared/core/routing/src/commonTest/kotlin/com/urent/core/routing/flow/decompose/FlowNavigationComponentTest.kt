package com.urent.core.routing.flow.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.urent.core.ui.mvi.BaseViewModel
import com.urent.core.ui.routing.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FlowNavigationComponentTest {
  @BeforeTest
  fun setUp() {
    Dispatchers.setMain(UnconfinedTestDispatcher())
  }

  @AfterTest
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `passes screen event to transition`() {
    val flow = TestFlowNavigationComponent()

    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)

    assertEquals(listOf(TestConfig.Root, TestConfig.Details), flow.configs())
  }

  @Test
  fun `passes events of pushed screen to transition`() {
    val flow = TestFlowNavigationComponent()
    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)

    flow.activeViewModel().dispatch(TestFlowEvent.BackRequested)

    assertEquals(listOf(TestConfig.Root), flow.configs())
  }

  @Test
  fun `stops passing events of destroyed screen`() {
    val flow = TestFlowNavigationComponent()
    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)
    val details = flow.activeViewModel()
    details.dispatch(TestFlowEvent.BackRequested)

    details.dispatch(TestFlowEvent.DetailsRequested)

    assertEquals(listOf(TestConfig.Root), flow.configs())
  }
}

private class TestFlowNavigationComponent : FlowNavigationComponent<TestConfig, TestChild>(
  context = DefaultComponentContext(LifecycleRegistry().apply { resume() }),
) {
  override fun initialConfig(): List<TestConfig> = listOf(TestConfig.Root)

  override fun transition(event: Event) {
    when (event) {
      TestFlowEvent.DetailsRequested -> nav.pushToFront(TestConfig.Details)
      TestFlowEvent.BackRequested -> nav.pop()
    }
  }

  override val childFactory: (TestConfig, ComponentContext) -> TestChild = { _, _ -> TestChild(TestViewModel()) }

  fun activeViewModel(): TestViewModel = stack.value.active.instance.viewModel

  fun configs(): List<Any> = stack.value.items.map { it.configuration }
}

private sealed interface TestConfig {
  data object Root : TestConfig
  data object Details : TestConfig
}

private class TestChild(override val viewModel: TestViewModel) : Screen

private sealed interface TestFlowEvent : Event {
  data object DetailsRequested : TestFlowEvent
  data object BackRequested : TestFlowEvent
}

// Sends every intent to its flow as is
private class TestViewModel : BaseViewModel<Unit, Event, Nothing>() {
  // Runs intents in place, so an event reaches the flow before dispatch returns
  override val container = viewModelScope.orbitContainer<Unit, Nothing>(
    initialState = Unit,
    buildSettings = { eventLoopDispatcher = { Dispatchers.Unconfined } },
  )

  override suspend fun Syntax<Unit, Nothing>.handle(viewIntent: Event) = sendEvent(viewIntent)
}
