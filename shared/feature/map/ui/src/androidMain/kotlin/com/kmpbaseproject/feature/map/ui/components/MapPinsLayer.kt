package com.kmpbaseproject.feature.map.ui.components

import androidx.compose.runtime.Stable
import com.kmpbaseproject.feature.map.ui.entity.MapPin
import com.kmpbaseproject.feature.map.ui.function.pinsDiff
import com.kmpbaseproject.feature.map.ui.mapper.toPoint
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import java.lang.ref.WeakReference

/**
 * City pins on the map, clustered by MapKit.
 *
 * A new set of pins is applied as a difference: pins that stay are not touched, so they neither
 * blink nor lose their place in a cluster. MapKit holds listeners weakly, so the layer listens
 * itself and lives exactly as long as the map shows it.
 */
@Stable
internal class MapPinsLayer(
  private val map: Map,
  private val images: PinImages,
) : ClusterListener, ClusterTapListener {
  private val clusterTapListener = WeakReference<ClusterTapListener>(this)
  private val collection = map.mapObjects.addClusterizedPlacemarkCollection(WeakReference(this))
  private val placemarks = HashMap<Long, PlacemarkMapObject>()

  fun show(pins: List<MapPin>) {
    val diff = pinsDiff(placemarks.keys, pins)
    if (diff.isEmpty) return
    diff.removedIds.forEach { id ->
      placemarks.remove(id)?.let(collection::remove)
    }
    diff.added.forEach { pin ->
      placemarks[pin.id] = collection.addPlacemark(pin.location.toPoint(), images.pin(pin.title), images.pinIconStyle)
    }
    // Placemarks appear only once clustered, and every change of the collection needs clustering again
    collection.clusterPlacemarks(CLUSTER_RADIUS, CLUSTER_MIN_ZOOM)
  }

  fun clear() {
    map.mapObjects.remove(collection)
  }

  override fun onClusterAdded(cluster: Cluster) {
    cluster.appearance.setIcon(images.cluster(cluster.size))
    cluster.addClusterTapListener(clusterTapListener)
  }

  override fun onClusterTap(cluster: Cluster): Boolean {
    val points = cluster.placemarks.map { placemark -> placemark.geometry }
    val bounds = BoundingBox(
      Point(points.minOf { it.latitude }, points.minOf { it.longitude }),
      Point(points.maxOf { it.latitude }, points.maxOf { it.longitude }),
    )
    val fitted = map.cameraPosition(Geometry.fromBoundingBox(bounds))
    map.move(
      // A step back from the exact fit keeps the outer pins off the screen edges
      CameraPosition(fitted.target, fitted.zoom - FIT_MARGIN_ZOOM, fitted.azimuth, fitted.tilt),
      Animation(Animation.Type.SMOOTH, CAMERA_ANIMATION_DURATION),
    )
    return true
  }
}

private const val CLUSTER_RADIUS = 80.0
private const val CLUSTER_MIN_ZOOM = 15
private const val FIT_MARGIN_ZOOM = 0.5f
private const val CAMERA_ANIMATION_DURATION = 0.3f
