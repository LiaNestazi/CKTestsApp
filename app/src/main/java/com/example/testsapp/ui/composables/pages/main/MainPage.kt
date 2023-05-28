package com.example.testsapp.ui.composables.pages.main

import android.app.Application
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.testsapp.ui.composables.functions.drawer.DrawerContentAuth
import com.example.testsapp.ui.navigation.SetupNavGraph
import com.example.testsapp.viewmodels.MainViewModel
import com.example.testsapp.viewmodels.TestViewModel
import com.example.testsapp.viewmodels.TestViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun MainPage(mainViewModel: MainViewModel){
    val context = LocalContext.current
    val testViewModel: TestViewModel = viewModel(
        factory = TestViewModelFactory(context.applicationContext as Application)
    )
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalDrawer(
        drawerContent = {
            DrawerContentAuth(itemClick = {
                when(it){
                    "Домашняя страница" ->{
                        navController.navigate("HomePage", navOptions = null)
                    }
                    "Мои группы" ->{
                        navController.navigate("GroupsPage", navOptions = null)
                    }
                    "Мои тесты" ->{
                        navController.navigate("TestsPage", navOptions = null)
                    }
                    "Настройки" ->{
                        navController.navigate("SettingsPage", navOptions = null)
                    }
                    "Список тестов" ->{
                        navController.navigate("TestsListPage", navOptions = null)
                    }
                    "Список пользователей" ->{
                        navController.navigate("UsersListPage", navOptions = null)
                    }
                }
                scope.launch {
                    drawerState.close()
                }
            }, navController, mainViewModel)
        },
        drawerState = drawerState
    ) {
        SetupNavGraph(navController = navController, scope = scope, drawerState = drawerState, mainViewModel, testViewModel)
    }
}
