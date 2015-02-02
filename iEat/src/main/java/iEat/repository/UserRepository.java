package iEat.repository;

import org.springframework.data.repository.CrudRepository;
import iEat.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
