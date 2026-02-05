Generate a sql insert statement to add sample data into the transmission_status table for testing purposes.
use the following columns: receipt_id, status, form_type, server, inserted_dt, update_dt.
valid form types are: FORM8980, FORM8981, FORM8985, FORM898, FORM9899, FORM8984, FORM14027.
valid status values are: ACCEPTED, REJECTED, ACCEPTED_WITH_ERRORS.
valid server values are: 'localhost', 'server1', 'server2'.
inserted_dt and update_dt should be in the format 'YYYY-MM-DD HH:MM:SS', and should be between '2024-01-01 00:00:00' and '2025-12-31 23:59:59'.
valid receipt_id values should follow the format: {FORM_TYPE}-{SEQUENCE}, where FORM_TYPE is FORM8980, FORM8981, FORM8985, FORM898, FORM9899, FORM8984, FORM14027; SEQUENCE is an eight-digit number starting from 00000001.
generate at least 50 rows of sample data with a mix of different form types, status values, and server values.

Use the following as an example of the statement structure:
INSERT INTO transmission_status (
    receipt_id, status, form_type, "server",  inserted_dt, update_dt
) VALUES 
  ('FORM8980-00000080', 'REJECTED', 'FORM8980', 'localhost', '2024-12-15 10:30:00', '2024-12-15 10:35:00'),
  ('FORM8985-00000085', 'ACCEPTED', 'FORM8985', 'localhost', '2024-12-15 11:00:00', '2024-12-15 11:05:00'),
  ('FORM8981-00000081', 'ACCEPTED_WITH_ERRORS', 'FORM8981', 'localhost', '2025-01-15 11:30:00', '2025-01-15 11:30:00')


For each row generated for the transmission_status table where the status is REJECTED, also generate corresponding rows for the transmission_error table.  
use the following columns: receipt_id, error_cd, error_msg, error_level, line_num, xpath_mapping, additional_info 
valid error_level values are: ERROR, WARNING, INFO.
valid receipt_id values should follow the format: {FORM_TYPE}-{SEQUENCE}, where FORM_TYPE is FORM8980, FORM8981, FORM8985, FORM8988, FORM8989, FORM8984, FORM14027; SEQUENCE is an eight-digit number starting from 00000001.
valid error_cd values should follow the format: {FORM_TYPE}-{ERROR_CODE}-{VERSION}, where FORM_TYPE is F8980, F8981, F8985, F8988, F8989, F8984, F14027; ERROR_CODE is a combination of letters and numbers; VERSION is a three-digit number.
valid error_cd values are: F8980-005, F8980-006, F8980-007, F8980-Schema-005, F8980-Schema-010, F8980-Schema-015, F8981-Schema-005, F8981-Schema-010, F8981-Schema-015, F8985-110, F8985-115, F8985-120, F8985-TIN-005, F8985-TIN-010, F8985-TIN-015, F8988-005, F8988-010, F8988-Schema-005, F8988-Schema-010, F8989-005, F8989-010, F8989-Schema-005, F8989-Schema-010, F8988-TIME-010, F8988-TIME-015, F8989-TIME-010, F8989-TIME-015 
generate at least 1 row and no more that 10 rows for each receipt_id.
generate at least 50 rows of sample data with a mix of different error codes, error messages, error levels, line numbers, xpath mappings, and additional info.

Use the following as an example of the statement structure:
INSERT INTO transmission_error (
    receipt_id, error_cd, error_msg, error_level, line_num, xpath_mapping, additional_info
) VALUES 
  ('FORM8980-00000080', 'F8980-MANIFEST-001', 'Invalid data format', 'ERROR', '10', '/root/element[1]', 'Field validation failed'),
  ('FORM8980-00000080', 'F8980-MANIFEST-002', 'Missing required element', 'ERROR', '14', '/root/element[4]', 'Field validation failed'),
  ('FORM8981-00000081', 'F8981-WARN-001', 'Missing optional field', 'WARNING', '25', '/root/optional[1]', 'Non-critical warning')