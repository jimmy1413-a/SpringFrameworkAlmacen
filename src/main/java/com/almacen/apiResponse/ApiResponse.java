package com.almacen.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
	
	private String message;
	private int status;
	private T data;
	
}
