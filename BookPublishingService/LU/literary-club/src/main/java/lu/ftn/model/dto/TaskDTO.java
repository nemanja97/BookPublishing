package lu.ftn.model.dto;

import java.util.Date;

public class TaskDTO {

    String processId;
    String taskId;
    String name;
    String assignee;
    Date createdAt;

    public TaskDTO() {
        super();
    }

    public TaskDTO(String processId, String taskId, String name, String assignee, Date createdAt) {
        this.processId = processId;
        this.taskId = taskId;
        this.name = name;
        this.assignee = assignee;
        this.createdAt = createdAt;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
