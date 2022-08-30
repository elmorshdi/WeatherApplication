package com.elmorshdi.weatheraplication.view.util

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.elmorshdi.weatheraplication.domain.weather.TimeFormat

//@BindingAdapter("loadImageUrl")
//fun loadImage(imageView: ImageView, url: String?) {
//
//    if (!url.isNullOrBlank()) {
//        Glide.with(imageView)
//            .load(url)
//            .into(imageView)
//    }
//}
@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, iconRes: Int) {
    imageView.setImageResource(iconRes)
}




 @BindingAdapter("setTime")
fun setTime(textView: AppCompatTextView, time: TimeFormat?) {
    val text = StringBuilder().append(time?.H.toString()).append(":").append(time?.M.toString()).toString()
    textView.text = text
}
@BindingAdapter("setDegree")
fun setDegree(textView: AppCompatTextView, degree: Double?) {
    val text = StringBuilder().append(degree?.toInt().toString()).append("°")
    textView.text = text
}

@BindingAdapter("setTemp")
fun setTemp(textView: AppCompatTextView, temp: Double?) {
    val text = StringBuilder().append(temp?.toInt().toString())
    textView.text = text
}

@BindingAdapter("setHumidity")
fun setHumidity(textView: AppCompatTextView, humidity: Int?) {
    val text = StringBuilder().append(humidity?.toString()).append("%")
    textView.text = text
}

@BindingAdapter("setWindSpeed")
fun setWindSpeed(textView: AppCompatTextView, speed: Double?) {
    val text = StringBuilder().append(speed?.toInt().toString()).append("Km/h")
    textView.text = text
}