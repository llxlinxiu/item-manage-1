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
@TableName("user")
@ApiModel(value="User对象", description="")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业微信员工id")
    private String qyWxUserId;

    @ApiModelProperty(value = "员工姓名")
    private String name;

    @ApiModelProperty(value = "主部门")
    private String mainDepartment;

    @ApiModelProperty(value = "部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。")
    private String userOrder;

    @ApiModelProperty(value = "职务信息")
    private String userPosition;

    @ApiModelProperty(value = "性别。0表示未定义，1表示男性，2表示女性")
    private Integer gender;

    @ApiModelProperty(value = "头像url")
    private String avatar;

    @ApiModelProperty(value = "激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。")
    private Integer status;

    @ApiModelProperty(value = "1、没封禁； 2、被封禁")
    private Integer banned;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;


}
