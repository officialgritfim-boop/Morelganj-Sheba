package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.navigation.Screen
import com.example.ui.screens.AddInstitutionScreen
import com.example.ui.screens.DetailBottomSheet
import com.example.ui.screens.EmergencyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProblemSolverScreen
import com.example.ui.theme.MorelganjGreenPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RedEmergency
import com.example.ui.util.IntentUtils
import com.example.ui.viewmodel.MorelganjViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MorelganjViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(viewModel: MorelganjViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    val selectedInstitution by viewModel.selectedInstitutionForDetail.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // BackHandler: Return to Home if on another tab
    if (currentScreen != Screen.HOME) {
        BackHandler {
            currentScreen = Screen.HOME
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.LocalHospital,
                                    contentDescription = null,
                                    tint = MorelganjGreenPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = when (currentScreen) {
                                Screen.HOME -> "মোরেলগঞ্জ সেবা"
                                Screen.PROBLEM_SOLVER -> "সমস্যা ও সমাধান"
                                Screen.ADD_INSTITUTION -> "প্রতিষ্ঠান আপলোড"
                                Screen.EMERGENCY -> "জরুরি হটলাইন"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    // Quick Call Emergency 999
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RedEmergency,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                IntentUtils.dialPhoneNumber(context, "999")
                            },
                            modifier = Modifier.size(36.dp).testTag("top_emergency_call_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneInTalk,
                                contentDescription = "জরুরি ৯৯৯",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MorelganjGreenPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                Screen.values().forEach { screen ->
                    val isSelected = currentScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.titleBn
                            )
                        },
                        label = {
                            Text(
                                text = screen.titleBn,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MorelganjGreenPrimary,
                            selectedTextColor = MorelganjGreenPrimary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onInstitutionClick = { inst -> viewModel.selectInstitutionForDetail(inst) },
                        onNavigateToProblemSolver = { currentScreen = Screen.PROBLEM_SOLVER },
                        onNavigateToAdd = { currentScreen = Screen.ADD_INSTITUTION }
                    )
                }
                Screen.PROBLEM_SOLVER -> {
                    ProblemSolverScreen(
                        viewModel = viewModel,
                        onInstitutionClick = { inst -> viewModel.selectInstitutionForDetail(inst) }
                    )
                }
                Screen.ADD_INSTITUTION -> {
                    AddInstitutionScreen(
                        viewModel = viewModel,
                        onInstitutionAdded = {
                            currentScreen = Screen.HOME
                        }
                    )
                }
                Screen.EMERGENCY -> {
                    EmergencyScreen()
                }
            }

            // Detailed Modal BottomSheet
            if (selectedInstitution != null) {
                DetailBottomSheet(
                    institution = selectedInstitution!!,
                    onDismiss = { viewModel.selectInstitutionForDetail(null) },
                    onToggleFavorite = { viewModel.toggleFavorite(selectedInstitution!!) }
                )
            }
        }
    }
}
