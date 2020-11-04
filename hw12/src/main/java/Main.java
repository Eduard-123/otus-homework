import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.DBManager;
import service.DBUserServiceImpl;
import servlet.LoginServlet;
import servlet.UserBrowserServlet;
import servlet.UserEditorServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(Main.class, "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        DBUserServiceImpl userService = DBUserServiceImpl.init(DBManager.getConnection());

        context.addServlet(new ServletHolder(new LoginServlet(cfg.getTemplate("login.ftl"), cfg.getTemplate("login-error.ftl"))),"/*");
        context.addServlet(new ServletHolder(new UserBrowserServlet(userService, cfg.getTemplate("user-browser.ftl"))),"/user-browser");
        context.addServlet(new ServletHolder(new UserEditorServlet(userService, cfg.getTemplate("user-editor.ftl"))),"/user-editor");

        server.start();
        server.join();
    }
}
