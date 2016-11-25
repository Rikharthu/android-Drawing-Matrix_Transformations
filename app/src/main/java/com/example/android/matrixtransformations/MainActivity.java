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

        Paint p;
        Path path;
        Matrix matrix;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            p.setStrokeWidth(3);
            p.setStyle(Paint.Style.STROKE);

            path = new Path();
            matrix = new Matrix();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);

            // <* Перемещение *>
            // создаем крест в path
            path=getDefaultPath();
            // рисуем path зеленым
            p.setColor(Color.GREEN);
            canvas.drawPath(path, p);
            // настраиваем матрицу на перемещение на 300 вправо и 200 вниз
            matrix.reset();
            matrix.setTranslate(300, 200);
            printMatrix("translate",matrix);
            // применяем матрицу к path
            path.transform(matrix);
            // рисуем path синим
            p.setColor(Color.BLUE);
            canvas.drawPath(path, p);


            // <* Масштабирование *>
            // создаем крест в path
            path=getDefaultPath();
            // рисуем path зеленым
            p.setColor(Color.GREEN);
            canvas.drawPath(path, p);
            // настраиваем матрицу на изменение размера:
            // в 2 раза по горизонтали
            // в 2,5 по вертикали
            // относительно точки (375, 100) (если не указывать, то - (0, 0) )
            matrix.reset();
            matrix.setScale(2f, 2.5f, 375, 100);
            printMatrix("scale",matrix);
            // применяем матрицу к path
            path.transform(matrix);
            // рисуем path синим
            p.setColor(Color.BLUE);
            canvas.drawPath(path, p);
            // рисуем точку относительно которой было выполнено преобразование
            p.setColor(Color.BLACK);
            canvas.drawCircle(375, 100, 5, p);


            // <* Поворот *>
            // создаем крест в path
            path=getDefaultPath();
            // рисуем path зеленым
            p.setColor(Color.GREEN);
            canvas.drawPath(path, p);
            // настраиваем матрицу на поворот на 120 градусов
            // относительно точки (600,400)
            matrix.reset();
            matrix.setRotate(120, 600, 400);
            printMatrix("rotate",matrix);
            // применяем матрицу к path
            path.transform(matrix);
            // рисуем path синим
            p.setColor(Color.BLUE);
            canvas.drawPath(path, p);
            // рисуем точку, относительно которой был выполнен поворот
            p.setColor(Color.BLACK);
            canvas.drawCircle(600, 400, 5, p);


            // * Порядок операций *

            // first rotate, then translate
            path=getDefaultPath();
            Path tmpPath=getDefaultPath();
            matrix.reset();
            matrix.setRotate(45, 400, 200);
            matrix.postTranslate(500, 0);
            path.transform(matrix, tmpPath);
            p.setColor(Color.RED);
            canvas.drawPath(tmpPath, p);


            // first translate, then rotate
            matrix.reset();
            /* точка сначала сместила фигуру, а потом повернула относительно её СТАРОГО центра */
            matrix.setRotate(45, 400, 200);
            matrix.preTranslate(500, 0);
            path.transform(matrix, tmpPath);
            p.setColor(Color.YELLOW);
            canvas.drawPath(tmpPath, p);


            //* Наклон *
            // создаем крест в path
            path=getDefaultPath();
            canvas.drawPath(path, p);

            matrix.reset();
            matrix.setSkew(0f,0.3f,375,100);

            printMatrix("skew",matrix);

            // применяем матрицу к path
            path.transform(matrix);

            // рисуем path синим
            p.setColor(Color.BLUE);
            canvas.drawPath(path, p);
            // рисуем точку, относительно которой был выполнен поворот
            p.setColor(Color.BLACK);
            canvas.drawCircle(600, 400, 5, p);


            /*
            Map методы:
            mapRadius – даете на вход радиус (если собираетесь трансформировать круг),
                а метод вам вернет значение радиуса после трансформаци
            mapPoints – даете массив точек, матрица выполняет над ними свои операции преобразования
                и возвращает вам в результате новый массив точек
            mapVectors – то же, что и mapPoints, но преобразования перемещения выполнены не будут
            mapRect – возьмет на вход прямоугольник, выполнит для него преобразование и вернет прямоугольник,
             составляющий границы получившейся фигуры. Рассмотрим этот метод на примере.
             */

        }

        private Path getDefaultPath(){
            Path path = new Path();
            path.addRect(300,150,450,200, Path.Direction.CW);
            path.addRect(350,100,400,250, Path.Direction.CW);
            path.addCircle(375, 125, 5, Path.Direction.CW);
            p.setColor(Color.GREEN);
            return path;
        }

        private void printMatrix(String operation, Matrix matrix){
            String matrixString=matrix.toShortString();
            matrixString=matrixString.replaceAll("\\]\\[","]\n[");
            Log.d("MainActivity",operation+":\n"+matrixString);
        }

    }
}
