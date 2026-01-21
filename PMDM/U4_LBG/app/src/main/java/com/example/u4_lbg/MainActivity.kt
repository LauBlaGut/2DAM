package com.example.u4_lbg



import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


val LilaFondo = Color(0xFFF3E5F5)
val LilaBoton = Color(0xFFCE93D8)
val LilaOscuro = Color(0xFF8E24AA)
val Blanco = Color.White


enum class Pantalla { MENU, AUDIO, VIDEO }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMultimedia()
        }
    }
}

@Composable
fun AppMultimedia() {
    var pantallaActual by remember { mutableStateOf(Pantalla.MENU) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LilaFondo
    ) {
        when (pantallaActual) {
            Pantalla.MENU -> VistaMenu(
                onIrAudio = { pantallaActual = Pantalla.AUDIO },
                onIrVideo = { pantallaActual = Pantalla.VIDEO }
            )
            Pantalla.AUDIO -> VistaAudio(
                onVolver = { pantallaActual = Pantalla.MENU }
            )
            Pantalla.VIDEO -> VistaVideo(
                onVolver = { pantallaActual = Pantalla.MENU }
            )
        }
    }
}

// ---------------------------------------------------------
// 1. PANTALLA DE MENÚ (Solo los dos botones iniciales)
// ---------------------------------------------------------
@Composable
fun VistaMenu(onIrAudio: () -> Unit, onIrVideo: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Qué quieres reproducir?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = LilaOscuro,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        BotonAesthetic(texto = "Reproductor de Audio", onClick = onIrAudio)
        Spacer(modifier = Modifier.height(20.dp))
        BotonAesthetic(texto = "Reproductor de Vídeo", onClick = onIrVideo)
    }
}

// ---------------------------------------------------------
// 2. PANTALLA DE AUDIO (Lógica de música)
// ---------------------------------------------------------
@Composable
fun VistaAudio(onVolver: () -> Unit) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.ts) }
    var estaReproduciendo by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    BackHandler { onVolver() }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reproduciendo Música", fontSize = 22.sp, color = LilaOscuro,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))

        // Tarjeta decorativa
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(LilaBoton),
            contentAlignment = Alignment.Center
        ) {
            Text("🎧", fontSize = 80.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    estaReproduciendo = false
                } else {
                    mediaPlayer.start()
                    estaReproduciendo = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = LilaOscuro),
            shape = RoundedCornerShape(50),
            modifier = Modifier.size(width = 200.dp, height = 60.dp)
        ) {
            Text(if (estaReproduciendo) "PAUSAR" else "REPRODUCIR", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = onVolver) {
            Text("Volver al Menú", color = LilaOscuro)
        }
    }
}

// ---------------------------------------------------------
// 3. PANTALLA DE VÍDEO (Lógica de vídeo)
// ---------------------------------------------------------
@Composable
fun VistaVideo(onVolver: () -> Unit) {
    val context = LocalContext.current

    BackHandler { onVolver() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reproductor de Vídeo", fontSize = 22.sp, color = LilaOscuro,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        // Contenedor del Vídeo
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth().height(250.dp)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    VideoView(ctx).apply {
                        val uri = Uri.parse("android.resource://"
                                + ctx.packageName + "/" + R.raw.hr)
                        setVideoURI(uri)

                        // Controles nativos (Play, Pause, Barra de progreso)
                        val mediaController = MediaController(ctx)
                        mediaController.setAnchorView(this)
                        setMediaController(mediaController)

                        start() // Autoplay al entrar
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onVolver,
            colors = ButtonDefaults.buttonColors(containerColor = LilaOscuro),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Volver al Menú")
        }
    }
}

// --- COMPONENTE REUTILIZABLE PARA BOTONES BONITOS ---
@Composable
fun BotonAesthetic(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = LilaBoton),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp),
        elevation = ButtonDefaults.buttonElevation(6.dp)
    ) {
        Text(text = texto, fontSize = 18.sp, color = Blanco, fontWeight = FontWeight.SemiBold)
    }
}