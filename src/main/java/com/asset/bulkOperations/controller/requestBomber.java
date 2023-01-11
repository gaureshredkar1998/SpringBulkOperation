package com.asset.bulkOperations.controller;

import com.asset.bulkOperations.model.Request;
import com.asset.bulkOperations.service.requestBomberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/bulk/api/v1/requestbomber")
public class requestBomber {

    @Autowired
    requestBomberService rs;

    @GetMapping("/m")
    public ResponseEntity<StreamingResponseBody> helloWorld2() throws URISyntaxException, IOException, InterruptedException {

        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 1000; i++) {
                try {
                    Thread.sleep(10);
                    response.write(("Data stream line - " + i + "\n").getBytes());
                    System.out.println("*********************");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }



    @PostMapping("/bombIt")

    public ResponseEntity<StreamingResponseBody> startBombing(@RequestBody Request request) {
        return  rs.startBombing(request);
    }
}
