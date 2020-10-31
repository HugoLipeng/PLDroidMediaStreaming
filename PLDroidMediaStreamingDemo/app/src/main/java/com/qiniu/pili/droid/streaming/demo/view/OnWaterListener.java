package com.qiniu.pili.droid.streaming.demo.view;

import android.graphics.Bitmap;

/**
 * @PackageName : com.qiniu.pili.droid.streaming.demo.view
 * @File : OnWaterListener.java
 * @Date : 2020/9/23 2020/9/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public interface OnWaterListener {

    /**
     * 绘制水印
     * @param water bitmap
     */
    void onDrawWater(Bitmap water);
}
