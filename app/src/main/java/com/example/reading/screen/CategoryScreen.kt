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
import androidx.compose.ui.text.style.TextAlign // TextAlign 임포트
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트
// import androidx.compose.ui.platform.LocalLifecycleOwner // ViewModel 스코프 지정을 위해 필요할 수 있음

// TODO: UserRecommendationProfileViewModel 클래스 별도 구현 필요 (위에 수정된 내용 참고)

@Composable
fun CategoryScreen(
    navController: NavHostController
    // TODO: TodayDoScreen에서 넘겨받은 userInterest 파라미터 제거
    // userInterest: String? // 이 파라미터는 제거합니다.
) {
    // Shared ViewModel 인스턴스 가져오기
    // ViewModel 스코프 설정은 네비게이션 그래프 설정 시 이루어져야 합니다.
    val viewModel: UserRecommendationProfileViewModel = viewModel()


    // TODO: ViewModel 상태를 읽어오도록 변경 고려
    // val selectedCategories = viewModel.selectedCategories.collectAsState().value
    val categories = listOf("운동", "공상", "요리", "동물", "곤충", "친구")
    val selectedCategories = remember { mutableStateListOf<String>() } // TODO: ViewModel로 이동 고려

    // TODO: ViewModel 상태를 읽어오도록 변경 고려
    // var otherInput by remember { viewModel.otherInput.collectAsState() }
    var otherInput by remember { mutableStateOf("") } // TODO: ViewModel로 이동 고려


    // TODO: ViewModel에 userInterest 저장 로직 제거 (TodayDoScreen에서 이미 저장했음)
    // LaunchedEffect(userInterest) { ... } // 이 블록은 제거합니다.


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
            textAlign = TextAlign.Center // TextAlign 임포트 필요
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
                                // TODO: ViewModel 함수 호출로 변경 고려
                                if (selectedCategories.contains(category)) {
                                    selectedCategories.remove(category)
                                } else {
                                    selectedCategories.add(category)
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                // TODO: ViewModel 상태에 따라 색상 변경 로직 수정 필요
                                containerColor = if (selectedCategories.contains(category)) Color.Magenta.copy(alpha = 0.1f) else Color.Transparent,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(2.dp,
                                // TODO: ViewModel 상태에 따라 보더 색상 변경 로직 수정 필요
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
                // TODO: ViewModel 상태와 연결 고려
                value = otherInput,
                onValueChange = {
                    otherInput = it // TODO: ViewModel 함수 호출로 변경 고려
                },
                placeholder = { Text("입력해주세요") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 다음 버튼
        Button(
            // TODO: ViewModel에 데이터 저장 후 HomeScreen으로 네비게이션
            onClick = {
                // 현재 CategoryScreen에서 선택/입력된 값을 ViewModel에 저장
                viewModel.setCategoryAndOther(selectedCategories.toList(), otherInput) // setCategoryAndOther 함수 호출

                // HomeScreen으로 네비게이션 (ViewModel이 데이터를 가지고 있으므로 인자 전달 불필요)
                navController.navigate("home") // TODO: 네비게이션 그래프에 정의된 정확한 라우트 이름 사용
            },
            enabled = selectedCategories.isNotEmpty() || otherInput.isNotBlank(), // 입력 값이 있거나 카테고리가 하나 이상 선택되었을 때 활성화
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("다음")
        }
    }
}