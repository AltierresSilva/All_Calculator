package com.example.allcalculator.ui.theme // Lembre-se do nome do seu projeto

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Nosso tema base. Ele garante que os componentes do Compose funcionem
 * sem fechar o app. No futuro, podemos colocar cores globais aqui se precisarmos.
 */
@Composable
fun TemaCalculadora(conteudo: @Composable () -> Unit) {
    MaterialTheme(
        content = conteudo
    )
}