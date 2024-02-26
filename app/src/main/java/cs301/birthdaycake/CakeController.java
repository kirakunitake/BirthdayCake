package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener, View.OnTouchListener
{
private CakeView cakeView;
private CakeModel cakeModel;

    // Initialize the touch listener in the constructor
    public CakeController(CakeView cakeView) {
        this.cakeView = cakeView;
        this.cakeModel = cakeView.getCakeModel();
        cakeView.setOnTouchListener(this); // Correctly set this class as the touch listener
    }

    @Override
    public void onClick(View v) {
    Log.d("cake","click!");
    cakeModel.candlesLit = false;
    cakeView.invalidate();
    }

    // This method is named to appear like an override but it's not correctly named for any interface method
    // If it's meant to be used internally or called explicitly, consider renaming or documenting its purpose clearly
    public void OnCheckedChangeListener() {
        cakeModel.hasCandles = false;
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        cakeModel.hasCandles = isChecked;
        cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        cakeModel.numCandles = progress;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Intentionally left blank
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Intentionally left blank
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        cakeModel.touch = true;
        cakeModel.balloon = true;
        cakeModel.touchX = x;
        cakeModel.touchY = y;
        cakeModel.balloonY = y;
        cakeModel.balloonX = x;
        cakeView.invalidate();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cakeModel.touchX = event.getX();
            cakeModel.touchY = event.getY();
            cakeModel.checkerboardVisible = true;
            cakeView.invalidate(); // Redraw the cake view
        }
        return true;
    }
}
