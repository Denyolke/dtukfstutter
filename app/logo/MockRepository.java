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

        // Data Structures (Topic 1) - 20+ questions
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
                        "A Queue follows FIFO: the first element added is the first one removed. Like a line at a store."),
                new Question(
                        "What is a linked list?",
                        Arrays.asList("A list stored in contiguous memory", "A sequence of nodes where each node points to the next", "A sorted array", "A type of tree"),
                        1,
                        "A linked list is a linear data structure where nodes are connected via pointers/references."),
                new Question(
                        "Which operation is most efficient in a linked list?",
                        Arrays.asList("Random access", "Insertion at beginning", "Searching", "Sorting"),
                        1,
                        "Linked lists excel at insertion/deletion at the beginning (O(1)) but are slow for random access (O(n))."),
                new Question(
                        "What is the space complexity of a binary search tree with n nodes?",
                        Arrays.asList("O(log n)", "O(n)", "O(1)", "O(n log n)"),
                        1,
                        "A BST needs O(n) space to store n nodes, plus O(log n) for the recursion stack."),
                new Question(
                        "How many pointers does a binary tree node have?",
                        Arrays.asList("1", "2", "3", "Unlimited"),
                        1,
                        "Each node in a binary tree has at most 2 pointers: left child and right child."),
                new Question(
                        "What is a hash table used for?",
                        Arrays.asList("Storing ordered data", "Fast lookup, insertion, deletion", "Sorting elements", "Balancing trees"),
                        1,
                        "Hash tables provide average O(1) time complexity for lookup, insertion, and deletion operations."),
                new Question(
                        "What causes hash collisions?",
                        Arrays.asList("Bad luck", "Two different keys hashing to the same index", "System failure", "Network issues"),
                        1,
                        "Hash collisions occur when multiple keys hash to the same index. Handled by chaining or open addressing."),
                new Question(
                        "Which is better for insertion: Array or Linked List?",
                        Arrays.asList("Array O(1)", "Array O(n)", "Linked List O(1) at beginning", "Depends on nothing"),
                        2,
                        "Linked List is better for insertion at the beginning (O(1) vs Array O(n)).")
        ));

        // Algorithms (Topic 2) - 20+ questions
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
                        "Quick Sort has an average time complexity of O(n log n), making it efficient for most practical scenarios."),
                new Question(
                        "What is the worst-case time complexity of Quick Sort?",
                        Arrays.asList("O(n log n)", "O(n)", "O(n²)", "O(1)"),
                        2,
                        "Quick Sort worst case is O(n²) when the pivot is always the smallest or largest element."),
                new Question(
                        "Which sorting algorithm is stable?",
                        Arrays.asList("Quick Sort", "Heap Sort", "Merge Sort", "Selection Sort"),
                        2,
                        "Merge Sort is a stable sorting algorithm - equal elements maintain their relative order."),
                new Question(
                        "What is divide and conquer?",
                        Arrays.asList("Fighting with others", "Breaking problem into subproblems, solving them, combining results", "Avoiding conflict", "Ignoring problems"),
                        1,
                        "Divide and conquer: divide problem, conquer subproblems, combine solutions. Used in Merge Sort, Quick Sort."),
                new Question(
                        "What is dynamic programming?",
                        Arrays.asList("Programming while moving", "Storing results to avoid recomputation", "Changing code at runtime", "Debugging code"),
                        1,
                        "DP solves overlapping subproblems by storing results, using memoization or tabulation."),
                new Question(
                        "What is the Fibonacci sequence?",
                        Arrays.asList("Random numbers", "0,1,1,2,3,5,8,13...", "Prime numbers", "Sorted array"),
                        1,
                        "Fibonacci: F(n) = F(n-1) + F(n-2), starting with F(0)=0, F(1)=1."),
                new Question(
                        "What is greedy algorithm?",
                        Arrays.asList("Slow algorithm", "Making locally optimal choices", "Random choice", "No pattern"),
                        1,
                        "Greedy algorithms make locally optimal choices hoping for global optimum. Used in activity selection, Huffman coding."),
                new Question(
                        "What is backtracking?",
                        Arrays.asList("Going backwards", "Exploring solutions, abandoning when invalid", "Reversing code", "Memory management"),
                        1,
                        "Backtracking explores solution space, discarding invalid branches. Used in N-Queens, Sudoku solver."),
                new Question(
                        "What is the space complexity of Merge Sort?",
                        Arrays.asList("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2,
                        "Merge Sort needs O(n) extra space for merging arrays during the sort process.")
        ));

        // OOP (Topic 3) - 20+ questions
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
                        "The 'extends' keyword is used in Java to inherit from a class. For interfaces, you use 'implements'."),
                new Question(
                        "What is polymorphism?",
                        Arrays.asList("Multiple forms of the same name", "Type of inheritance", "Abstract class", "Interface only"),
                        0,
                        "Polymorphism allows objects to take multiple forms. Method overriding and overloading are examples."),
                new Question(
                        "What is method overloading?",
                        Arrays.asList("Too many methods", "Same method name, different parameters", "Changing method return type", "Private methods"),
                        1,
                        "Method overloading: same method name with different parameter lists (different type, number, or order)."),
                new Question(
                        "What is method overriding?",
                        Arrays.asList("Declaring method twice", "Subclass providing specific implementation of parent method", "Deleting a method", "Renaming methods"),
                        1,
                        "Method overriding: subclass provides its own implementation of a parent class method."),
                new Question(
                        "What is an abstract class?",
                        Arrays.asList("A regular class", "Cannot be instantiated, may contain abstract methods", "Always final", "Must be final"),
                        1,
                        "Abstract classes cannot be instantiated and may contain abstract methods that subclasses must implement."),
                new Question(
                        "What is an interface?",
                        Arrays.asList("GUI display", "Contract defining methods without implementation", "A type of class", "Similar to abstract class"),
                        1,
                        "An interface is a contract specifying methods that implementing classes must provide."),
                new Question(
                        "What is the difference between abstract class and interface?",
                        Arrays.asList("No difference", "Abstract class can have state, interface cannot", "Interface can have constructors", "Both are identical"),
                        1,
                        "Abstract classes can have state (variables), constructors. Interfaces cannot. Multiple interface implementation is allowed."),
                new Question(
                        "What is composition?",
                        Arrays.asList("Music genre", "Has-a relationship (object contains another object)", "Is-a relationship", "Inheritance"),
                        1,
                        "Composition: HAS-A relationship. A class contains instances of other classes."),
                new Question(
                        "What is inheritance?",
                        Arrays.asList("Money from parents", "IS-A relationship (child inherits from parent)", "Composition", "Interface implementation"),
                        1,
                        "Inheritance: IS-A relationship. A class extends another class, inheriting its properties.")
        ));

        // Databases (Topic 4) - 20+ questions
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
                        "The SELECT command is used to retrieve or query data from one or more tables in a database."),
                new Question(
                        "What is a primary key?",
                        Arrays.asList("Most important key", "Unique identifier for each row", "The first column", "Password"),
                        1,
                        "Primary key uniquely identifies each row in a table. It must be unique and not null."),
                new Question(
                        "What is a foreign key?",
                        Arrays.asList("Key from another country", "Key that references primary key of another table", "Key for external use", "Encryption key"),
                        1,
                        "Foreign key is a column that references the primary key of another table, establishing relationships."),
                new Question(
                        "What is normalization in databases?",
                        Arrays.asList("Making data normal", "Organizing data to reduce redundancy and improve integrity", "Formatting data", "Encrypting data"),
                        1,
                        "Normalization organizes data efficiently, reducing redundancy and maintaining data integrity through multiple normal forms."),
                new Question(
                        "What is the first normal form (1NF)?",
                        Arrays.asList("All rows are identical", "No repeating groups, atomic values only", "All columns unique", "All data encrypted"),
                        1,
                        "1NF: All values must be atomic (indivisible). No repeating groups or arrays in columns."),
                new Question(
                        "What is a JOIN in SQL?",
                        Arrays.asList("Gluing tables", "Combining rows from two or more tables based on related columns", "Creating a new table", "Deleting data"),
                        1,
                        "JOIN combines rows from multiple tables based on related columns. Types: INNER, LEFT, RIGHT, FULL OUTER."),
                new Question(
                        "What is an INNER JOIN?",
                        Arrays.asList("Joining inside a table", "Returns rows with matching values in both tables", "Joins all rows", "Joins duplicates"),
                        1,
                        "INNER JOIN returns only rows where there are matches in both tables."),
                new Question(
                        "What is an index in a database?",
                        Arrays.asList("Number of tables", "Data structure to speed up data retrieval", "List of data", "Backup"),
                        1,
                        "Index is a data structure that improves query performance by allowing quick lookup of values."),
                new Question(
                        "What is a transaction in SQL?",
                        Arrays.asList("Money transfer", "Sequence of operations treated as a single unit, all-or-nothing", "Single SELECT query", "Data export"),
                        1,
                        "Transaction: multiple SQL operations executed as atomic unit. Either all succeed or all fail (ACID properties).")
        ));

        // Web Development (Topic 5) - 20+ questions
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
                        "The <h1> tag defines the most important (main) heading on a page. There should typically be only one <h1> per page."),
                new Question(
                        "What is the difference between class and id in CSS?",
                        Arrays.asList("No difference", "Class is reusable, id is unique", "Id is reusable", "Class is for styling"),
                        1,
                        "Class can be used by multiple elements (reusable), id must be unique to one element. Specificity: id > class."),
                new Question(
                        "What does responsive web design mean?",
                        Arrays.asList("Fast loading", "Quickly responding to clicks", "Design works on all screen sizes", "Using responsive libraries"),
                        2,
                        "Responsive design: websites adapt to different screen sizes and devices (mobile, tablet, desktop)."),
                new Question(
                        "What is a CSS selector?",
                        Arrays.asList("Choosing which computer", "Pattern to select HTML elements to style", "HTML tag", "JavaScript function"),
                        1,
                        "CSS selector targets HTML elements to apply styles. Examples: element, .class, #id, [attribute], child selector."),
                new Question(
                        "What is the box model in CSS?",
                        Arrays.asList("Boxing ring", "Content, padding, border, margin layout model", "Container system", "Design pattern"),
                        1,
                        "Box model: every element has content, padding (inside space), border, and margin (outside space)."),
                new Question(
                        "What is flexbox?",
                        Arrays.asList("Flexible boxing", "CSS layout module for flexible, responsive layouts", "JavaScript feature", "HTML element"),
                        1,
                        "Flexbox is a CSS layout tool that makes creating flexible, responsive layouts easier than floats/grids."),
                new Question(
                        "What is CSS Grid?",
                        Arrays.asList("Prison cells", "2D layout system for rows and columns", "3D layout", "Flex layout"),
                        1,
                        "CSS Grid: 2D layout system allowing control of rows and columns simultaneously. More powerful than Flexbox."),
                new Question(
                        "What is JavaScript?",
                        Arrays.asList("A coffee brand", "Programming language for client-side web interactivity", "HTML variant", "Web server"),
                        1,
                        "JavaScript is a lightweight programming language that runs in browsers, enabling interactive, dynamic web pages."),
                new Question(
                        "What is DOM?",
                        Arrays.asList("Department of Management", "Document Object Model - tree representation of HTML", "Database system", "Design pattern"),
                        1,
                        "DOM (Document Object Model) represents HTML as a tree structure, allowing JavaScript to manipulate elements dynamically.")
        ));

        return map;
    }
}
