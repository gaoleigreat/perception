package com.lego.perception.file.init;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CephInit implements InitializingBean {

    private AmazonS3 amazonS3;

    @Value("${ceph.accessKey}")
    private String accessKey;

    @Value("${ceph.secretKey}")
    private String secretKey;

    @Value("${ceph.host}")
    private String host;

    @Override
    public void afterPropertiesSet() throws Exception {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials (accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        amazonS3= new AmazonS3Client(awsCredentials,clientConfig);
        amazonS3.setEndpoint(host);
    }

    public AmazonS3 getAmazonS3Client(){

        return this.amazonS3;
    }
}
