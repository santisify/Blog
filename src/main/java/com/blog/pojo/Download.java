package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

@ApiModel("下载表对象")
@Table(name = "ylb_download")
public class Download {

    @ApiModelProperty("下载信息名称")
    private String dname;
    @ApiModelProperty("下载链接")
    private String ddesc;
    @ApiModelProperty("下载码")
    private String dcode;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDdesc() {
        return ddesc;
    }

    public void setDdesc(String ddesc) {
        this.ddesc = ddesc;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }
}
