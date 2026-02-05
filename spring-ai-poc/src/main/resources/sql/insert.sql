-- sample data for testing

-- Clear existing data  
DELETE FROM transmission_status_hist;
DELETE FROM transmission_status;
DELETE FROM transmission_error;

-- Insert into transmission_status table
INSERT INTO transmission_status (
    receipt_id, status, form_type, "server",  inserted_dt, update_dt
) VALUES 
  ('1095B-25-00013579', 'REJECTED', 'FORM1095B', 'localhost', '2025-12-15 10:30:00', '2025-12-15 10:35:00'),
  ('1094B-26-00012345', 'ACCEPTED', 'FORM1094B', 'localhost', '2025-12-15 11:00:00', '2025-12-15 11:05:00'),
  ('1094C-26-00023456', 'ACCEPTED_WITH_ERRORS', 'FORM1094C', 'localhost', '2026-01-15 11:30:00', '2026-01-15 11:30:00'),  
  ('FORM8980-00000080', 'REJECTED', 'FORM8980', 'localhost', '2024-12-15 10:30:00', '2024-12-15 10:35:00'),
  ('FORM8985-00000085', 'ACCEPTED', 'FORM8985', 'localhost', '2024-12-15 11:00:00', '2024-12-15 11:05:00'),
  ('FORM8981-00000081', 'ACCEPTED_WITH_ERRORS', 'FORM8981', 'localhost', '2025-01-15 11:30:00', '2025-01-15 11:30:00')
;

-- Insert into transmission_status_hist table
INSERT INTO transmission_status_hist (
    transmission_status_id, receipt_id, status, inserted_dt
) VALUES 
  (1, '1095B-25-00013579', 'RECEIVED', '2025-12-15 10:30:00'),
  (1, '1095B-25-00013579', 'PROCESSING', '2025-12-15 10:32:00'),
  (1, '1095B-25-00013579', 'REJECTED', '2025-12-15 10:35:00'),    
  (2, '1094B-26-00012345', 'RECEIVED', '2025-12-15 11:00:00'),
  (2, '1094B-26-00012345', 'Beginning Intake Processing', '2025-12-15 11:05:00'),
  (2, '1094B-26-00012345', 'Beginning Validation Processing', '2025-12-15 11:10:00'),
  (2, '1094B-26-00012345', 'TIN Validation Sent', '2025-12-15 11:15:00'),
  (2, '1094B-26-00012345', 'TIN Validation Completed', '2025-12-15 11:20:00'),
  (2, '1094B-26-00012345', 'ACCEPTED', '2025-12-15 11:30:00'),    
  (3, '1094C-26-00023456', 'RECEIVED', '2026-01-15 11:00:00'),
  (3, '1094C-26-00023456', 'Beginning Intake Processing', '2026-01-15 11:05:00'),
  (3, '1094C-26-00023456', 'Beginning Validation Processing', '2026-01-15 11:10:00'),
  (3, '1094C-26-00023456', 'TIN Validation Sent', '2026-01-15 11:15:00'),
  (3, '1094C-26-00023456', 'TIN Validation Completed', '2026-01-15 11:20:00'),
  (3, '1094C-26-00023456', 'Partially Accepted', '2026-01-15 11:35:00'),
  (4, 'FORM8980-00000080', 'RECEIVED', '2024-12-15 10:30:00'),
  (4, 'FORM8980-00000080', 'PROCESSING', '2024-12-15 10:32:00'),
  (4, 'FORM8980-00000080', 'REJECTED', '2024-12-15 10:35:00'),    
  (5, 'FORM8985-00000085', 'RECEIVED', '2025-12-15 11:00:00'),
  (5, 'FORM8985-00000085', 'Beginning Intake Processing', '2025-12-15 11:05:00'),
  (5, 'FORM8985-00000085', 'Beginning Validation Processing', '2025-12-15 11:10:00'),
  (5, 'FORM8985-00000085', 'TIN Validation Sent', '2025-12-15 11:15:00'),
  (5, 'FORM8985-00000085', 'TIN Validation Completed', '2025-12-15 11:20:00'),
  (5, 'FORM8985-00000085', 'ACCEPTED', '2025-12-15 11:30:00'),    
  (6, 'FORM8981-00000081', 'RECEIVED', '2025-01-15 11:00:00'),
  (6, 'FORM8981-00000081', 'Beginning Intake Processing', '2025-01-15 11:05:00'),
  (6, 'FORM8981-00000081', 'Beginning Validation Processing', '2025-01-15 11:10:00'),
  (6, 'FORM8981-00000081', 'TIN Validation Sent', '2025-01-15 11:15:00'),
  (6, 'FORM8981-00000081', 'TIN Validation Completed', '2025-01-15 11:20:00'),
  (6, 'FORM8981-00000081', 'Partially Accepted', '2025-01-15 11:35:00')  
;

-- Insert into transmission_status table
INSERT INTO transmission_error (
    receipt_id, error_cd, error_msg, error_level, line_num, xpath_mapping, additional_info
) VALUES 
  ('1095B-25-00013579', '1094B-MANIFEST-001', 'Invalid data format', 'ERROR', '10', '/root/element[1]', 'Field validation failed'),
  ('1095B-25-00013579', '1094B-MANIFEST-002', 'Missing required element', 'ERROR', '14', '/root/element[4]', 'Field validation failed'),
  ('1094C-26-00023456', '1094C-WARN-001', 'Missing optional field', 'WARNING', '25', '/root/optional[1]', 'Non-critical warning'),  
  ('FORM8980-00000080', 'F8980-MANIFEST-001', 'Invalid data format', 'ERROR', '10', '/root/element[1]', 'Field validation failed'),
  ('FORM8980-00000080', 'F8980-MANIFEST-002', 'Missing required element', 'ERROR', '14', '/root/element[4]', 'Field validation failed'),
  ('FORM8981-00000081', 'F8981-WARN-001', 'Missing optional field', 'WARNING', '25', '/root/optional[1]', 'Non-critical warning')
;