package com.ruoshui.flink.streaming.web.model.entity;

import com.ruoshui.flink.streaming.web.enums.IpStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data
public class IpStatus implements Serializable {

    private Long id;

    /**
     * ip
     */
    private String ip;

    /**
     * 1:运行 2:停止
     *
     * @see IpStatusEnum
     */
    private Integer status;

    /**
     * 最后一次启动时间
     */
    private Date lastTime;


    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date editTime;

    private String creator;

    private String editor;

    private static final long serialVersionUID = 1L;

}
