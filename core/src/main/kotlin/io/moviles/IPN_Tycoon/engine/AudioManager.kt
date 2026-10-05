package io.moviles.IPN_Tycoon.engine

import com.badlogic.gdx.graphics.Color
import io.moviles.IPN_Tycoon.GameState

/**
 * Gestor centralizado del subsistema de audio para EduTycoon.
 * Controla el silenciamiento y la activación de música y efectos sonoros,
 * gestionando el volumen maestro y la retroalimentación visual en la interfaz.
 */
object AudioManager {

    const val VOLUMEN_MAXIMO: Float = 1.0f
    const val VOLUMEN_SILENCIADO: Float = 0.0f

    /**
     * Volumen maestro actual aplicado al audio del juego (0.0f a 1.0f).
     */
    var volumenMaster: Float = VOLUMEN_MAXIMO
        private set

    /**
     * Indica si el audio general (música y efectos) está habilitado.
     */
    var audioHabilitado: Boolean
        get() = GameState.musicaActiva
        set(valor) {
            GameState.musicaActiva = valor
            volumenMaster = if (valor) VOLUMEN_MAXIMO else VOLUMEN_SILENCIADO
        }

    init {
        // Sincronizar volumen inicial con el estado persistido o en memoria
        volumenMaster = if (GameState.musicaActiva) VOLUMEN_MAXIMO else VOLUMEN_SILENCIADO
    }

    /**
     * Alterna entre silenciar y activar el audio.
     * @return El nuevo estado del audio (true si quedó habilitado, false si quedó silenciado).
     */
    fun alternarAudio(): Boolean {
        audioHabilitado = !audioHabilitado
        return audioHabilitado
    }

    /**
     * Establece el estado del audio explícitamente.
     */
    fun setAudio(habilitado: Boolean) {
        audioHabilitado = habilitado
    }

    /**
     * Retorna la etiqueta de texto para el botón en la interfaz de usuario.
     */
    fun obtenerTextoBoton(): String =
        if (audioHabilitado) "Audio: ACTIVADO" else "Audio: SILENCIADO"

    /**
     * Retorna el color temático para retroalimentación visual en la interfaz.
     */
    fun obtenerColorBoton(): Color =
        if (audioHabilitado) Color.GREEN else Color.CORAL
}
