package com.xmethodius.imagestock

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.xmethodius.imagestock.PhotosAdapter.OnPhotoClickedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotoPickerActivity : AppCompatActivity() {
    private var page = 1
    var searchLayout: FrameLayout? = null
    var appBarLayout: AppBarLayout? = null
    var searchBar: EditText? = null
    var recyclerView: RecyclerView? = null
    var progressBar: ProgressBar? = null
    var adapter: PhotosAdapter? = null
    var photoClickListener: OnPhotoClickedListener? = null
    var dataService: RetrofitInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_img_picker)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        appBarLayout = findViewById(R.id.appBar)
        searchLayout = findViewById(R.id.searchLayout)
        searchBar = findViewById(R.id.searchBar)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        dataService = RetrofitClient.getClient().create(RetrofitInterface::class.java)
        searchBar.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode === KeyEvent.KEYCODE_ENTER && event.action === KeyEvent.ACTION_DOWN) {
                search(searchBar.getText().toString())
                true
            } else {
                false
            }
        })
        photoClickListener = object : OnPhotoClickedListener {
            override fun photoClicked(photo: Image?, imageView: ImageView?) {
                val intent = Intent()
                intent.putExtra("image", photo)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.setLayoutManager(layoutManager)
        adapter = PhotosAdapter(ArrayList(), this, photoClickListener!!)
        recyclerView.setAdapter(adapter)
        recyclerView.addOnScrollListener(object : RecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadPhotos()
            }
        })
        loadPhotos()
    }

    private fun loadPhotos() {
        progressBar!!.visibility = View.VISIBLE
        dataService.getPhotos(page, null, "latest")
            .enqueue(object : Callback<List<Image?>?> {
                override fun onResponse(call: Call<List<Image?>?>?, response: Response<List<Image?>?>) {
                    val photos: List<Image> = response.body() as List<Image>
                    Log.d("Photos", "Photos Fetched " + photos.size)
                    //add to adapter
                    page++
                    adapter.addPhotos(photos)
                    recyclerView!!.adapter = adapter
                    progressBar!!.visibility = View.GONE
                }

                override fun onFailure(call: Call<List<Image?>?>?, t: Throwable?) {
                    progressBar!!.visibility = View.GONE
                }
            })
    }

    fun search(query: String?) {
        if (query != null && query != "") {
            progressBar!!.visibility = View.VISIBLE
            dataService.searchPhotos(query, null, null, null)
                .enqueue(object : Callback<TotalResults?> {
                    override fun onResponse(
                        call: Call<TotalResults?>?,
                        response: Response<TotalResults?>
                    ) {
                        val results: TotalResults? = response.body()
                        Log.d("Photos", "Total Results Found " + results.getTotal())
                        val photos: List<Image> = results.getResults() as List<Image>
                        adapter = PhotosAdapter(
                            photos, this@PhotoPickerActivity,
                            photoClickListener!!
                        )
                        recyclerView!!.adapter = adapter
                        progressBar!!.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<TotalResults?>?, t: Throwable) {
                        Log.d("Unsplash", t.localizedMessage)
                        progressBar!!.visibility = View.GONE
                    }
                })
        } else {
            loadPhotos()
        }
    }

    private fun showSearchBar() {
        appBarLayout!!.visibility = View.GONE
        searchLayout!!.visibility = View.VISIBLE
        searchBar!!.requestFocus()
    }

    fun hideSearchBar(view: View?) {
        searchLayout!!.visibility = View.GONE
        appBarLayout!!.visibility = View.VISIBLE
        searchBar!!.clearFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_picker, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.search) {
            Log.d("picker", "Search bar open")
            showSearchBar()
            return true
        }
        if (item.itemId === R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchLayout!!.visibility == View.VISIBLE) {
            Log.d("picker", "Search bar visible")
            hideSearchBar(null)
            return
        }
        super.onBackPressed()
    }
}