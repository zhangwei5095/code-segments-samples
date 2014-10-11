package

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class SpriteInfo implements Parcelable {

    int screenWidth;

    List<Thumb> thumb;

    String thumbURL;



    public List<Thumb> getThumb() {
        return thumb;
    }

    public void setThumb(List<Thumb> thumb) {
        this.thumb = thumb;
    }



    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }


    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeList(thumb);
        dest.writeString(thumbURL);

    }


    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public SpriteInfo createFromParcel(Parcel in) {
            return new SpriteInfo(in);
        }

        public SpriteInfo[] newArray(int size) {
            return new SpriteInfo[size];
        }
    };


    public SpriteInfo(){}

    public SpriteInfo(Parcel in) {
        in.readList(thumb, Thumb.class.getClassLoader());
        thumbURL = in.readString();
    }
}
