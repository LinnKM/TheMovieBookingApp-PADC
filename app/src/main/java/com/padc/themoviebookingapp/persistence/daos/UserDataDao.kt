package com.padc.themoviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.themoviebookingapp.data.vos.UserVO
import com.padc.themoviebookingapp.network.responses.RegisterUserResponse

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(userProfile: UserVO?)

    @Query("SELECT * FROM userProfile")
    fun getUserProfile(): UserVO?

    @Query("DELETE FROM userProfile")
    fun deleteUserProfile()
}