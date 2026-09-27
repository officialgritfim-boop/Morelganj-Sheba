package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.data.model.InstitutionCategory
import com.example.ui.theme.MorelganjGreenPrimary
import com.example.ui.viewmodel.MorelganjViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInstitutionScreen(
    viewModel: MorelganjViewModel,
    onInstitutionAdded: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formState by viewModel.addFormState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var unionDropdownExpanded by remember { mutableStateOf(false) }

    // Modern Zero-Permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateAddForm { s -> s.copy(photoUri = uri.toString()) }
            Toast.makeText(context, "ছবি সফলভাবে নির্বাচন করা হয়েছে", Toast.LENGTH_SHORT).show()
        }
    }

    val categories = listOf(
        InstitutionCategory.HOSPITAL,
        InstitutionCategory.GOVT_OFFICE,
        InstitutionCategory.MADRASAH,
        InstitutionCategory.SCHOOL_COLLEGE,
        InstitutionCategory.MOSQUE,
        InstitutionCategory.BANK,
        InstitutionCategory.EMERGENCY
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .testTag("add_institution_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MorelganjGreenPrimary,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "নতুন প্রতিষ্ঠান আপলোড ও নিবন্ধন",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "মোরেলগঞ্জের হাসপাতাল, স্কুল, মাদ্রাসা, সরকারি অফিস বা প্রতিষ্ঠানের ছবি, ডকুমেন্ট ও বিস্তারিত আপলোড করুন।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Error message banner
        if (formState.errorMessage != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formState.errorMessage!!,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Success message banner
        if (formState.successMessage != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MorelganjGreenPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formState.successMessage!!,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Institution Name (Bangla)
        OutlinedTextField(
            value = formState.nameBn,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(nameBn = it) } },
            label = { Text("প্রতিষ্ঠানের নাম (বাংলায়) *") },
            placeholder = { Text("উদা: মোরেলগঞ্জ সেন্ট্রাল ডায়াগনস্টিক / আলিয়া মাদ্রাসা") },
            modifier = Modifier.fillMaxWidth().testTag("input_name_bn"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        // Institution Name (English)
        OutlinedTextField(
            value = formState.nameEn,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(nameEn = it) } },
            label = { Text("প্রতিষ্ঠানের নাম (ইংরেজিতে - ঐচ্ছিক)") },
            placeholder = { Text("e.g. Morrelganj Central Hospital") },
            modifier = Modifier.fillMaxWidth().testTag("input_name_en"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        // Category dropdown
        ExposedDropdownMenuBox(
            expanded = categoryDropdownExpanded,
            onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded }
        ) {
            OutlinedTextField(
                value = formState.category.titleBn,
                onValueChange = {},
                readOnly = true,
                label = { Text("ক্যাটাগরি বা প্রতিষ্ঠানের ধরন *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor().testTag("category_dropdown"),
                shape = RoundedCornerShape(10.dp)
            )
            ExposedDropdownMenu(
                expanded = categoryDropdownExpanded,
                onDismissRequest = { categoryDropdownExpanded = false }
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat.titleBn) },
                        onClick = {
                            viewModel.updateAddForm { s -> s.copy(category = cat) }
                            categoryDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Union / Area dropdown
        ExposedDropdownMenuBox(
            expanded = unionDropdownExpanded,
            onExpandedChange = { unionDropdownExpanded = !unionDropdownExpanded }
        ) {
            OutlinedTextField(
                value = formState.unionName,
                onValueChange = {},
                readOnly = true,
                label = { Text("মোরেলগঞ্জের এলাকা / ইউনিয়ন *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unionDropdownExpanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor().testTag("union_dropdown"),
                shape = RoundedCornerShape(10.dp)
            )
            ExposedDropdownMenu(
                expanded = unionDropdownExpanded,
                onDismissRequest = { unionDropdownExpanded = false }
            ) {
                viewModel.allUnions.filter { it != "সকল এলাকা" }.forEach { union ->
                    DropdownMenuItem(
                        text = { Text(union) },
                        onClick = {
                            viewModel.updateAddForm { s -> s.copy(unionName = union) }
                            unionDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Full Address
        OutlinedTextField(
            value = formState.address,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(address = it) } },
            label = { Text("পূর্ণ ঠিকানা ও স্থান নির্দেশক *") },
            placeholder = { Text("উদা: বাজার রোড, পানগুছি ফেরিঘাট সংলগ্ন, মোরেলগঞ্জ") },
            modifier = Modifier.fillMaxWidth().testTag("input_address"),
            shape = RoundedCornerShape(10.dp)
        )

        // Google Maps Link
        OutlinedTextField(
            value = formState.googleMapsUrl,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(googleMapsUrl = it) } },
            label = { Text("গুগল ম্যাপ লোকেশন লিংক (ঐচ্ছিক)") },
            placeholder = { Text("https://maps.app.goo.gl/... অথবা খালি রাখুন") },
            modifier = Modifier.fillMaxWidth().testTag("input_maps_url"),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        // Head person details ("কারা চালাচ্ছেন বা কে আছেন")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.headPersonName,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(headPersonName = it) } },
                label = { Text("দায়িত্বপ্রাপ্ত ব্যক্তির নাম") },
                placeholder = { Text("উদা: ডা. ফারুক আহমেদ") },
                modifier = Modifier.weight(1f).testTag("input_head_name"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = formState.headPersonTitle,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(headPersonTitle = it) } },
                label = { Text("পদবী") },
                placeholder = { Text("প্রধান চিকিৎসক / অধ্যক্ষ") },
                modifier = Modifier.weight(1f).testTag("input_head_title"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )
        }

        // Staff / Doctors count & Visiting Hours
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.staffCountInfo,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(staffCountInfo = it) } },
                label = { Text("মোট কর্মী / ডাক্তার") },
                placeholder = { Text("উদা: ৫ জন ডাক্তার, ১০ জন নার্স") },
                modifier = Modifier.weight(1f).testTag("input_staff_count"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = formState.visitingHours,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(visitingHours = it) } },
                label = { Text("খোলা থাকার সময়") },
                placeholder = { Text("২৪ ঘণ্টা / সকাল ৯টা-৫টা") },
                modifier = Modifier.weight(1f).testTag("input_visiting_hours"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )
        }

        // Phone Numbers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.phone,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(phone = it) } },
                label = { Text("মোবাইল নম্বর *") },
                placeholder = { Text("01712-345678") },
                modifier = Modifier.weight(1f).testTag("input_phone"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = formState.alternatePhone,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(alternatePhone = it) } },
                label = { Text("বিকল্প নম্বর") },
                placeholder = { Text("01911-...") },
                modifier = Modifier.weight(1f).testTag("input_alt_phone"),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )
        }

        // Email & Website
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.email,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(email = it) } },
                label = { Text("ইমেইল (ঐচ্ছিক)") },
                placeholder = { Text("info@institution.com") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = formState.website,
                onValueChange = { viewModel.updateAddForm { s -> s.copy(website = it) } },
                label = { Text("ওয়েবসাইট (ঐচ্ছিক)") },
                placeholder = { Text("https://...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Language, contentDescription = null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )
        }

        // Services & Solved Problems (Keywords)
        OutlinedTextField(
            value = formState.servicesAndProblems,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(servicesAndProblems = it) } },
            label = { Text("কী কী সেবা বা সমস্যার সমাধান পাওয়া যায় (কীওয়ার্ডস) *") },
            placeholder = { Text("উদা: হৃদরোগ বিশেষজ্ঞ, ভর্তি তথ্য, নিয়োগ বিজ্ঞপ্তি, মাজা ব্যথা, গর্ভবতী সেবা, সিজার, নামজারি") },
            modifier = Modifier.fillMaxWidth().testTag("input_services_problems"),
            shape = RoundedCornerShape(10.dp),
            supportingText = { Text("কমা (,) দিয়ে আলাদা করুন যাতে মানুষ সার্চ করলে সহজে আপনার প্রতিষ্ঠান খুঁজে পায়") }
        )

        // Photo Upload Section
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "প্রতিষ্ঠানের ছবি আপলোড (Photo Upload)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (formState.photoUri != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = Uri.parse(formState.photoUri)),
                            contentDescription = "নির্বাচিত ছবি",
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        OutlinedButton(
                            onClick = { viewModel.updateAddForm { s -> s.copy(photoUri = null) } },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("ছবি মুছুন")
                        }
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("গ্যালারি থেকে প্রতিষ্ঠানের ছবি নির্বাচন করুন")
                    }
                }
            }
        }

        // Document Upload Section
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "সংশ্লিষ্ট ডকুমেন্ট / বিজ্ঞপ্তি আপলোড (Document Upload)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "যেমন: সিটিজেন চার্টার, ডাক্তারদের তালিকা, ভর্তি বিজ্ঞপ্তি, বা সার্কুলার ফাইল",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = formState.documentName ?: "",
                    onValueChange = { viewModel.updateAddForm { s -> s.copy(documentName = if (it.isBlank()) null else it) } },
                    placeholder = { Text("ডকুমেন্টের নাম বা ফাইল রেফারেন্স (উদা: সিটিজেন_চার্টার.pdf)") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Description, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }
        }

        // Description
        OutlinedTextField(
            value = formState.description,
            onValueChange = { viewModel.updateAddForm { s -> s.copy(description = it) } },
            label = { Text("প্রতিষ্ঠানের বিস্তারিত বিবরণ") },
            placeholder = { Text("আপনার প্রতিষ্ঠানের পরিচিতি, সুবিধাসমূহ ও অন্যান্য প্রয়োজনীয় তথ্য লিখুন...") },
            modifier = Modifier.fillMaxWidth().height(100.dp).testTag("input_description"),
            shape = RoundedCornerShape(10.dp),
            maxLines = 4
        )

        // Submit Button
        Button(
            onClick = {
                viewModel.submitNewInstitution {
                    Toast.makeText(context, "প্রতিষ্ঠান সফলভাবে যুক্ত হয়েছে!", Toast.LENGTH_LONG).show()
                    onInstitutionAdded()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_institution_btn"),
            colors = ButtonDefaults.buttonColors(
                containerColor = MorelganjGreenPrimary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = !formState.isSubmitting
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (formState.isSubmitting) "আপলোড হচ্ছে..." else "প্রতিষ্ঠান তথ্য আপলোড ও প্রকাশ করুন",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
