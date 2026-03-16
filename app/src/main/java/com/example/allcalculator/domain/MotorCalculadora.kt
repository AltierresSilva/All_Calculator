package com.example.allcalculator.domain

import java.math.BigDecimal
import java.math.RoundingMode

class MotorCalculadora {
    fun calcular(numeroAnterior: String, numeroAtual: String, operacao: String): String {
        if (numeroAnterior.isEmpty() || numeroAtual.isEmpty()) return ""

        return try {
            val valor1 = BigDecimal(numeroAnterior)
            val valor2 = BigDecimal(numeroAtual)

            val resultado = when (operacao) {
                "+" -> valor1.add(valor2)
                "-" -> valor1.subtract(valor2)
                // Aceita o símbolo oficial ou as letras/asterisco para evitar falhas
                "×", "x", "*" -> valor1.multiply(valor2)
                "÷", "/" -> {
                    if (valor2.compareTo(BigDecimal.ZERO) == 0) return "Erro"
                    valor1.divide(valor2, 8, RoundingMode.HALF_UP)
                }
                else -> return ""
            }

            resultado.stripTrailingZeros().toPlainString()

        } catch (e: Exception) {
            "Erro" // Se o utilizador tentar quebrar a app, mostramos apenas Erro
        }
    }
}