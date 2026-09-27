package com.example.data.model

data class ProblemNeed(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val descriptionBn: String,
    val keywords: List<String>,
    val recommendedCategory: InstitutionCategory,
    val adviceBn: String,
    val targetInstitutionKeyword: String
)
