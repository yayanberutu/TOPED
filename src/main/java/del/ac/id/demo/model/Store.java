package del.ac.id.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("store")
public class Store {
	@Id
	String id;
	
	private String store_name, location, reputation;
	private int points, latest_online;
	
	
	
	public Store() {

	}
	public Store(final String id, final String store_name, final String location, final String reputation, final int points, final int latest_online) {
		
		this.id = id;
		this.store_name = store_name;
		this.location = location;
		this.reputation = reputation;
		this.points = points;
		this.latest_online = latest_online;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReputation() {
		return reputation;
	}
	public void setReputation(String reputation) {
		this.reputation = reputation;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getLatest_online() {
		return latest_online;
	}
	public void setLatest_online(int latest_online) {
		this.latest_online = latest_online;
	}
	
}
