package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class InstitutionCategory(val titleBn: String, val titleEn: String) {
    ALL("সব প্রতিষ্ঠান", "All Types"),
    HOSPITAL("হাসপাতাল ও স্বাস্থ্য", "Hospitals & Health"),
    GOVT_OFFICE("সরকারি অফিস", "Govt Offices"),
    MADRASAH("মাদ্রাসা", "Madrasahs"),
    SCHOOL_COLLEGE("স্কুল ও কলেজ", "Schools & Colleges"),
    MOSQUE("মসজিদ ও ধর্মীয়", "Mosques & Religious"),
    BANK("ব্যাংক ও আর্থিক", "Banks & Financial"),
    EMERGENCY("জরুরি সেবা", "Emergency Services")
}

@Entity(tableName = "institutions")
data class Institution(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameBn: String,
    val nameEn: String,
    val category: String, // Category name string from InstitutionCategory
    val division: String = "খুলনা বিভাগ (Khulna)",
    val district: String = "বাগেরহাট জেলা (Bagerhat)",
    val upazila: String = "মোরেলগঞ্জ (Morrelganj)",
    val unionName: String, // e.g., মোরেলগঞ্জ পৌরসভা, তেলিগাতী, পঞ্চকরণ, হোগলাপাশা
    val address: String,
    val googleMapsUrl: String,
    val phone: String,
    val alternatePhone: String = "",
    val email: String = "",
    val website: String = "",
    val headPersonName: String, // যেমন: ডা. কামাল হোসেন / উপজেলা নির্বাহী কর্মকর্তা
    val headPersonTitle: String, // যেমন: উপজেলা স্বাস্থ্য ও প.প. কর্মকর্তা / অফিসার ইনচার্জ
    val staffCountInfo: String, // যেমন: ১২ জন ডাক্তার, ২৫ জন নার্স / ৩৫ জন শিক্ষক
    val visitingHours: String = "সকাল ৯:০০ - বিকাল ৫:০০",
    val servicesAndProblems: String, // keywords: "মাজা ব্যথা, হৃদরোগ বিশেষজ্ঞ, ভর্তি তথ্য, নিয়োগ বিজ্ঞপ্তি, সিজার, নামজারি"
    val description: String,
    val photoUri: String? = null,
    val documentName: String? = null, // e.g., "সিটিজেন চার্টার.pdf" or "ডাক্তারদের শিডিউল ও ভিজিটিং ফি.pdf"
    val isUserAdded: Boolean = false,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
