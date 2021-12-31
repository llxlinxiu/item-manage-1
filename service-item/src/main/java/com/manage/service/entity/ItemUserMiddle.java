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
@TableName("item_user_middle")
@ApiModel(value="ItemUserMiddle对象", description="")
public class ItemUserMiddle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目id")
    private String itemId;

    @ApiModelProperty(value = "组员id")
    private String userId;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;


}
