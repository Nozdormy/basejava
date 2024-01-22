package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.util.DateUtil.NOW;

public class ResumeTestData {
    public static void main(String[] args) {
        final Resume resume = createResume("1", "Ахилес");
        printResume(resume);
    }

    private static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(PHONE, "+30 458 456 1236");
        resume.addContact(SKYPE, "Son.of.Peleus");
        resume.addContact(MAIL, "Son_of_Peleus@gmail.com");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/achilles-0a7777777/");
        resume.addContact(GITHUB, "https://github.com/achilles");
        resume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/achilles");
        resume.addContact(HOME_PAGE, "https://www.achilles.com/");

        resume.addSection(PERSONAL, new TextSection("Непревзойденная скорость и ловкость" +
                "\nКрепость и сила в бою" +
                "\nУмение использовать оружие" +
                "\nВоенная стратегия и тактика"));
        resume.addSection(OBJECTIVE, new TextSection("Участник Троянской войны"));
        resume.addSection(ACHIEVEMENT, new ListSection(("На острове Тенедос" +
                "победил местного царя" +
                "\nПод стенами Трои, в первом же бою убил Кикна – героя-троянца" +
                "\nУбил троянского царевича Гектора")));
        resume.addSection(QUALIFICATIONS, new ListSection(("КМС по раздаче люлей")));

        Organization.Position period1 = new Organization.Position(LocalDate.of(-1300, 6, 11),
                LocalDate.of(-1285, 10, 5), "Воин", "То там то сям");
        Organization organizationWork1 = new Organization("Троянская война",
                "https://ru.wikipedia.org/wiki/Троянская_война", period1);
        Organization.Position period2 = new Organization.Position(LocalDate.of(-1285, 10, 5), NOW,
                "Легенда", "Герой, чье имя всегда будет ассоциироваться с мужеством, силой и славой.");
        Organization organizationWork2 = new Organization("История", "", period2);

        Organization.Position period3 = new Organization.Position(-1302, Month.JANUARY, -1301, Month.JANUARY,
                "Ученик Хирона", "Учился");
        Organization.Position period4 = new Organization.Position(-1301, Month.APRIL,-1300,Month.JUNE,
                "Помощник Хирона", "Помогал");
        Organization organizationStudy = new Organization("Школа Хирона", "Академия_Хирона.ru",
                period3, period4);

        resume.addSection(EXPERIENCE, new OrganizationSection(organizationWork1, organizationWork2));
        resume.addSection(EDUCATION, new OrganizationSection(organizationStudy));

        return resume;
    }

    private static void printResume(Resume resume) {
        System.out.println(resume.getFullName() + "\n");
        System.out.println(resume.getContact(PHONE) + "\n" + resume.getContact(SKYPE));
        System.out.println(resume.getContact(MAIL) + "\n" + resume.getContact(GITHUB));
        System.out.println(resume.getContact(STACKOVERFLOW) + "\n" + resume.getContact(HOME_PAGE));
        System.out.println("----------------------------");

        System.out.println(OBJECTIVE + "\n" + resume.getSection(OBJECTIVE) + "\n");
        System.out.println(PERSONAL + "\n" + resume.getSection(PERSONAL) + "\n");
        System.out.println(ACHIEVEMENT);
        ListSection achievements = (ListSection) resume.getSection(ACHIEVEMENT);
        for (String item : achievements.getItems()) {
            System.out.println(item);
        }

        System.out.println("\n" + QUALIFICATIONS);
        ListSection qualifications = (ListSection) resume.getSection(QUALIFICATIONS);
        for (String item : qualifications.getItems()) {
            System.out.println(item);
        }

        System.out.println("\n" + EXPERIENCE);
        OrganizationSection experience = (OrganizationSection) resume.getSection(EXPERIENCE);
        for (Organization organization : experience.getOrganizations()) {
            System.out.println(organization + "\n");
        }

        System.out.println("\n" + EDUCATION);
        OrganizationSection education = (OrganizationSection) resume.getSection(EDUCATION);
        for (Organization organization : education.getOrganizations()) {
            System.out.println(organization);
        }
    }
}