package ru.otus.taskmanager.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.otus.taskmanager.model.user.Role;
import ru.otus.taskmanager.model.task.Task;
import ru.otus.taskmanager.model.user.User;
import ru.otus.taskmanager.service.RoleService;
import ru.otus.taskmanager.service.TaskService;
import ru.otus.taskmanager.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PrerequisitesDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(PrerequisitesDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.admin.image}")
    private String defaultAdminImage;

    @Autowired
    public PrerequisitesDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword,
                defaultAdminImage);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        User manager = new User(
                "manager@mail.com",
                "Manager",
                "112233",
                "images/admin.png");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);

        userService.createUser(new User(
                "mark@mail.com",
                "Mark",
                "112233",
                "images/user.png"));

        userService.createUser(new User(
                "ann@mail.com",
                "Ann",
                "112233",
                "images/user.png"));

        userService.createUser(new User(
                "jhonny@mail.com",
                "Jhonny",
                "112233",
                "images/user.png"));

        userService.createUser(new User(
                "kate@mail.com",
                "Kate",
                "112233",
                "images/user.png"));

        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);

        LocalDate today = LocalDate.now();

        taskService.createTask(new Task(
                "Fix authorization bugs ",
                "random list of bugs here...",
                today.minusDays(40),
                true,
                userService.getUserByEmail("mark@mail.com").getName(),
                userService.getUserByEmail("mark@mail.com")
        ));

        taskService.createTask(new Task(
                "Possible SQL Injection in method getUserById...",
                "fix and prevent sql injection",
                today.minusDays(30),
                true,
                userService.getUserByEmail("ann@mail.com").getName(),
                userService.getUserByEmail("ann@mail.com")
        ));

        taskService.createTask(new Task(
                "Introduce workflow to new workers",
                "Meet them this friday",
                today.minusDays(2),
                false,
                userService.getUserByEmail("jhonny@mail.com").getName(),
                userService.getUserByEmail("jhonny@mail.com")
        ));

        taskService.createTask(new Task(
                "Meet customer to show off first demo of our project",
                "Create presentation of what has been done during last year",
                today.minusDays(10),
                true,
                userService.getUserByEmail("kate@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        taskService.createTask(new Task(
                "Get quotation for hosting and domain",
                "Get quotation for hosting and domain, particularly if specialized hosting is involved such as VPS hosting, cloud hosting, or special hosting or environment requirements",
                today.minusDays(5),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("kate@mail.com")
        ));

        taskService.createTask(new Task(
                "Check the quality of each content element",
                "Quality assure each piece of content you have outsourced or bought – and ask for changes where necessary",
                today.minusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("mark@mail.com")
        ));

        taskService.createTask(new Task(
                "Populate the website content with the various content items you have agreed with the client.",
                "todo",
                today.minusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("mark@mail.com")
        ));

        taskService.createTask(new Task(
                "Check all web copywriting",
                "Make sure web copywriting has been proofread and ran through a spelling and grammar checker to check for correctness. Use online tools such as Reverso, or Spellcheckplus.com. Check that generic content, such as lorem ipsum, has been properly removed and replaced.",
                today,
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("ann@mail.com")
        ));

        taskService.createTask(new Task(
                "Check all images and videos",
                "See that all images are in the correct places, smushed, formatted, width and height specified and working on all devices. Confirm that videos and audio files are in the correct places, formatted and working on all devices.",
                today.plusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("ann@mail.com")
        ));


        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}
