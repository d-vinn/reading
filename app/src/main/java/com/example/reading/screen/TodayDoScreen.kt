package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Material3 TextField, OutlinedTextField 사용
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트
import android.util.Log // Logcat 출력을 위해 추가

@Composable
fun TodayDoScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel) { // <--- 이 줄이 핵심 변경
    // 이 줄은 삭제하거나 주석 처리해야 합니다.
    // val viewModel: UserRecommendationProfileViewModel = viewModel()

    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "오늘은 어떤 일이 있었어? 오늘 일을 바탕으로 책을 추천해줄게!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("여기에 작성해 주세요") },
            label = { Text("오늘의 일") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.setUserInterest(inputText)
                Log.d("TodayDoScreen", "UserInterest 저장: $inputText")
                navController.navigate("category")
            },
            enabled = inputText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}