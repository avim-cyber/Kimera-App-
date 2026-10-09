package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.OpenWith
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.ConsultationData
import com.example.model.StyleVariation
import com.example.ui.theme.KimeraDeepEspresso
import com.example.ui.theme.KimeraOnPrimary
import com.example.ui.theme.KimeraOverlayDark
import com.example.ui.theme.KimeraOverlayLight
import com.example.ui.theme.KimeraPrimary
import com.example.ui.theme.KimeraPrimaryContainer
import com.example.ui.theme.KimeraPrimaryFixed
import com.example.ui.theme.KimeraSerifFont
import com.example.ui.theme.KimeraSurfaceContainer
import com.example.ui.theme.KimeraSurfaceContainerHigh
import com.example.ui.theme.KimeraSurfaceContainerLow
import com.example.ui.theme.KimeraSurfaceContainerLowest

@Composable
fun StudioScreen(
    selectedVariationId: String,
    isSplitMode: Boolean,
    onSelectVariation: (String) -> Unit,
    onToggleSplitMode: () -> Unit,
    onOpenFullscreen: (StyleVariation) -> Unit,
    onLockInVariation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val variations = ConsultationData.variations

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .testTag("studio_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 120.dp)
        ) {
            // Context header: Step 3 of 4 • Style Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = KimeraSurfaceContainerHigh,
                    modifier = Modifier.testTag("step_indicator_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(KimeraPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "STEP 3 OF 4 • STYLE SELECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = KimeraPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "AI Stylist Ready",
                        style = MaterialTheme.typography.labelSmall,
                        color = KimeraPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Headline & Subtitle
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Your Adapted Previews",
                    fontFamily = KimeraSerifFont,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "3 tailored variations synthesized from your Pinterest reference and face topology.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Segmented Button: [ AI Renders (3) ] | [ Split with Reference ]
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("renders_toggle_segment"),
                shape = RoundedCornerShape(24.dp),
                color = KimeraSurfaceContainerLow,
                border = BorderStroke(1.dp, KimeraSurfaceContainerHigh)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // AI Renders Option
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { if (isSplitMode) onToggleSplitMode() },
                        color = if (!isSplitMode) KimeraSurfaceContainerLowest else Color.Transparent,
                        shadowElevation = if (!isSplitMode) 2.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = if (!isSplitMode) KimeraPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "AI Renders (3)",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (!isSplitMode) KimeraPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Split with Reference Option
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { if (!isSplitMode) onToggleSplitMode() }
                            .testTag("split_reference_toggle"),
                        color = if (isSplitMode) KimeraSurfaceContainerLowest else Color.Transparent,
                        shadowElevation = if (isSplitMode) 2.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Compare,
                                contentDescription = null,
                                tint = if (isSplitMode) KimeraPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Split with Reference",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSplitMode) KimeraPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Variation Horizontal Quick Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                variations.forEach { variation ->
                    val isSelected = variation.id == selectedVariationId
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectVariation(variation.id) },
                        label = {
                            Text(
                                text = if (variation.isRecommended) "Variation ${variation.id} (Recommended)" else "Variation ${variation.id}",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) KimeraOnPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = KimeraOnPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KimeraPrimary,
                            containerColor = KimeraSurfaceContainerLow,
                            selectedLabelColor = KimeraOnPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) KimeraPrimary else KimeraSurfaceContainerHigh,
                            selectedBorderColor = KimeraPrimary,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("filter_chip_${variation.id}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Variations Cards List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                variations.forEach { variation ->
                    val isSelected = variation.id == selectedVariationId
                    VariationCard(
                        variation = variation,
                        isSelected = isSelected,
                        isSplitMode = isSplitMode,
                        onSelect = { onSelectVariation(variation.id) },
                        onOpenFullscreen = { onOpenFullscreen(variation) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Consultation Actions & Stylist Note Block
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("consultation_actions_panel"),
                shape = RoundedCornerShape(16.dp),
                color = KimeraSurfaceContainerLow,
                border = BorderStroke(1.dp, KimeraSurfaceContainerHigh)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Consultation Actions",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Station #4 • Stylist Priya",
                            style = MaterialTheme.typography.labelSmall,
                            color = KimeraPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val current = variations.find { it.id == selectedVariationId } ?: variations.first()
                                onOpenFullscreen(current)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_zoom_fullscreen"),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, KimeraSurfaceContainerHigh),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = KimeraSurfaceContainerLowest
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Zoom Fullscreen",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        OutlinedButton(
                            onClick = onToggleSplitMode,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("btn_side_by_side"),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, KimeraSurfaceContainerHigh),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = KimeraSurfaceContainerLowest
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Side-by-Side",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stylist Recommendation Box
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = KimeraSurfaceContainerLowest,
                        border = BorderStroke(1.dp, KimeraPrimaryFixed.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = KimeraPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Priya recommends Variation A: \"Keeps length on crown while slimming the jawline subtly.\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Sticky Bottom CTA Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLockInVariation,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("lock_in_variation_button"),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KimeraPrimary,
                        contentColor = KimeraOnPrimary
                    )
                ) {
                    Text(
                        text = "Discuss & Lock In Variation $selectedVariationId",
                        style = MaterialTheme.typography.labelLarge,
                        color = KimeraOnPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Sends chosen blueprint to station mirror & color bar",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun VariationCard(
    variation: StyleVariation,
    isSelected: Boolean,
    isSplitMode: Boolean,
    onSelect: () -> Unit,
    onOpenFullscreen: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("variation_card_${variation.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = KimeraSurfaceContainerLow
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) KimeraPrimary else KimeraSurfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image Preview Container with Overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(if (isSplitMode) 1.1f else 0.95f)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(KimeraSurfaceContainerHigh)
            ) {
                if (isSplitMode) {
                    // Split view: Reference vs Render
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(variation.referenceUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Original Reference",
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
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(variation.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = variation.title,
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
                                    text = "Variation ${variation.id}",
                                    color = KimeraOnPrimary,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Standard Single Render View
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(variation.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = variation.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Top Tag Overlays
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = KimeraOverlayDark
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(KimeraPrimaryContainer)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = variation.subtitle,
                                    color = KimeraOnPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = KimeraPrimaryFixed.copy(alpha = 0.9f)
                        ) {
                            Text(
                                text = variation.profileFit,
                                color = KimeraDeepEspresso,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Top Action Icons (Zoom & Compare)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = onOpenFullscreen,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(KimeraOverlayDark)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ZoomIn,
                                contentDescription = "Zoom In",
                                tint = KimeraOnPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Bottom badge on image: Currently Selected or Switch button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, KimeraOverlayDark)
                            )
                        )
                        .padding(12.dp)
                ) {
                    if (isSelected) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = KimeraOverlayDark,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = KimeraPrimaryContainer,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Currently Selected",
                                        color = KimeraOnPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = "${variation.matchPercentage}% Match",
                                    color = KimeraOnPrimary.copy(alpha = 0.9f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = onSelect,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .testTag("switch_to_${variation.id}"),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = KimeraSurfaceContainerLowest,
                                contentColor = KimeraDeepEspresso
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null,
                                tint = KimeraDeepEspresso,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Switch to Variation ${variation.id}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Card Body Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = variation.title,
                        fontFamily = KimeraSerifFont,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = KimeraSurfaceContainerHigh
                    ) {
                        Text(
                            text = variation.maintenanceLevel,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = variation.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Detail chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    variation.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = KimeraSurfaceContainerLowest,
                            border = BorderStroke(1.dp, KimeraSurfaceContainerHigh)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = tag.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
