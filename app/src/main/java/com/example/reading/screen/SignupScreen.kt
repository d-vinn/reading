package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트
import java.util.UUID // UUID 생성을 위해 추가
import android.util.Log // <-- 이 임포트를 추가
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun SignupScreen(navController: NavController, viewModel: UserRecommendationProfileViewModel) { // <--- 이 줄이 핵심 변경
    val context = LocalContext.current
    val cute_font = FontFamily(Font(R.font.cute))

    // 이 줄은 삭제하거나 주석 처리해야 합니다.
    // val viewModel: UserRecommendationProfileProfileViewModel = viewModel()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* ... */ },
            title = { Text("회원가입 완료") },
            text = { Text("회원가입이 완료되었습니다!") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setUserName(name)
                    viewModel.setAgeAndGender(age, gender)

                    if (viewModel.userId.value.isNullOrBlank()) {
                        viewModel.setUserId(UUID.randomUUID().toString())
                        Log.d("SignupScreen", "새로운 userId 생성 및 저장: ${viewModel.userId.value}")
                    } else {
                        Log.d("SignupScreen", "기존 userId: ${viewModel.userId.value}")
                    }

                    showDialog = false
                    navController.navigate("TodayDo") {
                        popUpTo("signup") { inclusive = true }
                    }
                }) {
                    Text("확인",color = colorResource(id = R.color.light_green))
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
        androidx.compose.material3.Text(
            text = "Sign-up",
            fontSize = 45.sp,
            style = TextStyle(fontFamily = cute_font)
        )

        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = name, onValueChange = { name = it }, label = { Text("이름") }, modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = age, onValueChange = { age = it }, label = { Text("나이") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.light_green),
            unfocusedBorderColor = Color.Gray,
            textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
            cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
            focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
            unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
        )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = gender, onValueChange = { gender = it }, label = { Text("성별") }, modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp),colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = phone, onValueChange = { phone = it }, label = { Text("전화번호") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp),colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it }, label = { Text("이메일") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp),colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.width(280.dp),
            shape = RoundedCornerShape(24.dp),colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.width(160.dp).height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF9EDC9A), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("회원가입")
        }
    }
}