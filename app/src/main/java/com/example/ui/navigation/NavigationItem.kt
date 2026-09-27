package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val titleBn: String, val icon: ImageVector) {
    HOME("হোম নির্দেশিকা", Icons.Default.Home),
    PROBLEM_SOLVER("সমস্যা ও সমাধান", Icons.Default.Lightbulb),
    ADD_INSTITUTION("প্রতিষ্ঠান আপলোড", Icons.Default.AddBusiness),
    EMERGENCY("জরুরি হটলাইন", Icons.Default.PhoneInTalk)
}
