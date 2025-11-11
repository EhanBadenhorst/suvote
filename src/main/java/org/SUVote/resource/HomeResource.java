package org.SUVote.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/home")
public class HomeResource {
    
    // Helper method to read HTML files
    private Response serveHtml(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream("/META-INF/resources/" + fileName);
            if (is == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("File not found: " + fileName)
                    .build();
            }
            String content = new String(is.readAllBytes());
            return Response.ok(content, MediaType.TEXT_HTML).build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error reading file: " + e.getMessage())
                .build();
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return serveHtml("index.html");
    }
    
    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response login() {
        return serveHtml("SUlogin.html");
    }
    
    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public Response register() {
        return serveHtml("SUregister.html");
    }
    
    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    public Response dashboard() {
        return serveHtml("SUdashboard.html");
    }
    
    @GET
    @Path("/vote")
    @Produces(MediaType.TEXT_HTML)
    public Response vote() {
        return serveHtml("SUvote.html");
    }
    
    @GET
    @Path("/results")
    @Produces(MediaType.TEXT_HTML)
    public Response results() {
        return serveHtml("results.html");
    }
    
    @GET
    @Path("/settings")
    @Produces(MediaType.TEXT_HTML)
    public Response settings() {
        return serveHtml("settings.html");
    }
    
    @GET
    @Path("/contact-us")
    @Produces(MediaType.TEXT_HTML)
    public Response contactUs() {
        return serveHtml("contact-us.html");
    }
    
    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_HTML)
    public Response admin() {
        return serveHtml("SUadmin.html");
    }
    
    @GET
    @Path("/admin/main")
    @Produces(MediaType.TEXT_HTML)
    public Response adminMain() {
        return serveHtml("SUadminmain.html");
    }
    
    @GET
    @Path("/admin/create")
    @Produces(MediaType.TEXT_HTML)
    public Response adminCreate() {
        return serveHtml("SUcreate.html");
    }
}