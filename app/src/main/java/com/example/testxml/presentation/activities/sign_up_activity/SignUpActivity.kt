package com.example.testxml.presentation.activities.sign_up_activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testxml.R
import com.example.testxml.presentation.activities.main_activity.MainActivity
import com.example.testxml.presentation.activities.sign_up_activity.util.Month
import com.example.testxml.presentation.activities.sign_up_activity.util.Sex
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class SignUpActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        val viewModel: SignUpViewModel by viewModels()

        val bottomsheet = layoutInflater.inflate(R.layout.bottom_sheet_sign_up, null)

        viewModel.state.observe(this){state->
            if (state.isSuccess){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        val date = bottomsheet.findViewById<EditText>(R.id.date)
        date.setShowSoftInputOnFocus(false)
        val login = bottomsheet.findViewById<EditText>(R.id.login)
        val email = bottomsheet.findViewById<EditText>(R.id.email)
        val name = bottomsheet.findViewById<EditText>(R.id.name)
        val password = bottomsheet.findViewById<EditText>(R.id.password)
        val confirmPassword = bottomsheet.findViewById<EditText>(R.id.passwordRepeat)

        val registerButton = bottomsheet.findViewById<Button>(R.id.btnReg)

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
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        updateButtonState()

        val manState = bottomsheet.findViewById<Button>(R.id.man)
        val womanState = bottomsheet.findViewById<Button>(R.id.woman)

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
            .setPositiveButton("Понятно"){dialog, which ->
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

        val dialog = BottomSheetDialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(bottomsheet)
        dialog.show()
    }
}