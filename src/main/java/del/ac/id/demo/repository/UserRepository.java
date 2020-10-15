package del.ac.id.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import del.ac.id.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
