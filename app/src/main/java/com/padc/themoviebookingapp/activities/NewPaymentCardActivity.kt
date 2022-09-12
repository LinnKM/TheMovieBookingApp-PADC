package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.utils.SUCCESS_LOGIN_CODE
import kotlinx.android.synthetic.main.activity_new_payment_card.*
import kotlinx.android.synthetic.main.activity_payment_card.*

class NewPaymentCardActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_RESPONSE_CODE = "EXTRA_RESPONSE_CODE"

        fun newIntent(context: Context): Intent {
            return Intent(context, NewPaymentCardActivity::class.java)
        }
    }
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    private var mResponseCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_payment_card)

        setUpToolBar()
        setUpListeners()

//        edtCardNumber.addTextChangedListener(object: TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
    }

    private fun setUpListeners(){

        // Back Button Listener
        toolBarAddCardScreen.setNavigationOnClickListener {
            setResultOnBackPressed()
        }

        // Next Button Listener
        btnNext.setOnClickListener {
            createPaymentCard()
        }
    }

    private fun setUpToolBar(){
        setSupportActionBar(toolBarAddCardScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_left_black_24dp)
    }

    private fun createPaymentCard() {
        val cardNum = edtCardNumber.text.toString()
        val cardHolder = edtCardHolder.text.toString()
        val expirationDate = edtExpirationDate.text.toString()
        val cvc = edtCvc.text.toString()

        // Create PaymentCard
        mMovieBookingModel.createPaymentCard(
            cardNumber = cardNum,
            cardHolder = cardHolder,
            expirationDate = expirationDate,
            cvc = cvc,
            onSuccess = {
                mResponseCode = it
                if(mResponseCode == SUCCESS_LOGIN_CODE){
                    setResultOnBackPressed()
                }
            },
            onFailure = {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun setResultOnBackPressed(){
        val intent = newIntent(this)
        intent.putExtra(EXTRA_RESPONSE_CODE, mResponseCode)
        setResult(2, intent)
        finish()
    }

}