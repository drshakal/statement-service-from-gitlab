package in.co.dhdigital.repositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import in.co.dhdigital.models.StatementServiceResponse;

@Repository
public class StatementServiceDao {

	public static final String HASH_KEY = "StatementServiceResponse";

	
	@Autowired
	private RedisTemplate<String, Object>  template;
	
	 
	  
	public StatementServiceResponse setStatementResponse(StatementServiceResponse statement) {
		 template.opsForHash().put(HASH_KEY , statement.getAccountDetails().getAccountNumber(), statement);
		 template.expire(HASH_KEY, 10, TimeUnit.SECONDS);
		 return statement;
	}
	
	public StatementServiceResponse findStatementResponseById(String accountNumber) {
		return (StatementServiceResponse) template.opsForHash().get(HASH_KEY, accountNumber);
	}
	
}
