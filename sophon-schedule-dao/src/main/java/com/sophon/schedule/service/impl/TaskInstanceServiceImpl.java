package com.sophon.schedule.service.impl;

import com.sophon.schedule.entity.TaskInstance;
import com.sophon.schedule.mapper.TaskInstanceMapper;
import com.sophon.schedule.service.ITaskInstanceService;
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
public class TaskInstanceServiceImpl extends ServiceImpl<TaskInstanceMapper, TaskInstance> implements ITaskInstanceService {

}
