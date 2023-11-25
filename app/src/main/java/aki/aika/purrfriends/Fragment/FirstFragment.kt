package aki.aika.purrfriends.Fragment

import aki.aika.purrfriends.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import aki.aika.purrfriends.databinding.FragmentFirstBinding
import android.content.Context
import android.util.Log

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val receivedValue = arguments?.getString("key")
        useDataFromSharedPreferences()

     /*   val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val value = sharedPref.getString("key", "Default Value")*/

        binding.canine.setOnClickListener {
            val bundle =  Bundle()
            bundle.putString("type" , "Dog")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        binding.feline.setOnClickListener {
            val bundle =  Bundle()
            bundle.putString("type" , "Cat")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }

    private fun loadFromSharedPreferences(): String {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        Log.d("SharedPreferences", "Loaded value: $sharedPref")

        return sharedPref.getString("key", "Default Value") ?: "Default Value"
    }

    // Example usage
    private fun useDataFromSharedPreferences() {
        val receivedValue = loadFromSharedPreferences()
        binding.textviewFirst.text = "Hello $receivedValue!!"
        // Do something with the received value in your second fragment
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}