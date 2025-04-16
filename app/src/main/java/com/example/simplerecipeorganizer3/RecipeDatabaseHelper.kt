package com.example.simplerecipeorganizer3
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "recipe.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "recipes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertRecipe(recipe: Recipe) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, recipe.title)
            put(COLUMN_CONTENT, recipe.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            recipes.add(Recipe(id, title, content))
        }

        cursor.close()
        db.close()
        return recipes
    }
    fun updateRecipe(recipe: Recipe) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, recipe.title)
            put(COLUMN_CONTENT, recipe.content)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(recipe.id.toString()))
        db.close()
    }
    fun getRecipeByID(recipeId: Int): Recipe? {
        val db = readableDatabase
        var recipe: Recipe? = null
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val selectionArgs = arrayOf(recipeId.toString())
        val cursor = db.rawQuery(query, selectionArgs)

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            recipe = Recipe(id, title, content)
        }

        cursor.close()
        db.close()
        return recipe
    }


}
