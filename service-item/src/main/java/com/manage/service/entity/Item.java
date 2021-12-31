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
@TableName("item")
@ApiModel(value="Item对象", description="")
public class Item extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目开始时间")
    private String startDate;

    @ApiModelProperty(value = "项目结束时间")
    private String endDate;

    @ApiModelProperty(value = "项目负责人id")
    private String userId;

    @ApiModelProperty(value = "项目状态，1开始，2进行中，已完成")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;


}
