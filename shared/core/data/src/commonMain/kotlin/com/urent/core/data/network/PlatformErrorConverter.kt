package com.urent.core.data.network

import com.urent.core.domain.entity.ConnectivityError

fun interface PlatformErrorConverter {
  fun convert(error: Throwable): ConnectivityError?
}
