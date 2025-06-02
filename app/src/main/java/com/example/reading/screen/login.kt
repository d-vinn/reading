package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val cute_font = FontFamily(Font(R.font.cute))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Log-in", fontSize = 45.sp, style = TextStyle(fontFamily = cute_font))

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.width(280.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.light_green),
                unfocusedBorderColor = Color.Gray,
                textColor = colorResource(id = R.color.light_green),         // 입력 텍스트 색상
                cursorColor = colorResource(id = R.color.light_green),       // 커서 색상
                focusedLabelColor = colorResource(id = R.color.light_green), // 포커스된 라벨 색상
                unfocusedLabelColor = Color.Gray                             // 비포커스 라벨 색상
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.width(280.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
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
                navController.navigate("TodayDo")
            },
            modifier = Modifier
                .width(130.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.light_green), // 연두색
                contentColor = Color.White // 텍스트 컬러
            )
        ) {
            Text("로그인")
        }
    }
}
