package com.urent.core.ui.error

import com.urent.core.domain.entity.ConnectivityError
import com.urent.core.ui.entity.UiError
import com.urent.core.ui.entity.UiMessage
import com.urent.resources.Res
import com.urent.resources.error_no_connection_title
import com.urent.resources.error_something_went_wrong_title

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
    message = UiMessage(title = Res.string.error_no_connection_title),
  )
}

fun defaultErrorMapper(error: Throwable): UiError {
  return UiError(
    cause = error,
    message = UiMessage(title = Res.string.error_something_went_wrong_title),
  )
}
