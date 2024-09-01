package com.example.fooballobs.actuator;

import com.example.fooballobs.service.FileLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;


@Endpoint(id = "football")
public class FootballCustomEndpoint {

    private final FileLoader fileLoader;

    public FootballCustomEndpoint(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @ReadOperation
    public String getFileVersion() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String teamsJson = objectMapper.writeValueAsString(fileLoader.getTeams());
            String fileName = fileLoader.getFileName().replace("\\", "/");

            return "{\"teams\": " + teamsJson + ", \"File\": \"" + fileName + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @WriteOperation
    public void refreshFile() {
        try {
            fileLoader.loadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
