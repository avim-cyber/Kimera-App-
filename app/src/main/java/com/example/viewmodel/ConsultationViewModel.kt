package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AccuracyAnchor
import com.example.model.ConsultationData
import com.example.model.ConsultationTab
import com.example.model.MatchFidelity
import com.example.model.StyleVariation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsultationViewModel : ViewModel() {

    private val _currentTab = MutableStateFlow(ConsultationTab.STUDIO)
    val currentTab: StateFlow<ConsultationTab> = _currentTab.asStateFlow()

    private val _selectedVariationId = MutableStateFlow("A")
    val selectedVariationId: StateFlow<String> = _selectedVariationId.asStateFlow()

    private val _isSplitWithReference = MutableStateFlow(false)
    val isSplitWithReference: StateFlow<Boolean> = _isSplitWithReference.asStateFlow()

    private val _fullscreenVariation = MutableStateFlow<StyleVariation?>(null)
    val fullscreenVariation: StateFlow<StyleVariation?> = _fullscreenVariation.asStateFlow()

    // Fidelity & Outcome state
    private val _fidelity = MutableStateFlow(MatchFidelity.EXACT)
    val fidelity: StateFlow<MatchFidelity> = _fidelity.asStateFlow()

    private val _realismRating = MutableStateFlow(5.0f)
    val realismRating: StateFlow<Float> = _realismRating.asStateFlow()

    private val _accuracyAnchors = MutableStateFlow(ConsultationData.initialAccuracyAnchors)
    val accuracyAnchors: StateFlow<List<AccuracyAnchor>> = _accuracyAnchors.asStateFlow()

    private val _chairsideNote = MutableStateFlow(
        "Client loved the fringe volume; felt confident before first cut. Used Matte Clay on damp hair and diffuser."
    )
    val chairsideNote: StateFlow<String> = _chairsideNote.asStateFlow()

    private val _isCompleting = MutableStateFlow(false)
    val isCompleting: StateFlow<Boolean> = _isCompleting.asStateFlow()

    private val _isSessionCompleted = MutableStateFlow(false)
    val isSessionCompleted: StateFlow<Boolean> = _isSessionCompleted.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast.asStateFlow()

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage.asStateFlow()

    // Session pipeline timer & live progress simulation
    private val _meshTimer = MutableStateFlow(50)
    val meshTimer: StateFlow<Int> = _meshTimer.asStateFlow()

    private val _pipelineProgress = MutableStateFlow(0.48f)
    val pipelineProgress: StateFlow<Float> = _pipelineProgress.asStateFlow()

    init {
        // Start simulated timer counting down
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_meshTimer.value > 0) {
                    _meshTimer.value -= 1
                    if (_pipelineProgress.value < 0.95f) {
                        _pipelineProgress.value += 0.005f
                    }
                }
            }
        }
    }

    fun setTab(tab: ConsultationTab) {
        _currentTab.value = tab
    }

    fun selectVariation(id: String) {
        _selectedVariationId.value = id
    }

    fun toggleSplitMode() {
        _isSplitWithReference.value = !_isSplitWithReference.value
    }

    fun setSplitMode(enabled: Boolean) {
        _isSplitWithReference.value = enabled
    }

    fun openFullscreen(variation: StyleVariation) {
        _fullscreenVariation.value = variation
    }

    fun closeFullscreen() {
        _fullscreenVariation.value = null
    }

    fun setFidelity(fidelity: MatchFidelity) {
        _fidelity.value = fidelity
    }

    fun setRealismRating(rating: Float) {
        _realismRating.value = rating
    }

    fun toggleAnchor(anchorId: String) {
        _accuracyAnchors.value = _accuracyAnchors.value.map {
            if (it.id == anchorId) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun updateChairsideNote(note: String) {
        if (note.length <= 250) {
            _chairsideNote.value = note
        }
    }

    fun completeSession() {
        viewModelScope.launch {
            _isCompleting.value = true
            delay(700)
            _isCompleting.value = false
            _isSessionCompleted.value = true
            _toastMessage.value = "Consultation Logged • Outcome saved to Vikram's hair history"
            _showToast.value = true

            delay(3500)
            _showToast.value = false
        }
    }

    fun lockInCurrentVariation() {
        viewModelScope.launch {
            val varId = _selectedVariationId.value
            _toastMessage.value = "Blueprint locked: Variation $varId sent to station mirror & color bar"
            _showToast.value = true
            delay(2500)
            _showToast.value = false
        }
    }
}
