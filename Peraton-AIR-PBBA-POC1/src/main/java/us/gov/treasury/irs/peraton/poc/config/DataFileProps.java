package us.gov.treasury.irs.peraton.poc.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "testfile")
public class DataFileProps {

	private List<String> dataFiles;
	private String[] headers;
}
