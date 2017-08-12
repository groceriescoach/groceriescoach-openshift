package com.groceriescoach.babyvillage.domain;

import java.io.Serializable;

public class BabyVillageRequestParameters implements Serializable {
    private static final long serialVersionUID = -22234601566794273L;

    //  {"page":1,"pageSize":30,"sort":"BrandAsc","filters":[],"keyword":"car"}

    private int page;
    private int pageSize;
    private String sort;
    private String keyword;

    private BabyVillageRequestParameters(int page, int pageSize, String sort, String keywords) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.keyword = keywords;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static BabyVillageRequestParameters createParameters(String keywords, int page) {
        return new BabyVillageRequestParameters(page, 200, "BrandAsc", keywords);
    }
}
