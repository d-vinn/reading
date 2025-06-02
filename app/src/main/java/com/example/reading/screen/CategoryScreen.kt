package com.example.reading

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign // TextAlign 임포트
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트
import androidx.compose.ui.res.colorResource

@Composable
fun CategoryScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel = viewModel()) { // <--- ViewModel 기본값 추가
    // Shared ViewModel 인스턴스 가져오기
    // 네비게이션 그래프에서 인스턴스를 공유하도록 설정했거나,
    // Activity/Fragment 범위에서 ViewModel을 가져오도록 설정했다면 이 코드는 올바릅니다.
    // val viewModel: UserRecommendationProfileViewModel = viewModel() // 이미 파라미터로 받으므로 이 줄은 제거합니다.

    // ViewModel의 selectedCategories와 otherInterest 상태를 읽어옵니다.
    val categoriesList by viewModel.selectedCategories.collectAsState()
    val otherInputText by viewModel.otherInterest.collectAsState()

    val categories = listOf("운동", "공상", "요리", "동물", "곤충", "친구")
    val kidFont = FontFamily(Font(R.font.uhbee_puding))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "오늘은 어떤 것에 대해 알고 싶어?\n관심사를 모두 선택해봐!",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, // TextAlign 임포트 필요
            lineHeight = 54.sp,
            style = TextStyle(fontFamily = kidFont)
        )

        Spacer(modifier = Modifier.height(48.dp))

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
                                // ViewModel의 setSelectedCategories 함수를 호출하여 상태 업데이트
                                val newSelectedCategories = categoriesList.toMutableList()
                                if (newSelectedCategories.contains(category)) {
                                    newSelectedCategories.remove(category)
                                } else {
                                    newSelectedCategories.add(category)
                                }
                                viewModel.setSelectedCategories(newSelectedCategories)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (categoriesList.contains(category)) Color(0xFF9EDC9A).copy(alpha = 0.1f) else Color.Transparent,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(2.dp,
                                if (categoriesList.contains(category)) Color(0xFF9EDC9A) else Color.Gray
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

        Spacer(modifier = Modifier.height(30.dp))

        // 기타 입력
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "기타:",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            OutlinedTextField(
                value = otherInputText ?: "", // null 처리
                onValueChange = { viewModel.setOtherInterest(it) }, // ViewModel 함수 호출
                modifier = Modifier
                    .width(280.dp)
                    .height(180.dp),
                placeholder = { Text("여기에 작성해 주세요") },
                label = { Text("여기에 작성해 주세요", color = colorResource(id = R.color.light_green)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.DarkGray,
                    cursorColor = Color.DarkGray
                )
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // 다음 버튼
        Button(
            onClick = {
                // 이 시점에서 ViewModel에는 이미 selectedCategories와 otherInterest가 저장되어 있습니다.
                // fetchRecommendations() 호출 시 이 값들이 user_combined_other_interests로 합쳐져 전송됩니다.
                navController.navigate("home")
            },
            enabled = categoriesList.isNotEmpty() || !otherInputText.isNullOrBlank(), // ViewModel 상태 사용
            modifier = Modifier
                .width(130.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.light_green), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("다음")
        }
    }
}