package com.example.testsapp.ui.composables.pages.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Test
import com.example.testsapp.room.TestRoom
import com.example.testsapp.singletone.SingletoneFirebase
import com.example.testsapp.ui.composables.functions.custom.FAB
import com.example.testsapp.ui.composables.functions.custom.RatingBar
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.MainViewModel
import com.example.testsapp.viewmodels.TestViewModel

@Composable
fun TestInfoPage(navController: NavHostController, mainViewModel: MainViewModel, testViewModel: TestViewModel, item_id: String?){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        val test = remember {
            mutableStateOf(Test())
        }

        if (item_id != null) {
            SingletoneFirebase.instance.database.getReference("Tests").child(item_id).get().addOnSuccessListener {
                test.value = it.getValue(Test::class.java) as Test
            }
        }

        when(test.value.name){
            "" ->{
                CircularProgressIndicator(color = colorResource(id = R.color.main_orange))
            }
            else ->{
                val author = remember {
                    if (test.value.author_login == "")
                        mutableStateOf("Без автора")
                    else
                        mutableStateOf(test.value.author_login)
                }
                val results = mainViewModel.currentUser.value.results
                var score = -1
                for (result in results){
                    if (result.test_uid == item_id){
                        score = result.score
                    }
                }
                val result = remember {
                    if (score != -1){
                        mutableStateOf(score.toString())
                    } else{
                        mutableStateOf("Не пройдено")
                    }
                }
                Column(modifier = Modifier.align(Alignment.TopCenter)) {
                    Row{
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.13f)
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Box(contentAlignment = Alignment.CenterStart,
                                    modifier = Modifier.fillMaxWidth(0.14f)){
                                    FAB({
                                        navController.popBackStack()
                                    }, iconResourceId = R.drawable.arrow_left)
                                }
                                Spacer(modifier = Modifier.size(4.dp))
                                Box(contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)){
                                    Text(
                                        "Информация о тесте",
                                        fontSize = 20.sp,
                                        style = MaterialTheme.typography.h1
                                    )
                                }
                                Box(contentAlignment = Alignment.CenterEnd,
                                    modifier = Modifier.fillMaxWidth()){
                                    FAB(onClick = {
                                        insertTestToLocalDB(context, test.value, testViewModel)
                                    }, iconResourceId = R.drawable.download)
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .padding(horizontal = 16.dp)) {
                        Text(text = test.value.name,
                            fontSize = 40.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(text = test.value.description,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 8.dp),
                            color = colorResource(id = R.color.light_gray)
                        )
                        Row {
                            Text(text = "Количество вопросов: ",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(text = test.value.question_count.toString(),
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(top = 50.dp)) {
                            Text(text = "Лучший результат: ",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.h1
                            )
                            Text(text = result.value,
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
                Box(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)){
                    Row(modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxHeight(0.35f)
                        .fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Рейтинг: ",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        RatingBar(rating = test.value.rating)
                    }
                    Row(modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth(0.5f),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                        Text(text = "Автор: ",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(text = author.value,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Button(onClick = { navController.navigate("PlayTestPage/$item_id") },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.main_orange), contentColor = Color.White),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)) {
                        Text("Пройти тест",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }

    }
}

fun insertTestToLocalDB(context: Context, test: Test, testViewModel: TestViewModel) {
    testViewModel.addTest(test)
    Toast.makeText(context, "Тест успешно сохранен!", Toast.LENGTH_SHORT).show()
}
