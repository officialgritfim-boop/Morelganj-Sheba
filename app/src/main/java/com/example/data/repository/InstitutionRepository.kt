package com.example.data.repository

import com.example.data.local.DefaultMorelganjData
import com.example.data.local.InstitutionDao
import com.example.data.model.Institution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class InstitutionRepository(private val institutionDao: InstitutionDao) {

    val allInstitutions: Flow<List<Institution>> = institutionDao.getAllInstitutions()
    val favoriteInstitutions: Flow<List<Institution>> = institutionDao.getFavoriteInstitutions()

    fun getInstitutionsByCategory(category: String): Flow<List<Institution>> {
        return institutionDao.getInstitutionsByCategory(category)
    }

    fun searchInstitutions(query: String): Flow<List<Institution>> {
        return institutionDao.searchInstitutions(query)
    }

    suspend fun getInstitutionById(id: Long): Institution? {
        return withContext(Dispatchers.IO) {
            institutionDao.getInstitutionById(id)
        }
    }

    suspend fun insertInstitution(institution: Institution): Long {
        return withContext(Dispatchers.IO) {
            institutionDao.insertInstitution(institution)
        }
    }

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            institutionDao.updateFavorite(id, isFavorite)
        }
    }

    suspend fun deleteInstitution(institution: Institution) {
        withContext(Dispatchers.IO) {
            institutionDao.deleteInstitution(institution)
        }
    }

    suspend fun checkAndSeedIfNeeded() {
        withContext(Dispatchers.IO) {
            val count = institutionDao.getCount()
            if (count == 0) {
                institutionDao.insertAll(DefaultMorelganjData.initialInstitutions)
            }
        }
    }
}
