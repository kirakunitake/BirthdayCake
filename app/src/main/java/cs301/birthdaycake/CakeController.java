package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener
{
private CakeView cakeView;
private CakeModel cakeModel;

public CakeController(CakeView cakeView){
    this.cakeView = cakeView;
    this.cakeModel = cakeView.getCakeModel();
}

    @Override
    public void onClick(View v) {
    Log.d("cake","click!");
    cakeModel.candlesLit = false;
    cakeView.invalidate();
    }

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

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}