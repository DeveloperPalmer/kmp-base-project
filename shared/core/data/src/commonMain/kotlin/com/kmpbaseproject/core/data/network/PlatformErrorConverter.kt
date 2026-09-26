package com.kmpbaseproject.core.data.network

import com.kmpbaseproject.core.domain.entity.ConnectivityError

fun interface PlatformErrorConverter {
  fun convert(error: Throwable): ConnectivityError?
}
