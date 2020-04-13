package com.hcl.library.service.rest.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfiguration extends ResourceConfig{
	
	public RestConfiguration() {
		packages("com.hcl.library");
		register(JacksonFeature.class);
	}
}
