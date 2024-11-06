package com.example.testxml.presentation.activities.profile_screen

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.opengl.Visibility
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.testxml.R
import com.example.testxml.databinding.ProfileFragmentBinding
import com.example.testxml.presentation.activities.friends_activity.FriendsActivity
import com.example.testxml.presentation.activities.main_activity.MainViewModel
import com.example.testxml.presentation.activities.movie_details_screen.MovieDetailsActivity
import com.example.testxml.presentation.activities.welcome_activity.WelcomeActivity
import com.squareup.picasso.Picasso
import java.util.Calendar

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private lateinit var binding: ProfileFragmentBinding
    private val viewModel:ProfileViewModel by viewModels()
    private lateinit var userLogin:String
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.loginData.observe(viewLifecycleOwner) { value ->
            userLogin = value
            viewModel.getFriends(userLogin)
        }

        binding = ProfileFragmentBinding.bind(view)

        viewModel.getProfile()

        val scroll = binding.scrollView
        val friendsLabel = binding.friends

        friendsLabel.setOnClickListener {
            startActivity(
                Intent(requireContext(), FriendsActivity::class.java)
                .putExtra(FriendsActivity.login, userLogin))
        }

        val greet = binding.greetText
        val nameText = binding.nameText
        val logoutButton = binding.logoutButton
        val infoTitle = binding.favoritesText

        val avatar1 = binding.avatar1
        val avatar2 = binding.avatar2
        val avatar3 = binding.avatar3

        val avatarImage1 = binding.avatarImage1
        val avatarImage2 = binding.avatarImage2
        val avatarImage3 = binding.avatarImage3

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

        logoutButton.setOnClickListener {
            viewModel.deleteUser(userLogin)
            viewModel.logoutUser()
            startActivity(Intent(requireContext(),WelcomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

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
                viewModel.updateProfile(editText.text.toString())
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
                if(state.value?.avatarLink != null){
                    Picasso.get().load(state.value!!.avatarLink).into(avatar)
                }

                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                greet.text = when (hour) {
                    in 6..11 -> "Доброе утро,"
                    in 12..17 -> "Добрый день,"
                    in 18..23 -> "Добрый вечер,"
                    else -> "Доброй ночи,"
                }

                nameText.text = state.value!!.name

                login.setText(state.value!!.nickName)
                name.setText(state.value!!.name)
                email.setText(state.value!!.email)
                date.setText(state.value!!.birthDate)

                when(state.value!!.gender){
                    0 -> man.background = requireContext().getDrawable(R.drawable.orange_sex_gradient_left)
                    1 -> woman.background = requireContext().getDrawable(R.drawable.orange_sex_gradient_right)
                }
            }
        }

        viewModel.updateState.observe(viewLifecycleOwner){state->
            if(state.isSuccess){
                if(viewModel.updatedAvatarLink != null){
                    Picasso.get().load(viewModel.updatedAvatarLink).into(avatar)
                }

                Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show()
            }
            if(state.isErrorOccured){
                Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.friendsState.observe(viewLifecycleOwner){state->
            if(state.isSuccess){
                if(state.value.isNullOrEmpty()){
                }
                else if(state.value!!.size == 1){
                    avatar1.visibility = VISIBLE
                    if(!state.value!![0].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![0].avatarLink).into(avatarImage1)
                    }
                }
                else if (state.value!!.size == 2){
                    avatar1.visibility = VISIBLE
                    if(!state.value!![0].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![0].avatarLink).into(avatarImage1)
                    }
                    avatar2.visibility = VISIBLE
                    if(!state.value!![1].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![1].avatarLink).into(avatarImage2)
                    }
                }
                else{
                    avatar1.visibility = VISIBLE
                    if(!state.value!![0].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![0].avatarLink).into(avatarImage1)
                    }
                    avatar2.visibility = VISIBLE
                    if(!state.value!![1].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![1].avatarLink).into(avatarImage2)
                    }
                    avatar3.visibility = VISIBLE
                    if(!state.value!![2].avatarLink.isNullOrEmpty()){
                        Picasso.get().load(state.value!![2].avatarLink).into(avatarImage3)
                    }
                }
            }
        }
    }
}