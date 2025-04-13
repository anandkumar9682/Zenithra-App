/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This is the DAO interface for accessing User data from the Room database.
 *              It provides methods for inserting, deleting, and querying user details.
 */

package com.asuni.zenithra.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asuni.zenithra.domain.UserDetailsEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(singInResponseResult: UserDetailsEntity)

    @Delete(entity = UserDetailsEntity::class)
    fun deleteSingInResponseResult(SingInResponseResult: UserDetailsEntity)

    @Query("SELECT * FROM user_details WHERE userId=:id")
    fun findSingInResponseResult(id: Int): UserDetailsEntity

    @Query("SELECT * FROM user_details LIMIT 1")
    fun getUser(): UserDetailsEntity

    @Query("SELECT * FROM user_details LIMIT 1")
    fun getUserLive(): LiveData<UserDetailsEntity>

    @Query("DELETE FROM user_details")
    fun deleteAllUsers()
}