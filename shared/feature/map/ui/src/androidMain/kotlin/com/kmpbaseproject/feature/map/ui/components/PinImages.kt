package com.kmpbaseproject.feature.map.ui.components

import android.graphics.Bitmap
import android.graphics.PointF
import android.util.SparseArray
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.kmpbaseproject.uikit.theme.AppTheme
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.roundToInt

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
 *
 * Shadows are blurred once per style: a bubble is the round [pill] stretched through its middle
 * column, and a pin also gets the [tailLayer] laid over it.
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
  private val clusters = SparseArray<ImageProvider>()
  private val margin = with(density) {
    val shadowReach = style.shadows.maxOf { shadow ->
      (shadow.radius + shadow.spread).toPx() + max(abs(shadow.offset.x.toPx()), abs(shadow.offset.y.toPx()))
    }
    ceil(shadowReach + STROKE_WIDTH.toPx())
  }
  private val pinHeight = with(density) { ceil(PIN_HEIGHT.toPx() + TAIL_HEIGHT.toPx() + 2 * margin).toInt() }
  private val pill by lazy { drawPill() }
  private val tailLayer by lazy { drawTailLayer() }

  /** Anchors a pin at the tip of its tail: every pin has the same height, so one style fits all. */
  val pinIconStyle: IconStyle = with(density) {
    val tipY = margin + PIN_HEIGHT.toPx() + TAIL_HEIGHT.toPx()
    IconStyle().setAnchor(PointF(0.5f, tipY / pinHeight))
  }

  fun pin(title: String): ImageProvider {
    return BubbleImage(id = "map_pin_${styleKey}_$title", text = title, tail = true)
  }

  // MapKit reports every cluster anew on each zoom step, so one image per size is enough
  fun cluster(size: Int): ImageProvider {
    return clusters[size] ?: BubbleImage(
      id = "map_cluster_${styleKey}_$size",
      text = size.toString(),
      tail = false
    ).also { image -> clusters.put(size, image) }
  }

  // MapKit may ask for images off the main thread, and the text measurer is shared
  @Synchronized
  private fun drawBubble(text: String, tail: Boolean): ImageBitmap = with(density) {
    val layout = textMeasurer.measure(text = text, style = style.text, maxLines = 1, softWrap = false)
    val height = PIN_HEIGHT.toPx()
    // An even stretch moves the middle by whole pixels, so the tail layer stays sharp
    val stretch = 2 * ((layout.size.width + 2 * PADDING.toPx() - height) / 2).roundToInt().coerceAtLeast(0)
    val middle = floor(margin + height / 2).toInt()
    renderBitmap(pill.width + stretch, if (tail) pinHeight else pill.height) {
      drawImage(
        image = pill,
        srcSize = IntSize(middle, pill.height),
      )
      drawImage(
        image = pill,
        srcOffset = IntOffset(middle, 0),
        srcSize = IntSize(1, pill.height),
        dstOffset = IntOffset(middle, 0),
        dstSize = IntSize(stretch, pill.height),
        filterQuality = FilterQuality.None,
      )
      drawImage(
        image = pill,
        srcOffset = IntOffset(middle, 0),
        srcSize = IntSize(pill.width - middle, pill.height),
        dstOffset = IntOffset(middle + stretch, 0),
      )
      if (tail) {
        drawImage(
          image = tailLayer,
          topLeft = Offset(stretch / 2f, 0f),
        )
      }
      drawText(
        textLayoutResult = layout,
        color = style.foreground,
        topLeft = Offset(
          x = margin + (height + stretch - layout.size.width) / 2,
          y = margin + (height - layout.size.height) / 2,
        ),
      )
    }
  }

  private fun drawPill(): ImageBitmap = with(density) {
    val height = PIN_HEIGHT.toPx()
    val body = Size(height, height)
    val outline = Path().apply { addOval(Rect(Offset.Zero, body)) }
    val side = ceil(height + 2 * margin).toInt()
    renderBitmap(side, side) {
      translate(margin, margin) {
        drawShadows(outline, body)
        drawPath(outline, style.background)
        drawPath(outline, style.stroke, style = Stroke(STROKE_WIDTH.toPx()))
      }
    }
  }

  /**
   * The tail where it hangs under the round pill. The layer covers the pill, so it keeps only the
   * part of the tail's shadow below the pill's stroke, and the tail's fill hides that stroke where
   * the two join.
   */
  private fun drawTailLayer(): ImageBitmap = with(density) {
    val strokeWidth = STROKE_WIDTH.toPx()
    val tailWidth = TAIL_WIDTH.toPx()
    val tailHeight = TAIL_HEIGHT.toPx()
    val center = margin + PIN_HEIGHT.toPx() / 2
    val edge = margin + PIN_HEIGHT.toPx()
    // The tail starts a bit inside the pill, so its fill leaves no seam between them
    val tail = triangle(Rect(center - tailWidth / 2, edge - strokeWidth, center + tailWidth / 2, edge + tailHeight))
    val below = Size(tailWidth * tailHeight / (tailHeight + strokeWidth), tailHeight)
    renderBitmap(pill.width, pinHeight) {
      clipRect(top = edge + strokeWidth / 2) {
        translate(center - below.width / 2, edge) {
          drawShadows(triangle(Rect(Offset.Zero, below)), below)
        }
      }
      drawPath(tail, style.background)
      clipRect(top = edge - strokeWidth / 2) {
        drawPath(tail, style.stroke, style = Stroke(strokeWidth))
      }
    }
  }

  private fun DrawScope.drawShadows(outline: Path, size: Size) {
    val shape = GenericShape { _, _ -> addPath(outline) }
    style.shadows.forEach { shadow ->
      with(DropShadowPainter(shape, shadow)) { draw(size) }
    }
  }

  private fun renderBitmap(width: Int, height: Int, block: DrawScope.() -> Unit): ImageBitmap {
    val bitmap = ImageBitmap(width, height)
    val size = Size(width.toFloat(), height.toFloat())
    CanvasDrawScope().draw(density, LayoutDirection.Ltr, Canvas(bitmap), size, block)
    return bitmap
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

/** A triangle pointing down from the top side of [bounds] to the middle of its bottom side. */
private fun triangle(bounds: Rect): Path {
  return Path().apply {
    moveTo(bounds.left, bounds.top)
    lineTo(bounds.right, bounds.top)
    lineTo(bounds.center.x, bounds.bottom)
    close()
  }
}

private val PIN_HEIGHT = 30.dp
private val PADDING = 8.dp
private val TAIL_WIDTH = 10.dp
private val TAIL_HEIGHT = 5.dp
private val STROKE_WIDTH = 1.dp
