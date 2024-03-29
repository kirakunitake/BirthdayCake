package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.graphics.Rect;


public class CakeView extends SurfaceView {
    private CakeModel cakeModel;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    private Paint touchLocPaint = new Paint();

    Paint balloonPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 60.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;

    // Define numCheckers and checkerSize as a constant for the checkerboard size
    private static final int numCheckers = 2;
    private static final int checkerSize = 50;



    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */

    private Paint checkerBoardPaint = new Paint();
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.cakeModel = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFFFD700);  //gold
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(0xFF0000FF);
        touchLocPaint.setColor(Color.RED); //Red

        setBackgroundColor(Color.WHITE);  //better than black default

        // Initialize checkerBoardPaint here
        checkerBoardPaint.setStyle(Paint.Style.FILL);



    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        if (cakeModel.hasCandles){
            if(cakeModel.candlesLit){
                //draw the outer flame
                float flameCenterX = left + candleWidth/2;
                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius/3;
                canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

                //draw the inner flame
                flameCenterY += outerFlameRadius/3;
                canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);

                //draw the wick
                float wickLeft = left + candleWidth/2 - wickWidth/2;
                float wickTop = bottom - wickHeight - candleHeight;
                canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
            }

        }

    }


    //method to draw the checkerboard pattern
    private void drawCheckerboard(Canvas canvas) {
        for (int i = 0; i < numCheckers; i++) {
            for (int j = 0; j < numCheckers; j++) {
                if ((i + j) % 2 == 0) {
                    checkerBoardPaint.setColor(Color.GREEN);
                } else {
                    checkerBoardPaint.setColor(Color.RED);
                }
                int left = (int)(cakeModel.touchX - (numCheckers / 2.0f) * checkerSize + i * checkerSize);
                int top = (int)(cakeModel.touchY - (numCheckers / 2.0f) * checkerSize + j * checkerSize);
                Rect rect = new Rect(left, top, left + checkerSize, top + checkerSize);
                canvas.drawRect(rect, checkerBoardPaint);
            }
        }
    }

    public void drawTouch(Canvas canvas, float touchX, float touchY){
        touchLocPaint.setTextSize(100);
        canvas.drawText("("+ touchX +", "+ touchY +")",1300,800,touchLocPaint);

    }



    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        if (cakeModel.hasCandles && cakeModel.numCandles > 0){
            for(int i = 1; i <= cakeModel.numCandles; i++){
                drawCandle(canvas, (cakeWidth*i/(cakeModel.numCandles+1))+cakeLeft, cakeTop);
            }
        }
        if (cakeModel.balloon)
        {
            canvas.drawOval(cakeModel.balloonX - 30, cakeModel.balloonY - 50, cakeModel.balloonX + 30, cakeModel.balloonY + 50, balloonPaint);
        }

        if(cakeModel.touch){
            drawTouch(canvas, cakeModel.touchX, cakeModel.touchY);
        }

        // Draw the checkerboard pattern if checkerboardVisible is true
        if (cakeModel.checkerboardVisible) {
            drawCheckerboard(canvas);
        }

    }//onDraw

    //getter method
    public CakeModel getCakeModel(){
        return cakeModel;
    }

}//class CakeView

