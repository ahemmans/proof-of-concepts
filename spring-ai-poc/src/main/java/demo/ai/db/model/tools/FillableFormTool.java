package demo.ai.db.model.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.ai.service.dto.Answer;
import demo.ai.util.FillableForm;
import demo.ai.util.FormTypeDeterminator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FillableFormTool {

	@Autowired private FormTypeDeterminator formTypeDeterminator;
	
	 @Tool(name = "isFormTypeFillable", description = "return a form's fillable status")
	public Answer isFormTypeFillable(@ToolParam(description="The form type of the submission") String formType) {
		log.info("==== FillableFormTool invoked ====");
		FillableForm form = FillableForm.fromFormType(formType);
		if (form == null) {
			log.warn("Form type '{}' is not recognized.", formType);
			return new Answer(formType, "Form type is not recognized.");
		} else {
			boolean fillable = form.isFillable();
			log.info("Form type '{}' is fillable: {}", formType, fillable);
			if (fillable) {
				log.info("Note: Form type '{}' is fillable.", formType);
				return new Answer(formType, "Form type '" + formType + "' is fillable: " + fillable);
			} else {
				log.info("Note: Form type '{}' is not fillable.", formType);
				return new Answer(formType, "Form type '" + formType + "' is not fillable: " + fillable);
			}
		}		
	}
	
	/*
	 @Tool(name = "isFormFillableByReceiptId", description = "return a form's fillable status")
	public Answer isFormTypeFillableByReciptId(@ToolParam(description="The Receipt ID of the submission") String receiptId) {
		log.info("==== isFormTypeFillableByReciptId invoked ==== : {}", receiptId);
		String formType = formTypeDeterminator.getFormTypeFromReceiptId(receiptId);
		FillableForm form = FillableForm.fromFormType(formType);
		if (form == null) {
			log.warn("Form type '{}' is not recognized.", formType);
			return new Answer(formType, "Form type is not recognized.");
		} else {
			boolean fillable = form.isFillable();
			log.info("Form type '{}' is fillable: {}", formType, fillable);
			if (fillable) {
				log.info("Note: Form type '{}' is fillable.", formType);
				return new Answer(formType, "Form type '" + formType + "' is fillable: " + fillable);
			} else {
				log.info("Note: Form type '{}' is not fillable.", formType);
				return new Answer(formType, "Form type '" + formType + "' is not fillable: " + fillable);
			}
		}		
	}
	*/
}
