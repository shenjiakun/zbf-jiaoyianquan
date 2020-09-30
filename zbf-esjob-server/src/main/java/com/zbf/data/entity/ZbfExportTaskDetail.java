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
public class ZbfExportTaskDetail implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 明细的ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务的分片ID
     */
    private Integer shardId;

    /**
     * 任务头的ID
     */
    private Integer taskHeaderId;

    /**
     * 明细执行的任务SQL
     */
    private String taskSql;

    /**
     * 明细数据生成的Excel的名字
     */
    private String fileName;

    /**
     * 明细数据生成的Excel的地址  （这里是存储在Minio服务的地址）
     */
    private String filePath;


}
