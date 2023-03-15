package uz.akhbor.githubtestapp.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.akhbor.githubtestapp.databinding.ItemUserBinding
import uz.akhbor.githubtestapp.domain.User

internal class UsersAdapter(
    private val onClickListener: (User) -> Unit
) : PagingDataAdapter<User, UsersAdapter.ViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        constructor(parent: ViewGroup) :
                this(ItemUserBinding.inflate(LayoutInflater.from(parent.context)))

        private lateinit var user: User

        init {
            binding.root.setOnClickListener {
                onClickListener(user)
            }
        }

        fun bind(user: User) = with(binding) {
            this@ViewHolder.user = user
            Glide.with(root)
                .load(user.avatarUrl)
                .into(avatarIv)

            loginTv.text = user.login
            idTv.text = user.id.toString()
        }
    }

    private object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }
}