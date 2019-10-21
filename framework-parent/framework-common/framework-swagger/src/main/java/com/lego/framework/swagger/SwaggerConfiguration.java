package com.lego.framework.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaodao
 * @descript
 * @since 2018/12/20
 **/
@ConditionalOnExpression
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Value("${swagger.title:后台接口api文档}")
    private String swaggerTitle;

    @Value("${swagger.contact.name:高磊}")
    private String contactName;

    @Value("${swagger.contact.url:https://www.baidu.com}")
    private String contactUrl;

    @Value("${swagger.contact.email:513684652@qq.com}")
    private String contactEmail;

    @Value("${spring.application.name:application.name}")
    private String swaggerDescription;

    @Value("${swagger.termsOfServiceUrl:https://www.baidu.com}")
    private String swaggerServiceUrl;

    @Value("${swagger.version:1.0}")
    private String swaggerVersion;

    @Value("${swagger.routeIdentifier:routeIdentifier}")
    private String routeIdentifier;

    @Autowired
    private Environment environment;


    @Bean
    public Docket docket() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("String")).parameterType("header").required(false).build();
        ParameterBuilder versionPar = new ParameterBuilder();
        versionPar.name("version").description("应用版本号").modelRef(new ModelRef("String")).parameterType("header").required(true).build();
        ParameterBuilder timePar = new ParameterBuilder();
        timePar.name("timeStamp").description("当前时间戳").modelRef(new ModelRef("Long")).parameterType("header").required(true).build();
        ParameterBuilder snPar = new ParameterBuilder();
        snPar.name("sn").description("设备序列号").modelRef(new ModelRef("String")).parameterType("header").required(false).build();
        ParameterBuilder deviceTypePar = new ParameterBuilder();
        deviceTypePar.name("deviceType").description("设备类型(1-Android;2-WEB)").modelRef(new ModelRef("String")).parameterType("header").allowableValues(new AllowableRangeValues("1", "2")).required(true).build();
        ParameterBuilder osVersionPar = new ParameterBuilder();
        osVersionPar.name("osVersion").description("设备系统版本号").modelRef(new ModelRef("String")).parameterType("header").required(true).build();


/*        parameters.add(tokenPar.build());
        parameters.add(versionPar.build());
        parameters.add(timePar.build());
        parameters.add(snPar.build());
        parameters.add(deviceTypePar.build());
        parameters.add(osVersionPar.build());*/

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(1).message("操作成功").build());
        responseMessages.add(new ResponseMessageBuilder().code(-1).message("权限校验失败").build());
        responseMessages.add(new ResponseMessageBuilder().code(-2).message("登录错误").build());
        responseMessages.add(new ResponseMessageBuilder().code(-3).message("服务内部错误").build());
        responseMessages.add(new ResponseMessageBuilder().code(-4).message("调用超时错误").build());
        responseMessages.add(new ResponseMessageBuilder().code(-5).message("其他错误").build());
        responseMessages.add(new ResponseMessageBuilder().code(-6).message("结果错误").build());
        responseMessages.add(new ResponseMessageBuilder().code(-7).message("数据处理异常").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
        //  .securitySchemes(Collections.singletonList(securityScheme()))
        //.securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .description("restful风格接口，服务名称：" + environment.getProperty("spring.application.name") + "</br>路由标识：" + routeIdentifier)
                // .termsOfServiceUrl(swaggerServiceUrl)
                .version(swaggerVersion)
                .build();
    }


}
