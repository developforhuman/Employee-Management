package com.idrak.emp.service;

import com.idrak.emp.model.request.EmployeeSearchRequest;
import com.idrak.emp.model.response.EmployeeTaskResponse;
import java.util.List;

public interface EmployeeService {

    List<EmployeeTaskResponse> findEmployeesWithTasksBetweenDatesAndDepartmentName(EmployeeSearchRequest request);

}
