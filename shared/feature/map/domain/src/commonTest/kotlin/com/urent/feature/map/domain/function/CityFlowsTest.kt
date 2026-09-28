package com.urent.feature.map.domain.function

import com.urent.feature.map.domain.entity.GeoPoint
import com.urent.feature.map.domain.entity.MapCity
import com.urent.feature.map.domain.mapper.accumulated
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CityFlowsTest {
  @Test
  fun `keeps the cities of earlier responses`() = runTest {
    val emitted = flowOf(listOf(MOSCOW), listOf(KHIMKI)).accumulated().toList()

    assertEquals(listOf(listOf(MOSCOW), listOf(MOSCOW, KHIMKI)), emitted)
  }

  @Test
  fun `emits nothing for a response without new cities`() = runTest {
    val emitted = flowOf(listOf(MOSCOW, KHIMKI), listOf(KHIMKI), listOf(MOSCOW)).accumulated().toList()

    assertEquals(listOf(listOf(MOSCOW, KHIMKI)), emitted)
  }
}

private val MOSCOW = MapCity(id = 1, name = "Moscow", location = GeoPoint(55.75204, 37.61781))
private val KHIMKI = MapCity(id = 2, name = "Khimki", location = GeoPoint(55.89704, 37.42969))
