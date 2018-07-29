package com.example.thunderbook.androidapp.login

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.appcompat.R.id.message
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.loopj.android.http.*
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.conn.ConnectTimeoutException
import org.json.JSONObject
import java.net.SocketException


class MainActivity : AppCompatActivity() {
    var constraint: ConstraintLayout? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Login");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init();
    }

    private fun init() {
        animateBg();
        loginAction();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun animateBg() {
        // background layout
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout);
        val childLayout = findViewById<ConstraintLayout>(R.id.childLayout);
        childLayout.setAlpha(0f);
        mainLayout.setAlpha(0f);
        mainLayout.setScaleX(0.1f);
        mainLayout.setScaleY(0.1f);
        mainLayout.setBackgroundColor(Color.parseColor("#f31899"));
        mainLayout.animate().alpha(1f).setDuration(1000);
        mainLayout.animate().scaleX(1f).setDuration(1000);
        mainLayout.animate().scaleY(1f).setDuration(1000);

        val handler = Handler();
        handler.postDelayed(Runnable {
            childLayout.animate().alpha(1f).setDuration(1000);
        }, 1000);
    }

    private fun loginAction() {
        val btn_click_me = findViewById(R.id.loginBtn) as Button;
        btn_click_me.setOnClickListener {
            try {
                btn_click_me.setEnabled(false);
                val payload = RequestParams();
                payload.put("username", findViewById<EditText>(R.id.username).getText().toString());
                payload.put("password", findViewById<EditText>(R.id.password).getText().toString());

                val client = AsyncHttpClient();
                client.post("http://192.168.29.94:3000/users", payload, object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
                        Log.i("API: /users", "Login successful");
                        Toast.makeText(this@MainActivity, "Login successful", Toast.LENGTH_SHORT).show();

                        val handler = Handler();
                        handler.postDelayed(Runnable {
                            val intent = Intent(this@MainActivity, HomeActivity::class.java);
                            startActivity(intent);
                        }, 2000);
                    }

                    override fun onFailure(statusCode: Int, headers: Array<Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                        if ((throwable is ConnectTimeoutException) || (throwable is SocketException)) {
                            Log.e("API: /users", "Connection TimedOut!!");
                            Toast.makeText(this@MainActivity, "Connection Timeout! Try again later.", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("API: /users", errorResponse?.get("message").toString());
                            Toast.makeText(this@MainActivity, "${errorResponse?.get("message").toString()}. Try again.", Toast.LENGTH_LONG).show();
                        }
                        btn_click_me.setEnabled(true);
                    }
                });
            }
            catch (e: Exception) {
                Log.e("MainActInitException", e.toString());
            }

        }
    }
}
