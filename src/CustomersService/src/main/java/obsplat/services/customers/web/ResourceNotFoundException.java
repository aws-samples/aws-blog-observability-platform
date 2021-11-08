// Original Copyright 2002-2017 the original author or authors. Licensed under the Apache 2.0 License.
// Modifications Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved. 

package obsplat.services.customers.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7037032375889045596L;

	public ResourceNotFoundException(String message) {
        super(message);
    }

}
