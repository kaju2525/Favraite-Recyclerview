package karun.com.recyclerviewfavraite

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import java.util.*


class SharedPreference {

    companion object {
        val PREFS_NAME = "Fab_Like"
        val FAVORITES = "Favorite"
    }

     fun storeFavorites(context: Context, favorites: List<Model>) {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: Editor

        editor = settings.edit()

        val jsonFavorites = Gson().toJson(favorites)
        editor.putString(FAVORITES, jsonFavorites)

        editor.commit()
    }

     fun loadFavorites(context: Context): ArrayList<Model>? {
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var favorites: List<Model>

        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val favoriteItems = Gson().fromJson(jsonFavorites, Array<Model>::class.java)
            favorites = Arrays.asList(*favoriteItems)
            favorites = ArrayList(favorites)
        } else
            return null

        return favorites
    }




    fun addFavorite(context: Context, beanSampleList: Model) {
        var favorites: MutableList<Model>? = loadFavorites(context)
        if (favorites == null)
            favorites = ArrayList()
        favorites.add(beanSampleList)
        storeFavorites(context, favorites)
    }

    fun removeFavorite(context: Context, beanSampleList: Model) {
        val favorites = loadFavorites(context)
        if (favorites != null) {
            favorites.remove(beanSampleList)
            storeFavorites(context, favorites)
        }
    }




}
