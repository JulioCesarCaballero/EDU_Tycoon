package io.moviles.IPN_Tycoon.engine

import io.moviles.IPN_Tycoon.GameState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AudioManagerTest {

    @Before
    fun setUp() {
        GameState.musicaActiva = true
        AudioManager.setAudio(true)
    }

    @Test
    fun `audio inicializado por defecto como activo con volumen maximo`() {
        assertTrue(AudioManager.audioHabilitado)
        assertTrue(GameState.musicaActiva)
        assertEquals(1.0f, AudioManager.volumenMaster, 0.001f)
        assertEquals("Audio: ACTIVADO", AudioManager.obtenerTextoBoton())
    }

    @Test
    fun `alternarAudio silencia el audio y fija volumen en cero`() {
        val nuevoEstado = AudioManager.alternarAudio()

        assertFalse(nuevoEstado)
        assertFalse(AudioManager.audioHabilitado)
        assertFalse(GameState.musicaActiva)
        assertEquals(0.0f, AudioManager.volumenMaster, 0.001f)
        assertEquals("Audio: SILENCIADO", AudioManager.obtenerTextoBoton())
    }

    @Test
    fun `alternarAudio reactiva el audio y restaura volumen al maximo`() {
        // Primer toggle: silencia
        AudioManager.alternarAudio()
        assertFalse(AudioManager.audioHabilitado)

        // Segundo toggle: reactiva
        val reactivado = AudioManager.alternarAudio()

        assertTrue(reactivado)
        assertTrue(AudioManager.audioHabilitado)
        assertTrue(GameState.musicaActiva)
        assertEquals(1.0f, AudioManager.volumenMaster, 0.001f)
        assertEquals("Audio: ACTIVADO", AudioManager.obtenerTextoBoton())
    }

    @Test
    fun `setAudio asigna estado explicito correctamente`() {
        AudioManager.setAudio(false)
        assertFalse(AudioManager.audioHabilitado)
        assertEquals(0.0f, AudioManager.volumenMaster, 0.001f)

        AudioManager.setAudio(true)
        assertTrue(AudioManager.audioHabilitado)
        assertEquals(1.0f, AudioManager.volumenMaster, 0.001f)
    }
}
