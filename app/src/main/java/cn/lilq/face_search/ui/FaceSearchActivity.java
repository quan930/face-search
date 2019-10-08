package cn.lilq.face_search.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lilq.face_search.MyApp;
import cn.lilq.face_search.R;
import cn.lilq.face_search.pojo.Face;
import cn.lilq.face_search.pojo.FaceAddAPI;
import cn.lilq.face_search.pojo.FaceSearchAPI;
import cn.lilq.face_search.util.Base64Util;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Response;

public class FaceSearchActivity extends AppCompatActivity {
    @BindView(R.id.search_image)
    ImageView imageView;
    @BindView(R.id.button_search)
    Button button;
    int REQUEST_IMAGE = 10;
    boolean isUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiImageSelector.create(getApplicationContext()).single().start(FaceSearchActivity.this, REQUEST_IMAGE);
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
                        FaceSearchAPI faceSearchAPI = ((MyApp)getApplication()).getClient().search(base64,"BASE64","test");
                        Log.d("百度api", "run: "+faceSearchAPI);
                        if (faceSearchAPI.getResult()!=null){
                            FaceSearchAPI.Result result = faceSearchAPI.getResult();
                            //响应人脸列表
                            List<Face> facesNew = new ArrayList<>();
                            //获取人脸列表
                            List<FaceSearchAPI.Face> faces = result.getFaceList();
                            Stream.of(faces).forEach(face -> {
                                Face faceNew = new Face();
                                //获取人脸位置
                                FaceSearchAPI.Location location = face.getLocation();
                                faceNew.setLocation(new Face.Location(location.getLeft(),location.getTop(),location.getWidth(),location.getHeight(),location.getRotation()));
                                //获取概率最大用户
                                if(face.getUserList()==null||face.getUserList().size()==0)
                                    return;
                                FaceSearchAPI.User user = Stream.of(face.getUserList()).max((o1, o2) -> o1.getScore()>=o2.getScore()?1:-1).get();
                                faceNew.setUserName(user.getInfo());
                                faceNew.setScore(user.getScore());
                                facesNew.add(faceNew);
                            });
                            Log.d("检测结果", "run: ");
                            imageView.setDrawingCacheEnabled(true);
                            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
                            imageView.setDrawingCacheEnabled(false);
                            drawRectangles(bitmap,facesNew);
                        }else {
                            runOnUiThread(()->Toast.makeText(getApplicationContext(),"检测失败",Toast.LENGTH_SHORT).show());
                        }
                    }
                }).start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            // 获取返回的图片列表(存放的是图片路径)
            if (data==null)
                return;
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // 处理你自己的逻辑 ....
            Log.d("tag", "" + path);
            File file = new File(path.get(0));
            Glide.with(this).load(file).into(imageView);
            isUpload = true;//已经上传图片
        }
    }
    private void drawRectangles(Bitmap imageBitmap,List<Face> facesNew) {
        int left, top, right, bottom;
        Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        // 获取更清晰的图像采样，防抖动
        paint.setDither(true);
        // 过滤一下，抗剧齿
        paint.setFilterBitmap(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//不填充
        paint.setStrokeWidth(5); //线的宽度
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
        textPaint.setTextSize(20);// 字体大小
        textPaint.setStrokeWidth(1);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
        textPaint.setColor(Color.GREEN);// 采用的颜色
        textPaint.setTextAlign(Paint.Align.CENTER);
        for (Face face : facesNew) {
            left = (int)Math.round(face.getLocation().getLeft());
            top = (int)Math.round(face.getLocation().getTop());
            right = (int)Math.round(face.getLocation().getWidth()+face.getLocation().getLeft());
            bottom = (int)Math.round(face.getLocation().getHeight()+face.getLocation().getTop());
            canvas.drawRect(left, top, right, bottom, paint);
            canvas.drawText(face.getUserName()+" 相识度"+(int)face.getScore()+"%",left+Math.round((face.getLocation().getWidth())/2),bottom,textPaint);
        }
        runOnUiThread(()->imageView.setImageBitmap(mutableBitmap));
    }
}
