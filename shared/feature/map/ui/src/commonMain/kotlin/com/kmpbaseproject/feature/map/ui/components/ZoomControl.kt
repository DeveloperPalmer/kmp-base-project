package com.kmpbaseproject.feature.map.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.resources.Res
import com.kmpbaseproject.resources.ic_minus_24
import com.kmpbaseproject.resources.ic_plus_24
import com.kmpbaseproject.resources.map_zoom_in
import com.kmpbaseproject.resources.map_zoom_out
import com.kmpbaseproject.uikit.theme.AppTheme
import com.kmpbaseproject.uikit.theme.dropShadow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ZoomControl(
  visible: Boolean,
  onZoomInClick: () -> Unit,
  onZoomOutClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  AnimatedVisibility(
    modifier = modifier,
    visible = visible,
    enter = EnterTransition.None,
    exit = ExitTransition.None,
  ) {
    val alpha by transition.animateFloat(label = "alpha") { state ->
      if (state == EnterExitState.Visible) 1f else 0f
    }
    Column(
      modifier = Modifier
        .width(CONTROLS_WIDTH)
        // fadeIn/fadeOut draw offscreen within the control bounds and clip the shadow until alpha reaches 1.
        .graphicsLayer {
          this.alpha = alpha
          compositingStrategy = CompositingStrategy.ModulateAlpha
        }
        .dropShadow(
          shadows = AppTheme.shadows.label,
          shape = AppTheme.shapes.semiMedium
        )
        .background(
          shape = AppTheme.shapes.semiMedium,
          color = AppTheme.colors.background.primary
        ),
    ) {
      ZoomButton(
        icon = Res.drawable.ic_plus_24,
        contentDescription = stringResource(Res.string.map_zoom_in),
        onClick = onZoomInClick,
      )
      HorizontalDivider(
        thickness = DIVIDER_THICKNESS,
        color = AppTheme.colors.divider.primary,
      )
      ZoomButton(
        icon = Res.drawable.ic_minus_24,
        contentDescription = stringResource(Res.string.map_zoom_out),
        onClick = onZoomOutClick,
      )
    }
  }
}

@Composable
private fun ZoomButton(
  icon: DrawableResource,
  contentDescription: String,
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(BUTTON_HEIGHT)
      .clickable(
        role = Role.Button,
        indication = null,
        interactionSource = null,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = contentDescription,
      tint = AppTheme.colors.icon.primary,
    )
  }
}

private val CONTROLS_WIDTH = 52.dp
private val BUTTON_HEIGHT = 47.dp
private val DIVIDER_THICKNESS = 2.dp
