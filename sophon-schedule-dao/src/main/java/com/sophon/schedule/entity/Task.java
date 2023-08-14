package com.sophon.schedule.entity;

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
@ApiModel(value = "Task对象", description = "")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增ID")
    private String id;

    @ApiModelProperty("任务父节点id")
    private String parentId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务描述")
    private String taskDesc;

    @ApiModelProperty("任务类型（datax、flink、spark）")
    private Integer taskType;

    @ApiModelProperty("任务类目（单任务、任务流、任务流节点）")
    private Integer taskNodeType;

    @ApiModelProperty("任务流节点位置信息")
    private String positions;

    @ApiModelProperty("任务参数")
    private String taskParams;

    @ApiModelProperty("自定义参数")
    private String customParams;

    @ApiModelProperty("系统参数")
    private String sysParams;

    @ApiModelProperty("环境参数")
    private String envParams;

    @ApiModelProperty("调度类型（周期、手动）")
    private Integer scheduleType;

    @ApiModelProperty("调度cron表达式")
    private String scheduleCron;

    @ApiModelProperty("重试次数")
    private Integer retryTimes;

    @ApiModelProperty("重试间隔")
    private Integer intervalTime;

    @ApiModelProperty("调度开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("调度结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("任务优先级")
    private Integer taskPriority;

    @ApiModelProperty("是否冻结，冻结不生成实例")
    private Integer isFreeze;

    @ApiModelProperty("任务版本")
    private Integer version;

    @ApiModelProperty("扩展字段")
    private String extra;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskNodeType() {
        return taskNodeType;
    }

    public void setTaskNodeType(Integer taskNodeType) {
        this.taskNodeType = taskNodeType;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(String taskParams) {
        this.taskParams = taskParams;
    }

    public String getCustomParams() {
        return customParams;
    }

    public void setCustomParams(String customParams) {
        this.customParams = customParams;
    }

    public String getSysParams() {
        return sysParams;
    }

    public void setSysParams(String sysParams) {
        this.sysParams = sysParams;
    }

    public String getEnvParams() {
        return envParams;
    }

    public void setEnvParams(String envParams) {
        this.envParams = envParams;
    }

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleCron() {
        return scheduleCron;
    }

    public void setScheduleCron(String scheduleCron) {
        this.scheduleCron = scheduleCron;
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

    public Integer getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Integer getIsFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(Integer isFreeze) {
        this.isFreeze = isFreeze;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id = " + id +
            ", parentId = " + parentId +
            ", taskName = " + taskName +
            ", taskDesc = " + taskDesc +
            ", taskType = " + taskType +
            ", taskNodeType = " + taskNodeType +
            ", positions = " + positions +
            ", taskParams = " + taskParams +
            ", customParams = " + customParams +
            ", sysParams = " + sysParams +
            ", envParams = " + envParams +
            ", scheduleType = " + scheduleType +
            ", scheduleCron = " + scheduleCron +
            ", retryTimes = " + retryTimes +
            ", intervalTime = " + intervalTime +
            ", startTime = " + startTime +
            ", endTime = " + endTime +
            ", taskPriority = " + taskPriority +
            ", isFreeze = " + isFreeze +
            ", version = " + version +
            ", extra = " + extra +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", isDeleted = " + isDeleted +
        "}";
    }
}
