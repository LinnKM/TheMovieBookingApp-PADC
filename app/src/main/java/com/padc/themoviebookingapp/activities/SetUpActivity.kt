package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.adapters.AccountSetUpViewPagerAdapter
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.delegates.LoginOrRegisterDelegate
import com.padc.themoviebookingapp.utils.LOGIN_STATE
import kotlinx.android.synthetic.main.activity_set_up.*

class SetUpActivity : AppCompatActivity(), LoginOrRegisterDelegate {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SetUpActivity::class.java)
        }
    }

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl
    lateinit var mDifferentAccountSetUpAdapter: AccountSetUpViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up)

        setUpViewPager()
        setUpTabLayoutWithViewPager()
    }

    private fun setUpViewPager() {
        mDifferentAccountSetUpAdapter = AccountSetUpViewPagerAdapter(this)
        vpDifferentAccountSetUp.adapter = mDifferentAccountSetUpAdapter
    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(tabLayoutDifferentViewPager, vpDifferentAccountSetUp) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Login"
                }
                else -> tab.text = "Sign in"
            }
        }.attach()
    }

    override fun loginOrRegister(
        name: String,
        email: String,
        phone: String,
        password: String,
        state: String
    ) {
        when (state) {
            // Login User
            LOGIN_STATE -> mMovieBookingModel.loginUser(
                email = email,
                password = password,
                onSuccess = { isSuccess ->
                    navigateToHomeScreen(isSuccess)
                }
            )
            // Register User
            else -> mMovieBookingModel.registerUser(
                name = name,
                email = email,
                phone = phone,
                password = password,
                onSuccess = { isSuccess ->
                    navigateToHomeScreen(isSuccess)
                }
            )
        }
    }

    // send to home screen if loginOrRegister successfully
    private fun navigateToHomeScreen(isSuccess: Boolean) {
        if(isSuccess){
            startActivity(HomeScreenActivity.newIntent(this))
            finish()
        }
    }
}