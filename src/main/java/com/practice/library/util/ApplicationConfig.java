//package com.practice.library.util;
//
//import com.practice.library.repository.AuthorRepository;
//import com.practice.library.repository.impl.MySQLAuthorRepositoryImpl;
//import com.practice.library.service.AuthorService;
//import com.practice.library.service.impl.AuthorServiceImpl;
//
//import java.util.HashSet;
//import java.util.Set;
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.core.Application;
//
//@ApplicationPath("/")
//public class ApplicationConfig extends Application {
//
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> set = new HashSet<>();
//        set.add(AuthorService.class);
//        set.add(AuthorRepository.class);
//        set.add(AuthorServiceImpl.class);
//        set.add(MySQLAuthorRepositoryImpl.class);
//        return set;
//    }
//}
