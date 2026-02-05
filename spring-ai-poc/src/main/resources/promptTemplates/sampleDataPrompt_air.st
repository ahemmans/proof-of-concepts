Generate a sql insert statement to add sample data into the transmission_status table for testing purposes.
use the following columns: receipt_id, status, form_type, server, inserted_dt, update_dt.
valid form types are: FORM1094B, FORM1095B, FORM1094C, FORM1095C.
valid status values are: ACCEPTED, REJECTED, ACCEPTED_WITH_ERRORS.
valid server values are: 'localhost', 'server1', 'server2'.
inserted_dt and update_dt should be in the format 'YYYY-MM-DD HH:MM:SS', and should be between '2024-01-01 00:00:00' and '2025-12-31 23:59:59'.
valid receipt_id values should follow the format: {FORM_TYPE}-{YY}-{SEQUENCE}, where FORM_TYPE is 1094B, 1095B, 1094C, or 1095C; YY is a two-digit represention of the year of the inserted_dt and SEQUENCE is an eight-digit number starting from 00000001.
generate at least 50 rows of sample data with a mix of different form types, status values, and server values.

Use the following as an example of the statement structure:
INSERT INTO transmission_status (
    receipt_id, status, form_type, "server",  inserted_dt, update_dt
) VALUES 
  ('1095B-25-00013579', 'REJECTED', 'FORM1095B', 'localhost', '2025-12-15 10:30:00', '2025-12-15 10:35:00'),
  ('1094B-26-00012345', 'ACCEPTED', 'FORM1094B', 'localhost', '2025-12-15 11:00:00', '2025-12-15 11:05:00'),
  ('1094C-26-00023456', 'ACCEPTED_WITH_ERRORS', 'FORM1094C', 'localhost', '2026-01-15 11:30:00', '2026-01-15 11:30:00');


For each row generated for the transmission_status table where the status is REJECTED, also generate corresponding rows for the transmission_error table.  
use the following columns: receipt_id, error_cd, error_msg, error_level, line_num, xpath_mapping, additional_info 
valid error_level values are: ERROR, WARNING, INFO.
valid receipt_id values should follow the format: {FORM_TYPE}-{YY}-{SEQUENCE}, where FORM_TYPE is 1094B, 1095B, 1094C, or 1095C; YY is a two-digit represention of the year of the inserted_dt and SEQUENCE is an eight-digit number starting from 00000001.
valid error_cd values should follow the format: {FORM_TYPE}-{ERROR_CODE}-{VERSION}, where FORM_TYPE is 1094B, 1095B, 1094C, or 1095C; ERROR_CODE is a combination of letters and numbers; VERSION is a two-digit number.
valid error_cd values are: 1094B-002-01, 1094B-004-01, 1094B-005-01, 1094B-006-01, 1094B-007-01, 1095B-TH-033-01, 1095B-TH-034-01, 1095B-TH-049-02, 1095B-TH-050-01, 1095B-TH-052-01, 1095B-TH-053-02, 1095B-TH-054-02, 1094C-001-01, 1094C-002-01, 1094C-005, 1094C-005-01, 1094C-006-01, 1095C-001, 1095C-003, 1095C-005
generate at least 1 row and no more that 10 rows for each receipt_id.
generate at least 50 rows of sample data with a mix of different error codes, error messages, error levels, line numbers, xpath mappings, and additional info.

Use the following as an example of the statement structure:
INSERT INTO transmission_error (
    receipt_id, error_cd, error_msg, error_level, line_num, xpath_mapping, additional_info
) VALUES 
  ('1095B-25-00013579', '1094B-MANIFEST-001', 'Invalid data format', 'ERROR', '10', '/root/element[1]', 'Field validation failed'),
  ('1095B-25-00013579', '1094B-MANIFEST-002', 'Missing required element', 'ERROR', '14', '/root/element[4]', 'Field validation failed'),
  ('1094C-26-00023456', '1094C-WARN-001', 'Missing optional field', 'WARNING', '25', '/root/optional[1]', 'Non-critical warning'),