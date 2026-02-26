-- Employee (no FK to employment_details; EmploymentDetails owns the 1:1 via employee_id)
CREATE TABLE employee (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

-- EmploymentDetails: 1:1 with Employee (FK on this side)
CREATE TABLE employment_details (
    id VARCHAR(36) PRIMARY KEY,
    hire_date DATE,
    departement VARCHAR(255),
    position VARCHAR(255),
    employee_id VARCHAR(36) NOT NULL UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_employment_details_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE INDEX idx_employment_details_employee_id ON employment_details(employee_id);

-- Compensation: N:1 to Employee
CREATE TABLE compensation (
    id VARCHAR(36) PRIMARY KEY,
    salary DECIMAL(19, 2),
    bonus DECIMAL(19, 2),
    employee_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_compensation_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE INDEX idx_compensation_employee_id ON compensation(employee_id);

-- LeaveRequest: N:1 to Employee
CREATE TABLE leave_request (
    id VARCHAR(36) PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    type VARCHAR(255),
    status VARCHAR(255),
    employee_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_leave_request_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE INDEX idx_leave_request_employee_id ON leave_request(employee_id);
