package com.kmpbaseproject.core.ui

import com.kmpbaseproject.core.domain.entity.LceState
import com.kmpbaseproject.core.ui.entity.ContentLoadState
import com.kmpbaseproject.core.ui.error.ErrorMapper
import com.kmpbaseproject.core.ui.error.baseErrorMappers

fun <T : Any> LceState<T>.toUiLceState(errorMapper: ErrorMapper = ::baseErrorMappers): ContentLoadState {
  return when (this) {
    is LceState.Content -> ContentLoadState.Ready
    is LceState.Loading -> ContentLoadState.Loading
    is LceState.Error -> ContentLoadState.Error(error = errorMapper(value))
  }
}
