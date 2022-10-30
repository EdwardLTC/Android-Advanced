package com.edward.mapcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edward.mapcompose.ui.theme.MapComposeTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

private const val TAG = "BasicMapActivity"

private val singapore = LatLng(1.35, 103.87)
private val defaultCameraPosition = CameraPosition.fromLatLngZoom(singapore, 11f)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isMapLoaded by remember { mutableStateOf(false) }
            val cameraPositionState = rememberCameraPositionState {
                position = defaultCameraPosition
            }

            Box(Modifier.fillMaxSize()) {
                GoogleMapView(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        isMapLoaded = true
                    },
                )
                if (!isMapLoaded) {
                    AnimatedVisibility(
                        modifier = Modifier
                            .matchParentSize(),
                        visible = !isMapLoaded,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(state = ScrollState(0)),
        horizontalArrangement = Arrangement.Center
    ) {
        MapType.values().forEach {
            MapTypeButton(type = it) { onMapTypeClick(it) }
        }
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val singaporeState = rememberMarkerState(position = singapore)
    var circleCenter by remember { mutableStateOf(singapore) }
    if (singaporeState.dragState == DragState.END) {
        circleCenter = singaporeState.position
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    var shouldAnimateZoom by remember { mutableStateOf(true) }
    var ticker by remember { mutableStateOf(0) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var mapVisible by remember { mutableStateOf(true) }

    if (mapVisible) {
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = onMapLoaded,
            onPOIClick = {
                Log.d(TAG, "POI clicked: ${it.name}")
            }
        ) {
            // Drawing on the map is accomplished with a child-based API
            val markerClick: (Marker) -> Boolean = {
                Log.d(TAG, "${it.title} was clicked")
                cameraPositionState.projection?.let { projection ->
                    Log.d(TAG, "The current projection is: $projection")
                }
                false
            }
            MarkerInfoWindowContent(
                state = singaporeState,
                title = "Zoom in has been tapped $ticker times.",
                onClick = markerClick,
                draggable = true,
            ) {
                Text(it.title ?: "Title", color = Color.Red)
            }
            Circle(
                center = circleCenter,
                fillColor = MaterialTheme.colors.secondary,
                strokeColor = MaterialTheme.colors.secondaryVariant,
                radius = 1000.0,
            )
            content()
        }

    }
    Column {
        MapTypeControls(onMapTypeClick = {
            Log.d("GoogleMap", "Selected map type $it")
            mapProperties = mapProperties.copy(mapType = it)
        })
        Row {
            MapButton(
                text = "Reset Map",
                onClick = {
                    mapProperties = mapProperties.copy(mapType = MapType.NORMAL)
                    cameraPositionState.position = defaultCameraPosition
                    singaporeState.position = singapore
                    singaporeState.hideInfoWindow()
                }
            )
            MapButton(
                text = "Toggle Map",
                onClick = { mapVisible = !mapVisible },
                modifier = Modifier.testTag("toggleMapVisibility"),
            )
        }
        val coroutineScope = rememberCoroutineScope()
        ZoomControls(
            shouldAnimateZoom,
            uiSettings.zoomControlsEnabled,
            onZoomOut = {
                if (shouldAnimateZoom) {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomOut())
                    }
                } else {
                    cameraPositionState.move(CameraUpdateFactory.zoomOut())
                }
            },
            onZoomIn = {
                if (shouldAnimateZoom) {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomIn())
                    }
                } else {
                    cameraPositionState.move(CameraUpdateFactory.zoomIn())
                }
                ticker++
            },
            onCameraAnimationCheckedChange = {
                shouldAnimateZoom = it
            },
            onZoomControlsCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}


@Composable
private fun MapTypeButton(type: MapType, onClick: () -> Unit) =
    MapButton(text = type.toString(), onClick = onClick)

@Composable
private fun ZoomControls(
    isCameraAnimationChecked: Boolean,
    isZoomControlsEnabledChecked: Boolean,
    onZoomOut: () -> Unit,
    onZoomIn: () -> Unit,
    onCameraAnimationCheckedChange: (Boolean) -> Unit,
    onZoomControlsCheckedChange: (Boolean) -> Unit,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        MapButton("-", onClick = { onZoomOut() })
        MapButton("+", onClick = { onZoomIn() })
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Camera Animations On?")
            Switch(
                isCameraAnimationChecked,
                onCheckedChange = onCameraAnimationCheckedChange,
                modifier = Modifier.testTag("cameraAnimations"),
            )
            Text(text = "Zoom Controls On?")
            Switch(
                isZoomControlsEnabledChecked,
                onCheckedChange = onZoomControlsCheckedChange
            )
        }
    }
}


@Composable
private fun MapButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GoogleMapViewPreview() {
    MapComposeTheme {

    }
}