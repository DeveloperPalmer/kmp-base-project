package com.kmpbaseproject.feature.map.ui.entity

import androidx.compose.runtime.Immutable

@Immutable
data class CityInfo(
  val name: String,
  val country: String,
  val population: Long,
)
