package com.padc.themoviebookingapp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.delegates.ConfirmButtonDelegate
import com.padc.themoviebookingapp.delegates.LoginOrRegisterDelegate
import com.padc.themoviebookingapp.utils.REGISTER_STATE
import com.padc.themoviebookingapp.viewpods.SignInViewPod
import kotlinx.android.synthetic.main.fragment_login.vPodLogInButtons
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), ConfirmButtonDelegate {

    private lateinit var mDelegate: LoginOrRegisterDelegate
    private lateinit var vPodLoginButton: SignInViewPod
    private var name: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var password: String? = null

    override fun onAttach(context: Context) {
        mDelegate = context as LoginOrRegisterDelegate
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vPodLoginButton = vPodLogInButtons as SignInViewPod
        vPodLoginButton.setDelegate(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onTapConfirm() {
        email = edtTextEmailSignIn.text.toString()
        name = edtTextName.text.toString()
        phone = edtTextPhoneNumber.text.toString()
        password = edtTextPasswordSignIn.text.toString()

        mDelegate.loginOrRegister(name = name?: "", email = email?: "", phone = phone?: "", password = password?: "", REGISTER_STATE)
    }
}