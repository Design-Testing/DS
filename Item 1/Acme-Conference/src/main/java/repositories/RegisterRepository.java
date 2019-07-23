
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

}
