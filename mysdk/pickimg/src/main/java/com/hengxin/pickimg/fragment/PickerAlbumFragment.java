package com.hengxin.pickimg.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hengxin.pickimg.DataHolder;
import com.hengxin.pickimg.PickerAlbumActivity;
import com.hengxin.pickimg.R;
import com.hengxin.pickimg.adapter.PickerAlbumAdapter;
import com.hengxin.pickimg.constant.Extras;
import com.hengxin.pickimg.constant.RequestCode;
import com.hengxin.pickimg.dao.MediaDAO;
import com.hengxin.pickimg.model.AlbumInfo;
import com.hengxin.pickimg.model.PhotoInfo;
import com.hengxin.pickimg.utils.ThumbnailsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * author: Y_Qing
 *  相册
 * date: 2018/12/28
 */
public class PickerAlbumFragment extends BaseFragment {
    View loadingLay;
    TextView loadingTips, loadingEmpty;
    private List<AlbumInfo> albumInfolist;
    private RecyclerView recyclerView;
    public static final String FILE_PREFIX = "file://";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_picker_album, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        statScanImage();
    }

    private void statScanImage() {
        new ImageScanAsyncTask().execute();
    }

    private void initView() {
        recyclerView = findView(R.id.picker_image_folder_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        loadingLay = findView(R.id.picker_image_folder_loading);
        loadingTips = findView(R.id.picker_image_folder_loading_tips);
        loadingEmpty = findView(R.id.picker_image_folder_loading_empty);
    }


    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    private class ImageScanAsyncTask extends AsyncTask<Void, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (albumInfolist == null) albumInfolist = new ArrayList<>();
            else albumInfolist.clear();
        }

        @Override
        protected Object doInBackground(Void... params) {
            getAllMediaThumbnails();
            getAllMediaPhotos();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (context != null && albumInfolist != null) {
                PickerAlbumAdapter albumAdapter = new PickerAlbumAdapter(albumInfolist, onClickListener);
                recyclerView.setAdapter(albumAdapter);
                if (albumInfolist.size() > 0) {
                    loadingLay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    loadingLay.setVisibility(View.VISIBLE);
                    loadingTips.setVisibility(View.GONE);
                    loadingEmpty.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void getAllMediaPhotos() {
        Cursor cursorPhotos = null;
        try {
            cursorPhotos = MediaDAO.getAllMediaPhotos(context);
            HashMap<String, AlbumInfo> hash = new HashMap<>();
            AlbumInfo albumInfo;
            PhotoInfo photoInfo;
            if (cursorPhotos != null && cursorPhotos.moveToFirst()) {
                do {
                    int index = 0;
                    int _id = cursorPhotos.getInt(cursorPhotos.getColumnIndex(MediaStore.Images.Media._ID));
                    String path = cursorPhotos.getString(cursorPhotos.getColumnIndex(MediaStore.Images.Media.DATA));
                    String album = cursorPhotos.getString(cursorPhotos.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    long size = cursorPhotos.getLong(cursorPhotos.getColumnIndex(MediaStore.Images.Media.SIZE));
                    if (!isValidImageFile(path)) {
                        Log.d("PICKER", "it is not a vaild path:" + path);
                        continue;
                    }
                    photoInfo = new PhotoInfo();
                    if (hash.containsKey(album)) {
                        albumInfo = hash.get(album);
                        if (albumInfolist.contains(albumInfo))
                            index = albumInfolist.indexOf(albumInfo);
                        photoInfo.imageId = _id;
                        photoInfo.filePath = FILE_PREFIX + path;
                        photoInfo.absolutePath = path;
                        photoInfo.size = size;
                        albumInfo.list.add(photoInfo);
                        albumInfolist.set(index, albumInfo);
                        hash.put(album, albumInfo);
                    } else {
                        albumInfo = new AlbumInfo();
                        List<PhotoInfo> photoList = new ArrayList<>();
                        photoInfo.imageId = _id;
                        photoInfo.filePath = FILE_PREFIX + path;
                        photoInfo.absolutePath = path;
                        photoInfo.size = size;
                        photoList.add(photoInfo);
                        albumInfo.imageID = _id;
                        albumInfo.pathFile = (FILE_PREFIX + path);
                        albumInfo.pathAbsolute = path;
                        albumInfo.nameAlbum = album;
                        albumInfo.list = photoList;
                        albumInfolist.add(albumInfo);
                        hash.put(album, albumInfo);
                    }
                } while (cursorPhotos.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursorPhotos != null) {
                    cursorPhotos.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private void getAllMediaThumbnails() {
        ThumbnailsUtil.clear();
        Cursor cursorThumb = null;
        try {
            cursorThumb = MediaDAO.getAllMediaThumbnails(context);
            if (cursorThumb != null && cursorThumb.moveToFirst()) {
                int imageID;
                String imagePath;
                do {
                    imageID = cursorThumb.getInt(cursorThumb.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
                    imagePath = cursorThumb.getString(cursorThumb.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    ThumbnailsUtil.put(imageID, FILE_PREFIX + imagePath);
                } while (cursorThumb.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursorThumb != null) {
                    cursorThumb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidImageFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }

    private View.OnClickListener onClickListener = v -> {
        AlbumInfo albumInfo = (AlbumInfo) v.getTag();
        // albumInfo 对象不再通过intent传递，避免数据量过大导致TransactionTooLargeException
        DataHolder.getInstance().setData(Extras.transAlbumKey,albumInfo);
        PickerAlbumActivity.startActivity(context, 1, RequestCode.PickPhoteCode, null, RequestCode.PickPhoteCode,context.getIntent().getBooleanExtra("noCut",false));
    };

}
