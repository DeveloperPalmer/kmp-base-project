package com.kmpbaseproject.core.routing.flow.decompose

import com.kmpbaseproject.core.ui.mvi.BaseViewModel

interface Node

interface Screen : Node {
  val viewModel: BaseViewModel<*, *>
}

interface Flow : Node {
  val component: FlowNode<*>
}
