package aki.aika.purrfriends.ViewModel

// BreedViewModel.kt
import aki.aika.purrfriends.Model.BreedModel
import aki.aika.purrfriends.Model.Data2Model
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BreedViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val dogBreedsReference = database.getReference("data")
    private val getData2 = database.getReference("data2")

    private val _dogBreeds = MutableLiveData<List<BreedModel>>()
    val dogBreeds: LiveData<List<BreedModel>> get() = _dogBreeds


    private val _data2 = MutableLiveData<List<Data2Model>>()
    val data2: LiveData<List<Data2Model>> get() = _data2

    init {
        // Retrieve data from Firebase
        dogBreedsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val breeds = mutableListOf<BreedModel>()
                for (childSnapshot in snapshot.children) {
                    val breed = childSnapshot.getValue(BreedModel::class.java)
                    breed?.let { breeds.add(it) }
                }
                _dogBreeds.value = breeds
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        getData2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data2 = mutableListOf<Data2Model>()
                for (childSnapshot in snapshot.children) {
                    val data = childSnapshot.getValue(Data2Model::class.java)
                    data?.let { data2.add(it) }
                }
                _data2.value = data2
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}




