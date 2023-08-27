package com.anshumemorial.skwebs

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.anshumemorial.skwebs.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var checkBoxTermsConditions: AppCompatCheckBox
    private lateinit var buttonShowTermsConditions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        show splash screen for 1 second
        Thread.sleep(1000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        =====================================================


//        binding input layouts
        nameLayout = binding.inputLayoutName
        emailLayout = binding.inputLayoutEmail
        passwordLayout = binding.inputLayoutPassword
        confirmPasswordLayout = binding.inputLayoutConfirmPassword
        checkBoxTermsConditions = binding.checkBoxTermsConditions
        buttonShowTermsConditions = binding.buttonShowTermsConditions

        //        applyFocusChangeListener for all layouts
        fun applyFocusChangeListener(layout: TextInputLayout) {
            layout.editText?.setOnFocusChangeListener { _, hasFocus ->
                val color = if (hasFocus) getColor(R.color.teal_700) else Color.GRAY
                layout.setStartIconTintList(ColorStateList.valueOf(color))
            }
        }

//        applied formula
        applyFocusChangeListener(nameLayout)
        applyFocusChangeListener(emailLayout)
        applyFocusChangeListener(passwordLayout)
        applyFocusChangeListener(confirmPasswordLayout)

//        click on submit button
        binding.buttonSubmit.setOnClickListener {
            if (isFormValid()) {
                clearHelpText()
                Snackbar.make(binding.root, "Submitted data!",Snackbar.LENGTH_LONG).show()
//                Toast.makeText(this, "Submitted data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearHelpText() {
        nameLayout.editText?.text = null
        emailLayout.editText?.text = null
        passwordLayout.editText?.text = null
        confirmPasswordLayout.editText?.text = null
        checkBoxTermsConditions.isChecked = false
    }

    private fun isFormValid(): Boolean {

        val name = nameLayout.editText?.text.toString().trim()
        val email = emailLayout.editText?.text.toString().trim()
        val password = passwordLayout.editText?.text.toString().trim()
        val confirmPassword = confirmPasswordLayout.editText?.text.toString().trim()

//        name
        if (name.isEmpty()) {
            nameLayout.helperText = "Name is required"
        } else if (name.length >= 30) {
            nameLayout.helperText = "Name is must be less than 30 characters"
        } else {
            nameLayout.helperText = null
        }

//        email
        if (email.isEmpty()) {
            emailLayout.helperText = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.helperText = "Invalid email"
        } else {
            emailLayout.helperText = null
        }

//        password
        if (password.isEmpty()) {
            this.passwordLayout.helperText = "Password is required"
        } else if (password.length < 8) {
            this.passwordLayout.helperText = "Password must be at least 8 characters!"
        } else if (!password.any { it.isDigit() }) {
            this.passwordLayout.helperText = "Must contain at least one digit!"
        } else if (!password.any { it.isLowerCase() }) {
            this.passwordLayout.helperText = "Must Contain 1 Lowercase Character"
        } else if (!password.any { it.isUpperCase() }) {
            this.passwordLayout.helperText = "Must Contain 1 Uppercase Character"
        } else if (!password.any { it in setOf('!', '@', '#', '$', '%', '^', '&', '+', '=') }) {
            this.passwordLayout.helperText =
                "Must contain 1 special character !@#$%^&+="
        } else {
            passwordLayout.helperText = null
        }

//        confirm password section
        if (confirmPassword.isEmpty()) {
            this.confirmPasswordLayout.helperText = "Confirm Password is required!"

        } else if (confirmPassword != password) {
            this.confirmPasswordLayout.helperText = "Confirm Password does not match!"

        } else {
            confirmPasswordLayout.helperText = null
        }

//        accept terms conditions
        if (!checkBoxTermsConditions.isChecked) {
            Snackbar.make(binding.formLayout, "Please accept terms and conditions",Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }
}