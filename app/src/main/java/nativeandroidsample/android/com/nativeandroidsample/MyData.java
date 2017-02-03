package nativeandroidsample.android.com.nativeandroidsample;

/**
 * Created by qainfotech on 3/2/17.
 */

public class MyData {

    private String title;
    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    MyData(String title, int viewType){
        this.title=title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
