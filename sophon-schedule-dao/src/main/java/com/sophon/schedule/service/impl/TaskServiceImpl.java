package com.sophon.schedule.service.impl;

import com.sophon.schedule.entity.Task;
import com.sophon.schedule.mapper.TaskMapper;
import com.sophon.schedule.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinmu
 * @since 2023-08-14
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
