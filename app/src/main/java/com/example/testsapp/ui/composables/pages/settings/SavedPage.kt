package com.example.testsapp.ui.composables.pages.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.testsapp.R
import com.example.testsapp.models.Test
import com.example.testsapp.ui.composables.functions.cards.TestCards
import com.example.testsapp.ui.composables.functions.custom.Header
import com.example.testsapp.viewmodels.TestViewModel

@Composable
fun SavedPage(navController: NavHostController, testViewModel: TestViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Column() {
            Header(navController = navController, title = "Сохраненные тесты")
            ShowLazyList(navController = navController, viewModel = testViewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowLazyList(navController: NavHostController, viewModel: TestViewModel) {
    val testsList = viewModel.readAllData.observeAsState(listOf()).value

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(testsList){item ->
                TestCards(navController, item = item, local = true)
            }
        }
        if (testsList.isEmpty()){
            Text(text = "Здесь пока ничего нет",
                fontSize = 16.sp,
                style = MaterialTheme.typography.body2,
                color = colorResource(id = R.color.light_gray)
            )
        }
    }
}