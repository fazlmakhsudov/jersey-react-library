package com.practice.jaxb;

import com.practice.library.practice.jaxb.Todo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bob")
public class TodoResource2 {

    // This method is called if XML is requested
//    @GET
//    @Produces({MediaType.APPLICATION_XML})
//    public Todo getXML() {
//        System.out.println("***** 111111111111111");
//        Todo todo = new Todo();
//        todo.setSummary("Application XML Todo Summary");
//        todo.setDescription("Application XML Todo Description");
//        System.out.println("***** 1111111111111111 --");
//        return todo;
//    }

    //    // This method is called if JSON is requested
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Todo getJSON() {
        Todo todo = new Todo();
        System.out.println("***** 11111111111122");
        todo.setId("332fdsgs");
        todo.setSummary("Application JSON Todo Summary");
        todo.setDescription("Application JSON Todo Description");
        System.out.println("***** 11111111111111122");
        return todo;
    }
//
////     This can be used to test the integration with the browser
//    @GET
//    @Produces({ MediaType.TEXT_PLAIN })
//    public String getHTML() {
//        System.out.println("***** 11111111111114444");
//        Todo todo = new Todo();
//        todo.setSummary("XML Todo Summary");
//        todo.setDescription("XML Todo Description");
//        return todo.toString();
//    }

}