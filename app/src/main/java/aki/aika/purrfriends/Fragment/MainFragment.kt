package aki.aika.purrfriends.Fragment

import aki.aika.purrfriends.Adapter.PrefManager
import aki.aika.purrfriends.R
import aki.aika.purrfriends.databinding.FragmentMainBinding
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences
    private var prefManager: PrefManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        prefManager = PrefManager(requireContext())

        if (!prefManager!!.isFirstTimeLaunch) {
            launchHomeScreen()
        } else {
            sendDataToFirstFragment()
        }
    }

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        findNavController().navigate(R.id.action_MainFragment_to_FirstFragment)
    }

    private fun saveToSharedPreferences(value: String) {
        val editor = sharedPref.edit()
        editor.putString("key", value)
        editor.apply()
    }

    private fun sendDataToFirstFragment() {
        binding.enter.setOnClickListener {
            val userInput = binding.name.text.toString()
            saveToSharedPreferences(userInput)
            Log.d("SharedPreferences", "Value saved: $userInput")
            prefManager!!.isFirstTimeLaunch = false
            findNavController().navigate(R.id.action_MainFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
