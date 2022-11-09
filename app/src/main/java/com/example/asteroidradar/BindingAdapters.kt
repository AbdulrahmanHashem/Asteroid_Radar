package com.example.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.asteroidradar.main.AsteroidListAdapter

@BindingAdapter("loading_status")
fun bindloading_status(progressBar: ProgressBar, visibility: Int) {
    progressBar.visibility = visibility
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
}

@BindingAdapter("setImage")
fun bindImageOfTheDay(imageView: ImageView, image: String?){
    image?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
//            .apply(
//                RequestOptions()
//                .placeholder(R.drawable.placeholder_picture_of_day)
//                .error(R.drawable.placeholder_picture_of_day))
            .into(imageView)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

//@BindingAdapter("setContentDescription")
//fun bindSetContentDescription(imageView: ImageView, text: String) {
//    try {
//        val context = imageView.context
//        if (text != ""){
//            imageView.contentDescription = String.format(context.getString(R.string.nasa_picture_of_day_content_description_format), text)
//        } else {
//            imageView.contentDescription = context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
//        }
//    } catch(e: Exception){
//        println(e.message)
//    }
//}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
