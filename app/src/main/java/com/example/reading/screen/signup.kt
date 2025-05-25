package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current

    // Shared ViewModel 인스턴스 가져오기
    // ViewModel 스코프 설정은 네비게이션 그래프 설정 시 이루어져야 합니다.
    val viewModel: UserRecommendationProfileViewModel = viewModel()


    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") } // ViewModel에 저장할 값
    var gender by remember { mutableStateOf("") } // ViewModel에 저장할 값
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* 다이얼로그 바깥 클릭 시 닫히지 않도록 비워 둠 */ },
            title = { Text("회원가입 완료") },
            text = { Text("회원가입이 완료되었습니다!") },
            confirmButton = {
                TextButton(onClick = {
                    // TODO: 실제 회원가입 처리 (서버 통신 등) 로직은 여기에 추가될 수 있습니다.
                    // 현재는 다이얼로그 확인 버튼 클릭 시 정보를 ViewModel에 저장하고 다음 화면으로 이동합니다.

                    // ViewModel에 나이와 성별 저장
                    viewModel.setAgeAndGender(age, gender) // setAgeAndGender 함수 호출

                    showDialog = false // 다이얼로그 닫기
                    // 다음 화면(사용자 정보 수집의 첫 단계)으로 이동
                    // "consent"가 다음 페이지가 맞다면 그대로 두고, 만약 TodayDoScreen이 첫 단계라면 TodayDoScreen으로 이동하도록 수정
                    navController.navigate("TodayDo") { // 예시: today_do_screen으로 이동
                        popUpTo("signup") { inclusive = true } // 뒤로가기 시 signup으로 안 돌아가게
                    }
                }) {
                    Text("확인")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("나이") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("성별") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("전화번호") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // TODO: 입력값 유효성 검사 로직 추가 필요
                showDialog = true // 유효성 검사 통과 시 다이얼로그 표시
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("회원가입")
        }
    }
}