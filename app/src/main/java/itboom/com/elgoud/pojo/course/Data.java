package itboom.com.elgoud.pojo.course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {
    @Expose
    @SerializedName("courses")
    Course[] courses;

    @Expose
    @SerializedName("count")
    int count;

    @Expose
    @SerializedName("currentPage")
    int currentPage;

    @Expose
    @SerializedName("firstItem")
    int firstItem;

    @Expose
    @SerializedName("hasMorePages")
    boolean hasMorePages;

    @Expose
    @SerializedName("lastItem")
    int lastItem;

    @Expose
    @SerializedName("lastPage")
    int lastPage;

    @Expose
    @SerializedName("nextPageUrl")
    String nextPageUrl;

    @Expose
    @SerializedName("onFirstPage")
    boolean onFirstPage;

    @Expose
    @SerializedName("perPage")
    int perPage;

    @Expose
    @SerializedName("previousPageUrl")
    String previousPageUrl;

    @Expose
    @SerializedName("total")
    int total;

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(int firstItem) {
        this.firstItem = firstItem;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public int getLastItem() {
        return lastItem;
    }

    public void setLastItem(int lastItem) {
        this.lastItem = lastItem;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isOnFirstPage() {
        return onFirstPage;
    }

    public void setOnFirstPage(boolean onFirstPage) {
        this.onFirstPage = onFirstPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
