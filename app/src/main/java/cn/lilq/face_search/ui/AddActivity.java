package cn.lilq.face_search.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lilq.face_search.MyApp;
import cn.lilq.face_search.R;
import cn.lilq.face_search.pojo.FaceAddAPI;
import cn.lilq.face_search.util.Base64Util;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddActivity extends AppCompatActivity {
    @BindView(R.id.edit_name)
    EditText editText;
    @BindView(R.id.face_img)
    ImageView imageView;
    @BindView(R.id.face_add)
    Button button;
    int REQUEST_IMAGE = 10;
    boolean isUpload = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(getApplicationContext()).single().start(AddActivity.this, REQUEST_IMAGE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有图片上传
                imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
                imageView.setDrawingCacheEnabled(false);
                String base64 = Base64Util.bitmapToBase64(bitmap);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FaceAddAPI faceAddAPI = ((MyApp)getApplication()).getClient().add(base64,"BASE64","test", UUID.randomUUID().toString().replaceAll("-",""),editText.getText().toString());
                        Log.d("百度api", "run: "+faceAddAPI);
                        runOnUiThread(() -> {
                            if (faceAddAPI.getErrorMsg().equals("SUCCESS")){
                                Toast.makeText(getApplicationContext(),"提交成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data==null)
                return;
            // 获取返回的图片列表(存放的是图片路径)
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // 处理你自己的逻辑 ....
            Log.d("tag", "" + path);
            File file = new File(path.get(0));
            Glide.with(this).load(file).into(imageView);
            isUpload = true;//已经上传图片
        }
    }
}
