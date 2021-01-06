package hu.mik.prog5.reactive.repository;

import hu.mik.prog5.reactive.document.EnumRole;
import hu.mik.prog5.reactive.document.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
	
	Optional<Role> findByName(EnumRole name);
	
}
