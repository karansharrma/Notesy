package com.example.notessaver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notessaver.databinding.FragmentLoginBinding
import com.example.notessaver.models.UserRequest
import com.example.notessaver.models.UserResponse
import com.example.notessaver.utils.NetworkResult
import com.example.notessaver.utils.TokenManager
import com.example.notessaver.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()


    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogin.setOnClickListener {
            val validatResult = validatUser()
            if (validatResult.first) {
                authViewModel.loginUser(getUserRequest())
            } else {
                binding.txtError.text = validatResult.second
            }
        }
        binding.txtSignUp.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }


    private fun validatUser(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(
            userRequest.email,
            userRequest.password,
            userRequest.username,
            true
        )
    }


    private fun getUserRequest(): UserRequest {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(email, password, " ")
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(
            viewLifecycleOwner,
            Observer { it: NetworkResult<UserResponse> ->
                when (it) {
                    is NetworkResult.Success -> {
                   tokenManager.saveToken(it.data!!.token)
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }

                    is NetworkResult.Error -> {
                        binding.txtError.text = it.message
                    }

                    is NetworkResult.Loading -> {
                        binding.txtSignUp.isClickable=false
                        binding.btnLogin.isClickable=false
                        binding.lottieLoader.isVisible = true
                        binding.lottieLoader.playAnimation()
                    }
                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}