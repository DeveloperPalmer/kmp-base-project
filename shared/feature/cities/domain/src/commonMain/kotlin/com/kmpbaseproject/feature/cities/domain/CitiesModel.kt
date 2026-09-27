package com.kmpbaseproject.feature.cities.domain

import com.kmpbaseproject.core.domain.ReactiveModel
import com.kmpbaseproject.feature.cities.domain.di.CitiesScope
import com.kmpbaseproject.lib.annotation.FlowCoroutineScope
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(CitiesScope::class)
class CitiesModel(
  @FlowCoroutineScope(CitiesScope::class)
  coroutineScope: CoroutineScope,
  citiesRepository: CitiesRepository,
) : ReactiveModel(coroutineScope) {
  val fetchCities = task(name = "fetchCities") { query: String, page: Int, limit: Int ->
    citiesRepository.fetchCities(
      query = query,
      page = page,
      limit = limit
    )
  }
}
