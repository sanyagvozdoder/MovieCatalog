package com.example.testxml.presentation.activities.sign_up_activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testxml.R
import com.example.testxml.databinding.SignUpActivityBinding
import com.example.testxml.presentation.activities.main_activity.MainActivity
import com.example.testxml.presentation.activities.sign_up_activity.util.Month
import com.example.testxml.presentation.activities.sign_up_activity.util.Sex
import java.util.Calendar


class SignUpActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        val viewModel: SignUpViewModel by viewModels()

        val binding = SignUpActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val screenHeight = resources.displayMetrics.heightPixels

        val imageBack = binding.imageView3
        imageBack.layoutParams.height = minOf((screenHeight * 0.35).toInt(), imageBack.layoutParams.height)

        val backBtn = binding.BackButton
        backBtn.setOnClickListener{
            finish()
        }

        val date = binding.date
        date.showSoftInputOnFocus = false
        date.isFocusableInTouchMode = false
        date.isClickable = true
        date.isCursorVisible = false
        val login = binding.login
        val email = binding.email
        val name = binding.name
        val password = binding.password
        val confirmPassword = binding.passwordRepeat

        val registerButton = binding.btnReg

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val month = Month.values()[monthOfYear]
            val formattedDate = "$dayOfMonth $month $year"
            date.setText(formattedDate)
        }

        date.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        fun updateButtonState() {
            val isDateFilled = date.text.toString().isNotEmpty()
            val isLoginFilled = login.text.toString().isNotEmpty()
            val isEmailFilled = email.text.toString().isNotEmpty()
            val isNameFilled = name.text.toString().isNotEmpty()
            val isPasswordFilled = password.text.toString().isNotEmpty()
            val isConfirmPasswordFilled = confirmPassword.text.toString().isNotEmpty()

            if (isDateFilled && isLoginFilled && isEmailFilled && isNameFilled && isPasswordFilled && isConfirmPasswordFilled) {
                registerButton.isEnabled = true
                registerButton.isClickable = true
                registerButton.background = resources.getDrawable(R.drawable.orange_gradient)
                registerButton.setTextColor(resources.getColor(R.color.white))
            } else {
                registerButton.isEnabled = false
                registerButton.isClickable = false
                registerButton.background = resources.getDrawable(R.drawable.edit_shape)
                registerButton.setTextColor(resources.getColor(R.color.hint_text))
            }
        }

        login.addTextChangedListener(object : TextWatcher {
            @SuppressLint("ClickableViewAccessibility")
            override fun afterTextChanged(s: Editable?) {
                val clear = binding.clearLogin
                if(login.text.toString().isNotEmpty()){
                    clear.visibility = VISIBLE
                    clear.setOnClickListener {
                        login.setText("")
                    }
                }
                else{
                    clear.visibility = INVISIBLE
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        email.addTextChangedListener(object : TextWatcher {
            @SuppressLint("ClickableViewAccessibility")
            override fun afterTextChanged(s: Editable?) {
                val clear = binding.clearEmail
                if(email.text.toString().isNotEmpty()){
                    clear.visibility = VISIBLE
                    clear.setOnClickListener {
                        email.setText("")
                    }
                }
                else{
                    clear.visibility = INVISIBLE
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        name.addTextChangedListener(object : TextWatcher {
            @SuppressLint("ClickableViewAccessibility")
            override fun afterTextChanged(s: Editable?) {
                val clear = binding.clearName
                if(name.text.toString().isNotEmpty()){
                    clear.visibility = VISIBLE
                    clear.setOnClickListener {
                        name.setText("")
                    }
                }
                else{
                    clear.visibility = INVISIBLE
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(date.text.toString().isNotEmpty()){
                    date.compoundDrawableTintList = ColorStateList.valueOf(getColor(R.color.white))
                }
                else{
                    date.compoundDrawableTintList = ColorStateList.valueOf(getColor(R.color.hint_text))
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passIcon = binding.passIc

                if(password.text.toString().isNotEmpty()){
                    passIcon.visibility = VISIBLE

                    if(password.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                        passIcon.setImageDrawable(getDrawable(R.drawable.ic_show_wrap))
                    }

                    else if(password.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                        passIcon.setImageDrawable(getDrawable(R.drawable.ic_hide_wrap))
                    }

                    passIcon.setOnClickListener {
                        if(password.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                            passIcon.setImageDrawable(getDrawable(R.drawable.ic_show_wrap))
                            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        }
                        else if(password.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                            passIcon.setImageDrawable(getDrawable(R.drawable.ic_hide_wrap))
                            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                    }
                }
                else{
                    passIcon.visibility = INVISIBLE
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                val passIcon = binding.passIcRepeat

                if(confirmPassword.text.toString().isNotEmpty()){
                    passIcon.visibility = VISIBLE

                    if(confirmPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                        passIcon.setImageDrawable(getDrawable(R.drawable.ic_show_wrap))
                    }

                    else if(confirmPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                        passIcon.setImageDrawable(getDrawable(R.drawable.ic_hide_wrap))
                    }

                    passIcon.setOnClickListener {
                        if(confirmPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD){
                            passIcon.setImageDrawable(getDrawable(R.drawable.ic_show_wrap))
                            confirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        }
                        else if(confirmPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                            passIcon.setImageDrawable(getDrawable(R.drawable.ic_hide_wrap))
                            confirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                    }
                }
                else{
                    passIcon.visibility = INVISIBLE
                }

                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        updateButtonState()

        val manState = binding.man
        val womanState = binding.woman

        var sexState = Sex.MAN

        manState.setOnClickListener{
            manState.background = resources.getDrawable(R.drawable.orange_sex_gradient_left)
            womanState.background = resources.getDrawable(R.drawable.default_sex_shape_right)
            sexState = Sex.MAN
        }

        womanState.setOnClickListener{
            womanState.background = resources.getDrawable(R.drawable.orange_sex_gradient_right)
            manState.background = resources.getDrawable(R.drawable.default_sex_shape_left)
            sexState = Sex.WOMAN
        }

        val passwordBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        passwordBuilder
            .setMessage("Ваши пароли не совпадают")
            .setTitle("Некорректный пароль")
            .setPositiveButton("Понятно"){ _, _ ->
                confirmPassword.setText("")
            }
        val passDialog = passwordBuilder.create()

        val passwordLengthBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        passwordLengthBuilder
            .setMessage("Длина пароля должна превышать шесть")
            .setTitle("Некорректный пароль")
            .setPositiveButton("Понятно"){dialog, which ->
            }
        val passLengthDialog = passwordLengthBuilder.create()

        val emailBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        emailBuilder
            .setMessage("Ваша почта не является корректной, попробуйте еще раз")
            .setTitle("Некорректная почта")
            .setPositiveButton("Понятно"){dialog, which ->

            }
        val emailDialog = emailBuilder.create()

        registerButton.setOnClickListener{
            if (password.text.toString() != confirmPassword.text.toString()){
                passDialog.show()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
                emailDialog.show()
            }
            else if (password.text.toString().length < 6){
                passLengthDialog.show()
            }
            else{
                viewModel.signUp(
                    applicationContext,
                    login = login.text.toString(),
                    password = password.text.toString(),
                    birthDate = date.text.toString(),
                    email = email.text.toString(),
                    gender = sexState,
                    name = name.text.toString()
                )
            }
        }

        viewModel.state.observe(this){state->
            if (state.isSuccess){
                viewModel.addUser(login.text.toString())
                startActivity(Intent(this, MainActivity::class.java)
                    .putExtra(MainActivity.login, login.text.toString())
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            }
        }
    }
}