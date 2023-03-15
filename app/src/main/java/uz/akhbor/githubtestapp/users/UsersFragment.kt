package uz.akhbor.githubtestapp.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.akhbor.githubtestapp.R
import uz.akhbor.githubtestapp.databinding.FragmentUsersBinding
import uz.akhbor.githubtestapp.details.DetailsFragment

/**
 * A [Fragment] to show the list of the github users
 */
@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = UsersAdapter {
        findNavController().navigate(
            R.id.action_UsersFragment_to_DetailsFragment,
            DetailsFragment.args(it.login)
        )
    }

    private val viewModel by viewModels<UsersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_UsersFragment_to_DetailsFragment)
//        }
        binding.usersRv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersState.collectLatest {
                adapter.submitData(it.pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.usersRv.adapter = null
        _binding = null
    }
}