package in.co.dhdigital.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import in.co.dhdigital.processors.StatementCacheProcessor;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
	static Logger log =LoggerFactory.getLogger(RedisConfig.class);

	 @Value("${spring.redis.host}")
	 private String host;
	 
	 @Value("${spring.redis.port}")
	 private Integer port;

	 @Value("${spring.redis.password}")
	 private String password;
	
    @Bean
    public JedisConnectionFactory connectionFactory() {
    	 log.info("=================================================================");
         log.info("redis config : {} : {} ", host, port);
         log.info("=================================================================");

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(password);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}