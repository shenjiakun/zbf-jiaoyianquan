package com.sjk.es.esconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 作者: LCG
 * 日期: 2020/7/28 09:03
 * 描述:
 */
@ConfigurationProperties(prefix = "com.sjk.es")
@Data
@Component
public class EsZkConfig {

    private String list;

    private String namespace;

}
