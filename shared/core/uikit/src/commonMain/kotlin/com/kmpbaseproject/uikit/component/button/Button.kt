package com.kmpbaseproject.uikit.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.uikit.theme.AppTheme

@Composable
internal fun ButtonInternal(
  text: String,
  colors: ButtonColors,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  // Явное нажатие нужно только превью: collectIsPressedAsState работает через корутину
  // и в статичный рендер не попадает, кнопка там всегда выглядела бы не нажатой.
  pressed: Boolean = interactionSource.collectIsPressedAsState().value,
) {
  val containerColor = colors.containerColor(
    pressed = pressed,
  )
  Box(
    modifier = modifier
      .heightIn(min = ButtonHeight)
      .background(color = containerColor.value, shape = AppTheme.shapes.regular)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        role = Role.Button,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      modifier = Modifier.padding(horizontal = 24.dp),
      text = text,
      style = AppTheme.typography.title4,
      color = colors.contentColor,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}

// Material3 ButtonColors не умеет цвет нажатия, а в Figma Pressed отличается именно фоном.
@Immutable
internal data class ButtonColors(
  val containerColor: Color,
  val pressedContainerColor: Color,
  val contentColor: Color,
)

internal object ButtonDefaults {
  @Composable
  fun primaryDefaultColors(): ButtonColors {
    return ButtonColors(
      containerColor = AppTheme.colors.button.primaryBackground,
      pressedContainerColor = AppTheme.colors.button.primaryBackgroundPressed,
      contentColor = AppTheme.colors.button.primaryForeground,
    )
  }
}

@Composable
private fun ButtonColors.containerColor(pressed: Boolean): State<Color> {
  return rememberUpdatedState(if (pressed) pressedContainerColor else containerColor)
}

private val ButtonHeight = 56.dp

internal data class ButtonPreviewState(
  val label: String,
  val pressed: Boolean,
)

internal class ButtonPreviewStateProvider : PreviewParameterProvider<ButtonPreviewState> {
  override val values = sequenceOf(
    ButtonPreviewState(
      label = "default",
      pressed = false,
    ),
    ButtonPreviewState(
      label = "pressed",
      pressed = true,
    ),
  )
}
