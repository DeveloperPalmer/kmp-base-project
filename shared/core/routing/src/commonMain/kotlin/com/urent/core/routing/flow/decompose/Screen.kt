package com.urent.core.routing.flow.decompose

import com.urent.core.ui.mvi.BaseViewModel

interface Node

interface Screen : Node {
  val viewModel: BaseViewModel<*, *, *>
}

interface Flow : Node {
  val component: FlowNode<*>
}
