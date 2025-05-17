package com.example.reading

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CategoryScreen(navController: NavHostController) {
    val categories = listOf("운동", "공상", "요리", "동물", "곤충", "친구")
    val selectedCategories = remember { mutableStateListOf<String>() }
    var otherInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "오늘은 어떤 것에 대해 알고 싶어?\n관심사를 모두 선택해봐!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 선택지 버튼
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            categories.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { category ->
                        OutlinedButton(
                            onClick = {
                                if (selectedCategories.contains(category)) {
                                    selectedCategories.remove(category)
                                } else {
                                    selectedCategories.add(category)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selectedCategories.contains(category)) Color.Magenta.copy(alpha = 0.1f) else Color.Transparent,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(2.dp,
                                if (selectedCategories.contains(category)) Color.Magenta else Color.Gray
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(category)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 기타 입력
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "기타:", fontSize = 16.sp, modifier = Modifier.padding(end = 8.dp))
            TextField(
                value = otherInput,
                onValueChange = { otherInput = it },
                placeholder = { Text("입력해주세요") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 다음 버튼
        Button(
            onClick = { navController.navigate("home") },
            enabled = selectedCategories.isNotEmpty() || otherInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}
