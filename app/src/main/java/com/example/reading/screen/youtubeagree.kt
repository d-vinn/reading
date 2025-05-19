package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material3.RadioButton


@Composable
fun ConsentScreen(navController: NavHostController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "[필수] 더욱 개인화된 추천을 위해 유튜브 구독 목록을 활용합니다.",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        RadioOption(
            label = "동의합니다",
            selected = selectedOption == "agree",
            onSelect = { selectedOption = "agree" }
        )
        Spacer(modifier = Modifier.height(8.dp))
        RadioOption(
            label = "동의하지 않습니다",
            selected = selectedOption == "disagree",
            onSelect = { selectedOption = "disagree" }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("login")
            },
            enabled = selectedOption == "agree" // "동의합니다"일 때만 활성화
        ) {
            Text("다음")
        }
    }
}

@Composable
fun RadioOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}
