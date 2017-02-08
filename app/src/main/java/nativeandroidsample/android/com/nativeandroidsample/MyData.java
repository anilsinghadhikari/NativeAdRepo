package nativeandroidsample.android.com.nativeandroidsample;

import android.graphics.drawable.Drawable;

/**
 * Created by qainfotech on 3/2/17.
 */

public class MyData {

    private String subtitle;
    private Drawable drawable;
    private String headline;
    private int viewType;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    MyData(String headline, String subtitle, Drawable drawable, int viewType){
        this.headline =headline;
        this.subtitle =subtitle;
        this.drawable =drawable;
        this.viewType=viewType;

    }


    MyData(int viewType){
        this.headline =headline;
        this.viewType=viewType;

    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
