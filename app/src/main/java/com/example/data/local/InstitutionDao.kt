package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Institution
import kotlinx.coroutines.flow.Flow

@Dao
interface InstitutionDao {
    @Query("SELECT * FROM institutions ORDER BY id ASC")
    fun getAllInstitutions(): Flow<List<Institution>>

    @Query("SELECT * FROM institutions WHERE category = :category ORDER BY id ASC")
    fun getInstitutionsByCategory(category: String): Flow<List<Institution>>

    @Query("SELECT * FROM institutions WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteInstitutions(): Flow<List<Institution>>

    @Query("""
        SELECT * FROM institutions 
        WHERE nameBn LIKE '%' || :query || '%' 
           OR nameEn LIKE '%' || :query || '%' 
           OR address LIKE '%' || :query || '%'
           OR unionName LIKE '%' || :query || '%'
           OR headPersonName LIKE '%' || :query || '%'
           OR servicesAndProblems LIKE '%' || :query || '%'
           OR description LIKE '%' || :query || '%'
        ORDER BY id ASC
    """)
    fun searchInstitutions(query: String): Flow<List<Institution>>

    @Query("SELECT * FROM institutions WHERE id = :id LIMIT 1")
    suspend fun getInstitutionById(id: Long): Institution?

    @Query("SELECT COUNT(*) FROM institutions")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstitution(institution: Institution): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(institutions: List<Institution>)

    @Update
    suspend fun updateInstitution(institution: Institution)

    @Delete
    suspend fun deleteInstitution(institution: Institution)

    @Query("UPDATE institutions SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)
}
