package com.kmpbaseproject.core.ui

import androidx.paging.LoadState
import com.kmpbaseproject.core.ui.entity.ContentLoadState
import com.kmpbaseproject.core.ui.error.ErrorMapper
import com.kmpbaseproject.core.ui.error.baseErrorMappers

fun LoadState.toUiLceState(errorMapper: ErrorMapper = ::baseErrorMappers): ContentLoadState {
  return when (this) {
    is LoadState.Loading -> ContentLoadState.Loading
    is LoadState.NotLoading -> ContentLoadState.Ready
    is LoadState.Error -> ContentLoadState.Error(error = errorMapper(error))
  }
}
