package com.example.testxml.presentation.activities.profile_screen

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testxml.R
import com.example.testxml.common.sharedprefs.getFromSharedPrefs
import com.example.testxml.databinding.ProfileFragmentBinding
import com.squareup.picasso.Picasso
import java.util.Calendar

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private lateinit var binding: ProfileFragmentBinding
    val viewModel:ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)

        getFromSharedPrefs(requireContext())?.let { viewModel.getProfile(it) }

        val greet = binding.greetText
        val nameText = binding.nameText
        val logoutButton = binding.logoutButton
        val infoTitle = binding.favoritesText

        val avatar = binding.avatar
        val login = binding.login
        val name = binding.name
        val email = binding.email
        val date = binding.date
        val man = binding.man
        val woman = binding.woman

        val paint = infoTitle.paint
        val width = paint.measureText(infoTitle.text.toString())
        val shader = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            intArrayOf(Color.parseColor("#FFDF2800"), Color.parseColor("#FFFF6633")),
            null,
            Shader.TileMode.CLAMP
        )
        infoTitle.paint.shader = shader

        val editText = EditText(context).apply {
            hint = "Вставьте ссылку на ваше изображение"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        editText.setHintTextColor(requireContext().getColor(R.color.hint_text))
        editText.setTextColor(requireContext().getColor(R.color.white))

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setTitle("Добавить изображения")
            .setView(editText)
            .setPositiveButton("Сохранить") { dialog, _ ->
                getFromSharedPrefs(requireContext())?.let { viewModel.updateProfile(it, editText.text.toString()) }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        avatar.setOnClickListener {
            dialog.show()
        }


        viewModel.profileState.observe(viewLifecycleOwner){state->
            if(state.isSuccess && state.value != null){
                if(state.value.avatarLink != null){
                    Picasso.get().load(state.value.avatarLink).into(avatar)
                }

                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                greet.text = when (hour) {
                    in 6..11 -> "Доброе утро,"
                    in 12..17 -> "Добрый день,"
                    in 18..23 -> "Добрый вечер,"
                    else -> "Доброй ночи,"
                }

                nameText.text = state.value.name

                login.setText(state.value.nickName)
                name.setText(state.value.name)
                email.setText(state.value.email)
                date.setText(state.value.birthDate)

                when(state.value.gender){
                    0 -> man.background = requireContext().getDrawable(R.drawable.orange_sex_gradient_left)
                    1 -> woman.background = requireContext().getDrawable(R.drawable.orange_sex_gradient_right)
                }
            }
        }

        viewModel.updateState.observe(viewLifecycleOwner){state->
            if(state.isSuccess){
                if(viewModel.updatedAvatarLink != null){
                    Log.d("penis", "picasso")
                    Picasso.get().load(viewModel.updatedAvatarLink).into(avatar)
                }

                Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show()
            }
            if(state.isErrorOccured){
                Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}