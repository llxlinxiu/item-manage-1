package com.manage.service.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.manage.common.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xiu
 * @since 2021-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("task")
@ApiModel(value="Task对象", description="")
public class Task extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    private String itemId;

    @ApiModelProperty(value = "当前项目当日任务排序")
    private String sort;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "任务类型id")
    private String taskTypeId;

    @ApiModelProperty(value = "每人用时")
    private Integer useTime;

    @ApiModelProperty(value = "当前任务完成状态，1未完成，2已完成")
    private Integer finishStatus;

    @ApiModelProperty(value = "任务填报时间")
    private String fillDate;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;



}
