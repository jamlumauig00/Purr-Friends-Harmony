package aki.aika.purrfriends.Fragment

import aki.aika.purrfriends.Adapter.BreedsAdapter
import aki.aika.purrfriends.Adapter.passPositionInterface
import aki.aika.purrfriends.R
import aki.aika.purrfriends.ViewModel.BreedViewModel
import aki.aika.purrfriends.databinding.FragmentSecondBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class SecondFragment : Fragment(), passPositionInterface {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var breedViewModel: BreedViewModel

    private var type: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        breedViewModel = ViewModelProvider(this)[BreedViewModel::class.java]
        type = arguments?.getString("type").toString()

        Log.d("type", type)

        getBreedData()

        return binding.root

    }

    private fun getBreedData() {
        breedViewModel.dogBreeds.observe(viewLifecycleOwner) { breedData ->
            // Update your UI or perform further processing with breedData
            val filteredList = breedData.filter { it.Type == type }

            for (viewModel in filteredList) {
                Log.d("BreedData", "Type: ${viewModel.Type}, Breed: ${viewModel.Breed}")
                // Add more fields as needed
            }
            binding.back.setOnClickListener {
                findNavController().popBackStack()
            }

            binding.type.apply {
                text = if (type == "Cat") {
                    "Feline Breeds"
                } else {
                    "Canine Breeds"
                }
            }
// Set up RecyclerView adapter with filtered data
            binding.breeds.apply {
                val adapter = BreedsAdapter(requireContext(), filteredList, this@SecondFragment)
                this.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onclick(breed: String) {
        val bundle =  Bundle()
        bundle.putString("breed" , breed)
        findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
    }
}