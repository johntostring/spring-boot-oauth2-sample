package com.nari.resource.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This guy is busy, nothing left.
 *
 * @author John Zhang
 */
@RestController
public class SampleController {

    ConcurrentMap<String, Book> booksMap = new ConcurrentHashMap<String, Book>();

    @GetMapping("/books")
    public ResponseEntity getBooks() {
        return ResponseEntity.ok(booksMap.values());
    }

    public SampleController() {
        Book book1 = new Book("1001", "JavaScript高级程序设计", "foobar", new Date());
        Book book2 = new Book("1002", "CSS权威指南", "foobar", new Date());
        Book book3 = new Book("1003", "黑客与画家", "foobar", new Date());
        Book book4 = new Book("1004", "Angular 2 从入门到放弃", "foobar", new Date());
        Book book5 = new Book("1005", "MySQL 从删库到跑路", "foobar", new Date());
        booksMap.put(book1.getId(), book1);
        booksMap.put(book2.getId(), book2);
        booksMap.put(book3.getId(), book3);
        booksMap.put(book4.getId(), book4);
        booksMap.put(book5.getId(), book5);
    }

    class Book {
        private String id;
        private String name;
        private String author;
        private Date publishDate;

        public Book() {
        }

        public Book(String id, String name, String author, Date publishDate) {
            this.id = id;
            this.name = name;
            this.author = author;
            this.publishDate = publishDate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Date getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(Date publishDate) {
            this.publishDate = publishDate;
        }
    }
}
