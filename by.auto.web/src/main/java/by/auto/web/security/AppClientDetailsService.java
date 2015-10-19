/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.auto.web.security;

import by.auto.domain.common.App;
import by.auto.persistence.repository.mongo.AppRepository;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Adapts App records returned by an {@link AppRepository} to Spring Security OAuth2 {@link org.springframework.security.oauth2.provider.ClientDetails}.
 * Allows an AppRepository to serve as the source for OAuth2 Clients known to the Spring Security OAuth2 provider.
 * @author ssadouski
 */
@Service("clientDetails")
public class AppClientDetailsService implements ClientDetailsService {

    @Inject
	private AppRepository appRepository;
	
	@Override
	public ClientDetails loadClientByClientId(String appId) throws OAuth2Exception {
		App app = appRepository.findByApiKey(appId);
        if (app != null) {
            return clientDetailsFor(app);
        } else {
            throw new OAuth2Exception("Invalid OAuth App ID " + appId);
        }
	}

	private ClientDetails clientDetailsFor(App app) {
		return new AppClientDetails(app);
	}
	
	private static class AppClientDetails extends BaseClientDetails {

		public AppClientDetails(App app) {
			// TODO Consider putting hard-coded values in DB instead.
			super(app.getApiKey(), "autoApi", "read,write", "authorization_code,token,password", "ROLE_CLIENT", app.getCallbackUrl());
			setClientSecret(app.getSecret());
		}

	}

}