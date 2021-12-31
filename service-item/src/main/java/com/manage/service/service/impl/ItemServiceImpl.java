package com.manage.service.service.impl;

import com.manage.service.entity.Item;
import com.manage.service.mapper.ItemMapper;
import com.manage.service.service.ItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiu
 * @since 2021-12-31
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
