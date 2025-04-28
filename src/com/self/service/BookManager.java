package com.self.service;

import java.util.ArrayList;

import com.self.vo.Book;

public interface BookManager {
	// ArrayList<Book> bookList = new ArrayList<Book>();
	
	void insertBook(Book book);
	void deleteBook(int isbn);
	void updateBook(Book book);
	Book getBook(int isbn);
	Book[] getAllBook();
	// ArrayList<Book> getAllBook();
	int getNumberOfBooks();
	Book[] searchBookByTitle(String title);
	// ArrayList<Book> searchBookByTitle(String title);
	Book[] searchBookByPrice(double min, double max);
	// ArrayList<Book> searchBookByPrice(double min, double max);
	double getSumPriceOfBooks();
	double getAvgPriceOfBooks();
	Book getRecentBook();
	String getPopularGenre();
	Book[] magazineOfThisYearInfo();
}