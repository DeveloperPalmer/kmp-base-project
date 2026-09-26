package com.urent.core.data.network

import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorModel(
  val error: String,
)
