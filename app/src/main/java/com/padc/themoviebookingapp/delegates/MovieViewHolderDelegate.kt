package com.padc.themoviebookingapp.delegates

import com.padc.themoviebookingapp.data.vos.MovieVO

interface MovieViewHolderDelegate {
    fun onTapMovie(movieId: Int)
}