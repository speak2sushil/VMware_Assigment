package com.vmware.springboot.webservice.restwebservice.service;


import com.vmware.springboot.webservice.restwebservice.model.TaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
    private static String baseFolder = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    //private static String baseFolder="/tmp";
    private static String taskSuffix = "_output.txt";
    Map<String, String> cache = new ConcurrentHashMap<>();


    @Override
    public UUID writeToFile(TaskRequest taskRequest, UUID taskId) {
        try {
            CompletableFuture.supplyAsync(() -> {
                LOGGER.info("Started async call {} ");
                cache.put(taskId.toString(), "IN_PROGRESS");
                File file = new File(baseFolder
                        + taskId + taskSuffix
                );

                FileWriter writer = null;
                try {
                    writer = new FileWriter(file);

                    for (int i = taskRequest.getGoal(); i >= 0; i = i - taskRequest.getStep()) {
                        //Thread.sleep(4000);
                        writer.write(String.valueOf(i) + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e.toString());
                }
                return taskId;
            }).whenComplete((result, ex) -> {
                if (null != ex) {
                    cache.put(taskId.toString(), "ERROR");
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                } else {
                    cache.put(taskId.toString(), "SUCCESS");

                }
                LOGGER.info("Processed async call {} ");
            });
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.toString());
        }
        return taskId;
    }

    public List<String> readFileInList(String taskId) {

        List<String> lines = Collections.emptyList();
        try {
            lines =
                    Files.readAllLines(Paths.get(baseFolder
                            + taskId + taskSuffix), StandardCharsets.UTF_8);
        } catch (IOException e) {

            LOGGER.error(e.getMessage(), e.toString());
        }
        return lines;
    }

    @Override
    public String getTaskStatus(String taskId) {
        return cache.get(taskId);
    }

    @Override
    public boolean isTaskAvailable(String taskId) {
        return cache.containsKey(taskId);
    }
}
