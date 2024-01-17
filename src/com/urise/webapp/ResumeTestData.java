package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.List;

import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.model.ContactType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume1 = new Resume("Ахилес");

        resume1.addContact(PHONE, "+30 458 456 1236");
        resume1.addContact(SKYPE, "Son.of.Peleus");
        resume1.addContact(MAIL, "Son_of_Peleus@gmail.com");
        resume1.addContact(LINKEDIN, "https://www.linkedin.com/in/achilles-0a7777777/");
        resume1.addContact(GITHUB, "https://github.com/achilles");
        resume1.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/achilles");
        resume1.addContact(HOME_PAGE, "https://www.achilles.com/");

        resume1.addSection(PERSONAL, new TextSection("Непревзойденная скорость и ловкость," +
                " Крепость и сила в бою, Умение использовать оружие, Военная стратегия и тактика"));
        resume1.addSection(OBJECTIVE, new TextSection("Участник Троянской войны"));
        resume1.addSection(ACHIEVEMENT, new ListSection(List.of("На острове Тенедос " +
                "победил местного царя. Под стенами Трои, в первом же бою убил Кикна – " +
                "героя-троянца. Убил троянского царевича Гектора")));
        resume1.addSection(QUALIFICATIONS, new ListSection(List.of("КМС по раздаче люлей")));

        Period period1 = new Period(LocalDate.of(-1300, 6, 11), LocalDate.of(-1285, 10, 5), "Воин",
                "То там то сям");
        Organization organizationWork1 = new Organization("Троянская война",
                "https://ru.wikipedia.org/wiki/Троянская_война", List.of(period1));
        Period period2 = new Period(LocalDate.of(-1285, 10, 5), LocalDate.now(), "Легенда",
                "Герой, чье имя всегда будет ассоциироваться с мужеством, силой и славой.");
        Organization organizationWork2 = new Organization("История", "", List.of(period2));

        resume1.addSection(EXPERIENCE, new OrganizationSection(List.of(organizationWork1, organizationWork2)));

        Period period3 = new Period(LocalDate.of(-1302, 1, 1), LocalDate.of(-1301, 1, 1),
                "Ученик Хирона", "");
        Organization organizationStudy = new Organization("Школа Хирона", "Академия_Хирона.ru",
                List.of(period3));
        resume1.addSection(EDUCATION, new OrganizationSection(List.of(organizationStudy)));

        System.out.println(resume1.getFullName() + "\n");
        System.out.println(resume1.getContact(PHONE) + "\n" + resume1.getContact(SKYPE));
        System.out.println(resume1.getContact(MAIL) + "\n" + resume1.getContact(GITHUB));
        System.out.println(resume1.getContact(STACKOVERFLOW) + "\n" + resume1.getContact(HOME_PAGE));
        System.out.println("----------------------------");

        System.out.println(OBJECTIVE + "\n" + resume1.getSection(OBJECTIVE) + "\n");
        System.out.println(PERSONAL + "\n" + resume1.getSection(PERSONAL) + "\n");
        System.out.println(ACHIEVEMENT);
        ListSection achievements = (ListSection) resume1.getSection(ACHIEVEMENT);
        for (String item : achievements.getItems()) {
            System.out.println(item);
        }

        System.out.println("\n" + QUALIFICATIONS);
        ListSection qualifications = (ListSection) resume1.getSection(QUALIFICATIONS);
        for (String item : qualifications.getItems()) {
            System.out.println(item);
        }

        System.out.println("\n" + EXPERIENCE);
        OrganizationSection experience = (OrganizationSection) resume1.getSection(EXPERIENCE);
        for (Organization organization : experience.getOrganizations()) {
            System.out.println(organization + "\n");
        }

        System.out.println("\n" + EDUCATION);
        OrganizationSection education = (OrganizationSection) resume1.getSection(EDUCATION);
        for (Organization organization : education.getOrganizations()) {
            System.out.println(organization);
        }
    }
}