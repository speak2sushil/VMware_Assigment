package com.vmware.springboot.webservice.restwebservice.service;

import com.vmware.springboot.webservice.restwebservice.model.TaskRequest;

import java.util.List;
import java.util.UUID;

public interface FileService {
    UUID writeToFile(TaskRequest taskRequest, UUID taskId);
    List<String> readFileInList(String taskId);
    String getTaskStatus(String taskId);

    boolean isTaskAvailable(String taskId);

}
