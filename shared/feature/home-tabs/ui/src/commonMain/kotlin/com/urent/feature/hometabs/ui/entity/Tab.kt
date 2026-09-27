package com.urent.feature.hometabs.ui.entity

import com.urent.resources.Res
import com.urent.resources.cities_screen_title
import com.urent.resources.ic_list_24
import com.urent.resources.ic_map_24
import com.urent.resources.map_screen_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Tab(
  val icon: DrawableResource,
  val title: StringResource
) {
  Cities(
    icon = Res.drawable.ic_list_24,
    title = Res.string.cities_screen_title
  ),
  Map(
    icon = Res.drawable.ic_map_24,
    title = Res.string.map_screen_title
  ),
}
