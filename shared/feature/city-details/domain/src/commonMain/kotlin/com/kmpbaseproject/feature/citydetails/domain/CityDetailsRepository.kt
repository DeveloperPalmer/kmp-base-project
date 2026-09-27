package com.kmpbaseproject.feature.citydetails.domain

import com.kmpbaseproject.feature.citydetails.domain.entity.CityDetails
import kotlinx.coroutines.flow.Flow

interface CityDetailsRepository {
  fun city(id: Long): Flow<CityDetails>
}
