package aki.aika.purrfriends.Fragment

import aki.aika.purrfriends.R
import aki.aika.purrfriends.ViewModel.BreedViewModel
import aki.aika.purrfriends.databinding.FragmentThirdBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private lateinit var breedViewModel: BreedViewModel

    private var breed: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        breedViewModel = ViewModelProvider(this)[BreedViewModel::class.java]
        breed = arguments?.getString("breed").toString()

        Log.d("type", breed)

        getBreedData()

        return binding.root

    }

    private fun getBreedData() {
        breedViewModel.dogBreeds.observe(viewLifecycleOwner) { breedData ->
            // Update your UI or perform further processing with breedData
            val filteredList = breedData.filter { it.Breed == breed }

            for (viewModel in filteredList) {
                Log.d(
                    "BreedData",
                    "Type: ${viewModel.Type}, Breed: ${viewModel.Breed}, Adaptability: ${viewModel.Adaptability}"
                )

                binding.breedText.text = viewModel.Breed
                binding.height.text = viewModel.HeightInches
                binding.weight.text = viewModel.WeightPounds
                binding.life.text = viewModel.LifeSpanYears
                binding.personality.text = viewModel.PersonalityTraits

                Glide.with(requireContext())
                    .load(viewModel.Images) // Assuming Images is the URL field in BreedData
                    .into(binding.imageBreed)

                createAndAddCircles(binding.circle1, viewModel.Adaptability.toInt())
                createAndAddCircles(binding.circle2, viewModel.Grooming.toInt())
                createAndAddCircles(binding.circle3, viewModel.Friendliness.toInt())
                createAndAddCircles(binding.circle4, viewModel.Trainability.toInt())
                createAndAddCircles(binding.circle5, viewModel.ExerciseNeeds.toInt())

                binding.back.setOnClickListener {
                    findNavController().popBackStack()
                }
            }

        }
    }

    private fun createAndAddCircles(container: LinearLayout, shadedCount: Int) {
        container.removeAllViews() // Clear existing views

        // Create and add shaded circles
        for (i in 0 until shadedCount) {
            val shadedCircle = createCircleImageView(true)
            container.addView(shadedCircle)
        }

        // Create and add unshaded circles
        for (i in 0 until (5 - shadedCount)) {
            val unshadedCircle = createCircleImageView(false)
            container.addView(unshadedCircle)
        }
    }

    private fun createCircleImageView(isShaded: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val margin = requireContext().resources.getDimensionPixelSize(R.dimen.circle_margin)
        layoutParams.setMargins(0, 0, margin, 0)
        layoutParams.width = resources.getDimensionPixelSize(R.dimen.circle_size)
        layoutParams.height = resources.getDimensionPixelSize(R.dimen.circle_size)
        imageView.layoutParams = layoutParams

        if (isShaded) {
            imageView.setBackgroundResource(R.drawable.shaded_circle_background)
        } else {
            imageView.setBackgroundResource(R.drawable.unshaded_circle_background)
        }

        return imageView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}