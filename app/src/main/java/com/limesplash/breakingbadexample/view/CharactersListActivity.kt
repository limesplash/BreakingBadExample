package com.limesplash.breakingbadexample.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.limesplash.breakingbadexample.R
import com.limesplash.breakingbadexample.model.Character
import com.limesplash.breakingbadexample.model.mvi.CharactersViewState
import com.limesplash.breakingbadexample.presenter.MviCharactersPresenter
import com.limesplash.breakingbadexample.util.ObservableDelegate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [CharacterDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class CharactersListActivity : MviBBCharactersView,  MviActivity<MviBBCharactersView, MviCharactersPresenter>() {

    override fun emitFirstTimeLoadEvent(): Observable<Boolean> = Observable.just(true).subscribeOn(Schedulers.io())

    //hack to prevent multiple character selection on state restore
    private var selectedCharacterDisplayed = false
    private val characterClickObservable = ObservableDelegate<Character>()

    override fun emitSelectCharacterEvent(): Observable<Character>  = characterClickObservable.map {
        selectedCharacterDisplayed = false
        it
    }


    override fun updateViewState(charactersViewState: CharactersViewState) {

        charactersViewState.characters?.let {
            setupRecyclerView(item_list, it)
        }

        charactersViewState.selectedCharacter?.let {
            displayCharacterDetails(it)
        }

    }

    private fun displayCharacterDetails(character: Character) {

        if(selectedCharacterDisplayed)
            return

        val intent = Intent(this, CharacterDetailActivity::class.java).apply {
             putExtra(CharacterDetailActivity.EXTRA_CHARACTER, GsonBuilder().create().toJson(character))
        }
        startActivity(intent)

        selectedCharacterDisplayed = true
    }

    override fun createPresenter(): MviCharactersPresenter {
        return MviCharactersPresenter() //TODO inject
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)

//        if (item_detail_container != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            twoPane = true
//        }

        setupRecyclerView(item_list, emptyList())
    }


    private fun setupRecyclerView(recyclerView: RecyclerView, characters: List<Character>) {
        recyclerView.adapter =
            CharactersRVAdapter(
                characters
                , characterClickObservable.getLambda()
            )
    }
}
