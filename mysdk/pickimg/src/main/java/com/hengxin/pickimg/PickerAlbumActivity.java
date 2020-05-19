package com.hengxin.pickimg;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hengxin.pickimg.constant.Extras;
import com.hengxin.pickimg.constant.RequestCode;
import com.hengxin.pickimg.crop.CropImageActivity;
import com.hengxin.pickimg.fragment.PickerAlbumFragment;
import com.hengxin.pickimg.fragment.PickerImageFragment;
import com.hengxin.pickimg.model.AlbumInfo;
import com.hengxin.pickimg.utils.CompressCallBack;
import com.hengxin.pickimg.utils.FileUtil;
import com.hengxin.pickimg.utils.PhotoUtils;

import java.io.File;

public class PickerAlbumActivity extends AppCompatActivity {
    //0相冊 1,相冊詳情  数据带回需要清楚缓存
    public static int CropMaxLenght = 50;//裁剪大小 50kb
    public static int Cropheight = 1080;//默认屏幕宽度
    public static int Cropwidth = 1080;//默认屏幕高度 默认设置宽高
    private android.support.v4.app.Fragment fragment;
    private View pemissText;
    private boolean inited;
    private static final String KEY_STATE = "state";
    private String outPath;
    private int maxSizeLength;
    private int height;
    private int width;

    public static void startActivity(Activity activity, int type, int wayType, AlbumInfo albumInfo, int requestCode, boolean noCut) {
        Intent intent = new Intent(activity, PickerAlbumActivity.class);
        intent.putExtra(Extras.albumTypeKey, type);
        intent.putExtra(Extras.albumWay, wayType);
        intent.putExtra(Extras.maxImageSize, CropMaxLenght);
        intent.putExtra(Extras.cropOutputImageHeight, Cropheight);
        intent.putExtra(Extras.cropOutputImageWidth, Cropwidth);
        intent.putExtra("noCut", noCut);
        if (albumInfo != null) intent.putExtra("album_type_value", albumInfo);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_album);
        pemissText = findViewById(R.id.picker_album_permission);
        findViewById(R.id.title_bar_back).setOnClickListener(v -> finish());
        ((TextView) findViewById(R.id.title_bar_title)).setText("相册");
        processIntent(savedInstanceState);
    }

    private boolean noCut;

    private void processIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int from = intent.getIntExtra(Extras.albumWay, RequestCode.PickPhoteCode);
        maxSizeLength = intent.getIntExtra(Extras.maxImageSize, 0);
        height = intent.getIntExtra(Extras.cropOutputImageHeight, 0);
        width = intent.getIntExtra(Extras.cropOutputImageWidth, 0);
        noCut = intent.getBooleanExtra("noCut", false);
        if (from == RequestCode.PickPhoteCode) {
            openPhono();
        } else {
            if (savedInstanceState != null)
                inited = savedInstanceState.getBoolean(KEY_STATE);
            if (!inited) {
                inited = true;
                pickFromCamera();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_STATE, inited);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inited = savedInstanceState.getBoolean(KEY_STATE);
    }

    private void pickFromCamera() {
        try {
            outPath = FileUtil.get().getCropFile(this).getAbsolutePath();
            File outputFile = new File(outPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT > 23) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.hengxin.pickimg.fileProvider", outputFile));
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
            }
            startActivityForResult(intent, RequestCode.PickTakePhoteCode);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void openPhono() {
        pemissText.setVisibility(View.GONE);
        findViewById(R.id.picker_album_fl).setVisibility(View.VISIBLE);
        selectFragment();

    }


    private void selectFragment() {
        int album_type_key = getIntent().getIntExtra(Extras.albumTypeKey, 0);
        if (fragment != null) {
            return;
        }
        if (album_type_key == 0) {
            fragment = new PickerAlbumFragment();
        } else {
            fragment = new PickerImageFragment();
            Bundle bundle = new Bundle();
//            bundle.putSerializable("album_type_value", getIntent().getSerializableExtra("album_type_value"));
            bundle.putBoolean("noCut", noCut);
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.picker_album_fl, fragment).commit();
//        picker_album_fl
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        switch (requestCode) {
            case RequestCode.PickPhoteCode:
                pickFromLocal(data);//裁剪成功
                break;
            case RequestCode.PickTakePhoteCode:
                onPickedCamera(data, requestCode);//照片得到返回
                break;
            case RequestCode.PickTakePhoteToCropCode:
                pickFromLocal(data);// 相机图片 裁剪成功
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void pickFromLocal(Intent data) {
        Intent intent = new Intent(data);
        String stringExtra = data.getStringExtra(Extras.intputPath);//裁剪目录
//裁剪目录
        setResult(RESULT_OK, intent);
        finish(); //接受本地相册剪切对象
    }

    private void onPickedCamera(Intent data, int requestCode) {
        //从相机中获取data数据
        String photoPath = pathFromResult(data);
        if (noCut) {
            Log.i("fflin", "photoPath 1 = " + photoPath);
            PhotoUtils.qualityCompress(this, photoPath, new CompressCallBack() {

                @Override
                public void onCompressSucc(String filePath) {
                    Intent result = new Intent();
                    result.putExtra(Extras.intputPath, filePath);//返回裁剪图片路径
                    setResult(RESULT_OK, result);
                    finish();
                }

                @Override
                public void onCompressFiled(String message) {
                    Log.i("fflin", "压缩图片失败------------------" + message);
                }
            });

        } else {
            CropImageActivity.startCropActivity(this, photoPath, width, height, RequestCode.PickTakePhoteToCropCode, maxSizeLength);
        }
    }

    private String pathFromResult(Intent data) {
        if (data == null || data.getData() == null) {
            return outPath;
        }

        Uri uri = data.getData();
        Cursor cursor = getContentResolver()
                .query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor == null) {
            // miui 2.3 有可能为null
            return uri.getPath();
        } else {
            if (uri.toString().contains("content://com.android.providers.media.documents/document/image")) { // htc 某些手机
                // 获取图片地址
                String _id = null;
                String uridecode = uri.decode(uri.toString());
                int id_index = uridecode.lastIndexOf(":");
                _id = uridecode.substring(id_index + 1);
                Cursor mcursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, " _id = " + _id,
                        null, null);
                mcursor.moveToFirst();
                int column_index = mcursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                outPath = mcursor.getString(column_index);
                if (!mcursor.isClosed()) {
                    mcursor.close();
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return outPath;

            } else {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                outPath = cursor.getString(column_index);
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return outPath;
            }
        }
    }

}
