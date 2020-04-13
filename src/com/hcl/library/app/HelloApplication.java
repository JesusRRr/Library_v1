package com.hcl.library.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class HelloApplication extends ResourceConfig {
	
	public HelloApplication() {
		packages("com.hcl.library");
		register(JacksonFeature.class);
	}
}
