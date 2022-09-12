package com.padc.themoviebookingapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.padc.themoviebookingapp.fragments.LoginFragment
import com.padc.themoviebookingapp.fragments.SignInFragment

class AccountSetUpViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LoginFragment()
            else -> SignInFragment()
        }
    }
}