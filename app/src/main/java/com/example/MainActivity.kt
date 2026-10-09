package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ConsultationTab
import com.example.ui.components.KimeraBottomNav
import com.example.ui.components.KimeraHeader
import com.example.ui.dialogs.FullscreenZoomDialog
import com.example.ui.screens.FormulaScreen
import com.example.ui.screens.SessionScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ConsultationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                KimeraApp()
            }
        }
    }
}

@Composable
fun KimeraApp(
    viewModel: ConsultationViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedVariationId by viewModel.selectedVariationId.collectAsStateWithLifecycle()
    val isSplitWithReference by viewModel.isSplitWithReference.collectAsStateWithLifecycle()
    val fullscreenVariation by viewModel.fullscreenVariation.collectAsStateWithLifecycle()

    val fidelity by viewModel.fidelity.collectAsStateWithLifecycle()
    val realismRating by viewModel.realismRating.collectAsStateWithLifecycle()
    val accuracyAnchors by viewModel.accuracyAnchors.collectAsStateWithLifecycle()
    val chairsideNote by viewModel.chairsideNote.collectAsStateWithLifecycle()
    val isCompleting by viewModel.isCompleting.collectAsStateWithLifecycle()
    val isSessionCompleted by viewModel.isSessionCompleted.collectAsStateWithLifecycle()
    val showToast by viewModel.showToast.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()

    val timerSeconds by viewModel.meshTimer.collectAsStateWithLifecycle()
    val pipelineProgress by viewModel.pipelineProgress.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            KimeraHeader(
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                titleSuffix = when (currentTab) {
                    ConsultationTab.SESSION -> "Mirror Session"
                    ConsultationTab.STUDIO -> null
                    ConsultationTab.FORMULA -> "Outcome Log"
                }
            )
        },
        bottomBar = {
            KimeraBottomNav(
                currentTab = currentTab,
                onTabSelected = { viewModel.setTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentTab,
                animationSpec = tween(250),
                label = "tab_crossfade"
            ) { tab ->
                when (tab) {
                    ConsultationTab.SESSION -> {
                        SessionScreen(
                            timerSeconds = timerSeconds,
                            progress = pipelineProgress,
                            onNavigateToStudio = { viewModel.setTab(ConsultationTab.STUDIO) }
                        )
                    }
                    ConsultationTab.STUDIO -> {
                        StudioScreen(
                            selectedVariationId = selectedVariationId,
                            isSplitMode = isSplitWithReference,
                            onSelectVariation = { viewModel.selectVariation(it) },
                            onToggleSplitMode = { viewModel.toggleSplitMode() },
                            onOpenFullscreen = { viewModel.openFullscreen(it) },
                            onLockInVariation = { viewModel.lockInCurrentVariation() }
                        )
                    }
                    ConsultationTab.FORMULA -> {
                        FormulaScreen(
                            fidelity = fidelity,
                            realismRating = realismRating,
                            accuracyAnchors = accuracyAnchors,
                            chairsideNote = chairsideNote,
                            isCompleting = isCompleting,
                            isSessionCompleted = isSessionCompleted,
                            showToast = showToast,
                            toastMessage = toastMessage,
                            onFidelityChanged = { viewModel.setFidelity(it) },
                            onRealismChanged = { viewModel.setRealismRating(it) },
                            onToggleAnchor = { viewModel.toggleAnchor(it) },
                            onNoteChanged = { viewModel.updateChairsideNote(it) },
                            onCompleteSession = { viewModel.completeSession() }
                        )
                    }
                }
            }

            // Fullscreen Zoom Dialog if opened
            fullscreenVariation?.let { variation ->
                FullscreenZoomDialog(
                    variation = variation,
                    onDismiss = { viewModel.closeFullscreen() }
                )
            }
        }
    }
}
