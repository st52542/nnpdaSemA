package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
