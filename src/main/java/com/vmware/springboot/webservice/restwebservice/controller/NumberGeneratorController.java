package com.vmware.springboot.webservice.restwebservice.controller;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.vmware.springboot.webservice.restwebservice.model.TaskRequest;
import com.vmware.springboot.webservice.restwebservice.model.TaskResponse;
import com.vmware.springboot.webservice.restwebservice.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class NumberGeneratorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberGeneratorController.class);

    @Autowired
    FileService fileService;


    @PostMapping("/api/generate")
    public ResponseEntity<?> generateNumber(@Valid @RequestBody Map<String,Integer> inputData) {
        LOGGER.info("Started generateNumber request {} ");
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setGoal(inputData.get("Goal"));
        taskRequest.setStep(inputData.get("Step"));
        UUID taskId = UUID.randomUUID();
        fileService.writeToFile(taskRequest, taskId);
        String createdTask = "task" + ":" + taskId;
        LOGGER.info("Processed generateNumber API {} ");
        return new ResponseEntity<>(createdTask, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/tasks/{taskId}/status", produces = "application/json")
    public ResponseEntity<?> getTaskStatus(@PathVariable("taskId") String taskId) {
        if (fileService.isTaskAvailable(taskId)) {
            return new ResponseEntity<>(new TaskResponse(fileService.getTaskStatus(taskId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/api/tasks/{taskId}", produces = "application/json")
    public ResponseEntity<?> getTaskData(@PathVariable("taskId") String taskId, @RequestParam String action) {
        if (fileService.isTaskAvailable(taskId) && "get_numlist".equalsIgnoreCase(action)  && fileService.getTaskStatus(taskId).equals("SUCCESS")) {
            return new ResponseEntity<>(new TaskResponse(fileService.readFileInList(taskId).stream().collect(Collectors.joining(","))), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
