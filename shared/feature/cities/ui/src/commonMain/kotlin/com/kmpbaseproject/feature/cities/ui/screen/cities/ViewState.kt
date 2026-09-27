package com.kmpbaseproject.feature.cities.ui.screen.cities

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.kmpbaseproject.feature.cities.domain.entity.City
import kotlinx.coroutines.flow.Flow

@Immutable
data class ViewState(
  val citiesSearchQuery: String,
  val cities: Flow<PagingData<City>>,
)
