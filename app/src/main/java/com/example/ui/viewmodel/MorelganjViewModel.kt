package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.DefaultMorelganjData
import com.example.data.model.Institution
import com.example.data.model.InstitutionCategory
import com.example.data.model.ProblemNeed
import com.example.data.repository.InstitutionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FilterCriteria(
    val query: String = "",
    val category: InstitutionCategory = InstitutionCategory.ALL,
    val union: String = "সকল এলাকা",
    val onlyFavorites: Boolean = false,
    val activeProblem: ProblemNeed? = null
)

data class AddInstitutionFormState(
    val nameBn: String = "",
    val nameEn: String = "",
    val category: InstitutionCategory = InstitutionCategory.HOSPITAL,
    val unionName: String = "মোরেলগঞ্জ পৌরসভা",
    val address: String = "",
    val googleMapsUrl: String = "",
    val phone: String = "",
    val alternatePhone: String = "",
    val email: String = "",
    val website: String = "",
    val headPersonName: String = "",
    val headPersonTitle: String = "",
    val staffCountInfo: String = "",
    val visitingHours: String = "সকাল ৯:০০ - বিকাল ৫:০০",
    val servicesAndProblems: String = "",
    val description: String = "",
    val photoUri: String? = null,
    val documentName: String? = null,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class MorelganjViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InstitutionRepository

    private val _filterCriteria = MutableStateFlow(FilterCriteria())
    val filterCriteria: StateFlow<FilterCriteria> = _filterCriteria.asStateFlow()

    private val _selectedInstitutionForDetail = MutableStateFlow<Institution?>(null)
    val selectedInstitutionForDetail: StateFlow<Institution?> = _selectedInstitutionForDetail.asStateFlow()

    private val _addFormState = MutableStateFlow(AddInstitutionFormState())
    val addFormState: StateFlow<AddInstitutionFormState> = _addFormState.asStateFlow()

    val allProblemNeeds: List<ProblemNeed> = DefaultMorelganjData.problemNeeds
    val allUnions: List<String> = DefaultMorelganjData.unionsOfMorelganj

    init {
        val dao = AppDatabase.getDatabase(application, viewModelScope).institutionDao()
        repository = InstitutionRepository(dao)
        viewModelScope.launch {
            repository.checkAndSeedIfNeeded()
        }
    }

    val institutions: StateFlow<List<Institution>> = combine(
        repository.allInstitutions,
        _filterCriteria
    ) { list: List<Institution>, filter: FilterCriteria ->
        var result = list

        if (filter.onlyFavorites) {
            result = result.filter { it.isFavorite }
        }

        if (filter.category != InstitutionCategory.ALL) {
            result = result.filter { it.category == filter.category.name }
        }

        if (filter.union != "সকল এলাকা") {
            result = result.filter { it.unionName == filter.union }
        }

        if (filter.activeProblem != null) {
            val keyword = filter.activeProblem.targetInstitutionKeyword.lowercase()
            val problemMatches = result.filter { inst ->
                inst.nameBn.lowercase().contains(keyword) ||
                inst.servicesAndProblems.lowercase().contains(keyword) ||
                filter.activeProblem.keywords.any { k -> inst.servicesAndProblems.contains(k, ignoreCase = true) }
            }
            if (problemMatches.isNotEmpty()) {
                result = problemMatches
            }
        }

        if (filter.query.isNotBlank()) {
            val q = filter.query.trim().lowercase()
            result = result.filter { inst ->
                inst.nameBn.lowercase().contains(q) ||
                inst.nameEn.lowercase().contains(q) ||
                inst.address.lowercase().contains(q) ||
                inst.unionName.lowercase().contains(q) ||
                inst.division.lowercase().contains(q) ||
                inst.district.lowercase().contains(q) ||
                inst.upazila.lowercase().contains(q) ||
                inst.headPersonName.lowercase().contains(q) ||
                inst.headPersonTitle.lowercase().contains(q) ||
                inst.servicesAndProblems.lowercase().contains(q) ||
                inst.description.lowercase().contains(q)
            }
        }

        result
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(newQuery: String) {
        val matched = if (newQuery.length >= 2) {
            allProblemNeeds.find { problem ->
                problem.keywords.any { kw -> newQuery.contains(kw, ignoreCase = true) || kw.contains(newQuery, ignoreCase = true) }
            }
        } else {
            null
        }
        _filterCriteria.value = _filterCriteria.value.copy(
            query = newQuery,
            activeProblem = matched
        )
    }

    fun onCategorySelected(category: InstitutionCategory) {
        _filterCriteria.value = _filterCriteria.value.copy(category = category)
    }

    fun onUnionSelected(union: String) {
        _filterCriteria.value = _filterCriteria.value.copy(union = union)
    }

    fun toggleFavoritesFilter() {
        val current = _filterCriteria.value.onlyFavorites
        _filterCriteria.value = _filterCriteria.value.copy(onlyFavorites = !current)
    }

    fun selectProblemNeed(problemNeed: ProblemNeed) {
        _filterCriteria.value = _filterCriteria.value.copy(
            activeProblem = problemNeed,
            category = problemNeed.recommendedCategory,
            query = problemNeed.keywords.firstOrNull() ?: ""
        )
    }

    fun clearActiveProblem() {
        _filterCriteria.value = _filterCriteria.value.copy(
            activeProblem = null,
            query = ""
        )
    }

    fun selectInstitutionForDetail(institution: Institution?) {
        _selectedInstitutionForDetail.value = institution
    }

    fun toggleFavorite(institution: Institution) {
        viewModelScope.launch {
            repository.toggleFavorite(institution.id, !institution.isFavorite)
        }
    }

    fun updateAddForm(transform: (AddInstitutionFormState) -> AddInstitutionFormState) {
        _addFormState.value = transform(_addFormState.value)
    }

    fun submitNewInstitution(onSuccess: () -> Unit) {
        val state = _addFormState.value
        if (state.nameBn.isBlank()) {
            _addFormState.value = state.copy(errorMessage = "প্রতিষ্ঠানের বাংলা নাম লিখুন")
            return
        }
        if (state.phone.isBlank()) {
            _addFormState.value = state.copy(errorMessage = "যোগাযোগের ফোন নম্বর দিন")
            return
        }
        if (state.address.isBlank()) {
            _addFormState.value = state.copy(errorMessage = "প্রতিষ্ঠানের পূর্ণ ঠিকানা লিখুন")
            return
        }

        viewModelScope.launch {
            _addFormState.value = state.copy(isSubmitting = true, errorMessage = null)
            val newInstitution = Institution(
                nameBn = state.nameBn.trim(),
                nameEn = if (state.nameEn.isNotBlank()) state.nameEn.trim() else state.nameBn.trim(),
                category = state.category.name,
                unionName = state.unionName,
                address = state.address.trim(),
                googleMapsUrl = if (state.googleMapsUrl.isNotBlank()) state.googleMapsUrl.trim() else "https://maps.google.com/?q=${state.nameBn.trim()}+Morrelganj+Bagerhat",
                phone = state.phone.trim(),
                alternatePhone = state.alternatePhone.trim(),
                email = state.email.trim(),
                website = state.website.trim(),
                headPersonName = if (state.headPersonName.isNotBlank()) state.headPersonName.trim() else "দায়িত্বপ্রাপ্ত কর্মকর্তা",
                headPersonTitle = if (state.headPersonTitle.isNotBlank()) state.headPersonTitle.trim() else "পরিচালক / প্রধান",
                staffCountInfo = if (state.staffCountInfo.isNotBlank()) state.staffCountInfo.trim() else "কর্মকর্তা ও কর্মচারী কর্মরত",
                visitingHours = if (state.visitingHours.isNotBlank()) state.visitingHours.trim() else "সকাল ৯:০০ - বিকাল ৫:০০",
                servicesAndProblems = if (state.servicesAndProblems.isNotBlank()) state.servicesAndProblems.trim() else "সাধারণ নাগরিক সেবা",
                description = if (state.description.isNotBlank()) state.description.trim() else "মোরেলগঞ্জ উপজেলার একটি নিবন্ধিত প্রতিষ্ঠান।",
                photoUri = state.photoUri,
                documentName = state.documentName,
                isUserAdded = true
            )
            repository.insertInstitution(newInstitution)
            _addFormState.value = AddInstitutionFormState(
                successMessage = "আপনার প্রতিষ্ঠান সফলভাবে মোরেলগঞ্জ নির্দেশিকায় যুক্ত হয়েছে!"
            )
            onSuccess()
        }
    }

    fun resetAddForm() {
        _addFormState.value = AddInstitutionFormState()
    }
}
