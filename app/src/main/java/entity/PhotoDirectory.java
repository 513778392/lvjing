package entity;

import android.graphics.Bitmap;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

public class PhotoDirectory {

  private String id;
  private String coverPath;
  private String name;
  private long   dateAdded;
  Bitmap bitmap;

  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }


  boolean isXuan;

  public boolean isXuan() {
    return isXuan;
  }

  public void setXuan(boolean xuan) {
    isXuan = xuan;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PhotoDirectory)) return false;

    PhotoDirectory directory = (PhotoDirectory) o;

    boolean hasId = !TextUtils.isEmpty(id);
    boolean otherHasId = !TextUtils.isEmpty(directory.id);

    if (hasId && otherHasId) {
      if (!TextUtils.equals(id, directory.id)) {
        return false;
      }

      return TextUtils.equals(name, directory.name);
    }

    return false;
  }

  @Override public int hashCode() {
    if (TextUtils.isEmpty(id)) {
      if (TextUtils.isEmpty(name)) {
        return 0;
      }

      return name.hashCode();
    }

    int result = id.hashCode();

    if (TextUtils.isEmpty(name)) {
      return result;
    }

    result = 31 * result + name.hashCode();
    return result;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCoverPath() {
    return coverPath;
  }

  public void setCoverPath(String coverPath) {
    this.coverPath = coverPath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(long dateAdded) {
    this.dateAdded = dateAdded;
  }


  @Override
  public String toString() {
    return "PhotoDirectory{" +
            "id='" + id + '\'' +
            ", coverPath='" + coverPath + '\'' +
            ", name='" + name + '\'' +
            ", dateAdded=" + dateAdded +
            '}';
  }
}
