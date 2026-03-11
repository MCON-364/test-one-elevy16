package edu.touro.las.mcon364.test;

import java.util.*;

public class StudyTracker {

    private final Map<String, List<Integer>> scoresByLearner = new HashMap<>();
    private final Deque<UndoStep> undoStack = new ArrayDeque<>();
    // Helper methods already provided for tests and local inspection.
    public Optional<List<Integer>> scoresFor(String name) {
        return Optional.ofNullable(scoresByLearner.get(name));
    }

    public Set<String> learnerNames() {
        return scoresByLearner.keySet();
    }
    /**
     * Problem 11
     * Add a learner with an empty score list.
     *
     * Return:
     * - true if the learner was added
     * - false if the learner already exists
     *
     * Throw IllegalArgumentException if name is null or blank.
     */
    public boolean addLearner(String name) {

        // check if learner already exists
        if (learnerNames().contains(name)) {
            return false;
        }
        // check if learner is null or blank
        if (learnerNames() == null || learnerNames().equals("")) {
            throw new IllegalArgumentException();
        }

        // add learner
        scoresByLearner.put(name, new ArrayList<>());
        return true;
    }

    /**
     * Problem 12
     * Add a score to an existing learner.
     *
     * Return:
     * - true if the score was added
     * - false if the learner does not exist
     *
     * Valid scores are 0 through 100 inclusive.
     * Throw IllegalArgumentException for invalid scores.
     *
     * This operation should be undoable.
     */
    public boolean addScore(String name, int score) {
        // check if learner does not exist
        if (!learnerNames().contains(name)) {
            return false;
        }
        // check if invalid score
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException();
        }
        // add score to existing learner
        scoresByLearner.get(name).add(score);
        return true;
    }

    /**
     * Problem 13
     * Return the average score for one learner.
     *
     * Return Optional.empty() if:
     * - the learner does not exist, or
     * - the learner has no scores
     */
    public Optional<Double> averageFor(String name) {
        // look up this student, get optional with their grades or empty
        var gradesOptional = scoresFor(name);

        // if student doesn't exist, don't calculate average
        if (gradesOptional.isEmpty()) {
            return Optional.empty();
        }

        // student exists, get their actual grade list
        var grades = gradesOptional.get();

        // if grades don't exist, don't calculate average
        if (grades.isEmpty()) {
            return Optional.empty();
        }

        // add grade
        int count = 0;
        int sum = 0;
        for (var grade : grades) {
            sum += grade;
            count++;
        }

        // calculate average and return
        double average = (double) sum / count;
        return Optional.of(average);
    }

    /**
     * Problem 14
     * Convert a learner average into a letter band.
     *
     * A: 90+
     * B: 80-89.999...
     * C: 70-79.999...
     * D: 60-69.999...
     * F: below 60
     *
     * Return Optional.empty() when no average exists.
     */
    public Optional<String> letterBandFor(String name) {
        // get average optional
        var averageOptional = averageFor(name);

        // check if empty
        if (averageOptional.isEmpty()) {
            return Optional.empty();
        }

        // get actual average
        double average =  averageOptional.get();

        // convert to tens digit to use in switch statement
        int grade = (int) average/10;

        // calculate letter grade
        String letter = switch (grade) {
            case 10, 9 -> {
                yield "A";
            }
            case 8 -> {
                yield "B";
            }
            case 7 -> {
                yield "C";
            }
            case 6 -> {
                yield "D";
            }
            default -> {
                yield "F";
            }
        };
        return Optional.of(letter);
    }

    /**
     * Problem 15
     * Undo the most recent state-changing operation.
     *
     * Return true if something was undone.
     * Return false if there is nothing to undo.
     */
    public boolean undoLastChange() {
        // if stack is empty, return false
        if (undoStack.isEmpty()) {
            return false;
        }

        // pop most recent action
        UndoStep action = undoStack.pop();

        // execute the undo
        action.undo();
        return true;    }


}
