select   tbl1.* ,tbl2.* ,tbl3.* from
       (select UNIQUE_SUBMISSION_ID,SUBMISSION_STATUS_TXT ,gso_id from AIR_DSIT_NEW.SUBMISSION_BC 
     where gso_id in ( Select gso_id
  from AIR_DSIT_NEW.transmitter_bc
  where parent_gso_id='1094C-23-00001530')) tbl1 
  full join 
  
   (  select "subrec",errortype,ERRORMSG,gso_id
  from
  (select IE.errortype,SUBSTR( IFE.ir_rec_seq_num, INSTR( ir_rec_seq_num, '|' ) + 1 ) as "subrec",IFE.element_name_txt,IE.ERRORMSG,IE.gso_id
  from AIR_DSIT_NEW.ir_form_ERROR IFE,
  AIR_DSIT_NEW.ifs_ERROR IE
  where IE.GSO_ID in ( Select gso_id
  from AIR_DSIT_NEW.transmitter_bc
  where parent_gso_id='1094C-23-00001530')
  and IFE.error_id=IE.error_id
  )) tbl2
  on  tbl1.gso_id =tbl2.gso_id
  
  full join (select EFT.TRANS_FILE_NAME_TXT AS Augmented_XML, EFT.gso_id from AIR_NEW.DS_EOY_FORMS_TRANSMIT EFT
  where EFT.GSO_ID in ('1094C-23-00001530')
  ) tbl3
  on  tbl1.gso_id =tbl3.gso_id;
