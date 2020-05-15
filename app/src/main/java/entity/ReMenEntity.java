package entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class ReMenEntity {
    String endRow;
    String firstPage;
    boolean hasNextPage;
    boolean hasPreviousPage;
    boolean isFirstPage;
    boolean isLastPage;
    int lastPage;
    List<RenMenItemEntity> list;
    String navigateFirstPage;
    String navigateLastPage;
    String navigatePages;
    String[] navigatepageNums;
    String nextPage;
    String orderBy;
    String pageSize;
    String pages;
    String prePage;
    String size;
    String startRow;
    String total;

    @Override
    public String toString() {
        return "ReMenEntity{" +
                "endRow='" + endRow + '\'' +
                ", firstPage='" + firstPage + '\'' +
                ", hasNextPage=" + hasNextPage +
                ", hasPreviousPage=" + hasPreviousPage +
                ", isFirstPage=" + isFirstPage +
                ", isLastPage=" + isLastPage +
                ", lastPage=" + lastPage +
                ", list=" + list +
                ", navigateFirstPage='" + navigateFirstPage + '\'' +
                ", navigateLastPage='" + navigateLastPage + '\'' +
                ", navigatePages='" + navigatePages + '\'' +
                ", navigatepageNums=" + Arrays.toString(navigatepageNums) +
                ", nextPage='" + nextPage + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pages='" + pages + '\'' +
                ", prePage='" + prePage + '\'' +
                ", size='" + size + '\'' +
                ", startRow='" + startRow + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<RenMenItemEntity> getList() {
        return list;
    }

    public void setList(List<RenMenItemEntity> list) {
        this.list = list;
    }

    public String getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(String navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public String getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(String navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public String getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(String navigatePages) {
        this.navigatePages = navigatePages;
    }

    public String[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(String[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
