package com.hengxin.pickimg.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hengxin.pickimg.R;
import com.hengxin.pickimg.constant.Extras;
import com.hengxin.pickimg.imageview.CropImageView;
import com.hengxin.pickimg.utils.BitmapDecoder;
import com.hengxin.pickimg.utils.FileUtil;
import com.hengxin.pickimg.utils.ImageUtil;


public class CropImageActivity extends AppCompatActivity implements View.OnClickListener {
    CropImageView cropImageView;

    public static void startCropActivity(Activity activity, String srcFilePath, int width, int height, int requestCode, int maxSizelength) {
        Intent intent = new Intent(activity, CropImageActivity.class);
        intent.putExtra(Extras.photoFileSrc, srcFilePath);
        intent.putExtra(Extras.cropOutputImageWidth, width);
        intent.putExtra(Extras.cropOutputImageHeight, height);
        intent.putExtra(Extras.maxImageSize, maxSizelength);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        findViewById(R.id.title_bar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) findViewById(R.id.title_bar_title)).setText("裁剪");
        findViewById(R.id.title_bar_right_text).setVisibility(View.VISIBLE);
        findViewById(R.id.title_bar_right_text).setOnClickListener(this);
        initCropImageView();
    }

    private void initCropImageView() {
        cropImageView = findViewById(R.id.crop_image);
        Intent intent = getIntent();
        String srcFile = intent.getStringExtra(Extras.photoFileSrc);
        int outWidth = intent.getIntExtra(Extras.cropOutputImageWidth, 720);
        int outHeight = intent.getIntExtra(Extras.cropOutputImageHeight, 720);
        cropImageView.setOutput(outWidth, outHeight);

        // 抛到下一个UI循环，等到我们的activity真正到了前台
        // 否则可能会获取不到openGL的最大texture size，导致解出的bitmap过大，显示不了
        cropImageView.post(() -> {
            Bitmap src = BitmapDecoder.decodeSampledForDisplay(this, srcFile);
            src = ImageUtil.rotateBitmapInNeeded(srcFile, src);
            cropImageView.setImageBitmap(src);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.crop_text) {
            //将裁剪之后的图片放大file中
            String absolutePath = FileUtil.get().getCropFile(this).getAbsolutePath();
            int maxLength = getIntent().getIntExtra(Extras.maxImageSize, 0);
//            Log.i("getCropFile", absolutePath + "..."+maxLength);
            cropImageView.saveCroppedImage(absolutePath, maxLength);
            Intent result = new Intent();
            result.putExtra(Extras.intputPath, absolutePath);//返回裁剪图片路径
            setResult(RESULT_OK, result);
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_bar_right_text) {
            //将裁剪之后的图片放大file中
            String absolutePath = FileUtil.get().getCropFile(this).getAbsolutePath();
            int maxLength = getIntent().getIntExtra(Extras.maxImageSize, 0);
            cropImageView.saveCroppedImage(absolutePath, maxLength);
            Intent result = new Intent();
            result.putExtra(Extras.intputPath, absolutePath);//返回裁剪图片路径
            setResult(RESULT_OK, result);
            finish();
        }

    }
}
