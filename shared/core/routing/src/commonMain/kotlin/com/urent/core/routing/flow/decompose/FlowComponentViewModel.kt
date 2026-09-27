package com.urent.core.routing.flow.decompose

import com.urent.core.routing.di.FlowComponent
import com.urent.core.ui.mvi.BaseViewModel

inline fun <reified VM : BaseViewModel<*, *>> FlowComponent.viewModel(
  vararg params: Any?,
): VM {
  val key = VM::class.qualifiedName.orEmpty()
  return if (params.isEmpty()) {
    flowViewModelProviders()
      .firstOrNull { it.key == key }
      ?.factory
      ?.invoke()
  } else {
    assistedFlowViewModelProviders()
      .firstOrNull { it.key == key }
      ?.factory
      ?.build(*params)
  }
    as? VM
    ?: error("ViewModel $key is not registered")
}
