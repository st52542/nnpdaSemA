package upce.nnpda.sema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upce.nnpda.sema.Entity.Type;

public interface TypeRepository extends JpaRepository<Type,Long> {
}
