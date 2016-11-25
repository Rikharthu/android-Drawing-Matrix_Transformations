package com.example.android.matrixtransformations;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

public abstract class DrawUtils {

    public static void DrawMatrixTransformationsDemo(Canvas canvas) {
        Paint p = new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        Matrix matrix = new Matrix();

        // <* Перемещение *>
        // создаем крест в path
        path = getDefaultPath();
        // рисуем path зеленым
        p.setColor(Color.GREEN);
        canvas.drawPath(path, p);
        // настраиваем матрицу на перемещение на 300 вправо и 200 вниз
        matrix.reset();
        matrix.setTranslate(300, 200);
        printMatrix("translate", matrix);
        // применяем матрицу к path
        path.transform(matrix);
        // рисуем path синим
        p.setColor(Color.BLUE);
        canvas.drawPath(path, p);


        // <* Масштабирование *>
        // создаем крест в path
        path = getDefaultPath();
        // настраиваем матрицу на изменение размера:
        // в 2 раза по горизонтали
        // в 2,5 по вертикали
        // относительно точки (375, 100) (если не указывать, то - (0, 0) )
        matrix.reset();
        matrix.setScale(2f, 2.5f, 375, 100);
        printMatrix("scale", matrix);
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
        path = getDefaultPath();
        // настраиваем матрицу на поворот на 120 градусов
        // относительно точки (600,400)
        matrix.reset();
        matrix.setRotate(120, 600, 400);
        printMatrix("rotate", matrix);
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
        path = getDefaultPath();
        Path tmpPath = getDefaultPath();
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
        matrix.reset();
        matrix.setSkew(0f, 0.3f, 375, 100);

        printMatrix("skew", matrix);

        // применяем матрицу к path
        path.transform(matrix);

        // рисуем path синим
        p.setColor(Color.BLUE);
        canvas.drawPath(path, p);
        // рисуем точку, относительно которой был выполнен поворот
        p.setColor(Color.BLACK);
        canvas.drawCircle(600, 400, 5, p);


        // рисуем изначальный path
        p.setColor(Color.GREEN);
        canvas.drawPath(getDefaultPath(), p);

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

    public static void DrawRectToRectDemo(Canvas canvas) {
        // initialization
        Paint p = new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        Path path = new Path(); // снеговик
        path.addCircle(200, 100, 50, Path.Direction.CW);
        path.addCircle(200, 225, 75, Path.Direction.CW);
        path.addCircle(200, 400, 100, Path.Direction.CW);
        Path pathDst= new Path(); // трансформированный снеговик
        RectF rectfBounds = new RectF();
        RectF rectfDst = new RectF();
        rectfDst.set(500, 50, 800, 150);
        Matrix matrix= new Matrix();

        // снеговик
        p.setColor(Color.BLUE);
        canvas.drawPath(path, p);

        // граница снеговика
        path.computeBounds(rectfBounds, true);
        p.setColor(Color.GREEN);
        canvas.drawRect(rectfBounds, p);

        // START
        // рамка
        p.setColor(Color.BLACK);
        canvas.drawRect(rectfDst, p);
        // преобразование
        matrix.reset();
        // Этот метод берет два прямоугольника и определяет какие преобразования необходимо выполнить
        // над первым прямоугольником, чтобы он полностью поместился во втором
        // START – в левой (или верхней) стороне
        matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.START);
        // transform snowman
        path.transform(matrix, pathDst);
        // снеговик
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);

        rectfDst.offset(0, 150);

        // CENTER
        // рамка
        p.setColor(Color.BLACK);
        canvas.drawRect(rectfDst, p);
        // преобразование
        matrix.reset();
        // CENTER – в центре
        matrix.setRectToRect(rectfBounds, rectfDst,
                Matrix.ScaleToFit.CENTER);
        path.transform(matrix, pathDst);
        // снеговик
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);

        rectfDst.offset(0, 150);

        // END
        // рамка
        p.setColor(Color.BLACK);
        canvas.drawRect(rectfDst, p);
        // преобразование
        matrix.reset();
        // END – в правой (или нижней) стороне
        matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.END);
        path.transform(matrix, pathDst);
        // снеговик
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);

        rectfDst.offset(0, 150);

        // FILL
        // рамка
        p.setColor(Color.BLACK);
        canvas.drawRect(rectfDst, p);
        // преобразование
        matrix.reset();
        // FILL – не сохранять соотношение сторон, а растянуть первый прямоугольник так,
        // чтобы он полностью заполнил второй
        matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.FILL);
        path.transform(matrix, pathDst);
        // снеговик
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);
    }

    public static void DrawPolyToPolyDemo(Canvas canvas){
        Paint textPaint = new Paint();
        textPaint.setTextSize(32);
        Paint p= new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        Path path= new Path();
        Path pathDst= new Path();
        RectF rectf= new RectF(100, 100, 200, 200);
        Matrix matrix= new Matrix();
        float[] src= new float[] { 100, 100 };
        float[] dst= new float[] { 150, 120 };

        // зеленый квадрат
        path.reset();
        path.addRect(rectf, Path.Direction.CW);
        p.setColor(Color.GREEN);
        canvas.drawPath(path, p);

        // * Одна точка *
        // преобразование
        // src - исходные координаты
        // dsc - целевые координаты (после преобразования)
        // метод настроит матрицу так, чтобы из исходных получить целевые координаты
        matrix.setPolyToPoly(src, 0, dst, 0, 1);
        // т.к. передана одна точка, то будет простое смещение вправо на 50 и вниз на 50
        path.transform(matrix, pathDst);

        // синий квадрат
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);


        // * Две точки *
        int points = 2; // меняй чтобы посмотреть изменения
        src = new float[]{100,100,200,200};
        dst = new float[]{50,300,250,500};
        float[] dst2 = new float[]{400,200,500,200};
        // соеденим соответствующие точки
        p.setColor(Color.BLACK);
        canvas.drawLine(src[0], src[1], src[2], src[3], p);
        canvas.drawLine(dst[0], dst[1], dst[2], dst[3], p);
        canvas.drawLine(dst2[0], dst2[1], dst2[2], dst2[3], p);

        // зеленый квадрат
        path.reset();
        path.addRect(rectf, Path.Direction.CW);
        p.setColor(Color.GREEN);
        canvas.drawPath(path, p);

        // синий квадрат
        // преобразование
        // points - сколько точек использовать для трансформации
        matrix.setPolyToPoly(src, 0, dst, 0, points);
        path.transform(matrix, pathDst);
        // рисование
        p.setColor(Color.BLUE);
        canvas.drawPath(pathDst, p);

        // красный квадрат
        // преобразование
        matrix.setPolyToPoly(src, 0, dst2, 0, points);
        path.transform(matrix, pathDst);
        // рисование
        p.setColor(Color.RED);
        canvas.drawPath(pathDst, p);

        canvas.drawText("1",src[0],src[1],textPaint);
        canvas.drawText("2",src[2],src[3],textPaint);
        canvas.drawText("1",dst[0],dst[1],textPaint);
        canvas.drawText("2",dst[2],dst[3],textPaint);
        canvas.drawText("1",dst2[0],dst2[1],textPaint);
        canvas.drawText("2",dst2[2],dst2[3],textPaint);


        // * Три точки *
    }

    private static Path getDefaultPath() {
        Path path = new Path();
        path.addRect(300, 150, 450, 200, Path.Direction.CW);
        path.addRect(350, 100, 400, 250, Path.Direction.CW);
        path.addCircle(375, 125, 5, Path.Direction.CW);
        return path;
    }

    private static void printMatrix(String operation, Matrix matrix) {
        String matrixString = matrix.toShortString();
        matrixString = matrixString.replaceAll("\\]\\[", "]\n[");
        Log.d("MainActivity", operation + ":\n" + matrixString);
    }

}
