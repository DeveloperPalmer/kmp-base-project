package com.urent.feature.cities.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.urent.core.domain.ReactiveModel
import com.urent.feature.cities.domain.di.CitiesScope
import com.urent.feature.cities.domain.entity.City
import com.urent.feature.cities.domain.mediator.CitiesRemoteMediator
import com.urent.feature.cities.domain.mediator.PAGE_SIZE
import com.urent.lib.annotation.FlowCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(CitiesScope::class)
class CitiesModel(
  @FlowCoroutineScope(CitiesScope::class)
  coroutineScope: CoroutineScope,
  citiesRepository: CitiesRepository,
) : ReactiveModel(coroutineScope) {
  private val stateFlow = MutableStateFlow(State())

  val cities: Flow<PagingData<City>> = stateFlow
    .map { it.citiesSearchQuery }
    .flatMapLatest { query ->
      Pager(
        config = PagingConfig(
          pageSize = PAGE_SIZE,
          enablePlaceholders = false
        ),
        remoteMediator = CitiesRemoteMediator(
          query = query,
          citiesRepository = citiesRepository
        ),
        pagingSourceFactory = { citiesRepository.cities(query) },
      ).flow
    }
    .cachedIn(scope)

  fun search(query: String) {
    stateFlow.update { it.copy(citiesSearchQuery = query) }
  }
}

private data class State(
  val citiesSearchQuery: String = ""
)
