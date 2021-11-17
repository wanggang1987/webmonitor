/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wang.webmonitor;

import java.sql.Timestamp;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author wanggang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 用于配置swagger2，包含文档基本信息 指定swagger2的作用域（这里指定包路径下的所有API）
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //指定需要扫描的controller
                .apis(RequestHandlerSelectors.basePackage("org.wang.webmonitor"))
                .paths(PathSelectors.any())
                .build().directModelSubstitute(Timestamp.class, Date.class);
    }

    /**
     * 构建文档基本信息，用于页面显示，可以包含版本、 联系人信息、服务地址、文档描述信息等
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("Spring Boot2中采用Swagger2构建RESTful APIs")
                .description("通过访问swagger-ui.html,实现接口测试、文档生成")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }

}
