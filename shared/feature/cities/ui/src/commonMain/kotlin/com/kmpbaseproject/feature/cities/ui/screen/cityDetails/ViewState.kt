package com.kmpbaseproject.feature.cities.ui.screen.cityDetails

import androidx.compose.runtime.Immutable
import com.kmpbaseproject.feature.cities.domain.entity.CityDetails

@Immutable
data class ViewState(
  val city: CityDetails? = null,
)
