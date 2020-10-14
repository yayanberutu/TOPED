package del.ac.id.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import del.ac.id.demo.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

}
