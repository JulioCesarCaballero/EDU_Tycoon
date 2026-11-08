package io.moviles.IPN_Tycoon

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.widget.VisWindow
import io.moviles.IPN_Tycoon.engine.BuildingIncomeManager
import ktx.actors.onChange
import ktx.scene2d.*

class BuildingInfoWindow(
    private val data: Propiedad,
    private val onBuildingChanged: () -> Unit
) : VisWindow("Gestión del Plantel") {

    init {
        addCloseButton()
        closeOnEscape()
        isModal = false

        val costo: Long
        val btnTexto: String
        val puedeMejorar: Boolean

        when {
            !data.comprada -> {
                costo        = data.precio
                btnTexto     = "COMPRAR  \$${fmt(costo)}"
                puedeMejorar = true
            }
            data.nivel < data.mejoraMax -> {
                costo        = GameState.costoMejora(data)
                btnTexto     = "MEJORAR LVL ${data.nivel + 1}  \$${fmt(costo)}"
                puedeMejorar = true
            }
            else -> {
                costo        = 0L
                btnTexto     = "NIVEL MÁXIMO"
                puedeMejorar = false
            }
        }

        add(scene2d.table {

            // ── Nombre ────────────────────────────────────────────────
            label(data.nombre) {
                color = Color.GOLD
            }.cell(padBottom = 6f)
            row()

            // ── Descripción ───────────────────────────────────────────
            label(data.descripcion) {
                setWrap(true)
                color = Color.LIGHT_GRAY
            }.cell(width = 280f, padBottom = 10f)
            row()

            // ── Stats ─────────────────────────────────────────────────
            label("Capacidad: ${data.capacidad} alumnos"); row()
            label("Ciclo de producción: ${data.tiempoCicloSegundos.toInt()}s") {
                color = Color.YELLOW
            }.cell(padBottom = 2f)
            row()

            if (data.comprada) {
                label("Nivel actual: ${data.nivel} / ${data.mejoraMax}") {
                    color = Color.CYAN
                }.cell(padBottom = 2f)
                row()

                val ingresoCiclo = BuildingIncomeManager.calcularIngresoPorCiclo(data)
                label("Ingreso por ciclo: \$${fmt(ingresoCiclo)}") {
                    color = Color.GREEN
                }
                row()

                if (BuildingIncomeManager.tieneIngresoListo(data.id)) {
                    val listas = BuildingIncomeManager.obtenerGananciasListas(data.id)
                    label("★ ¡\$${fmt(listas)} listos! Toca la burbuja en el mapa") {
                        color = Color.GOLD
                    }.cell(padTop = 2f, padBottom = 2f)
                    row()
                } else {
                    val restante = BuildingIncomeManager.obtenerTiempoRestante(data).toInt()
                    label("Próximo cobro en: ${restante}s") {
                        color = Color.LIGHT_GRAY
                    }.cell(padBottom = 2f)
                    row()
                }

                if (data.nivel < data.mejoraMax) {
                    val ingresoSiguiente = BuildingIncomeManager.calcularIngresoPorCiclo(data, data.nivel + 1)
                    label("Nivel ${data.nivel + 1}: \$${fmt(ingresoSiguiente)} cada ${data.tiempoCicloSegundos.toInt()}s") {
                        color = Color.GRAY
                    }
                    row()
                }
            } else {
                val ingresoNivel1 = BuildingIncomeManager.calcularIngresoPorCiclo(data, 1)
                label("Ingreso inicial: \$${fmt(ingresoNivel1)} cada ${data.tiempoCicloSegundos.toInt()}s") {
                    color = Color.GREEN
                }
                row()
            }

            // ── Saldo ─────────────────────────────────────────────────
            label("Tu saldo: \$${fmt(GameState.dinero)}") {
                color = Color.LIGHT_GRAY
            }.cell(padTop = 6f, padBottom = 4f)
            row()

            // ── Botón acción ──────────────────────────────────────────
            textButton(btnTexto) {
                isDisabled = !puedeMejorar

                onChange {
                    if (!puedeMejorar) return@onChange

                    if (!GameState.puedeComprar(costo)) {
                        setText("¡Saldo insuficiente!")
                        color = Color.RED
                        isDisabled = true
                        return@onChange
                    }

                    GameState.gastar(costo)

                    if (!data.comprada) {
                        data.comprada = true
                        data.nivel    = 1
                    } else {
                        data.nivel++
                    }

                    onBuildingChanged()
                    this@BuildingInfoWindow.remove()
                }
            }.cell(padTop = 14f, expandX = true, fillX = true)
        }).pad(16f)

        pack()
        centerWindow()
    }

    fun show(stage: Stage) { stage.addActor(this) }

    private fun fmt(v: Long) = when {
        v >= 1_000_000L -> "${"%.2f".format(v / 1_000_000.0)}M"
        v >= 1_000L     -> "${"%.1f".format(v / 1_000.0)}K"
        else            -> v.toString()
    }
}
