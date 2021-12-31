package com.manage.service.service.impl;

import com.manage.service.entity.Task;
import com.manage.service.mapper.TaskMapper;
import com.manage.service.service.TaskService;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
