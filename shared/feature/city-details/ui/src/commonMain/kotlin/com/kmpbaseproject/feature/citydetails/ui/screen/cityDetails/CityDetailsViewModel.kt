package com.kmpbaseproject.feature.citydetails.ui.screen.cityDetails

import com.kmpbaseproject.core.ui.mvi.BaseViewModel
import com.kmpbaseproject.feature.citydetails.domain.di.CityDetailsScope
import com.kmpbaseproject.lib.annotation.ViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import org.orbitmvi.orbit.orbitContainer
import org.orbitmvi.orbit.syntax.Syntax

@Inject
@ViewModel(CityDetailsScope::class)
class CityDetailsViewModel(
  @Assisted cityId: Long,
) : BaseViewModel<ViewState, ViewIntent, Nothing>() {
  override val container = viewModelScope.orbitContainer<ViewState, Nothing>(ViewState(cityId))

  override suspend fun Syntax<ViewState, Nothing>.handle(viewIntent: ViewIntent) = Unit
}
