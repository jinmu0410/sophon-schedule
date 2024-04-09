package com.sophon.schedule.api.ds;

import com.github.weaksloth.dolphins.core.DolphinClient;
import com.github.weaksloth.dolphins.process.ProcessDefineParam;
import com.github.weaksloth.dolphins.process.ProcessDefineResp;
import com.github.weaksloth.dolphins.process.TaskDefinition;
import com.github.weaksloth.dolphins.schedule.ScheduleDefineParam;
import com.github.weaksloth.dolphins.schedule.ScheduleInfoResp;
import com.github.weaksloth.dolphins.task.DinkyTask;
import com.github.weaksloth.dolphins.task.ShellTask;
import com.github.weaksloth.dolphins.util.TaskDefinitionUtils;
import com.github.weaksloth.dolphins.util.TaskLocationUtils;
import com.github.weaksloth.dolphins.util.TaskRelationUtils;

import java.util.Collections;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/1 14:06
 */
public class ShellTaskTest {

    public static void main(String[] args) {
        Long projectCode = 13132483984736l;
        BeanConfig beanConfig = new BeanConfig();
        DolphinClient client = beanConfig.dolphinClient();
        Long taskCode = client.opsForProcess().generateTaskCode(projectCode, 1).get(0);

        //1.任务定义
        DinkyTask dinkyTask = new DinkyTask();
        dinkyTask.setAddress("http://192.168.201.54:7888");
        dinkyTask.setTaskId("353");
        dinkyTask.setOnline(false);

        TaskDefinition taskDefinition =
                TaskDefinitionUtils.createDefaultTaskDefinition(taskCode, dinkyTask);

        //2.任务流
        ProcessDefineResp processDefineResp = submit(client, taskCode, taskDefinition, "456-test-dinky-task-dag", "test-dinky-task");
        System.out.println("000=== " + processDefineResp);


        //3.上线任务流
        Boolean online = client.opsForProcess().online(projectCode, processDefineResp.getCode());
        System.out.println("上线完成" + online);

        //4.调度任务流
//        ScheduleDefineParam scheduleDefineParam = new ScheduleDefineParam();
//        scheduleDefineParam.setProcessDefinitionCode(processDefineResp.getCode());
//        ScheduleDefineParam.Schedule schedule = new ScheduleDefineParam.Schedule();
//        schedule.setCrontab("0 0 0/6 * * ?");
//        schedule.setStartTime("2024-01-01 00:00:00");
//        schedule.setEndTime("2034-01-01 00:00:00");
//        schedule.setTimezoneId("Asia/Shanghai");
//
//        scheduleDefineParam.setSchedule(schedule);
//        ScheduleInfoResp scheduleInfoResp = client.opsForSchedule().create(projectCode, scheduleDefineParam);
//        System.out.println("1111=== " + scheduleInfoResp);
    }

    private static ProcessDefineResp submit(DolphinClient client,
            Long taskCode, TaskDefinition taskDefinition, String processName, String description) {
        ProcessDefineParam pcr = new ProcessDefineParam();
        pcr.setName(processName)
                .setLocations(TaskLocationUtils.verticalLocation(taskCode))
                .setDescription(description)
                .setTenantCode("default")
                .setTimeout("0")
                .setExecutionType(ProcessDefineParam.EXECUTION_TYPE_PARALLEL)
                .setTaskDefinitionJson(Collections.singletonList(taskDefinition))
                .setTaskRelationJson(TaskRelationUtils.oneLineRelation(taskCode))
                .setGlobalParams(null);

        ProcessDefineResp resp = client.opsForProcess().create(12811250008512l, pcr);

        return resp;
    }
}
