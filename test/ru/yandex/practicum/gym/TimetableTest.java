package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
        int expectedMonday = 1;
        int actualMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        int expectedTuesday = 0;
        int actualTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();

        Assertions.assertEquals(
                expectedMonday,
                actualMonday,
                "Ожидалось тренировок в понедельник: %d. Фактическое количество тренировок в понедельник: %d".formatted(expectedMonday, actualMonday)
        );

        Assertions.assertEquals(
                expectedTuesday,
                actualTuesday,
                "Ожидалось тренировок во вторник: %d. Фактическое количество тренировок во вторник: %d".formatted(expectedTuesday, actualTuesday)
        );
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        int expectedMonday = 1;
        int actualMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size();
        Assertions.assertEquals(
                expectedMonday,
                actualMonday,
                "Ожидалось тренировок в понедельник: %d. Фактическое количество тренировок в понедельник: %d".formatted(expectedMonday, actualMonday)
        );

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        int expectedThursday = 2;
        int actualThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size();
        Assertions.assertEquals(
                expectedMonday,
                actualMonday,
                "Ожидалось тренировок в четверг: %d. Фактическое количество тренировок в четверг: %d".formatted(expectedThursday, actualThursday)
        );

        List<TrainingSession> thursdaysTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertTrue(
                thursdaysTrainings.get(0).getTimeOfDay().getHours() == 13
                        && thursdaysTrainings.get(0).getTimeOfDay().getMinutes() == 0
        );

        Assertions.assertTrue(
                thursdaysTrainings.get(1).getTimeOfDay().getHours() == 20
                        && thursdaysTrainings.get(1).getTimeOfDay().getMinutes() == 0
        );

        // Проверить, что за вторник не вернулось занятий
        int expectedTuesday = 0;
        int actualTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size();
        Assertions.assertEquals(
                expectedTuesday,
                actualTuesday,
                "Ожидалось тренировок во вторник: %d. Фактическое количество тренировок во вторник: %d".formatted(expectedTuesday, actualTuesday)
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        int expectedMonday1300 = 1;
        int actualMonday1300 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size();

        Assertions.assertEquals(
                expectedMonday1300,
                actualMonday1300,
                "Ожидалось тренировок в понедельник в 13-00: %d. Фактическое количество тренировок в понедельник в 13-00: %d".formatted(expectedMonday1300, actualMonday1300)
        );

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        int expectedMonday1400 = 0;
        int actualMonday1400 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size();

        Assertions.assertEquals(
                expectedMonday1400,
                actualMonday1400,
                "Ожидалось тренировок в понедельник в 14-00: %d. Фактическое количество тренировок в понедельник в 14-00: %d".formatted(expectedMonday1400, actualMonday1400)
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWithDifferentTimes() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Петров", "Петр", "Петрович");
        Group group = new Group("Бокс", Age.ADULT, 90);

        // граничные значения времени
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(0, 0)));    // полночь
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(6, 30)));   // утро
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));   // полдень
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(18, 45)));  // вечер
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(23, 59)));  // почти полночь

        // общее количество
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(5, mondaySessions.size(),
                "В понедельник должно быть 5 тренировок");


        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(0, 0)).size(),
                "Должна быть тренировка в 00:00");
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(12, 0)).size(),
                "Должна быть тренировка в 12:00");
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(23, 59)).size(),
                "Должна быть тренировка в 23:59");

        // время, на которое нет тренировок
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(15, 30)).size(),
                "Не должно быть тренировки в 15:30");
    }

    @Test
    void testMultipleTrainingsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 60);

        TrainingSession training1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession training2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(training1);
        timetable.addNewTrainingSession(training2);

        // в 10:00 две тренировки
        Set<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        Assertions.assertEquals(2, sessions.size(),
                "Должно быть 2 тренировки в одно время");

        // в понедельник тоже 2 тренировки
        List<TrainingSession> daySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(2, daySessions.size(),
                "В понедельник должно быть 2 тренировки");
    }

    @Test
    void testTrainingSortingByTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Сидоров", "Алексей", "Владимирович");
        Group group = new Group("Фитнес", Age.ADULT, 45);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 30)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 45)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);

        Assertions.assertEquals(4, sessions.size(),
                "Должно быть 4 тренировки");

        // отсортированы по возрастанию времени
        for (int i = 0; i < sessions.size() - 1; i++) {
            TimeOfDay currentTime = sessions.get(i).getTimeOfDay();
            TimeOfDay nextTime = sessions.get(i + 1).getTimeOfDay();

            Assertions.assertTrue(
                    currentTime.getHours() < nextTime.getHours() ||
                            (currentTime.getHours() == nextTime.getHours() &&
                                    currentTime.getMinutes() <= nextTime.getMinutes()),
                    "Тренировки должны быть отсортированы по времени"
            );
        }
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Стретчинг", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        Map<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countByCoaches.size(),
                "Должен быть 1 тренер");
        Assertions.assertEquals(3, countByCoaches.get(coach),
                "У тренера должно быть 3 тренировки");
    }

    @Test
    void testGetCountByCoachesMultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Алексей", "Владимирович");

        Group group = new Group("Кардио", Age.ADULT, 45);
        // тренировки 1 тренера
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(11, 0)));

        // тренировки 2 тренера
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        // тренировки 3 тренера
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.SUNDAY, new TimeOfDay(12, 0)));

        Map<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(3, countByCoaches.size(),
                "Должно быть 3 тренера");

        // по убыванию количества тренировок
        List<Integer> counts = new ArrayList<>(countByCoaches.values());
        Assertions.assertTrue(counts.get(0) >= counts.get(1) && counts.get(1) >= counts.get(2),
                "Тренеры должны быть отсортированы по убыванию количества тренировок");

        // конкретные значения
        Assertions.assertEquals(4, countByCoaches.get(coach1));
        Assertions.assertEquals(2, countByCoaches.get(coach2));
        Assertions.assertEquals(1, countByCoaches.get(coach3));
    }

    @Test
    void testGetCountByCoachesWithSameTrainingCount() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Антонов", "Антон", "Антонович");
        Coach coach2 = new Coach("Борисов", "Борис", "Борисович");
        Coach coach3 = new Coach("Васильев", "Василий", "Васильевич");

        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 60);
        Group group3 = new Group("Стретчинг", Age.ADULT, 45);

        // тренеры ведут по 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group1, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(11, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group2, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group3, coach3,
                DayOfWeek.SATURDAY, new TimeOfDay(12, 0)));

        Map<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(3, countByCoaches.size(),
                "Должно быть 3 тренера");

        // у всех по 2 тренировки
        for (Integer count : countByCoaches.values()) {
            Assertions.assertEquals(2, count,
                    "У каждого тренера должно быть по 2 тренировки");
        }
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        Map<Coach, Integer> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertTrue(countByCoaches.isEmpty(),
                "Для пустого расписания метод должен возвращать пустую карту");
    }
}
