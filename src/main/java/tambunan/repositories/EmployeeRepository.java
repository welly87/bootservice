package tambunan.repositories;

import org.springframework.data.repository.CrudRepository;
import tambunan.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
