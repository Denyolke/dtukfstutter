package com.example.stutter.data;

import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;

import java.util.*;

public class MockRepository {

    public static List<Topic> getTopics() {
        List<Topic> t = new ArrayList<>();
        t.add(new Topic("1", "Data Structures", "Arrays, Lists, Stacks & Queues", "📚", false, 3, 5));
        t.add(new Topic("2", "Algorithms", "Sorting & Searching Basics", "🔍", false, 0, 6));
        t.add(new Topic("3", "Object-Oriented Programming", "Classes, Objects & Inheritance", "🎯", false, 0, 4));
        t.add(new Topic("4", "Databases", "SQL & Database Design", "🗄️", false, 0, 5));
        t.add(new Topic("5", "Web Development", "HTML, CSS & JavaScript", "🌐", false, 0, 7));
        return t;
    }

    public static Map<String, List<Question>> getQuestionsBank() {
        Map<String, List<Question>> map = new HashMap<>();

        // Data Structures (Topic 1)
        map.put("1", Arrays.asList(
                new Question(
                        "What data structure uses LIFO (Last In, First Out) principle?",
                        Arrays.asList("Queue", "Stack", "Array", "Linked List"),
                        1,
                        "A Stack follows LIFO: the last element added is the first one to be removed. Think of a stack of plates - you take from the top."),
                new Question(
                        "What is the time complexity of accessing an element in an array by index?",
                        Arrays.asList("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2,
                        "Array index access is O(1) because arrays store elements in contiguous memory locations, allowing direct access."),
                new Question(
                        "Which data structure uses FIFO (First In, First Out) principle?",
                        Arrays.asList("Stack", "Queue", "Deque", "Priority Queue"),
                        1,
                        "A Queue follows FIFO: the first element added is the first one removed. Like a line at a store.")
        ));

        // Algorithms (Topic 2)
        map.put("2", Arrays.asList(
                new Question(
                        "What is the time complexity of Binary Search?",
                        Arrays.asList("O(n)", "O(log n)", "O(n log n)", "O(1)"),
                        1,
                        "Binary search halves the search space with each comparison, resulting in O(log n) time complexity."),
                new Question(
                        "Which sorting algorithm has the best average-case time complexity?",
                        Arrays.asList("Bubble Sort O(n²)", "Quick Sort O(n log n)", "Insertion Sort O(n²)", "Selection Sort O(n²)"),
                        1,
                        "Quick Sort has an average time complexity of O(n log n), making it efficient for most practical scenarios.")
        ));

        // OOP (Topic 3)
        map.put("3", Arrays.asList(
                new Question(
                        "What is encapsulation in OOP?",
                        Arrays.asList(
                                "Bundling data and methods together while hiding internal details",
                                "Creating objects from classes",
                                "Inheriting properties from parent classes",
                                "Polymorphic behavior"
                        ),
                        0,
                        "Encapsulation is the practice of bundling data (variables) and methods together while hiding the internal implementation details from the outside world."),
                new Question(
                        "Which keyword is used to inherit from a class in Java?",
                        Arrays.asList("implements", "extends", "super", "inherit"),
                        1,
                        "The 'extends' keyword is used in Java to inherit from a class. For interfaces, you use 'implements'.")
        ));

        // Databases (Topic 4)
        map.put("4", Arrays.asList(
                new Question(
                        "What does SQL stand for?",
                        Arrays.asList("Structured Query Language", "Simple Query Language", "Standard Query Logic", "System Query Language"),
                        0,
                        "SQL stands for Structured Query Language, which is used to manage and query relational databases."),
                new Question(
                        "Which SQL command is used to retrieve data from a database?",
                        Arrays.asList("INSERT", "UPDATE", "SELECT", "DELETE"),
                        2,
                        "The SELECT command is used to retrieve or query data from one or more tables in a database.")
        ));

        // Web Development (Topic 5)
        map.put("5", Arrays.asList(
                new Question(
                        "What does CSS stand for?",
                        Arrays.asList("Computer Style Sheet", "Cascading Style Sheet", "Creative Style Sheet", "Core Style System"),
                        1,
                        "CSS stands for Cascading Style Sheets. It's used to style and layout web pages — controlling colors, fonts, spacing, etc."),
                new Question(
                        "Which HTML tag is used to define the main heading of a page?",
                        Arrays.asList("<h2>", "<h1>", "<header>", "<heading>"),
                        1,
                        "The <h1> tag defines the most important (main) heading on a page. There should typically be only one <h1> per page.")
        ));

        return map;
    }
}
