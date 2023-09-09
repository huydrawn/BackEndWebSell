package spring.server.commercial.config.acl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
public class AclConfig {

	@Autowired
	CacheManager cacheManager;
	@Autowired
	private DataSource dataSource;

	@Bean
	public DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(this.aclService());
		expressionHandler.setPermissionEvaluator(permissionEvaluator);

		return expressionHandler;
	}

	@Bean
	public LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(),
				permissionGrantingStrategy());
	}

	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

	@Bean
	public AclCache aclCache() {
		return new SpringCacheBasedAclCache(cacheManager.getCache("aclCache"), permissionGrantingStrategy(),
				aclAuthorizationStrategy());
	}

	@Bean
	public JdbcMutableAclService aclService() {
		JdbcMutableAclService jdbcMutableAclService = new JdbcMutableAclService(dataSource, lookupStrategy(),
				aclCache());
		jdbcMutableAclService.setSidIdentityQuery("SELECT @@IDENTITY"); // Chỉ định truy vấn lấy ID của SID từ MySQL
		jdbcMutableAclService.setClassIdentityQuery("SELECT @@IDENTITY"); // Chỉ định truy vấn lấy ID của class từ MySQL
		jdbcMutableAclService.setSidIdentityQuery("SELECT @@IDENTITY"); // Chỉ định truy vấn lấy ID của ACL từ MySQL
		return jdbcMutableAclService;
	}
}