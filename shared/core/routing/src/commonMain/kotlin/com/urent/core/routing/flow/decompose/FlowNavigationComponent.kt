package com.urent.core.routing.flow.decompose

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

@Stable
abstract class FlowNavigationComponent<Config : Any, Child : Any>(
  protected val context: ComponentContext,
) {
  abstract fun initialConfig(): List<Config>

  protected val nav = StackNavigation<Config>()

  protected abstract val childFactory: (Config, ComponentContext) -> Child

  // There is an initialization conflict between stack and childFactory fields,
  // using lazy as a workaround to super class field initialization order problem
  val stack: Value<ChildStack<*, Child>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    context.childStack(
      source = nav,
      serializer = null,
      initialStack = { initialConfig() },
      childFactory = childFactory,
      handleBackButton = true,
    )
  }
}
