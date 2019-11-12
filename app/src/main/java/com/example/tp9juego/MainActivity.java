package com.example.tp9juego;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    CCGLSurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        view = new CCGLSurfaceView(this);
        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Juego juego;
        juego = new Juego(view);
        juego.InicioJuego();
    }
}
