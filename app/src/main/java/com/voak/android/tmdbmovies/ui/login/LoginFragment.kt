package com.voak.android.tmdbmovies.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentLoginBinding
import com.voak.android.tmdbmovies.ui.bottomnavigation.BottomNavigationActivity
import dagger.android.support.DaggerFragment
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

        val loginViewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginFragmentViewModel::class.java)

        binding = FragmentLoginBinding.bind(view).apply {
            viewModel = loginViewModel
            lifecycleOwner = this@LoginFragment
        }

        binding?.loginButton?.setOnClickListener {
            binding?.loginErrorTextView?.visibility = View.GONE
            val username: String = binding?.loginEditText?.text.toString()
            val password: String = binding?.passwordEditText?.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                binding?.viewModel?.auth(username, password)
            } else {
                binding?.loginErrorTextView?.text = getString(R.string.need_login_and_password_error)
                binding?.loginErrorTextView?.visibility = View.VISIBLE
            }
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.viewModel?.getLoadingLiveData()?.observe(viewLifecycleOwner, Observer {
            binding?.loginButton?.visibility = if (it) View.GONE else View.VISIBLE
            binding?.loginProgress?.visibility = if (it) View.VISIBLE else View.GONE
        })

        binding?.viewModel?.getLoginErrorLiveData()?.observe(viewLifecycleOwner, Observer {
            binding?.loginErrorTextView?.text = when (it) {
                "timeout" -> getString(R.string.timeout_error)
                "HTTP 401 " -> getString(R.string.wrong_login_or_password_error)
                "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname" ->
                    getString(R.string.connection_error)
                else -> ""
            }
            binding?.loginErrorTextView?.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
        })

        binding?.viewModel?.getSessionIdLiveData()?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                startActivity(Intent(context, BottomNavigationActivity::class.java))
            }
        })
    }
}