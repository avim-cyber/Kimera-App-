package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.StyleVariation
import com.example.ui.theme.KimeraDeepEspresso
import com.example.ui.theme.KimeraOnPrimary
import com.example.ui.theme.KimeraOnSurface
import com.example.ui.theme.KimeraOnSurfaceVariant
import com.example.ui.theme.KimeraOverlayDark
import com.example.ui.theme.KimeraPrimary
import com.example.ui.theme.KimeraSerifFont
import com.example.ui.theme.KimeraSurface
import com.example.ui.theme.KimeraSurfaceContainerHigh
import com.example.ui.theme.KimeraSurfaceContainerLow

@Composable
fun FullscreenZoomDialog(
    variation: StyleVariation,
    onDismiss: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showReferenceSplit by remember { mutableStateOf(false) }

    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 4f)
        offset += offsetChange
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Bright light surface background instead of black
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(KimeraSurface.copy(alpha = 0.98f))
                .testTag("fullscreen_zoom_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top control bar with dark, high-contrast text
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = variation.title,
                            fontFamily = KimeraSerifFont,
                            fontWeight = FontWeight.Bold,
                            color = KimeraOnSurface,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "${variation.subtitle} • High-Res Topology",
                            color = KimeraOnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { showReferenceSplit = !showReferenceSplit },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (showReferenceSplit) KimeraPrimary else KimeraSurfaceContainerHigh)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Compare,
                                contentDescription = "Toggle Split",
                                tint = if (showReferenceSplit) KimeraOnPrimary else KimeraOnSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(KimeraSurfaceContainerHigh)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = KimeraOnSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Center Image viewport (pinch & pan)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(KimeraSurfaceContainerLow)
                        .transformable(state = state),
                    contentAlignment = Alignment.Center
                ) {
                    if (showReferenceSplit) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(variation.referenceUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Reference",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    color = KimeraOverlayDark
                                ) {
                                    Text(
                                        text = "Pinterest Ref",
                                        color = KimeraOnPrimary,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(variation.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "AI Render",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    color = KimeraPrimary.copy(alpha = 0.9f)
                                ) {
                                    Text(
                                        text = "Adapted Render",
                                        color = KimeraOnPrimary,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(variation.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = variation.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    translationX = offset.x,
                                    translationY = offset.y
                                )
                        )
                    }
                }

                // Bottom Hint
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = KimeraSurfaceContainerHigh
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = null,
                            tint = KimeraOnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Pinch to zoom hair texture & hairline blend • Tap compare for split",
                            color = KimeraOnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
