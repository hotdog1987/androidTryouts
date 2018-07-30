package com.example.thunderbook.androidapp.login

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragement_fade_animations.*


class FadeAnimation : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            return inflater.inflate(R.layout.fragement_fade_animations, container, false);
        } catch (e: Exception) {
            Log.e("onCreateView", "Exception Occurred: ${e.message}");
            return null;
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            super.onViewCreated(view, savedInstanceState)
            init(view);
        } catch (e: Exception) {
            Log.e("onCreateView", "Exception Occurred: ${e.message}");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun init(view: View): Unit {
        try {
            activity!!.title = "Fading Animations";

            val gokuView: ImageView = view.findViewById<ImageView>(R.id.goku);
            val vegetaView: ImageView = view.findViewById<ImageView>(R.id.vegeta);

            gokuView.setOnClickListener(this);
            vegetaView.setOnClickListener(this);
        } catch (e: Exception) {
            Log.e("init", "Exception Occurred: ${e.message}");
        }
    }

    override fun onClick(v: View) {
        try {
            when (v.getId()) {
                R.id.goku -> fade(goku, vegeta);
                R.id.vegeta -> fade(vegeta, goku);
            }
        } catch (e: Exception) {
            Log.e("onClick", "Exception Occurred: ${e.message}");
        }
    }

    private fun fade(v1: ImageView, v2: ImageView) {
        try {
            v1.animate().alpha(0f).setDuration(1000);
            v2.animate().alpha(1f).setDuration(1000);
            v2.bringToFront();
        } catch (e: Exception) {
            Log.e("fade", "Exception Occurred: ${e.message}");
        }
    }
}