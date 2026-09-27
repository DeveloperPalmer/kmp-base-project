package com.kmpbaseproject.core.ui.error

import com.kmpbaseproject.core.domain.entity.ConnectivityError
import com.kmpbaseproject.core.ui.entity.UiError
import com.kmpbaseproject.core.ui.entity.UiMessage
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.common_error_description
import com.kmpbaseproject.resources.common_error_title
import com.kmpbaseproject.resources.error_no_connection_description
import com.kmpbaseproject.resources.error_no_connection_title
import com.kmpbaseproject.resources.img_no_connection_88

typealias ErrorMapper = (Throwable) -> UiError

fun baseErrorMappers(error: Throwable): UiError {
  return connectionErrorMapper(error) ?: defaultErrorMapper(error)
}

fun connectionErrorMapper(error: Throwable): UiError? {
  if (error !is ConnectivityError) {
    return null
  }
  return UiError(
    cause = error,
    message = UiMessage(
      title = Res.string.error_no_connection_title,
      description = Res.string.error_no_connection_description,
      illustration = Res.drawable.img_no_connection_88,
    ),
  )
}

fun defaultErrorMapper(error: Throwable): UiError {
  return UiError(
    cause = error,
    message = UiMessage(
      title = Res.string.common_error_title,
      description = Res.string.common_error_description,
    ),
  )
}
