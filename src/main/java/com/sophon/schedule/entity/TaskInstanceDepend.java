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
@TableName("task_instance_depend")
@ApiModel(value = "TaskInstanceDepend对象", description = "")
public class TaskInstanceDepend implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("实例id")
    private String taskInstanceId;

    @ApiModelProperty("父类实例id")
    private String parentTaskInstanceId;

    @ApiModelProperty("强弱依赖")
    private Integer type;

    @ApiModelProperty("属于哪个任务流实例id")
    private String belTaskInstanceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public String getParentTaskInstanceId() {
        return parentTaskInstanceId;
    }

    public void setParentTaskInstanceId(String parentTaskInstanceId) {
        this.parentTaskInstanceId = parentTaskInstanceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBelTaskInstanceId() {
        return belTaskInstanceId;
    }

    public void setBelTaskInstanceId(String belTaskInstanceId) {
        this.belTaskInstanceId = belTaskInstanceId;
    }

    @Override
    public String toString() {
        return "TaskInstanceDepend{" +
            "id = " + id +
            ", taskInstanceId = " + taskInstanceId +
            ", parentTaskInstanceId = " + parentTaskInstanceId +
            ", type = " + type +
            ", belTaskInstanceId = " + belTaskInstanceId +
        "}";
    }
}
