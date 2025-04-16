package com.example.simplerecipeorganizer3
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplerecipeorganizer3.databinding.ActivityAddRecipeBinding



class AddRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var db: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize the database helper
        db = RecipeDatabaseHelper(this)




        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val recipe = Recipe(0, title, content)

            // Insert the recipe into the database
            db.insertRecipe(recipe)


            // Show a toast message indicating that the recipe is saved
            Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG).show()

            // Finish the activity
            finish()
        }
    }
}
