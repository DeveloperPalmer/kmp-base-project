package com.urent.feature.map.ui.function

import com.urent.feature.map.ui.entity.MapPin

internal data class PinsDiff(
  val added: List<MapPin>,
  val removedIds: Set<Long>,
) {
  val isEmpty: Boolean
    get() = added.isEmpty() && removedIds.isEmpty()
}

/** What the map has to change to show [pins] instead of [shownIds]; pins already shown stay as they are. */
internal fun pinsDiff(shownIds: Set<Long>, pins: List<MapPin>): PinsDiff {
  val pinIds = pins.mapTo(HashSet()) { pin -> pin.id }
  return PinsDiff(
    added = pins.filterNot { pin -> pin.id in shownIds },
    removedIds = shownIds - pinIds,
  )
}
