package com.limesplash.breakingbadexample.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.limesplash.breakingbadexample.R
import com.limesplash.breakingbadexample.model.Character
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [CharactersListActivity].
 */
class CharacterDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_CHARACTER = "character"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        val characterJSON = intent.extras.getString(EXTRA_CHARACTER)

        characterJSON?.let {
            val character: Character = GsonBuilder().create().fromJson(characterJSON,Character::class.java)
            name.text = "Name: " + character.name
            occupation.text = "Occupation: " + character.occupation?.joinToString(", ")
            status.text = "Status: " + character.status
            appearance.text = "Appears in Seasons: " + character.appearance.joinToString(", ")

            Glide.with(this)
                .load(character.imageURL)
                .into(image)
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                //navigateUpTo(Intent(this, CharactersListActivity::class.java))
                finish() //same behaviour as back button
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
