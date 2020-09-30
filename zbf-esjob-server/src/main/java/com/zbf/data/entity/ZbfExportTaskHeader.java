package com.zbf.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wts
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZbfExportTaskHeader implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 任务头ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 版本
     */
    private String version;

    /**
     * 操作人
     */
    private Integer userId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务状态  默认状态0准备状态  1表示进行中   2表示已完成
     */
    private Integer taskStatus;

    /**
     * 任务明细的个数
     */
    private Integer taskTailNum;

    /**
     * 任务明细完成的个数
     */
    private Integer taskTailOkNum;

    /**
     * 任务的SQL
     */
    private String taskSql;

    /**
     * 创建时间
     */
    private String createTime;


}
