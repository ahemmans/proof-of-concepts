package us.states.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "us.gov.treasury.irs.msg.forms")
@Configuration
public class TransmittalFormsConfig {

}
