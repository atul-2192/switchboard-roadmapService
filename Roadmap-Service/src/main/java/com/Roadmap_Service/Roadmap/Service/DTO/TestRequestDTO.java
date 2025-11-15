package com.Roadmap_Service.Roadmap.Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRequestDTO {
    private String topic;
    private List<TestObject> testresults;
}
