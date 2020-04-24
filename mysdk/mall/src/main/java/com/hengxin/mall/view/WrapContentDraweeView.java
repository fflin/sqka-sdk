package com.hengxin.mall.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.hengxin.mall.R;
import com.hengxin.mall.utils.ViewUtil;
import com.youth.banner.Banner;

public class WrapContentDraweeView extends SimpleDraweeView {
    private BaseControllerListener<ImageInfo> mBaseControllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            setAspectRatio(imageInfo);
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            setAspectRatio(imageInfo);
        }
    };

    public WrapContentDraweeView(Context context) {
        super(context);
    }

    public WrapContentDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageURI(Uri uri, Object callerContext) {
        setBackgroundColor(0xffffffff);
        getHierarchy().setPlaceholderImage(R.drawable.placeholder_rectangle, ScalingUtils.ScaleType.FIT_CENTER);
        setController(((PipelineDraweeControllerBuilder) getControllerBuilder()).setControllerListener(mBaseControllerListener).setCallerContext(callerContext).setUri(uri).setOldController(getController()).build());
    }

    private void setAspectRatio(ImageInfo imageInfo) {
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (imageInfo != null) {
            float aspectRatio = (float) imageInfo.getWidth() / imageInfo.getHeight();
            if ("first".equals(getTag())) {
                setTag(null);
                try {
                    ViewParent parent1 = getParent();
                    ViewParent parent2 = parent1.getParent();
                    ViewParent parent3 = parent2.getParent();
                    if (parent3 instanceof Banner) {
                        int height = (int) (ViewUtil.getScreenWidth() / aspectRatio);
                        ViewGroup.LayoutParams layoutParams = ((View) parent3).getLayoutParams();
                        layoutParams.height = height;
                        ((Banner) parent3).setTag(height);
                    }
                } catch (Exception e) {

                }
            }
            setAspectRatio(aspectRatio);
        }
    }
}
