package com.kmpbaseproject.feature.cities.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kmpbaseproject.core.domain.ReactiveModel
import com.kmpbaseproject.core.domain.di.AppFlowScope
import com.kmpbaseproject.core.domain.storage.DownloadsStorage
import com.kmpbaseproject.feature.cities.domain.entity.City
import com.kmpbaseproject.feature.cities.domain.entity.CityDetails
import com.kmpbaseproject.feature.cities.domain.mediator.CitiesRemoteMediator
import com.kmpbaseproject.feature.cities.domain.mediator.PAGE_SIZE
import com.kmpbaseproject.lib.annotation.FlowCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Inject
@SingleIn(AppFlowScope::class)
class CitiesModel(
  @FlowCoroutineScope(AppFlowScope::class)
  coroutineScope: CoroutineScope,
  private val citiesRepository: CitiesRepository,
  private val downloadsStorage: DownloadsStorage,
) : ReactiveModel(coroutineScope) {
  private val stateFlow = MutableStateFlow(State())

  val cities: Flow<PagingData<City>> = stateFlow
    .map { it.citiesSearchQuery }
    // An empty query (first load, cleared search) goes through at once
    .debounce { query -> if (query.isEmpty()) Duration.ZERO else SEARCH_DEBOUNCE }
    .distinctUntilChanged()
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
        pagingSourceFactory = { citiesRepository.citiesPagingSource(query) },
      ).flow
    }
    .cachedIn(scope)

  fun cityDetails(id: Long): Flow<CityDetails> = citiesRepository.cityDetails(id)

  fun search(query: String) {
    stateFlow.update { it.copy(citiesSearchQuery = query) }
  }

  suspend fun export(query: String) {
    val markdown = citiesRepository.cities(query)
      .mapIndexed { index, city -> "${index + 1}. ${city.title}" }
      .joinToString(
        separator = "\n",
        postfix = "\n"
      )
    downloadsStorage.save(
      fileName = FILE_NAME,
      mimeType = MIME_TYPE,
      content = markdown
    )
  }

  private data class State(
    val citiesSearchQuery: String = ""
  )
}

private val SEARCH_DEBOUNCE = 300.milliseconds
private const val FILE_NAME = "cities.md"
private const val MIME_TYPE = "text/markdown"