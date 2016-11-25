package com.example.android.matrixtransformations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {



        public DrawView(Context context) {
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);

//            DrawUtils.DrawMatrixTransformationsDemo(canvas);
//            DrawUtils.DrawRectToRectDemo(canvas);
            DrawUtils.DrawPolyToPolyDemo(canvas);

        }

    }
}
