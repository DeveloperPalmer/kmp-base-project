package com.kmpbaseproject.uikit.component.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.uikit.theme.AppTheme
import com.kmpbaseproject.uikit.theme.dropShadow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .dropShadow(shape = RectangleShape, shadows = AppTheme.shadows.bottomBar)
      .background(AppTheme.colors.background.primary)
      .navigationBarsPadding(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 8.dp)
        .selectableGroup(),
      verticalAlignment = Alignment.CenterVertically,
      content = content,
    )
  }
}

@Composable
fun RowScope.BottomBarItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: DrawableResource,
  contentDescription: String?,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .weight(1f)
      .fillMaxHeight()
      .selectable(
        role = Role.Tab,
        selected = selected,
        indication = null,
        interactionSource = null,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
        .size(width = 64.dp, height = 32.dp)
        .background(
          color = if (selected) AppTheme.colors.background.brand else Color.Transparent,
          shape = AppTheme.shapes.circle,
        ),
      contentAlignment = Alignment.Center,
    ) {
      Icon(
        painter = painterResource(icon),
        contentDescription = contentDescription,
        tint = if (selected) AppTheme.colors.accent.brand else AppTheme.colors.icon.secondary,
      )
    }
  }
}
