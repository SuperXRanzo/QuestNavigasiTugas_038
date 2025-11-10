package com.example.questnavigasitugas_038.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.questnavigasitugas_038.data.DataSource
import com.example.questnavigasitugas_038.data.FormData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val participants: List<FormData> = DataSource.participants
)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    var nama = mutableStateOf("")
    var alamat = mutableStateOf("")
    var gender = mutableStateOf("Laki-laki") // Nilai default
    val statusOptions = listOf("Lajang", "Kawin", "Cerai", "Duda", "Janda")
    var status = mutableStateOf(statusOptions[0]) // Default "Lajang"

    var lastSubmittedData = mutableStateOf<FormData?>(null)

    fun addParticipant() {
        val newParticipant = FormData(
            nama = nama.value,
            gender = if (gender.value == "Laki-laki") "Laki - Laki" else "Perempuan", // Sesuaikan format
            status = status.value,
            alamat = alamat.value
        )

        _uiState.update { currentState ->
            currentState.copy(
                participants = currentState.participants + newParticipant
            )
        }

        lastSubmittedData.value = newParticipant

        resetForm()
    }

    private fun resetForm() {
        nama.value = ""
        alamat.value = ""
        gender.value = "Laki-laki"
        status.value = statusOptions[0]
    }
}