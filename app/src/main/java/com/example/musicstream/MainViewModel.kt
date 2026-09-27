package com.example.musicstream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicstream.model.Track
import com.example.musicstream.model.toTrack
import com.example.musicstream.network.AudiusApi
import com.example.musicstream.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val query: String = "",
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val nowPlayingId: String? = null,
    val isPlaying: Boolean = false
)

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    // ExoPlayer - anayecheza mkondo (stream) wa sauti moja kwa moja kutoka kwenye kiungo
    val player: ExoPlayer = ExoPlayer.Builder(app).build()

    init {
        // Pakia nyimbo maarufu (trending) mara app inapofunguliwa
        loadTrending()
    }

    fun onQueryChange(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
    }

    fun loadTrending() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val res = RetrofitClient.api.trendingTracks()
                _uiState.value = _uiState.value.copy(
                    tracks = res.data.map { it.toTrack() },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Imeshindwa kupakia. Angalia mtandao wako."
                )
            }
        }
    }

    fun search() {
        val q = _uiState.value.query.trim()
        if (q.isEmpty()) {
            loadTrending()
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val res = RetrofitClient.api.searchTracks(q)
                _uiState.value = _uiState.value.copy(
                    tracks = res.data.map { it.toTrack() },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Utafutaji umeshindwa. Angalia mtandao wako."
                )
            }
        }
    }

    fun playTrack(track: Track) {
        val current = _uiState.value
        // Kama huyu huyu ndiye anayechezwa, geuza play/pause tu
        if (current.nowPlayingId == track.id) {
            if (player.isPlaying) {
                player.pause()
                _uiState.value = current.copy(isPlaying = false)
            } else {
                player.play()
                _uiState.value = current.copy(isPlaying = true)
            }
            return
        }

        val url = AudiusApi.streamUrl(RetrofitClient.currentHost.trimEnd('/'), track.id)
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        _uiState.value = current.copy(nowPlayingId = track.id, isPlaying = true)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
