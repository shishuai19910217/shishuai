package com.ido85.frame.common.restful;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {

	/**
	 * Returns the id identifying the object.
	 * 
	 * @return the identifier or {@literal null} if not available.
	 */
	ID getId();
}
