package org.lukosan.salix.autoconfigure;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lukosan.salix.SalixProperties;
import org.lukosan.salix.SalixScopeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

@Configuration
@EnableConfigurationProperties(SalixProperties.class)
public class SalixUiAutoConfiguration {

	@Configuration
	@ComponentScan(basePackages="org.lukosan.salix.ui")
	public static class SalixControllerConfiguration {
		
		private static final Log logger = LogFactory.getLog(SalixControllerConfiguration.class);
		
		@Autowired
		private SalixProperties salixProperties;
		
		@Bean
		@ConditionalOnMissingBean
		public PermissionEvaluator permissionEvaluator() {
			return new PermissionEvaluator() {
				@Override
				public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
					return true;
				}
				@Override
				public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
					return true;
				}
			};
		}
		
		@Bean
		@ConditionalOnMissingBean
		public SalixScopeRegistry salixScopeRegistry() {
			return new SalixScopeRegistry();
		}
		
		@PostConstruct
		public void postConstruct() {
			if(logger.isInfoEnabled())
				logger.info("PostConstruct " + getClass().getSimpleName());
		}

		
	}
}