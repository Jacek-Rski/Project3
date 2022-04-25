//Jacek Rzepczynski
//Project 2

package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        TaskCollection collection = new TaskCollection();
        TaskView view = new TaskView(collection);
        view.run();
    }
}


class Task {
    String name;
    String description;
    int priority;

    Task(String name, String description, int priority) {
        this.setName(name);
        this.setDescription(description);
        this.setPriority(priority);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        if (priority < 0) {
            this.priority = 0;
        } else this.priority = Math.min(priority, 5);

    }
}

class TaskCollection {
    LinkedList tasks = new LinkedList();

    TaskCollection() {
    }

    Task createTask(String name, String description, int priority) {
        return new Task(name, description, priority);
    }

    public void addTask(String name, String description, int priority) {
        this.tasks.add(this.createTask(name, description, priority));
    }

    public void removeTask(int index) {
        if (this.tasks.size() > index) {
            this.tasks.remove(index);
        }

    }

    public void updateTask(int index, String name, String description, int priority) {
        this.tasks.set(index, this.createTask(name, description, priority));
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<Task> getTasks(int priorty) {
        List<Task> matchedTasks = new LinkedList();
        Iterator var3 = this.tasks.iterator();

        while (var3.hasNext()) {
            Task task = (Task) var3.next();
            if (task.getPriority() == priorty) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }
}

class TaskView {
    TaskCollection taskCollection;
    Scanner scanner;

    TaskView(TaskCollection collection) {
        this.scanner = new Scanner(System.in);
        this.taskCollection = collection;
    }

    boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NullPointerException | NumberFormatException var3) {
            return false;
        }
    }

    String prompt(String text) {
        System.out.println(text);
        return this.scanner.nextLine();
    }

    int promptInt(String text) {
        String response = this.prompt(text);

        for (boolean isInt = this.isInteger(response); !isInt; isInt = this.isInteger(response)) {
            response = this.prompt(text);
        }

        return Integer.parseInt(response);
    }

    void add() {
        String name = this.prompt("Enter the new task's name.");
        String description = this.prompt("Enter the new task's description.");
        int priority = this.promptInt("Enter the new task's priority");
        this.taskCollection.addTask(name, description, priority);
    }

    void remove() {
        int index = this.promptInt("Enter the index of the task to remove.");
        this.taskCollection.removeTask(index);
    }

    void update() {
        int index = this.promptInt("Enter the index of the task to update.");
        String newName = this.prompt("Enter the new name.");
        String newDescription = this.prompt("Enter the new Description");
        int newPriority = this.promptInt("Enter the new priority");
        this.taskCollection.updateTask(index, newName, newDescription, newPriority);
    }

    void displayTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            System.out.println("Task index: " + i + ", Name: " + task.getName() + ", Description: " + task.getDescription() + ", Priority: " + task.getPriority());
        }

    }

    void list() {
        this.displayTasks(this.taskCollection.getTasks());
    }

    void listByPriority() {
        int priority = this.promptInt("Enter a priority");
        this.displayTasks(this.taskCollection.getTasks(priority));
    }

    void menu() {
        boolean endLoop = false;

        while (!endLoop) {
            System.out.println("Menu");
            System.out.println("1) Add a task.");
            System.out.println("2) Remove a task.");
            System.out.println("3) Update a task.");
            System.out.println("4) List all tasks.");
            System.out.println("5) List all tasks of a certain priority.");
            System.out.println("0) Exit");
            int userInput = this.promptInt("Choose an option.");
            switch (userInput) {
                case 0:
                    endLoop = true;
                    break;
                case 1:
                    this.add();
                    break;
                case 2:
                    this.remove();
                    break;
                case 3:
                    this.update();
                    break;
                case 4:
                    this.list();
                    break;
                case 5:
                    this.listByPriority();
            }
        }

    }

    public void run() {
        this.menu();
    }
}