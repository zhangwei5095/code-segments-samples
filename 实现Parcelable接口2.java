package 

import android.os.Parcel;
import android.os.Parcelable;


public class Thumb implements Parcelable {

    float posX;
    float posY;
    float width;
    float height;
    float rotation;
    float scale;
    String imgURL;

    public static final Parcelable.Creator<Thumb> CREATOR= new Parcelable.Creator<Thumb>() {

        public Thumb createFromParcel(Parcel in) {
            return new Thumb(in);
        }

        public Thumb[] newArray(int size) {
            return new Thumb[size];
        }
    };



    public Thumb(){}

    public Thumb(Parcel in) {
        posX = in.readFloat();
        posY = in.readFloat();
        width = in.readFloat();
        height = in.readFloat();
        rotation = in.readFloat();
        scale = in.readFloat();
        imgURL = in.readString();
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }


    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }


    @Override
    public int describeContents() {


        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(posX);
        dest.writeFloat(posY);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeFloat(rotation);
        dest.writeFloat(scale);
        dest.writeString(imgURL);

    }
}
