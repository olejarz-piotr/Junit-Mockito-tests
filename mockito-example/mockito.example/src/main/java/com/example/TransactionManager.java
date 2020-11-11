package com.example;

public interface TransactionManager {

	<Type> Type doInTransaction(TransactionCode<Type> code);

}
