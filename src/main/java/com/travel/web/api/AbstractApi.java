package com.travel.web.api;

import org.springframework.http.ResponseEntity;

public interface AbstractApi<T,K> {
	
	public ResponseEntity<?> findAll();
	public T createNew(K object);
	public T findById(Long id);
	public T deleteById(Long id);
	public T updateById(Long id, K object);

}
