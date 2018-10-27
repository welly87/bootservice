package tambunan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tambunan.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;


}
