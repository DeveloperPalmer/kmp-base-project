@file:Suppress("UnusedImport")

package com.kmpbaseproject.feature.citydetails.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import com.kmpbaseproject.core.data.cities.CitiesDatabase
import com.kmpbaseproject.feature.citydetails.domain.CityDetailsRepository
import com.kmpbaseproject.feature.citydetails.domain.entity.CityDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
class CityDetailsDataRepository(
  private val citiesDatabase: CitiesDatabase,
) : CityDetailsRepository {
  override fun city(id: Long): Flow<CityDetails> {
    return citiesDatabase.cityQueries
      .getCity(id = id, mapper = ::CityDetails)
      .asFlow()
      .mapToOneNotNull(Dispatchers.IO)
  }
}
