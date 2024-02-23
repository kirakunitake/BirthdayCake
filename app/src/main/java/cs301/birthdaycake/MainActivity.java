package cs301.birthdaycake;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        CakeView cakeView = findViewById(R.id.cakeview);
        CakeController cakeController = new CakeController(cakeView);

        Button candleButton = findViewById(R.id.blowOutButton);
        candleButton.setOnClickListener(cakeController);

        Switch candlesSwitch = findViewById(R.id.candlesSwitch);
        candlesSwitch.setOnCheckedChangeListener(cakeController);

        SeekBar candleSeek = findViewById(R.id.candleSeekBar);
        candleSeek.setOnSeekBarChangeListener(cakeController);
    }

    public void goodbye(View buttotn){
        Log.i("button","Goodbye");
    }
}
