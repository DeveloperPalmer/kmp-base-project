package com.kmpbaseproject.feature.map.ui.function

import com.kmpbaseproject.feature.map.domain.entity.GeoPoint
import com.kmpbaseproject.feature.map.ui.entity.MapPin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PinsDiffTest {
  @Test
  fun `adds every pin to an empty map`() {
    val diff = pinsDiff(shownIds = emptySet(), pins = listOf(MOSCOW, KHIMKI))

    assertEquals(listOf(MOSCOW, KHIMKI), diff.added)
    assertEquals(emptySet(), diff.removedIds)
  }

  @Test
  fun `adds only new pins and leaves shown ones as they are`() {
    val diff = pinsDiff(shownIds = setOf(MOSCOW.id), pins = listOf(MOSCOW, KHIMKI))

    assertEquals(listOf(KHIMKI), diff.added)
    assertEquals(emptySet(), diff.removedIds)
  }

  @Test
  fun `removes pins that are gone`() {
    val diff = pinsDiff(shownIds = setOf(MOSCOW.id, KHIMKI.id), pins = listOf(KHIMKI))

    assertEquals(emptyList(), diff.added)
    assertEquals(setOf(MOSCOW.id), diff.removedIds)
  }

  @Test
  fun `changes nothing for the same pins`() {
    val diff = pinsDiff(shownIds = setOf(MOSCOW.id, KHIMKI.id), pins = listOf(MOSCOW, KHIMKI))

    assertTrue(diff.isEmpty)
  }
}

private val MOSCOW = MapPin(id = 1, title = "Moscow", location = GeoPoint(55.75204, 37.61781))
private val KHIMKI = MapPin(id = 2, title = "Khimki", location = GeoPoint(55.89704, 37.42969))
