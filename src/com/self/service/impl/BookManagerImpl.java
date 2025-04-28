package com.self.service.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.self.service.BookManager;
import com.self.vo.Book;
import com.self.vo.Magazine;
import com.self.vo.Novel;

public class BookManagerImpl implements BookManager{
	private Book[] books;
	// private ArrayList<Book> bookList = new ArrayList<Book>();
	public static final int MAX_SIZE = 100;
	private int idx;
	private int numberOfBook; // 총 입고된 책의 수량 등으로 활용할 수 있음
	
	// 싱글톤
	private static BookManagerImpl service = new BookManagerImpl();
	private BookManagerImpl() {
		books = new Book[MAX_SIZE];
		// bookList = new ArrayList<Book>();
	}
	public static BookManagerImpl getInstance() {
		return service;
	}
	
	@Override
	public void insertBook(Book book) {
		if(idx==MAX_SIZE) {
			System.out.println("더이상 책을 등록할 수 없습니다.");
			return;
		} else {
			books[idx++] = book;
			System.out.println("<"+book.getTitle()+">가 등록되었습니다.");
			numberOfBook++;
		}
	}

	@Override
	public void deleteBook(int isbn) {
		int find = -1;
		if(idx==0) return;
		for(int i=0; i<idx; i++) {
			if(books[i].getIsbn() == isbn) {
				find = i;
				break;
			}
		}
		for(int i=find; i<idx; i++) {
			if(i==MAX_SIZE) {
				books[i] = null;
			} else {
				books[i] = books[i+1];
			}
		}
		idx--;
	}

	@Override
	public void updateBook(Book book) {
		for(Book b : books) {
			if(b==null) break;
			if(b.getIsbn() == book.getIsbn()) {
				b.setTitle(book.getTitle());
				b.setAuthor(book.getAuthor());
				b.setPublisher(book.getPublisher());
				b.setPrice(book.getPrice());
				if(b instanceof Magazine) {
					((Magazine) b).setPublishDate(((Magazine) book).getPublishDate());
				} else if(b instanceof Novel) {
					((Novel) b).setGenre(((Novel) book).getGenre());
				}
			}
		}
	}

	@Override
	public Book getBook(int isbn) {
		Book find = null;
//		for(int i=0; i<idx; i++) {
//			if(books[i].getIsbn() == isbn) {
//				find = books[i];
//				break;
//			}
//		}
		for(Book b : books) { // 향상된 for문이 더 나음
			if(b.getIsbn()==isbn) {
				find = b;
				break;
			}
		}
		if(find==null) System.out.println("해당 책을 찾을 수 없습니다.");
		return find;
	}

	@Override
	public Book[] getAllBook() {
		return Arrays.copyOf(books, idx);
	}

	@Override
	public int getNumberOfBooks() {
		return idx;
		// 삭제된 것과 상관 없이 입고된 책의 수 반환할 경우
		// return numberOfBook;
	}

	@Override
	public Book[] searchBookByTitle(String title) {
		Book[] temp = new Book[idx];
		int idx2= 0;
		for(int i=0; i<idx; i++) {
			if(books[i]==null) break;
			if(books[i].getTitle().equals(title)) {
				temp[idx2++] = books[i];
			}
		}
		return Arrays.copyOf(temp, idx2);
	}

	@Override
	public Book[] searchBookByPrice(double min, double max) {
		Book[] temp = new Book[idx];
		int idx2 = 0;
		for(int i=0; i<idx; i++) {
			if(books[i]==null) break;
			if(books[i].getPrice() >= min && books[i].getPrice() <= max)
				temp[idx2++] = books[i];
		}
		return Arrays.copyOf(temp, idx2);
	}

	@Override
	public double getSumPriceOfBooks() {
		double sum = 0.0;
		for(Book b: books) {
			if(b==null) break;
			sum += b.getPrice();
		}
		return sum;
	}

	@Override
	public double getAvgPriceOfBooks() {
		return getSumPriceOfBooks()/idx;
	}

	@Override
	public Book getRecentBook() {
		Book temp = null;
		int year = 0;
		int month = 0;
		for(Book b: books){
			if(b==null) break;
			if(b instanceof Magazine) {
				if(((Magazine) b).getPublishDate().getYear() > year) {
					temp = b;
					year = ((Magazine) b).getPublishDate().getYear();
					month = ((Magazine) b).getPublishDate().getMonth();
				} else if(((Magazine) b).getPublishDate().getYear() == year) {
					if(((Magazine) b).getPublishDate().getMonth() > month) {
						temp = b;
						year = ((Magazine) b).getPublishDate().getYear();
						month = ((Magazine) b).getPublishDate().getMonth();
					}
				}
			}
		}
		return temp;
	}

	@Override
	public String getPopularGenre() {
		// 소설의 경우 장르가 한정되어 있다
		// 여기서는 임시로 "한국소설"과 "해외소설", "기타소설"로 구분한다
		// 장르가 늘어나도 arr1 안과 if-instancof 문 안의 if문만 추가하면 된다
		String[] arr1 = {"한국소설", "해외소설", "기타소설"};
		int[] arr2 = new int[arr1.length];
		for(Book b:books) {
			if(b==null) break;
			if(b instanceof Novel) {
				if(((Novel) b).getGenre().equals("한국소설")) {
					arr2[0]++;
				} else if (((Novel) b).getGenre().equals("해외소설")) {
					arr2[1]++;
				} else if (((Novel) b).getGenre().equals("기타소설")) {
					arr2[2]++;
				}
			}
		}
		int max = 0;
		String genre = "";
		for(int i=0; i<arr1.length; i++) {
			if (arr2[i] > max) {
				max = arr2[i];
				genre = arr1[i];
			}
		}
		return genre;
	}
	@Override
	public Book[] magazineOfThisYearInfo() {
		return null;
	}
}