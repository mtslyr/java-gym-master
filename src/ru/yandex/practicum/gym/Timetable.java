package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, Set<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        Map<TimeOfDay, Set<TrainingSession>> dayTable = timetable.computeIfAbsent(day, k -> new TreeMap<>());
        dayTable.computeIfAbsent(time, k -> new HashSet<>()).add(trainingSession);
        System.out.println("Тренировка добавлена в расписание");
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        Map<TimeOfDay, Set<TrainingSession>> dayTable = timetable.get(dayOfWeek);
        if (dayTable == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> result = new ArrayList<>();
        for (Set<TrainingSession> sessions : dayTable.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public Set<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        Map<TimeOfDay, Set<TrainingSession>> dayTable = timetable.get(dayOfWeek);

        if (dayTable == null || !dayTable.containsKey(timeOfDay)) {
            return Collections.emptySet();
        }

        return dayTable.get(timeOfDay);
    }

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> count = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            for (TimeOfDay time : timetable.get(day).keySet()) {
                for (TrainingSession training : timetable.get(day).get(time)) {
                    count.merge(training.getCoach(), 1, Integer::sum);
                }
            }
        }

        List<Map.Entry<Coach, Integer>> entries = new ArrayList<>(count.entrySet());

        entries.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());

        Map<Coach, Integer> sorted = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : entries) {
            sorted.put(entry.getKey(), entry.getValue());
        }

        return sorted;
    }
}
