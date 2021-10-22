package info.kulikov.singleactivityapp.ui.usercard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import info.kulikov.singleactivityapp.databinding.FragmentUsercardBinding

class UsercardFragment : Fragment() {

    private lateinit var usercardViewModel: UsercardViewModel
    private var _binding: FragmentUsercardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usercardViewModel =
            ViewModelProvider(this).get(UsercardViewModel::class.java)

        _binding = FragmentUsercardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        usercardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}