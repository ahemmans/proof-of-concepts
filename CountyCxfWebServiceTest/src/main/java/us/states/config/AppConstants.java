package us.states.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstants {

	public static final String WS_URL = "http://localhost:8089/ws";
	public static final String NAMESPACE_URI = "http://www.tier3llc.com/countyservice";
	public static final String TARGET_NAMESPACE = "coun";	
	
	public static final String FORM_1094_1095B_ROOT_NODE = "p:Form109495BTransmittalUpstream";
	public static final String FORM_1094B_DETAIL_NODE = "p1:Form1094BUpstreamDetail";
	public static final String FORM_1095B_DETAIL_NODE = "p1:Form1095BUpstreamDetail";
	
	public static final String FORM_1094_1095C_ROOT_NODE = "p:Form109495CTransmittalUpstream";
	public static final String FORM_1094C_DETAIL_NODE = "p1:Form1094CUpstreamDetail";
	public static final String FORM_1095C_DETAIL_NODE = "p1:Form1095CUpstreamDetail";
	
	public static final String FORM_1095A_ROOT_NODE = "p:Form1095ATransmissionUpstream";
	public static final String FORM_1095A_DETAIL_NODE = "p1:Form1095AUpstreamDetail";
	
	public static final String FORM_1094_1095C_SCRIPS_ROOT_NODE  = "p:Form109495CTransmittalSCRIPS";
	public static final String FORM_1094_1095C_SCRIPS_HEADER_NODE  = "p1:ACABusinessHeaderSCRIPSIdP";
	public static final String FORM_1094C_SCRIPS_SUBMISSION_DETAIL_NODE  = "p1:Form1094CSCRIPSsubmissionDetail";
	public static final String FORM_1095C_SCRIPS_DETAIL_NODE  = "p1:Form1095CSCRIPSDetail";
	
	public static final String ELEMENT_TAX_YEAR = "p1:TaxYr";
	public static final String ELEMENT_TAX_YEARP = ELEMENT_TAX_YEAR+"P";
	public static final String ELEMENT_PAYER_DLNP = "p1:PayerDLNP";
	public static final String ELEMENT_PAYEE_DLNP = "p1:PayeeDLNP";
}
