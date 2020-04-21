package com.hengxin.mall.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Administrator on 2018/1/9.
 */

public class FrcosUtils {
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath, final int imageWidth, boolean isGif) {
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                int height = imageInfo.getHeight();
                int width = imageInfo.getWidth();
                if (layoutParams != null) {
                    layoutParams.width = imageWidth;
                    layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
                    simpleDraweeView.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                Log.d("TAG", "Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        PipelineDraweeController controller = null;
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imagePath))
                .setProgressiveRenderingEnabled(true)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true);
        PipelineDraweeControllerBuilder pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        if (pipelineDraweeControllerBuilder != null) {
            controller = (PipelineDraweeController) pipelineDraweeControllerBuilder.setControllerListener(controllerListener).setImageRequest(imageRequestBuilder.build())
                    .setUri(Uri.parse(imagePath))
                    .setAutoPlayAnimations(true).setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(controller);
        }
    }

    public static void loadForUrlBitmap(String ImgUrl, LoadFrescoBitmap loadFrescoBitmap, Context context) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(ImgUrl))
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {

            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                Bitmap resultBitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
                loadFrescoBitmap.onNewResultImpl(resultBitmap);
            }
            @Override
            public void onFailureImpl(DataSource dataSource) {
                loadFrescoBitmap.onFailureImpl();
            }
        }, CallerThreadExecutor.getInstance());
    }

   public interface LoadFrescoBitmap {
        void onNewResultImpl(Bitmap bitmap);

        void onFailureImpl();
    }
}
