package activitytest.example.com.uibestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class AvatarPreview extends AppCompatActivity {

    private static final String TAG = "AvatarPreview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_preview);

        //设置预览的图片地址
        Intent intent = getIntent();
        int src = intent.getIntExtra("src", 0);
        ImageView avatarPreView = (ImageView) findViewById(R.id.avatar_preview);
        avatarPreView.setImageResource(src);
    }
}
