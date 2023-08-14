package com.sophon.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinmu
 * @since 2023-08-14
 */
@TableName("task_instance")
@ApiModel(value = "TaskInstance对象", description = "")
public class TaskInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务实例id")
    private String id;

    @ApiModelProperty("实例名称")
    private String taskInstanceName;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("父类实例id")
    private String parentTaskInstanceId;

    @ApiModelProperty("实例节点类型（单任务、任务流、任务流节点...）")
    private Integer taskInstanceNodeType;

    @ApiModelProperty("实例类型datax、flink、spark")
    private Integer taskInstanceType;

    @ApiModelProperty("运行类型（周期、手动、测试）")
    private Integer runningType;

    @ApiModelProperty("实例状态（未运行、已提交、运行中等等）")
    private Integer taskInstanceStatus;

    @ApiModelProperty("优先级")
    private Integer taskInstancePriority;

    @ApiModelProperty("环境参数")
    private String envParams;

    @ApiModelProperty("任务参数")
    private String taskParams;

    @ApiModelProperty("是否空跑标识")
    private Integer mock;

    @ApiModelProperty("业务时间")
    private LocalDateTime bizTime;

    @ApiModelProperty("触发时间")
    private LocalDateTime triggerTime;

    @ApiModelProperty("下一次触发时间")
    private LocalDateTime nextTriggerTime;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("重试次数")
    private Integer retryTimes;

    @ApiModelProperty("重试时间间隔（秒）")
    private Integer intervalTime;

    @ApiModelProperty("当前触发次数")
    private Integer currentRetryTimes;

    @ApiModelProperty("applicationId")
    private String applicationId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("版本")
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskInstanceName() {
        return taskInstanceName;
    }

    public void setTaskInstanceName(String taskInstanceName) {
        this.taskInstanceName = taskInstanceName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getParentTaskInstanceId() {
        return parentTaskInstanceId;
    }

    public void setParentTaskInstanceId(String parentTaskInstanceId) {
        this.parentTaskInstanceId = parentTaskInstanceId;
    }

    public Integer getTaskInstanceNodeType() {
        return taskInstanceNodeType;
    }

    public void setTaskInstanceNodeType(Integer taskInstanceNodeType) {
        this.taskInstanceNodeType = taskInstanceNodeType;
    }

    public Integer getTaskInstanceType() {
        return taskInstanceType;
    }

    public void setTaskInstanceType(Integer taskInstanceType) {
        this.taskInstanceType = taskInstanceType;
    }

    public Integer getRunningType() {
        return runningType;
    }

    public void setRunningType(Integer runningType) {
        this.runningType = runningType;
    }

    public Integer getTaskInstanceStatus() {
        return taskInstanceStatus;
    }

    public void setTaskInstanceStatus(Integer taskInstanceStatus) {
        this.taskInstanceStatus = taskInstanceStatus;
    }

    public Integer getTaskInstancePriority() {
        return taskInstancePriority;
    }

    public void setTaskInstancePriority(Integer taskInstancePriority) {
        this.taskInstancePriority = taskInstancePriority;
    }

    public String getEnvParams() {
        return envParams;
    }

    public void setEnvParams(String envParams) {
        this.envParams = envParams;
    }

    public String getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(String taskParams) {
        this.taskParams = taskParams;
    }

    public Integer getMock() {
        return mock;
    }

    public void setMock(Integer mock) {
        this.mock = mock;
    }

    public LocalDateTime getBizTime() {
        return bizTime;
    }

    public void setBizTime(LocalDateTime bizTime) {
        this.bizTime = bizTime;
    }

    public LocalDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(LocalDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public LocalDateTime getNextTriggerTime() {
        return nextTriggerTime;
    }

    public void setNextTriggerTime(LocalDateTime nextTriggerTime) {
        this.nextTriggerTime = nextTriggerTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getCurrentRetryTimes() {
        return currentRetryTimes;
    }

    public void setCurrentRetryTimes(Integer currentRetryTimes) {
        this.currentRetryTimes = currentRetryTimes;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TaskInstance{" +
            "id = " + id +
            ", taskInstanceName = " + taskInstanceName +
            ", taskId = " + taskId +
            ", parentTaskInstanceId = " + parentTaskInstanceId +
            ", taskInstanceNodeType = " + taskInstanceNodeType +
            ", taskInstanceType = " + taskInstanceType +
            ", runningType = " + runningType +
            ", taskInstanceStatus = " + taskInstanceStatus +
            ", taskInstancePriority = " + taskInstancePriority +
            ", envParams = " + envParams +
            ", taskParams = " + taskParams +
            ", mock = " + mock +
            ", bizTime = " + bizTime +
            ", triggerTime = " + triggerTime +
            ", nextTriggerTime = " + nextTriggerTime +
            ", startTime = " + startTime +
            ", endTime = " + endTime +
            ", retryTimes = " + retryTimes +
            ", intervalTime = " + intervalTime +
            ", currentRetryTimes = " + currentRetryTimes +
            ", applicationId = " + applicationId +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", version = " + version +
        "}";
    }
}
