/**
 * 
 */
package com.ido85.config;

import org.springframework.context.annotation.Bean;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

/**
 * @author rongxj
 *
 */
@EnableReactor
public class RectorConfiguration {

	@Bean
	Environment env() {
		return Environment.initializeIfEmpty().assignErrorJournal();
	}

	@Bean
	EventBus createEventsBus(Environment env) {
		return EventBus.create(env, Environment.WORK_QUEUE);
	}
}
