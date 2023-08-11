package com.example.playlistmaker2

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.LinkedList


class SearchActivity : AppCompatActivity(), OnTrackClickListener{
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var buttonBack: ImageButton
    private lateinit var placeHolderImage: ImageView
    private lateinit var placeHolderText: TextView
    private lateinit var placeHolderButton: ImageButton
    private lateinit var trackList: RecyclerView
    private lateinit var searchHistory: SongHistoryPreference
    private lateinit var serchHistoryView: LinearLayout
    private lateinit var searchHistoryRecyclerView: RecyclerView
    private lateinit var clearHistoryButton: Button
    private lateinit var erroeView: FrameLayout



    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(iTunesApi::class.java)

    private val songs = ArrayList<Song>()
    private val searchHistoryTracks = ArrayList<Song>()

    private val adapter = SongAdapter(this)
    private val historyAdapter = SongAdapter(this)






    companion object {
        private const val TEXT = "text"
        private const val iTunesBaseUrl = "https://itunes.apple.com/"
        private const val PLAYLIST_MAKER_PREFERENCES = "PLAYLIST_MAKER_PREFERENCES"
        private const val MAX_SIZE_OF_SEARCH_HISTORY_TRACKS = 10
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        adapter.songs = songs
        historyAdapter.songs = searchHistoryTracks

        searchHistory = SongHistoryPreference(getSharedPreferences(PLAYLIST_MAKER_PREFERENCES, MODE_PRIVATE))





        trackList = findViewById(R.id.trackList)
        trackList.adapter = adapter
        serchHistoryView = findViewById(R.id.linear_layout_search_history)
        erroeView = findViewById(R.id.errorView)
        clearHistoryButton = findViewById(R.id.button_clear_search_history)
        searchHistoryRecyclerView = findViewById(R.id.recycler_view_search_history)
        searchHistoryRecyclerView.adapter = historyAdapter
        inputEditText = findViewById(R.id.inputEditText)
        clearButton = findViewById(R.id.clearIcon)
        buttonBack = findViewById(R.id.back)
        placeHolderText = findViewById(R.id.place_holder_text)
        placeHolderImage = findViewById(R.id.place_holder_image)
        placeHolderButton = findViewById(R.id.place_holder_updateButton)
        inputEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(inputEditText, InputMethodManager.SHOW_IMPLICIT)


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)




        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && inputEditText.text.isEmpty()) showSearchHistory() else serchHistoryView.visibility = View.GONE
        }



        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputEditText.hasFocus() && p0?.isEmpty() == true) showSearchHistory() else serchHistoryView.visibility = View.GONE
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        if (savedInstanceState != null) {
            val searchText = savedInstanceState.getString(TEXT)
            inputEditText.setText(searchText)
        }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                getSong(inputEditText.text.toString(), adapter)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(inputEditText, InputMethodManager.SHOW_IMPLICIT)
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                true
            }
            false
        }

        buttonBack.setOnClickListener {
            finish()
        }


        clearHistoryButton.setOnClickListener{
            trackList.visibility = View.VISIBLE
            serchHistoryView.visibility = View.GONE
            searchHistory.clear()
        }


        clearButton.setOnClickListener {
            inputEditText.setText("")
            songs.clear()
            adapter.notifyDataSetChanged()

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(clearButton.windowToken, 0)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

    }




    private fun getSong(text: String, adapter: SongAdapter){
        iTunesService.search(text).enqueue(object : Callback<iTunesResponse>{
            override fun onResponse(
                call: Call<iTunesResponse>,
                response: Response<iTunesResponse>
            ) {
                if (response.code() == 200){
                    songs.clear()
                    if (response.body()?.results?.isNotEmpty() == true){
                        songs.addAll(response.body()?.results!!)
                        adapter.notifyDataSetChanged()
                    }
                    if (songs.isEmpty()){
                        placeHolderNoFound()

                    }else{
                        placeHolderButton.visibility = View.GONE
                        erroeView.visibility = View.GONE
                        trackList.visibility = View.VISIBLE
                    }
                }else{
                    placeHolderNoInternet()
                    placeHolderButton.setOnClickListener {
                        getSong(text, adapter)
                    }
                }
            }

            override fun onFailure(call: Call<iTunesResponse>, t: Throwable) {
                placeHolderNoInternet()
                placeHolderButton.setOnClickListener {
                    getSong(text, adapter)
                }
            }
        })

    }
    private fun placeHolderNoInternet(){
        songs.clear()
        adapter.notifyDataSetChanged()
        trackList.visibility = View.GONE
        erroeView.visibility =View.VISIBLE
        placeHolderButton.visibility = View.VISIBLE
        placeHolderText.text = getText(R.string.not_internet)
        placeHolderImage.setImageResource(R.drawable.not_internet)

    }




    private fun showSearchHistory() {
        erroeView.visibility =View.GONE
        placeHolderButton.visibility =View.GONE
        val tracks = searchHistory.get()
        if (tracks.isNotEmpty()) {
            trackList.visibility = View.GONE
            serchHistoryView.visibility = View.VISIBLE
            searchHistoryTracks.clear()
            searchHistoryTracks.addAll(tracks)
            historyAdapter.notifyDataSetChanged()
        }
    }

    private fun placeHolderNoFound(){
        songs.clear()
        adapter.notifyDataSetChanged()
        erroeView.visibility = View.VISIBLE
        placeHolderImage.setImageResource(R.drawable.not_found)
        placeHolderText.text = getText(R.string.image_not_found)

    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val searchText = inputEditText.text.toString()
        outState.putString(TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchText = savedInstanceState.getString(TEXT)
        inputEditText.setText(searchText)
    }

    override fun onTrackClick(track: Song) {
        searchHistoryTracks.clear()
        searchHistoryTracks.addAll(searchHistory.get())
        if (searchHistoryTracks.contains(track)) {
            searchHistoryTracks.remove(track)
        } else if (searchHistoryTracks.size == MAX_SIZE_OF_SEARCH_HISTORY_TRACKS) {
            searchHistoryTracks.removeAt(MAX_SIZE_OF_SEARCH_HISTORY_TRACKS - 1)
        }
        searchHistoryTracks.add(0, track)
        searchHistory.add(searchHistoryTracks)

    }


}