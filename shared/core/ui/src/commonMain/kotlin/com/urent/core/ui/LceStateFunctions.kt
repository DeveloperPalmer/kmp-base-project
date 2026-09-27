package com.urent.core.ui

import com.urent.core.domain.entity.LceState
import com.urent.core.ui.entity.ContentLoadState
import com.urent.core.ui.error.ErrorMapper
import com.urent.core.ui.error.baseErrorMappers

fun <T : Any> LceState<T>.toUiLceState(errorMapper: ErrorMapper = ::baseErrorMappers): ContentLoadState {
  return when (this) {
    is LceState.Content -> ContentLoadState.Ready
    is LceState.Loading -> ContentLoadState.Loading
    is LceState.Error -> ContentLoadState.Error(error = errorMapper(value))
  }
}
