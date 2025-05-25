package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // TextAlign 임포트
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트

// TODO: UserRecommendationProfileViewModel 클래스 별도 구현 필요 (위에 수정된 내용 참고)

@Composable
fun TodayDoScreen(navController: NavHostController) {
    // Shared ViewModel 인스턴스 가져오기
    val viewModel: UserRecommendationProfileViewModel = viewModel()

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
            textAlign = TextAlign.Center // TextAlign 임포트 필요
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("여기에 작성해 주세요") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // 입력 값을 ViewModel에 저장
                viewModel.setUserInterest(inputText) // setUserInterest 함수 호출

                // 다음 화면 (CategoryScreen)으로 이동 (인자 전달 불필요)
                navController.navigate("category") // TODO: 네비게이션 그래프에 정의된 정확한 라우트 이름 사용
            },
            enabled = inputText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}