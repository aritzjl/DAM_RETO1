package com.reto1.ultramarinos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reto1.ultramarinos.ui.theme.AppTheme

object TimerManager {
    val miHilo = MiHilo()

    init {
        miHilo.start()
    }
}


class MiHilo : Thread() {

    var timerText by mutableStateOf("24:00:00")
    var seg by mutableStateOf(0)
    var min by mutableStateOf(0)
    var hora by mutableStateOf(24)

    override fun run() {


        while (true) {
            Thread.sleep(1000)
            seg--
            if (seg < 0) {
                seg = 59
                min--
                if (min < 0) {
                    min = 59
                    hora--
                    if (hora < 0) {
                        hora = 24 // Reset to 24 hours
                        seg = 0
                        min = 0
                    }
                }
            }
            timerText = formatText(seg, min, hora)
        }

    }

    fun formatText(seg: Int, min: Int, hora: Int): String {
        return "${hora.toString().padStart(2,'0')}:" +
                "${min.toString().padStart(2,'0')}:" +
                "${seg.toString().padStart(2,'0')}"
    }
}




@Composable
fun Timer() {
    val timerText by remember{
        derivedStateOf {
            Snapshot.withMutableSnapshot {
                TimerManager.miHilo.timerText
            }
        }
    }
        Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        Text(
            text = timerText,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(2.dp),
        )

    }
}


@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    AppTheme {
        Timer()
    }
}