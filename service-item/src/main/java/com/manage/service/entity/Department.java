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
@TableName("department")
@ApiModel(value="Department对象", description="")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门的中文名称，第三方应用获取不到")
    private String name;

    @ApiModelProperty(value = "部门的英文名称，第三方应用获取不到")
    private String nameEn;

    @ApiModelProperty(value = "父部门id。根部门为1")
    private String parentId;

    @ApiModelProperty(value = "在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)")
    private String departmentOrder;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;


}
