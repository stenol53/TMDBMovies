package com.voak.android.tmdbmovies.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.ProgressIndicator
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentLoginBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val loginViewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(LoginFragmentViewModel::class.java)

        // set getString function to use it into viewModel
        loginViewModel.getString = { resId: Int -> getString(resId) }

        binding = FragmentLoginBinding.bind(view).apply {
            viewModel = loginViewModel
            lifecycleOwner = this@LoginFragment
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.viewModel?.loading?.observe(viewLifecycleOwner, Observer {
            binding?.loginButton?.visibility = if (it) View.GONE else View.VISIBLE
            binding?.loginProgress?.visibility = if (it) View.VISIBLE else View.GONE
        })

        binding?.viewModel?.loginError?.observe(viewLifecycleOwner, Observer {
            binding?.loginErrorTextView?.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
        })

        binding?.viewModel?.sessionId?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                findNavController().navigate(R.id.bottom_navigation_activity)
            }
        })
    }
}