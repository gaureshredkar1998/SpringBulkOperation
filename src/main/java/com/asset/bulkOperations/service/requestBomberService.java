package com.asset.bulkOperations.service;

import com.asset.bulkOperations.model.HTTPRequest;
import com.asset.bulkOperations.model.Request;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class requestBomberService {




    private Object doRequest(HttpRequest httpRequest) throws URISyntaxException, IOException, InterruptedException {

        HttpResponse response = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
       // System.out.println(response.body());
        return response.body();
    }

    private HttpRequest createRequest(HTTPRequest httpRequest) throws URISyntaxException {

        HttpRequest request = null;
        String headerArray[] = createHeaders(httpRequest.getHeaders());
        switch (httpRequest.getHttpMethod()) {

            case "POST" :
                request = HttpRequest.newBuilder().uri(new URI(httpRequest.getUrl())).headers(headerArray).build();
                break;
            case "GET" :
                request = HttpRequest.newBuilder().uri(new URI(httpRequest.getUrl())).GET().headers(headerArray).build();
        }



        return  request;
    }

    private String[] createHeaders(Map<String, String> headers) {
        List<String> headersList = new ArrayList<>();

        if(headers != null) {
            headers.forEach((key,value)->{
                headersList.add(key);
                headersList.add(value);
            });
        }

        return  headersList.toArray(new String[headersList.size()]);
    }


    public ResponseEntity<StreamingResponseBody> startBombing(Request request) {
        int numberOfThreads = request.getConfig().getNumberOfThreads() ==0?5:request.getConfig().getNumberOfThreads();

         long requestPerThread = request.getConfig().getRequestPerThread() ==0 ? 100: request.getConfig().getRequestPerThread();



        StreamingResponseBody responseBody = response -> {
            CountDownLatch latch=new CountDownLatch(numberOfThreads);

            for (int i = 1; i <= numberOfThreads; i++) {
                int finalI = i;
                Runnable r1 = () -> {
                    try {
                        for (int j = 1; j <= requestPerThread; j++) {
                            HttpRequest req = createRequest(request.getHttpRequest());
                            Object res = doRequest(req);

                            System.out.println("Thread number: " + finalI + ": " + "call number: " + j + "TimeStamp: " + System.currentTimeMillis() + ":::: RESPONSE: " + res);
                            response.write(("Thread number: " + finalI + ": " + "call number: " + j + "TimeStamp: " + System.currentTimeMillis() + ":::: RESPONSE: " + res + "\n \n").getBytes());

                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                };

                Thread t1 = new Thread(r1);
                t1.start();
            }
            try {
                latch.await(); // wait for latch to count down to 0 + add error handling and return value
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }
}
