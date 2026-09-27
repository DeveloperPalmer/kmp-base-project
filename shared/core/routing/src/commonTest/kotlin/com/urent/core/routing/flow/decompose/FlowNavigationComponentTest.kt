package com.urent.core.routing.flow.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.urent.core.routing.di.FlowComponent
import com.urent.core.ui.mvi.BaseViewModel
import com.urent.core.ui.routing.Event
import com.urent.core.ui.viewmodel.AssistedViewModelProvider
import com.urent.core.ui.viewmodel.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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
  fun `navigates to config returned by transition`() {
    val flow = TestFlowNavigationComponent()

    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)

    assertEquals(listOf(TestConfig.Root, TestConfig.Details), flow.configs())
  }

  @Test
  fun `passes events of pushed screen to transition`() {
    val flow = TestFlowNavigationComponent()
    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)

    flow.activeViewModel().dispatch(TestFlowEvent.MoreRequested)

    assertEquals(listOf(TestConfig.Root, TestConfig.Details, TestConfig.More), flow.configs())
  }

  @Test
  fun `stops passing events of destroyed screen`() {
    val flow = TestFlowNavigationComponent()
    flow.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)
    val details = flow.activeViewModel()
    flow.back()

    details.dispatch(TestFlowEvent.DetailsRequested)

    assertEquals(listOf(TestConfig.Root), flow.configs())
  }

  @Test
  fun `passes ignored event to parent flow`() {
    val parent = TestParentFlow()

    parent.nested().activeViewModel().dispatch(TestFlowEvent.ParentRequested)

    assertEquals(listOf(TestParentConfig.Nested, TestParentConfig.Other), parent.configs())
  }

  @Test
  fun `keeps handled event in its flow`() {
    val parent = TestParentFlow()
    val nested = parent.nested()

    nested.activeViewModel().dispatch(TestFlowEvent.DetailsRequested)

    assertEquals(listOf(TestConfig.Root, TestConfig.Details), nested.configs())
    assertEquals(listOf(TestParentConfig.Nested), parent.configs())
  }

  @Test
  fun `cancels flow coroutine scope on destroy`() {
    val lifecycle = LifecycleRegistry().apply { resume() }
    val flow = TestFlowNavigationComponent(DefaultComponentContext(lifecycle))

    lifecycle.destroy()

    assertFalse(flow.component.coroutineScope().isActive)
  }
}

private fun resumedContext(): ComponentContext = DefaultComponentContext(LifecycleRegistry().apply { resume() })

private class TestFlowNavigationComponent(
  context: ComponentContext = resumedContext(),
  override val component: FlowComponent = ScopeFlowComponent(),
) : FlowNavigationComponent<TestConfig, TestChild>(context) {
  override fun initialConfig(): List<TestConfig> = listOf(TestConfig.Root)

  override fun transition(event: Event): FlowTransition<TestConfig> {
    return when (event) {
      TestFlowEvent.DetailsRequested -> FlowTransition.NavigateTo(TestConfig.Details)
      TestFlowEvent.MoreRequested -> FlowTransition.NavigateTo(TestConfig.More)
      else -> FlowTransition.Ignore
    }
  }

  override val childFactory: (TestConfig, ComponentContext) -> TestChild = { _, _ -> TestChild(TestViewModel()) }

  fun back() = navigation.pop()

  fun activeViewModel(): TestViewModel = stack.value.active.instance.viewModel

  fun configs(): List<Any> = stack.value.items.map { it.configuration }
}

// Hosts TestFlowNavigationComponent as a nested flow
private class TestParentFlow : FlowNavigationComponent<TestParentConfig, TestParentChild>(resumedContext()) {
  override val component: FlowComponent = ScopeFlowComponent()

  override fun initialConfig(): List<TestParentConfig> = listOf(TestParentConfig.Nested)

  override fun transition(event: Event): FlowTransition<TestParentConfig> {
    return when (event) {
      TestFlowEvent.ParentRequested -> FlowTransition.NavigateTo(TestParentConfig.Other)
      else -> FlowTransition.Ignore
    }
  }

  override val childFactory: (TestParentConfig, ComponentContext) -> TestParentChild = { config, componentContext ->
    when (config) {
      TestParentConfig.Nested -> TestParentChild.Nested(TestFlowNavigationComponent(componentContext))
      TestParentConfig.Other -> TestParentChild.Other
    }
  }

  fun nested(): TestFlowNavigationComponent {
    return (
      stack.value.items
        .first()
        .instance as TestParentChild.Nested
    ).component
  }

  fun configs(): List<Any> = stack.value.items.map { it.configuration }
}

private class ScopeFlowComponent : FlowComponent {
  private val scope = CoroutineScope(Job())

  override fun viewModelProviders(): Set<ViewModelProvider> = emptySet()

  override fun assistedViewModelProviders(): Set<AssistedViewModelProvider> = emptySet()

  override fun flowViewModelProviders(): Set<ViewModelProvider> = emptySet()

  override fun assistedFlowViewModelProviders(): Set<AssistedViewModelProvider> = emptySet()

  override fun coroutineScope(): CoroutineScope = scope
}

private sealed interface TestConfig {
  data object Root : TestConfig
  data object Details : TestConfig
  data object More : TestConfig
}

private class TestChild(override val viewModel: TestViewModel) : Screen

private sealed interface TestParentConfig {
  data object Nested : TestParentConfig
  data object Other : TestParentConfig
}

private sealed interface TestParentChild : Node {
  class Nested(override val component: TestFlowNavigationComponent) : TestParentChild, Flow
  data object Other : TestParentChild
}

private sealed interface TestFlowEvent : Event {
  data object DetailsRequested : TestFlowEvent
  data object MoreRequested : TestFlowEvent
  data object ParentRequested : TestFlowEvent
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
