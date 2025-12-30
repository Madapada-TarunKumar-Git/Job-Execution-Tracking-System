/* =========================================================
   DATABASE: Job Execution Tracker
   ========================================================= */
DROP DATABASE IF EXISTS job_execution_tracker;
CREATE DATABASE IF NOT EXISTS job_execution_tracker;
USE job_execution_tracker;

/* =========================
   TABLE: jobs
   ========================= */
CREATE TABLE jobs (
                      job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      job_name VARCHAR(100) NOT NULL UNIQUE,
                      job_type VARCHAR(50) NOT NULL,
                      owner_team VARCHAR(100),
                      environment VARCHAR(50) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/* =========================
   TABLE: job_executions
   ========================= */
CREATE TABLE job_executions (
                                execution_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                job_id BIGINT NOT NULL,
                                start_time DATETIME NOT NULL,
                                end_time DATETIME,
                                status_message VARCHAR(20) NOT NULL,
                                duration_ms BIGINT,
                                error_message VARCHAR(2000),
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_job_execution_job
                                    FOREIGN KEY (job_id)
                                        REFERENCES jobs(job_id)
                                        ON DELETE CASCADE
);

/* =========================
   INDEXES
   ========================= */
CREATE INDEX idx_job_exec_job_id ON job_executions(job_id);
CREATE INDEX idx_job_exec_status ON job_executions(status_message);

/* =========================
   SAMPLE DATA (OPTIONAL)
   ========================= */
INSERT INTO jobs (job_name, job_type, owner_team, environment)
VALUES ('DailyBillingJob', 'BATCH', 'Finance', 'DEV');

INSERT INTO job_executions (job_id, start_time, status_message)
VALUES (1, NOW(), 'STARTED');

select * from job_executions;
select * from jobs;
