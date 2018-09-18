package practice.rpcfs.chp4;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class HttpRpcServer {
    public static void main(String[] args) {
        //tomcat
        Tomcat tomcat = null;
        try {
            tomcat = new Tomcat();
            tomcat.setPort(8080);
            Connector connector = tomcat.getConnector();
            connector.setProperty("maxThreads", String.valueOf(200));
            connector.setProperty("minSpareThreads", "20");
            connector.setProperty("maxConnections", String.valueOf(10000));
            connector.setProperty("URIEncoding", "UTF-8");
            connector.setProperty("connectionTimeout", String.valueOf(20000));
            connector.setProperty("enableLookups", "false");
            connector.setProperty("keepAliveTimeout", String.valueOf(60000));
            connector.setProperty("maxKeepAliveRequests", String.valueOf(100));
            connector.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
            connector.setProperty("compression", "on");
            int compressionMinSize = 1024 * 1024;
            connector.setProperty("compressionMinSize", String.valueOf(compressionMinSize));
            connector.setProperty("compressableMimeType", "application/json");

            String workPath = System.getProperty("user.dir");
            File webAppPath = new File(workPath, "src/main/webapp");
//            if (!webAppPath.isDirectory()) {
//                webAppPath = new File(workPath, "webapp");
//            }

            System.err.println("=======webAppPath:" + webAppPath.getCanonicalPath() + "=========");

            StandardContext ctx = (StandardContext) tomcat.addWebapp("/Practice", webAppPath.getCanonicalPath());

            File additionWebInfClasses = new File("target/classes");
            if (additionWebInfClasses.exists()) {
                WebResourceRoot resources = new StandardRoot(ctx);
                resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
                ctx.setResources(resources);
            }

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start tomcat server", e);
        } finally {
            if (tomcat != null) {
                try {
                    tomcat.stop();
                } catch (Exception e) {
                    System.err.println("tomcat shutdown error," + e.getMessage());
                }
            }
        }
    }
}
