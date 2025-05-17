package com.example.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class EmotionOption(val id: Int, val label: String, val imageRes: Int)

@Composable
fun EmotionSelectionScreen(navController: NavController) {
    val emotions = listOf(
        EmotionOption(1, "기쁨", R.drawable.emo1),
        EmotionOption(2, "화남", R.drawable.emo2),
        EmotionOption(3, "슬픔", R.drawable.emo3),
        EmotionOption(4, "우울함", R.drawable.emo4),
        EmotionOption(5, "지루함", R.drawable.emo5),
        EmotionOption(6, "신남", R.drawable.emo6)
    )

    val selected = remember { mutableStateListOf<Int>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "오늘의 기분은 어때? 1~2개를 선택해봐!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(emotions) { emotion ->
                val isSelected = selected.contains(emotion.id)

                Column(
                    modifier = Modifier
                        .clickable {
                            if (isSelected) {
                                selected.remove(emotion.id)
                            } else if (selected.size < 2) {
                                selected.add(emotion.id)
                            }
                        }
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color.Magenta else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = emotion.imageRes),
                        contentDescription = emotion.label,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(emotion.label)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("TodayDo") },
            enabled = selected.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}
