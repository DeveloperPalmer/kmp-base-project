package com.urent.feature.citydetails.domain

import com.urent.feature.citydetails.domain.entity.CityDetails
import kotlinx.coroutines.flow.Flow

interface CityDetailsRepository {
  fun city(id: Long): Flow<CityDetails>
}
