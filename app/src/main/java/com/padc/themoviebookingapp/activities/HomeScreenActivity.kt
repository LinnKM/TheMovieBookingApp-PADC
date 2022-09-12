package com.padc.themoviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.padc.themoviebookingapp.R
import com.padc.themoviebookingapp.data.models.MovieBookingImpl
import com.padc.themoviebookingapp.data.models.MovieBookingModel
import com.padc.themoviebookingapp.delegates.MovieViewHolderDelegate
import com.padc.themoviebookingapp.utils.COMINGSOON_STATUS
import com.padc.themoviebookingapp.utils.CURRENT_STATUS
import com.padc.themoviebookingapp.utils.MOVIE_BOOKING_API_BASE_URL
import com.padc.themoviebookingapp.viewpods.CustomDrawerViewPod
import com.padc.themoviebookingapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.view_pod_custom_drawer.*

class HomeScreenActivity : AppCompatActivity(), MovieViewHolderDelegate {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, HomeScreenActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    // ViewPods
    private lateinit var mNowShowingMovieViewPod: MovieListViewPod
    private lateinit var mComingSoonMovieViewPod: MovieListViewPod
    private lateinit var mCustomDrawerViewPod: CustomDrawerViewPod
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // SetUp UI
        setUpMovieListViewPod()
        setUpToolBar()
        setUpDrawer()

        setUpListeners()
        requestData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home_screen, menu)
        return true
    }

    private fun setUpMovieListViewPod() {
        mNowShowingMovieViewPod = vPodMovieListOnShowing as MovieListViewPod
        mNowShowingMovieViewPod.setUpViewPod(this, "Now Showing")

        mComingSoonMovieViewPod = vPodMovieListComingSoon as MovieListViewPod
        mComingSoonMovieViewPod.setUpViewPod(this, "Coming Soon")

        mCustomDrawerViewPod = vpCustomDrawer as CustomDrawerViewPod
    }

    // request data
    private fun requestData() {
        // Call UserProfile
        mMovieBookingModel.getUserProfile(
            onSuccess = {
                mCustomDrawerViewPod.bindData(it)
                tvGreetProfileName.text = "${it.name?.split(" ")?.first() ?: ""}!"
                Glide.with(this)
                    .load("$MOVIE_BOOKING_API_BASE_URL${it.profileImage}")
                    .into(ivProfilePic)

                println("UserToken: ${it.userToken}")
            },
            onFailure = {
                showError(it)
            }
        )
        // Call Now Playing Movies
        mMovieBookingModel.getMoviesByStatus(
            status = CURRENT_STATUS,
            onSuccess = { movieList ->
                mNowShowingMovieViewPod.setData(movieList)
            }
        )

        // Call UpComing Movies
        mMovieBookingModel.getMoviesByStatus(
            status = COMINGSOON_STATUS,
            onSuccess = { movieList ->
                mComingSoonMovieViewPod.setData(movieList)
            }
        )
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolBarHomeScreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpDrawer() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayoutHomeScreen, R.string.Open, R.string.Close)
        actionBarDrawerToggle?.let {
            it.isDrawerIndicatorEnabled = false
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
            drawerLayoutHomeScreen.addDrawerListener(it)
            it.syncState()
        }
    }

    override fun onBackPressed() {
        if (drawerLayoutHomeScreen.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutHomeScreen.close()
        } else {
            super.onBackPressed()
        }
    }

    private fun setUpListeners() {
        // DrawerButton Listener
        toolBarHomeScreen.setNavigationOnClickListener {
            drawerLayoutHomeScreen.openDrawer(GravityCompat.START)
        }

        llLogOut.setOnClickListener {
            // LogOut User
            mMovieBookingModel.logoutUser(
                onSuccess = { isSuccess ->
                    if (isSuccess) {
                        startActivity(SetUpActivity.newIntent(this))
                        finish()
                        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}