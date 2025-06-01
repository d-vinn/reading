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
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel = viewModel()) { // <--- ViewModel 인스턴스를 명시적으로 한 번만 가져옴 (기본값 설정)

    // ViewModel에서 selectedCategories 및 otherInterest 상태를 collectAsState로 읽어옵니다.
    // 이렇게 해야 ViewModel의 상태 변경이 UI에 반영되고, UI 입력이 ViewModel에 저장됩니다.
    val selectedCategories by viewModel.selectedCategories.collectAsState()
    val otherInterest by viewModel.otherInterest.collectAsState()

    val categories = listOf("운동", "공상", "요리", "동물", "곤충", "친구")


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
            textAlign = TextAlign.Center
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
                                // ViewModel 함수 호출로 변경: 선택된 카테고리를 ViewModel에 업데이트
                                val newSelectedCategories = if (selectedCategories.contains(category)) {
                                    selectedCategories.minus(category) // 제거
                                } else {
                                    selectedCategories.plus(category) // 추가
                                }
                                viewModel.setSelectedCategories(newSelectedCategories)
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
                // ViewModel 상태와 연결
                value = otherInterest ?: "", // otherInterest가 null일 경우 빈 문자열 표시
                onValueChange = { newValue ->
                    viewModel.setOtherInterest(newValue) // ViewModel 함수 호출로 변경
                },
                placeholder = { Text("입력해주세요") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 다음 버튼
        Button(
            onClick = {
                // 이 시점에서 ViewModel에는 `selectedCategories`와 `otherInterest`가 이미 저장되어 있습니다.
                // 이제 서버에 추천 요청을 보낼 차례입니다.
                // 일반적으로 '다음' 버튼은 다음 화면으로 이동하고, 서버 요청은 다음 화면에서
                // 모든 사용자 입력이 완료된 후 트리거하는 것이 좋습니다.
                // 만약 이 화면에서 바로 요청을 보내려면 여기에 viewModel.fetchRecommendations() 호출을 추가합니다.
                // 예시:
                // viewModel.fetchRecommendations()

                navController.navigate("home")
            },
            // 'otherInterest'는 nullable이므로 .isNullOrBlank() 사용
            enabled = selectedCategories.isNotEmpty() || !otherInterest.isNullOrBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}