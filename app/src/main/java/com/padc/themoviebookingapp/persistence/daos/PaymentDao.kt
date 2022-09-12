package com.padc.themoviebookingapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padc.themoviebookingapp.data.vos.PaymentVO

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaymentMethods(paymentMethods: List<PaymentVO>)

    @Query("SELECT * FROM paymentMethods")
    fun getPaymentMethods(): List<PaymentVO>

    @Query("DELETE FROM paymentMethods")
    fun deletePaymentMethods()
}