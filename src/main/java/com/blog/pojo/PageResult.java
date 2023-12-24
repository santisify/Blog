package com.blog.pojo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页结果集
 * @param <T>
 */
@ApiModel("分页对象")
public class PageResult<T> {

    @ApiModelProperty("总条数")
    private Long total;//总条数
    @ApiModelProperty("总页数")
    private Integer totalpage;//总页数
    @ApiModelProperty("分页信息对象")
    private PageInfo pageInfo;
    @ApiModelProperty("数据对象集合")
    private List<T> datas;

    public PageResult() {
    }

    public PageResult(Long total, List<T> datas) {
        this.total = total;
        this.datas = datas;
    }

    public PageResult(Long total, Integer totalpage, List<T> datas) {
        this.total = total;
        this.totalpage = totalpage;
        this.datas = datas;
    }

    public PageResult(PageInfo pageInfo, List<T> datas) {
        this.pageInfo = pageInfo;
        this.datas = datas;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
