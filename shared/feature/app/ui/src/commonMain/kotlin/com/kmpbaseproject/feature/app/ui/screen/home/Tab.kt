package com.kmpbaseproject.feature.app.ui.screen.home

import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.cities_screen_title
import com.kmpbaseproject.resources.ic_list_24
import com.kmpbaseproject.resources.ic_map_24
import com.kmpbaseproject.resources.map_screen_title
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
