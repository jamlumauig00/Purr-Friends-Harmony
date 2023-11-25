package aki.aika.purrfriends.Adapter

import aki.aika.purrfriends.Model.BreedModel
import aki.aika.purrfriends.databinding.LayoutBreedsBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class BreedsAdapter(
    private val context: Context,
    private val breeds: List<BreedModel>,
    private var onClickInterface: passPositionInterface
) : RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutBreedsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutBreedsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBreed = breeds[position]
        // Populate views with data from currentBreed
        val input = currentBreed.Breed
            holder.binding.breed.text = input
            Glide.with(context)
                .load(currentBreed.Images) // Assuming Images is the URL field in BreedData
                .transform(RoundedCornersTransformation(16f)) // Adjust radius as needed
                .into(holder.binding.BreedPhoto)

        holder.itemView.setOnClickListener {
            onClickInterface.onclick(currentBreed.Breed)
        }
        // Add similar lines for other TextViews displaying different properties
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

}

interface passPositionInterface{
    fun onclick(breed: String)
}