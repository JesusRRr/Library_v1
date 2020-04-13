package com.hcl.library.app;

import org.glassfish.jersey.server.ResourceConfig;

public class HelloApplication extends ResourceConfig {
	
	public HelloApplication() {
		packages("com.javahelps.jerseydemo.services");
	}
}
