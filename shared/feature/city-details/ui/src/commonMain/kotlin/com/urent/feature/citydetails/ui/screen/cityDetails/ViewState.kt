package com.urent.feature.citydetails.ui.screen.cityDetails

import androidx.compose.runtime.Immutable
import com.urent.feature.citydetails.domain.entity.CityDetails

@Immutable
data class ViewState(
  val city: CityDetails? = null,
)
