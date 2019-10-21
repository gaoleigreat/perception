package com.lego.perceptiom.file;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.lego")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.lego"})
@EnableDiscoveryClient
public class FileServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(FileServerApplication.class, args);
    }
}
