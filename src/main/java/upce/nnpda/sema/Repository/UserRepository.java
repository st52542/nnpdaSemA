package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
    //User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByName(String jmeno);

    User findByEmail(String email);

    void removeUserById(Long id);
}
