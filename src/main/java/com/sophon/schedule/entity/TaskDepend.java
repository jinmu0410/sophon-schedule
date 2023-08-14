package com.sophon.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("task_depend")
@ApiModel(value = "TaskDepend对象", description = "")
public class TaskDepend implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("父类ID")
    private String parentTaskId;

    @ApiModelProperty("强弱依赖")
    private Integer type;

    @ApiModelProperty("版本")
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TaskDepend{" +
            "id = " + id +
            ", taskId = " + taskId +
            ", parentTaskId = " + parentTaskId +
            ", type = " + type +
            ", version = " + version +
        "}";
    }
}
