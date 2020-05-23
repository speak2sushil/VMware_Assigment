package com.vmware.springboot.webservice.restwebservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.springboot.webservice.restwebservice.model.TaskRequest;
import com.vmware.springboot.webservice.restwebservice.service.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumberGeneratorController.class)
public class NumberGeneratorControllerTest {


    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper mapper;


    @MockBean
    FileService fileService;

    @Test
    public void post_generateNumberWith202() throws Exception {
        Map<String,Integer> input=new HashMap<>();
        input.put("Goal",20);
        input.put("Step",2);
        UUID taskId = UUID.randomUUID();
        Mockito.when(fileService.writeToFile(Mockito.any(TaskRequest.class),Mockito.any(UUID.class))).thenReturn(taskId);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/generate")
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(input));

        mockMvc.perform(builder).andExpect(status().isAccepted());

    }

    @Test
    public void get_getTaskStatus_returns200WithSuccess() throws Exception {
        UUID taskId = UUID.randomUUID();
        Mockito.when(fileService.getTaskStatus(taskId.toString())).thenReturn("SUCCESS");
        Mockito.when(fileService.isTaskAvailable(taskId.toString())).thenReturn(true);
        String URI="/api/tasks/"+taskId.toString()+"/status";
        mockMvc.perform(
                MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void get_getTaskDetail_returns200WithData() throws Exception {
        UUID taskId = UUID.randomUUID();
        List<String> fileList=Arrays.asList("10","8","6","4,","2","0");
        Mockito.when(fileService.getTaskStatus(taskId.toString())).thenReturn("SUCCESS");
        Mockito.when(fileService.isTaskAvailable(taskId.toString())).thenReturn(true);
        Mockito.when(fileService.readFileInList(taskId.toString())).thenReturn(fileList);
        String URI="/api/tasks/"+taskId.toString()+"?action=get_numlist";
        mockMvc.perform(
                MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(fileService, times(1)).readFileInList(taskId.toString());

    }
}
