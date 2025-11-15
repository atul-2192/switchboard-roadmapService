package com.Roadmap_Service.Roadmap.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RoadmapServiceApplication {

	public static void main(String[] args) {
		log.info("RoadmapServiceApplication :: main() :: Starting :: Roadmap Service Application");
		SpringApplication.run(RoadmapServiceApplication.class, args);
		log.info("RoadmapServiceApplication :: main() :: Started Successfully :: Roadmap Service Application");
	}

}
