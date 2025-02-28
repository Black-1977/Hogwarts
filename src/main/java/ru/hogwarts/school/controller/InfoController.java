package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;

import java.util.stream.Stream;

@RestController
public class InfoController {

    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int port;

    @GetMapping("port")
    public String portInfo() {
        logger.debug("Server port: {}", port);
        return ("Port : " + port);
    }

    @GetMapping("calculate")
    public int calculate(){

        int sum;
        long startTime, endTime;
        final int MAX_SIZE = 1_000_000;

        startTime = System.currentTimeMillis();
        sum = Stream
                .iterate(1, a -> a + 1)
                .limit(MAX_SIZE)
                .reduce(0, Integer::sum);
        endTime = System.currentTimeMillis();
        logger.info("Calculate {} time 1: {}", sum, endTime - startTime);

        startTime = System.currentTimeMillis();
        sum = Stream
                .iterate(1, a -> a + 1)
                .limit(MAX_SIZE)
                .parallel()
                .reduce(0, Integer::sum);
        endTime = System.currentTimeMillis();
        logger.info("Calculate {} time 2: {}", sum, endTime - startTime);

        sum = 0;
        startTime = System.currentTimeMillis();
        for (int i = 0; i <= MAX_SIZE; i++) {
            sum += i;
        }
        endTime = System.currentTimeMillis();
        logger.info("Calculate {} time 3: {}", sum, endTime - startTime);

        return sum;
    }

}
