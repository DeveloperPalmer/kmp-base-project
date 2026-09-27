package com.urent.feature.citydetails.domain

import com.urent.core.domain.ReactiveModel
import com.urent.feature.citydetails.domain.di.CityDetailsScope
import com.urent.feature.citydetails.domain.entity.CityDetails
import com.urent.lib.annotation.FlowCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(CityDetailsScope::class)
class CityDetailsModel(
  @FlowCoroutineScope(CityDetailsScope::class)
  coroutineScope: CoroutineScope,
  private val cityDetailsRepository: CityDetailsRepository,
) : ReactiveModel(coroutineScope) {
  fun city(id: Long): Flow<CityDetails> = cityDetailsRepository.city(id)
}
