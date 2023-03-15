package uz.akhbor.githubtestapp.details

import android.os.Bundle
import android.telecom.Call.Details
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.akhbor.githubtestapp.R
import uz.akhbor.githubtestapp.base.EMPTY
import uz.akhbor.githubtestapp.base.ext.YEAR_MONTH_DATE_TIME_FORMATTER
import uz.akhbor.githubtestapp.databinding.FragmentDetailsBinding
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
internal class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val login: String
        get() = requireArguments().getString(LOGIN_KEY) ?: String.EMPTY

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadDetails(login)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsState.collectLatest(::renderUi)
        }
        binding.reloadBtn.setOnClickListener {
            viewModel.loadDetails(login)
        }
    }

    private fun renderUi(state: DetailsState) = with(binding) {
        progressContainer.isVisible = state.isLoading
        errorContainer.isVisible = state.isError
        val detail = state.userDetail ?: return@with
        Glide.with(requireContext()).load(state.userDetail.avatarUrl).into(avatarIv)

        loginTv.text = detail.login
        followersTv.text = getString(R.string.followers_text, detail.followers)
        followingTv.text = getString(R.string.following_text, detail.following)
        createdDateTv.isVisible = detail.createdAt != null
        createdDateTv.text = getString(
            R.string.created_date_text,
            detail.createdAt?.format(YEAR_MONTH_DATE_TIME_FORMATTER) ?: String.EMPTY
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOGIN_KEY = "LOGIN_KEY"
        fun args(login: String): Bundle {
            return bundleOf(LOGIN_KEY to login)
        }
    }
}