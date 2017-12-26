package com.basant.spring.data.elasticsearch.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.basant.spring.data.elasticsearch.api.model.User;
import com.basant.spring.data.elasticsearch.api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	ElasticsearchOperations operations;
	@Autowired
	ElasticsearchTemplate template;

	@PostConstruct
	public void loadALL() {
		List<User> users = new ArrayList<>();
		users.add(new User(11, "basanta", "dev", 20000, new String[] { "bangalore", "Marathali" }));
		users.add(new User(14, "basa", "test", 30000, new String[] { "bangalore", "BTM" }));
		users.add(new User(89, "bikash", "civil", 20000, new String[] { "hyderbad", "Hitech city" }));
		users.add(new User(18, "Asutosh", "OAS", 80000, new String[] { "Pune", "sumaguda" }));
		users.add(new User(61, "saroj", "dev", 20000, new String[] { "bangalore", "kartik nagar" }));
		users.add(new User(88, "bitu", "dentist", 90000, new String[] { "mumbai", "andheri" }));
		operations.putMapping(User.class);
		repository.save(users);
	}

	public List<User> findByName(String name) {
		return repository.findByName(name);
	}

	public List<User> findByProfession(String profession) {
		return repository.findByProfession(profession);
	}

	public Iterable<User> getAll() {
		return repository.findAll();
	}
	// custom search using(QueryBuilder) wild-card filter
	public List<User> getAllWihFilter(String text) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("name").field("profession"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("name")
						.field("profession"));
		NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(query).build();
		List<User> users = template.queryForList(build, User.class);
		return users;
	}

	// using inbuilt pagination

	public List<User> getPaginationData(long salary) {
		return repository.findBySalary(salary, new PageRequest(0, 2)).getContent();
	}
	
	// custom search query using SearchQuery
	public List<User> getCustomSearchdata(String address) {
		String searchType = ".*" + address + ".*";
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.regexpQuery("address", searchType)).build();
		List<User> users = template.queryForList(searchQuery, User.class);
		return users;

	}
}
