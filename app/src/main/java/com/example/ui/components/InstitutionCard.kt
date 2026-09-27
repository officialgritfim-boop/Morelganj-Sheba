package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Institution
import com.example.data.model.InstitutionCategory
import com.example.ui.theme.AmberGovt
import com.example.ui.theme.BlueHospital
import com.example.ui.theme.GreenMosque
import com.example.ui.theme.MorelganjGreenPrimary
import com.example.ui.theme.PurpleEducation
import com.example.ui.theme.RedEmergency
import com.example.ui.util.IntentUtils

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InstitutionCard(
    institution: Institution,
    searchHighlight: String = "",
    onCardClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val categoryColor = when (institution.category) {
        InstitutionCategory.HOSPITAL.name -> BlueHospital
        InstitutionCategory.GOVT_OFFICE.name -> AmberGovt
        InstitutionCategory.MADRASAH.name -> GreenMosque
        InstitutionCategory.SCHOOL_COLLEGE.name -> PurpleEducation
        InstitutionCategory.MOSQUE.name -> GreenMosque
        InstitutionCategory.EMERGENCY.name -> RedEmergency
        InstitutionCategory.BANK.name -> Color(0xFF1E3A8A)
        else -> MorelganjGreenPrimary
    }

    val categoryIcon = when (institution.category) {
        InstitutionCategory.HOSPITAL.name -> Icons.Default.LocalHospital
        InstitutionCategory.GOVT_OFFICE.name -> Icons.Default.AccountBalance
        InstitutionCategory.MADRASAH.name -> Icons.Default.Mosque
        InstitutionCategory.SCHOOL_COLLEGE.name -> Icons.Default.School
        InstitutionCategory.MOSQUE.name -> Icons.Default.Mosque
        InstitutionCategory.EMERGENCY.name -> Icons.Default.Warning
        InstitutionCategory.BANK.name -> Icons.Default.AccountBalance
        else -> Icons.Default.LocationOn
    }

    val categoryLabelBn = try {
        InstitutionCategory.valueOf(institution.category).titleBn
    } catch (e: Exception) {
        "প্রতিষ্ঠান"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("institution_card_${institution.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Category Badge + Area Tag + Favorite Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = categoryColor.copy(alpha = 0.14f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = categoryIcon,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = categoryColor
                            )
                            Text(
                                text = categoryLabelBn,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = categoryColor
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = institution.unionName,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                        )
                    }

                    if (institution.isUserAdded) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Text(
                                text = "নাগরিক যুক্ত",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(36.dp).testTag("fav_button_${institution.id}")
                ) {
                    Icon(
                        imageVector = if (institution.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "পছন্দের তালিকায় রাখুন",
                        tint = if (institution.isFavorite) Color.Red else MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Institution Name & English Subtitle
            Text(
                text = institution.nameBn,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    lineHeight = 22.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (institution.nameEn.isNotBlank()) {
                Text(
                    text = institution.nameEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location Address
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp).padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = institution.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Key Personnel / Doctor In-charge Box ("কারা চালাচ্ছেন / কে আছেন")
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            modifier = Modifier.size(28.dp)
                        ) {}
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = institution.headPersonName,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${institution.headPersonTitle} • ${institution.staffCountInfo}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Solved problems / keywords tags
            if (institution.servicesAndProblems.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                val tags = institution.servicesAndProblems.split(",", "•", ";")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .take(4)

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tags.forEach { tag ->
                        val isMatched = searchHighlight.isNotBlank() &&
                                (tag.contains(searchHighlight, ignoreCase = true) || searchHighlight.contains(tag, ignoreCase = true))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isMatched) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = "✓ $tag",
                                fontSize = 10.5.sp,
                                fontWeight = if (isMatched) FontWeight.Bold else FontWeight.Normal,
                                color = if (isMatched) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons: Call & Google Maps Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Call Button
                Button(
                    onClick = {
                        IntentUtils.dialPhoneNumber(context, institution.phone)
                    },
                    modifier = Modifier.weight(1f).height(42.dp).testTag("call_btn_${institution.id}"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MorelganjGreenPrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "সরাসরি কল করুন",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "কল দিন", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                // Google Maps Button
                Button(
                    onClick = {
                        IntentUtils.openGoogleMaps(context, institution.googleMapsUrl, institution.nameBn)
                    },
                    modifier = Modifier.weight(1f).height(42.dp).testTag("maps_btn_${institution.id}"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Navigation,
                        contentDescription = "গুগল ম্যাপে যান",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "ম্যাপে যান", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                // Share Button
                OutlinedButton(
                    onClick = {
                        IntentUtils.shareInstitution(
                            context = context,
                            nameBn = institution.nameBn,
                            address = institution.address,
                            phone = institution.phone,
                            headPerson = "${institution.headPersonName} (${institution.headPersonTitle})",
                            mapsUrl = institution.googleMapsUrl
                        )
                    },
                    modifier = Modifier.height(42.dp).testTag("share_btn_${institution.id}"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "শেয়ার করুন",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
