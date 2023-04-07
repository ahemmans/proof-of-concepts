package us.states.ws; 


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;
import com.tier3llc.countyservice.ObjectFactory;

import lombok.extern.slf4j.Slf4j;
import us.states.dao.CountyRepository;
import us.states.domain.County;
import us.states.util.DataUtils;
import us.states.validation.PayloadValidator;

@Slf4j
@Endpoint
public class CountyServiceEndpoint implements CountyService {

	private static final String NAMESPACE_URI = "http://www.tier3llc.com/countyservice";

	@Autowired
	private CountyRepository countyRepository;
	
	@Autowired
	private DataUtils dataUtils;
	
	@Autowired
	private PayloadValidator payloadValidator;
	
	@PayloadRoot(localPart = "getCountiesRequest", namespace = NAMESPACE_URI)
	@ResponsePayload
	@Override
	public GetCountiesResponse getCountiesResponse(@RequestPayload GetCountiesRequest request) {		
		payloadValidator.validateRequest(request);
		
		Optional<String> state = Optional.ofNullable(request.getState());
		List<County> countyList = new ArrayList<County>();
		if (state.isPresent()) {
			countyList = countyRepository.findByStAbbr(request.getState().toUpperCase());
		} else {
			Optional<County> county = countyRepository.findById(request.getId());
			if(county.isPresent()) {
				countyList.add(county.get());
			} else {
				countyList = countyRepository.findAll();
			}			
		}		
		
		return getResponse(countyList);
	}
	
	private GetCountiesResponse getResponse(List<County> countyList) {
		ObjectFactory objF = new ObjectFactory();
		GetCountiesResponse countyResp = objF.createGetCountiesResponse();
		for (County county : countyList) {
			countyResp.getCounty().add(mapObject(county));
		}
		countyResp.setStatus("");
		return countyResp;
	}
	
	private com.tier3llc.countyservice.County mapObject(County obj) {
		com.tier3llc.countyservice.County newCounty = new com.tier3llc.countyservice.County();
		newCounty.setId(obj.getId());
		//newCounty.setCounty(obj.getCounty());
		newCounty.setCounty(RandomStringUtils.random(8, "0123456789abcdef"));
		newCounty.setPopulation(obj.getPopulation());
		newCounty.setStAbbr(obj.getStAbbr());
		newCounty.setState(obj.getState());
		newCounty.setFilename(obj.getFilename());
		newCounty.setMimetype(obj.getMimetype());
		newCounty.setFilingseason(obj.getFilingseason());
		if (obj.getFiledata()!=null && obj.getFiledata().length>0) {
			newCounty.setFiledata(DataUtils.mapFileData(obj.getFiledata()));
			//log.info("OBJ BYTE DATA: {}", obj.getFiledata());
		} else {
			newCounty.setFiledata(DataUtils.mapFileData());
		}
				
		return newCounty;
	}

}
