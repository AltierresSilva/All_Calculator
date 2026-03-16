package com.example.allcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.allcalculator.ui.screens.TelaCalculadoraPadrao
import com.example.allcalculator.ui.theme.TemaCalculadora
import com.google.android.gms.ads.MobileAds // Importação do AdMob

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Liga o motor de anúncios em segundo plano
        MobileAds.initialize(this) {}

        setContent {
            TemaCalculadora {
                TelaCalculadoraPadrao()
            }
        }
    }
}