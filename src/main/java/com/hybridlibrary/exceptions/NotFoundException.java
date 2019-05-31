package com.hybridlibrary.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String entity){
        super(entity);
    }
}
