ALTER TABLE appointments
ALTER COLUMN appointment_datetime SET DATA TYPE TIMESTAMP;

ALTER TABLE appointments
ALTER COLUMN appointment_initial_datetime SET DATA TYPE TIMESTAMP;

ALTER TABLE appointments
ALTER COLUMN appointment_finish_datetime SET DATA TYPE TIMESTAMP;
