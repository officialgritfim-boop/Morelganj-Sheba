package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MorelganjGreenPrimary
import com.example.ui.theme.RedEmergency
import com.example.ui.util.IntentUtils

data class EmergencyContact(
    val titleBn: String,
    val subtitleBn: String,
    val phone: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun EmergencyScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val emergencyContacts = listOf(
        EmergencyContact(
            titleBn = "জাতীয় জরুরি সেবা (৯৯৯)",
            subtitleBn = "পুলিশ, ফায়ার সার্ভিস ও অ্যাম্বুলেন্স (টোল ফ্রি)",
            phone = "999",
            icon = Icons.Default.Warning,
            color = RedEmergency
        ),
        EmergencyContact(
            titleBn = "মোরেলগঞ্জ থানা (ডিউটি অফিসার)",
            subtitleBn = "আইন-শৃঙ্খলা ও তাত্ক্ষণিক পুলিশ সহায়তা",
            phone = "01713-374164",
            icon = Icons.Default.LocalPolice,
            color = Color(0xFF1E3A8A)
        ),
        EmergencyContact(
            titleBn = "মোরেলগঞ্জ ফায়ার সার্ভিস স্টেশন",
            subtitleBn = "অগ্নিনির্বাপণ ও উদ্ধার কার্যক্রম (২৪ ঘণ্টা)",
            phone = "01730-336712",
            icon = Icons.Default.LocalFireDepartment,
            color = Color(0xFFEA580C)
        ),
        EmergencyContact(
            titleBn = "উপজেলা স্বাস্থ্য কমপ্লেক্স জরুরি বিভাগ",
            subtitleBn = "জরুরি রোগী ভর্তি ও ডাক্তার সহায়তা",
            phone = "01730-324561",
            icon = Icons.Default.LocalHospital,
            color = Color(0xFF0284C7)
        ),
        EmergencyContact(
            titleBn = "মোরেলগঞ্জ সেন্ট্রাল এয়ারকন্ডিশনড অ্যাম্বুলেন্স",
            subtitleBn = "২৪ ঘণ্টা খুলনা ও ঢাকায় রোগী পরিবহন",
            phone = "01712-998877",
            icon = Icons.Default.LocalHospital,
            color = Color(0xFF059669)
        ),
        EmergencyContact(
            titleBn = "পল্লী বিদ্যুৎ অভিযোগ কেন্দ্র (মোরেলগঞ্জ)",
            subtitleBn = "বিদ্যুৎ বিপর্যয়, তার ছেঁড়া ও জরুরি লাইন মেরামত",
            phone = "01769-402844",
            icon = Icons.Default.ElectricBolt,
            color = Color(0xFFD97706)
        ),
        EmergencyContact(
            titleBn = "নারী ও শিশু নির্যাতন প্রতিরোধ হেল্পলাইন",
            subtitleBn = "জাতীয় নারী ও শিশু সেল (টোল ফ্রি)",
            phone = "109",
            icon = Icons.Default.PhoneInTalk,
            color = Color(0xFF9333EA)
        ),
        EmergencyContact(
            titleBn = "সরকারি তথ্য ও সেবা (৩৩৩)",
            subtitleBn = "সরকারি সকল নাগরিক সেবা ও অফিসারদের তথ্য",
            phone = "333",
            icon = Icons.Default.PhoneInTalk,
            color = MorelganjGreenPrimary
        )
    )

    LazyColumn(
        modifier = modifier.fillMaxSize().testTag("emergency_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = RedEmergency,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "জরুরি যোগাযোগ তালিকা",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = "যেকোনো আকস্মিক বিপদ, দুর্ঘটনা বা দুর্যোগে সরাসরি মোরেলগঞ্জের স্থানীয় হেল্পলাইনে দ্রুত এক ক্লিকেই কল দিন।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        items(emergencyContacts) { contact ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = contact.color.copy(alpha = 0.12f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = contact.icon,
                                contentDescription = null,
                                tint = contact.color,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = contact.titleBn,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = contact.subtitleBn,
                                fontSize = 11.5.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = contact.phone,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = contact.color
                            )
                        }
                    }

                    Button(
                        onClick = { IntentUtils.dialPhoneNumber(context, contact.phone) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = contact.color,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "কল", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
