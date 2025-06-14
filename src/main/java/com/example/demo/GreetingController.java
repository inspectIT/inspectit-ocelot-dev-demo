package com.example.demo;

import com.example.demo.service.MyAbstract;
import com.example.demo.service.MyInterface;
import com.example.demo.service.RandomService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class GreetingController extends MyAbstract implements MyInterface {

    //private static final Logger log = LogManager.getLogger(GreetingController.class);
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private RandomService randomService;

    private int counter = 0;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        this.randomService.getUser();
        incrementCounter();
        testGreeting();
        myMethod();
        return "greeting";
    }

    private void incrementCounter() {
        counter++;
        System.out.println("REQUEST " + counter);
    }

    @WithSpan
    private void testGreeting() {
        log.info("REQUEST TEST {}", counter);
        System.currentTimeMillis();
    }

    @GetMapping(value="/changePicture", produces="application/json")
    public ResponseEntity<String> view(@RequestParam(name = "alt") String alt) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String traceParent = request.getHeader("traceparent");
        System.out.println("############### " + traceParent + " ###############");

        randomService.getUser();
        int call = this.randomService.getVeryBigData("http://127.0.0.1:8082/service");

        if (alt.equals("inspectIT color"))
            return new ResponseEntity<>("{\"src\": \"images/InspectIT_Ocelot_BW.jpg\", \"alt\": \"inspectIT bw\"}", HttpStatus.OK);
        else {
            return new ResponseEntity<>("{\"src\": \"images/InspectIT_Ocelot.jpg\", \"alt\": \"inspectIT color\"}", HttpStatus.OK);
        }
    }

    @GetMapping(value = "/exception")
    public ResponseEntity<String> throwException() {
        throw new RuntimeException("EXCEPTION ON PURPOSE");
    }

    @Override
    public void myMethod() {
        System.out.println("MY METHOD");
    }
}

