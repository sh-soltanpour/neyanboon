package client;

import dtos.ProjectDto;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

public interface MetaDataClient {

    @RequestLine("GET /joboonja/project")
    @Headers("Content-Type: application/json")
    List<ProjectDto> getProjects();
}
