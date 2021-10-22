package info.kulikov.singleactivityapp.ui.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import info.kulikov.singleactivityapp.R
import info.kulikov.singleactivityapp.database.AppRoomDatabase
import info.kulikov.singleactivityapp.databinding.FragmentUsersBinding
import info.kulikov.singleactivityapp.domain.User
import io.reactivex.disposables.Disposable
import java.util.*

class UsersFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewModel
    private var _binding: FragmentUsersBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var mAdapter: UsersAdapter? = null
    private var dataRepositoryLoading: Disposable? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java) // подключили ViewModel

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpRecyclerView();

        usersViewModel.appDao = AppRoomDatabase.getDatabase(
            requireActivity().applicationContext)
            .appDao()

        // подписались на изменние списка пользователей userListLiveData
        usersViewModel.userListLiveData.observe(viewLifecycleOwner, {
            mAdapter?.addRepositories(it)
            mAdapter?.notifyDataSetChanged()
        })
        // забрали данные из сети
        // на уровне usersViewModel все запишется userListLiveData, а на него
        // мы подписались выше
        dataRepositoryLoading = usersViewModel.loadApiRepositories()

        usersViewModel.loadDatabaseRepositories()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dataRepositoryLoading = null
    }

    private fun setUpRecyclerView() {
        mAdapter = UsersAdapter(ArrayList<User>()) {
            val arguments = Bundle()
            arguments.putSerializable("USER", it)

            Navigation.findNavController(binding.root).navigate(
                R.id.action_navigation_users_to_navigation_usercard, arguments
            )
        }

        binding.recyclerViewRepositories.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewRepositories.adapter = mAdapter
    }
}