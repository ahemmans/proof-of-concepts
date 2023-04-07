package us.states.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tier3llc.countyservice.GetCountiesRequest;

import lombok.extern.slf4j.Slf4j;

import us.states.domain.ServiceFault;
import us.states.exception.ServiceFaultException;

@Slf4j
@Service
public class PayloadValidator {

	public static void validateRequest(GetCountiesRequest request) {
		log.info("==== PayloadValidator validateRequest request ====");
		List<ServiceFault> serviceFaults = new ArrayList<ServiceFault>();
		
		if (ObjectUtils.isEmpty(request.getId())) {
			serviceFaults.add(new ServiceFault(ErrorCode.ME01.toString(), ErrorCode.ME01.getDesc()));
			
		} else if (request.getId()==null || String.valueOf(request.getId()).equals("0")) {
			serviceFaults.add(new ServiceFault(ErrorCode.RV01.toString(), ErrorCode.RV01.getDesc()));
		}
		
		if (StringUtils.isEmpty(request.getFilename())) {
			serviceFaults.add(new ServiceFault(ErrorCode.RV02.toString(), ErrorCode.RV02.getDesc()));
		}
		
		if (StringUtils.isEmpty(request.getMimetype())) {
			serviceFaults.add(new ServiceFault(ErrorCode.RV03.toString(), ErrorCode.RV03.getDesc()));
		
		} else if (ObjectUtils.isEmpty(MimeType.valueOfText(request.getMimetype()))) {						//is it an approved mimetype
			serviceFaults.add(new ServiceFault(ErrorCode.MT02.toString(), ErrorCode.MT02.getDesc().replace("%mt%", request.getMimetype())));
		
		} else if (!StringUtils.isEmpty(request.getFilename())) { 											//compare valid mimetype to file extension
			String retrievedMimeType = MimeType.valueOfText(request.getMimetype())
					.toString()
					.toLowerCase().trim();
			String fileExt = request.getFilename()
					.substring(request.getFilename().lastIndexOf(".")+1)
					.toLowerCase().trim();
			//log.info("**** retrievedMimeType : {}", retrievedMimeType);
			//log.info("**** fileExt : {}", fileExt);
			if (!fileExt.equals(retrievedMimeType)) {
				serviceFaults.add(new ServiceFault(ErrorCode.MT01.toString(), ErrorCode.MT01.getDesc()));
			}
		}
		
		if (serviceFaults.size()>0) {
			throw new ServiceFaultException(serviceFaults);
		}
		
	}	
	
}
