package com.kmpbaseproject.feature.map.ui.components

import android.graphics.Bitmap
import android.graphics.PointF
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.shadow.DropShadowPainter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.uikit.theme.AppTheme
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max

@Composable
internal fun rememberPinImages(): PinImages {
  val density = LocalDensity.current
  val fontFamilyResolver = LocalFontFamilyResolver.current
  val style = PinStyle(
    background = AppTheme.colors.pins.primaryBackground,
    foreground = AppTheme.colors.pins.foreground,
    stroke = AppTheme.colors.pins.stroke,
    text = AppTheme.typography.label,
    shadows = AppTheme.shadows.pin,
  )
  return remember(density, fontFamilyResolver, style) {
    PinImages(density, fontFamilyResolver, style)
  }
}

@Immutable
internal data class PinStyle(
  val background: Color,
  val foreground: Color,
  val stroke: Color,
  val text: TextStyle,
  val shadows: List<Shadow>,
)

/**
 * Icons of city pins and clusters, drawn with the theme tokens.
 *
 * An image is drawn only when MapKit first asks for it and is then cached by MapKit under its id,
 * so a city that stays inside a cluster costs nothing. The id carries the style, so a theme change
 * never reuses an image drawn with the old colors.
 */
@Immutable
internal class PinImages(
  private val density: Density,
  fontFamilyResolver: FontFamily.Resolver,
  private val style: PinStyle,
) {
  private val textMeasurer = TextMeasurer(
    defaultFontFamilyResolver = fontFamilyResolver,
    defaultDensity = density,
    defaultLayoutDirection = LayoutDirection.Ltr,
    cacheSize = 0,
  )
  private val styleKey = style.hashCode()
  private val margin = with(density) {
    val shadowReach = style.shadows.maxOf { shadow ->
      (shadow.radius + shadow.spread).toPx() + max(abs(shadow.offset.x.toPx()), abs(shadow.offset.y.toPx()))
    }
    ceil(shadowReach + STROKE_WIDTH.toPx())
  }

  /** Anchors a pin at the tip of its tail: every pin has the same height, so one style fits all. */
  val pinIconStyle: IconStyle = with(density) {
    val tipY = margin + PIN_HEIGHT.toPx() + TAIL_HEIGHT.toPx()
    IconStyle().setAnchor(PointF(0.5f, tipY / (tipY + margin)))
  }

  fun pin(title: String): ImageProvider {
    return BubbleImage(id = "map_pin_${styleKey}_$title", text = title, tail = true)
  }

  fun cluster(size: Int): ImageProvider {
    return BubbleImage(id = "map_cluster_${styleKey}_$size", text = size.toString(), tail = false)
  }

  // MapKit may ask for images off the main thread, and the text measurer is shared
  @Synchronized
  private fun drawBubble(text: String, tail: Boolean): ImageBitmap = with(density) {
    val layout = textMeasurer.measure(text = text, style = style.text, maxLines = 1, softWrap = false)
    val height = PIN_HEIGHT.toPx()
    val body = Size(max(layout.size.width + 2 * PADDING.toPx(), height), height)
    val tailHeight = if (tail) TAIL_HEIGHT.toPx() else 0f
    val shape = Size(body.width, body.height + tailHeight)
    val outline = bubbleOutline(
      body = body,
      tailWidth = TAIL_WIDTH.toPx(),
      tailHeight = tailHeight,
      overlap = STROKE_WIDTH.toPx(),
    )
    val bitmap = ImageBitmap(ceil(shape.width + 2 * margin).toInt(), ceil(shape.height + 2 * margin).toInt())
    val canvasSize = Size(bitmap.width.toFloat(), bitmap.height.toFloat())
    CanvasDrawScope().draw(this, LayoutDirection.Ltr, Canvas(bitmap), canvasSize) {
      translate(margin, margin) {
        val outlineShape = GenericShape { _, _ -> addPath(outline) }
        style.shadows.forEach { shadow ->
          with(DropShadowPainter(outlineShape, shadow)) { draw(shape) }
        }
        drawPath(outline, style.background)
        drawPath(outline, style.stroke, style = Stroke(STROKE_WIDTH.toPx()))
        drawText(
          textLayoutResult = layout,
          color = style.foreground,
          topLeft = Offset((body.width - layout.size.width) / 2, (body.height - layout.size.height) / 2),
        )
      }
    }
    bitmap
  }

  private inner class BubbleImage(
    private val id: String,
    private val text: String,
    private val tail: Boolean,
  ) : ImageProvider() {
    override fun getId(): String = id

    override fun getImage(): Bitmap = drawBubble(text, tail).asAndroidBitmap()
  }
}

/** A pill, with a tail pointing down from its middle when [tailHeight] is not zero. */
private fun bubbleOutline(body: Size, tailWidth: Float, tailHeight: Float, overlap: Float): Path {
  val pill = Path().apply {
    addRoundRect(RoundRect(0f, 0f, body.width, body.height, CornerRadius(body.height / 2)))
  }
  if (tailHeight == 0f) return pill
  // The tail starts a bit inside the pill, so the union leaves no seam between them
  val tail = Path().apply {
    moveTo((body.width - tailWidth) / 2, body.height - overlap)
    lineTo((body.width + tailWidth) / 2, body.height - overlap)
    lineTo(body.width / 2, body.height + tailHeight)
    close()
  }
  return Path.combine(PathOperation.Union, pill, tail)
}

private val PIN_HEIGHT = 30.dp
private val PADDING = 8.dp
private val TAIL_WIDTH = 10.dp
private val TAIL_HEIGHT = 5.dp
private val STROKE_WIDTH = 1.dp
