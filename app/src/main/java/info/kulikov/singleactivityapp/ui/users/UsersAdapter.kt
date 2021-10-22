package info.kulikov.singleactivityapp.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.kulikov.singleactivityapp.R
import info.kulikov.singleactivityapp.domain.User

class UsersAdapter(
    private var repositories: MutableList<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false).let {
                ViewHolder(it, onClick)
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repositories[position])
    }

    class ViewHolder(private val containerView: View, private val onClick: (User) -> Unit) :
        RecyclerView.ViewHolder(containerView) {

        private val fullName: TextView = containerView.findViewById(R.id.text_view_title)

        fun bindData(repository: User) {
            with(repository) {
                fullName.text = "$first_name $last_name"
                containerView.setOnClickListener { onClick(this) }
            }
        }
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    fun addRepositories(newRepositories: List<User>?) {
        newRepositories?.let {
            repositories.clear()
            repositories.addAll(it)
        }
    }

}