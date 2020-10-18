package del.ac.id.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import del.ac.id.demo.model.Store;

public interface StoreRepository extends MongoRepository<Store, String> {
	
}
