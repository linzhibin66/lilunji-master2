package com.dgcheshang.cheji.netty.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Base64;
import com.dgcheshang.cheji.CjApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/16 0016.
 */
public class ImageMarkUtil {

    public static int f_size=14;
    public static int jg=3;
    public static int f_color= Color.RED;
    public static int ysbl=55;
    public static int ysbl2=75;

    /**
     * 设置水印图片在左上角
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                dp2px(context, paddingLeft), dp2px(context, paddingTop));
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 设置水印图片在右下角
     * @param
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
     * @param
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingTop) {
        return createWaterMaskBitmap( src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                dp2px(context, paddingTop));
    }

    /**
     * 设置水印图片到左下角
     * @param
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, dp2px(context, paddingLeft),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到中间
     * @param
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 给图片添加文字到左上角
     * @param context
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到右下角
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color

     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到右上方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text,
                                            int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到左下方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到中间
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text,
                                          int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 缩放图片
     * @param src
     * @param w
     * @param h
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * 给图片添加水印
     */
    public static byte[] picMark(String picpath,String mark){
        Context context=CjApplication.getInstance();

        try {
            //Bitmap bitmap = base64ToBitmap(src_base64);

            Bitmap bitmap = BitmapFactory.decodeFile(picpath);
            bitmap=bitmapCompress(bitmap);
            String[] ss=mark.split(";");
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
            paint.setColor(f_color);
            paint.setTextSize(f_size);
            for(int i=0;i<ss.length;i++){
                String[] ss1=ss[i].split(",");
                if(i==0){
                    //左上文字水印
                    for(int j=0;j<ss1.length;j++){
                        Rect bounds = new Rect();
                        paint.getTextBounds(ss1[j], 0, ss1[j].length(), bounds);
                        bitmap= drawTextToBitmap(context, bitmap, ss1[j], paint, bounds,
                                jg,
                                (f_size+jg)*(j+1));

                    }
                }
                if(i==1){
                    for(int j=ss1.length-1;j>=0;j--){
                        Rect bounds = new Rect();
                        paint.getTextBounds(ss1[j], 0, ss1[j].length(), bounds);
                        bitmap= drawTextToBitmap(context, bitmap, ss1[j], paint, bounds,
                                jg,
                                bitmap.getHeight() - (f_size+jg)*j-jg);
                    }
                }

                if(i==2){
                    for(int j=ss1.length-1;j>=0;j--){
                        Rect bounds = new Rect();
                        paint.getTextBounds(ss1[j], 0, ss1[j].length(), bounds);
                        bitmap=drawTextToBitmap(context, bitmap, ss1[j], paint, bounds,
                                bitmap.getWidth() - bounds.width() - jg,
                                bitmap.getHeight() - (f_size+jg)*j-jg);
                    }
                }

            }
           /* String str=bitmapToBase64(bitmap);
            str=str.replaceAll("\r|\n", "");*/
            return bitmap2Bytes(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, ysbl2, baos);
        return baos.toByteArray();
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, ysbl2, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {

        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ByteArrayOutputStream baos = null;
        if (bitmap != null) {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, ysbl2, baos);

            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

            try {
                baos.flush();
                baos.close();
                isBm.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * bitmap压缩
     * @param
     * @return
     */
    public static Bitmap bitmapCompress(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        if (bitmap != null) {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, ysbl, baos);

            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

            try {
                baos.flush();
                baos.close();
                isBm.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }



}
