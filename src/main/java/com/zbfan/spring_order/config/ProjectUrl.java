package com.zbfan.spring_order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="project-url")
public class ProjectUrl {

    public String wechatMpAuthorize;

    public String wechatOpenAuthorize;

    public String sell;
}
