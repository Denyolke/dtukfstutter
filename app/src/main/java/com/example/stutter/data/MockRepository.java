package com.example.stutter.data;

import com.example.stutter.model.Level;
import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockRepository {

    public static List<Topic> getTopics() {
        List<Topic> t = new ArrayList<>();
        t.add(new Topic("1", "Data Structures", "Arrays, Lists, Stacks & Queues", "📚", false, 0, 10));
        t.add(new Topic("2", "Algorithms", "Sorting & Searching Basics", "🔍", false, 0, 10));
        t.add(new Topic("3", "Object-Oriented Programming", "Classes, Objects & Inheritance", "🎯", false, 0, 10));
        t.add(new Topic("4", "Databases", "SQL & Database Design", "🗄️", false, 0, 10));
        t.add(new Topic("5", "Web Development", "HTML, CSS & JavaScript", "🌐", false, 0, 10));
        return t;
    }

    public static List<Level> getLevelsForTopic(String topicId, int completedLessons) {
        List<Level> levels = new ArrayList<>();

        String[] titles = {
                "Foundations",
                "Arrays",
                "Linked Lists",
                "Stacks",
                "Queues",
                "Trees",
                "Binary Search Trees",
                "Heaps",
                "Graphs",
                "Advanced Review"
        };

        String[] difficulties = {
                "Beginner", "Beginner", "Beginner", "Intermediate", "Intermediate",
                "Intermediate", "Advanced", "Advanced", "Advanced", "Expert"
        };

        int[] questionCounts = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

        for (int i = 1; i <= 10; i++) {
            boolean completed = i <= completedLessons;
            levels.add(new Level(
                    topicId + "_level_" + i,
                    topicId,
                    i,
                    titles[i - 1],
                    questionCounts[i - 1],
                    completed,
                    difficulties[i - 1]
            ));
        }

        return levels;
    }

    public static Map<String, Map<Integer, List<Question>>> getQuestionsBankByTopicAndLevel() {
        Map<String, Map<Integer, List<Question>>> bank = new HashMap<>();

        Map<Integer, List<Question>> dataStructures = new HashMap<>();

        // LEVEL 1 - FOUNDATIONS
        dataStructures.put(1, Arrays.asList(
                q("What is a data structure?",
                        a("A way of organizing data", "A programming language", "A compiler", "An operating system"),
                        0,
                        "A data structure is a way to store and organize data so it can be used efficiently."),

                q("Which of the following is a linear data structure?",
                        a("Tree", "Graph", "Stack", "Heap"),
                        2,
                        "A stack is linear because elements are arranged in sequence."),

                q("What is the first valid index of a Java array?",
                        a("1", "0", "-1", "Depends on array size"),
                        1,
                        "Java arrays are zero-indexed, so the first position is index 0."),

                q("Arrays usually store elements in:",
                        a("Random memory", "Contiguous memory", "Cloud memory", "Separate files"),
                        1,
                        "Arrays use contiguous memory locations, which allows fast index access."),

                q("Which data structure follows LIFO?",
                        a("Queue", "Stack", "Array", "Tree"),
                        1,
                        "LIFO means Last In, First Out, which is the principle of a stack."),

                q("Which data structure follows FIFO?",
                        a("Stack", "Queue", "Heap", "Graph"),
                        1,
                        "FIFO means First In, First Out, which is the principle of a queue."),

                q("Which stack operation removes the top element?",
                        a("push", "peek", "pop", "enqueue"),
                        2,
                        "pop removes the top element from the stack."),

                q("Which queue operation adds a new element?",
                        a("dequeue", "enqueue", "pop", "peek"),
                        1,
                        "enqueue inserts an element into the queue."),

                q("What is the time complexity of array access by index?",
                        a("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2,
                        "Array access by index is constant time because the memory position is known directly."),

                q("Which of these is NOT a data structure?",
                        a("Linked List", "Queue", "Binary", "Tree"),
                        2,
                        "Linked list, queue and tree are data structures. 'Binary' by itself is not one.")
        ));

        // LEVEL 2 - ARRAYS
        dataStructures.put(2, Arrays.asList(
                q("What is the main advantage of an array?",
                        a("Dynamic size", "Fast indexed access", "No memory usage", "Always sorted"),
                        1,
                        "Arrays are excellent for fast direct access using an index."),

                q("What is the worst-case time complexity of inserting into the middle of an array?",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2,
                        "Elements may need to be shifted, so insertion in the middle is O(n)."),

                q("Deleting an element from the middle of an array is usually:",
                        a("O(1)", "O(n)", "O(log n)", "Impossible"),
                        1,
                        "Removing from the middle often requires shifting elements left."),

                q("What happens if you access an invalid array index in Java?",
                        a("Returns null", "Returns 0", "ArrayIndexOutOfBoundsException", "Nothing"),
                        2,
                        "Java throws ArrayIndexOutOfBoundsException for invalid indexes."),

                q("What does traversing an array mean?",
                        a("Sorting it", "Checking one element only", "Visiting each element", "Deleting it"),
                        2,
                        "Traversal means processing elements one by one."),

                q("Which of these arrays stores rows and columns?",
                        a("1D array", "2D array", "Linked list", "Stack"),
                        1,
                        "A 2D array is used for row-column style data."),

                q("Binary search on an array requires the array to be:",
                        a("Reversed", "Dynamic", "Sorted", "2D"),
                        2,
                        "Binary search only works correctly on sorted data."),

                q("What is a disadvantage of arrays?",
                        a("Fast access", "Fixed size", "Simple traversal", "Contiguous storage"),
                        1,
                        "A standard array has fixed size after creation."),

                q("Which structure is best for constant-time indexed access?",
                        a("Array", "Linked List", "Queue", "Tree"),
                        0,
                        "Arrays are best for O(1) index-based access."),

                q("If an array has length 10, what is the last valid index?",
                        a("10", "9", "8", "11"),
                        1,
                        "Indexing starts at 0, so the last valid index is length - 1.")
        ));

        // LEVEL 3 - LINKED LISTS
        dataStructures.put(3, Arrays.asList(
                q("A linked list is made of:",
                        a("Indexes", "Nodes", "Matrices", "Stacks"),
                        1,
                        "A linked list is composed of nodes connected by references."),

                q("Each node in a singly linked list usually contains:",
                        a("Only data", "Data and next reference", "Two arrays", "A hash function"),
                        1,
                        "A singly linked list node stores data and a reference to the next node."),

                q("What is the first node of a linked list called?",
                        a("Root", "Head", "Top", "Base"),
                        1,
                        "The first node is commonly called the head."),

                q("Linked lists are especially good at:",
                        a("Random access", "Insertion at beginning", "Binary search", "Indexing"),
                        1,
                        "Insertion at the beginning of a linked list is O(1)."),

                q("Accessing the 8th element in a linked list is usually:",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2,
                        "You must traverse from the head, so access is linear."),

                q("A doubly linked list node has:",
                        a("One reference", "Two references", "No data", "An index only"),
                        1,
                        "Doubly linked lists usually store previous and next references."),

                q("Which is easier in a doubly linked list than singly linked list?",
                        a("Backward traversal", "Array indexing", "Sorting", "Compilation"),
                        0,
                        "Doubly linked lists allow moving in both directions."),

                q("What is a major disadvantage of linked lists?",
                        a("Fast insertion", "Extra memory for references", "No dynamic size", "No traversal"),
                        1,
                        "References require additional memory per node."),

                q("Which structure does NOT require contiguous memory?",
                        a("Array", "Linked List", "Static array", "Matrix"),
                        1,
                        "Linked list nodes can exist in different memory locations."),

                q("Deleting the head of a linked list is typically:",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        0,
                        "Removing the head only requires moving the head reference.")
        ));

        // LEVEL 4 - STACKS
        dataStructures.put(4, Arrays.asList(
                q("Which operation adds an item to a stack?",
                        a("enqueue", "push", "insertAfter", "poll"),
                        1,
                        "push adds an element to the top of the stack."),

                q("Which operation returns the top item without removing it?",
                        a("peek", "pop", "push", "dequeue"),
                        0,
                        "peek reads the top element but keeps it in the stack."),

                q("What happens when you pop from an empty stack?",
                        a("Normal behavior", "Underflow", "Overflow", "Resize"),
                        1,
                        "Trying to remove from an empty stack is called underflow."),

                q("Function calls in programming are commonly managed using:",
                        a("Queue", "Tree", "Stack", "Graph"),
                        2,
                        "The call stack tracks function calls and returns."),

                q("Which expression style is often evaluated using a stack?",
                        a("Postfix", "CSV", "Binary image", "HTML table"),
                        0,
                        "Stacks are commonly used to evaluate postfix expressions."),

                q("Undo functionality in text editors is commonly implemented with a:",
                        a("Graph", "Heap", "Stack", "Matrix"),
                        2,
                        "The most recent action must be undone first, which fits LIFO."),

                q("What is stack overflow?",
                        a("Too many queue items", "Too many recursive or stack allocations", "Deleting everything", "A sorting algorithm"),
                        1,
                        "Stack overflow occurs when the stack exceeds its capacity."),

                q("Which item is removed first from a stack with A, then B, then C pushed?",
                        a("A", "B", "C", "None"),
                        2,
                        "The last item pushed, C, is the first popped."),

                q("Balanced parentheses checking often uses a:",
                        a("Stack", "Queue", "Array only", "Heap"),
                        0,
                        "Stacks help match opening and closing symbols in order."),

                q("Which of the following is NOT a stack use case?",
                        a("Undo", "Backtracking", "Breadth-first search", "Function call management"),
                        2,
                        "BFS uses a queue, not a stack.")
        ));

        // LEVEL 5 - QUEUES
        dataStructures.put(5, Arrays.asList(
                q("Which operation removes an item from a queue?",
                        a("push", "dequeue", "peek", "append"),
                        1,
                        "dequeue removes the front item from the queue."),

                q("Which item leaves first in a queue?",
                        a("Last inserted", "First inserted", "Largest", "Random"),
                        1,
                        "Queues follow FIFO order."),

                q("Which real-life example is most like a queue?",
                        a("Plate stack", "Browser history", "People waiting in line", "Recursive calls"),
                        2,
                        "The first person in line is served first, like FIFO."),

                q("Which algorithm commonly uses a queue?",
                        a("DFS", "BFS", "Quick Sort", "Binary Search"),
                        1,
                        "Breadth-first search explores nodes level by level using a queue."),

                q("A circular queue is useful because it:",
                        a("Prevents all errors", "Reuses empty spaces", "Sorts automatically", "Uses recursion"),
                        1,
                        "Circular queues reuse freed positions efficiently."),

                q("Which queue type removes highest-priority items first?",
                        a("Simple queue", "Circular queue", "Priority queue", "Deque"),
                        2,
                        "Priority queues order removals by priority, not arrival time."),

                q("Which operation reads the front without removing it?",
                        a("peek/front", "dequeue", "push", "rotate"),
                        0,
                        "peek or front returns the next value to be removed."),

                q("Printing jobs are often modeled using a:",
                        a("Stack", "Queue", "Tree", "Graph"),
                        1,
                        "Jobs are usually processed in arrival order."),

                q("What is queue underflow?",
                        a("Queue too large", "Removing from empty queue", "Adding to full dynamic queue", "Sorting backwards"),
                        1,
                        "Underflow means the structure is empty when removal is attempted."),

                q("Which structure supports insertion and deletion from both ends?",
                        a("Deque", "Stack", "Heap", "Tree"),
                        0,
                        "Deque means double-ended queue.")
        ));

        // LEVEL 6 - TREES
        dataStructures.put(6, Arrays.asList(
                q("What is the top node of a tree called?",
                        a("Head", "Root", "Leaf", "Tail"),
                        1,
                        "The root is the top-most node of a tree."),

                q("A node with no children is called:",
                        a("Root", "Internal node", "Leaf", "Edge"),
                        2,
                        "A leaf node has no child nodes."),

                q("A tree with at most two children per node is a:",
                        a("Graph", "Binary tree", "Heap only", "Queue tree"),
                        1,
                        "A binary tree allows up to two children per node."),

                q("The number of edges from root to a node is often called:",
                        a("Depth", "Width", "Weight", "Balance"),
                        0,
                        "Depth measures distance from the root."),

                q("The longest path from root to leaf helps define:",
                        a("Density", "Height", "Priority", "Capacity"),
                        1,
                        "Height is based on the longest root-to-leaf path."),

                q("Trees are useful for representing:",
                        a("Hierarchical data", "Only arrays", "Only random numbers", "Only queues"),
                        0,
                        "Trees naturally represent hierarchical relationships."),

                q("Which is NOT a tree term?",
                        a("Root", "Leaf", "Vertex", "Child"),
                        2,
                        "Vertex is more commonly used in graph terminology."),

                q("In a binary tree, a node can have at most:",
                        a("1 child", "2 children", "3 children", "Unlimited children"),
                        1,
                        "Binary means at most two children."),

                q("Which traversal visits root, then left subtree, then right subtree?",
                        a("Inorder", "Postorder", "Preorder", "Level order"),
                        2,
                        "Preorder is Root → Left → Right."),

                q("Which traversal visits left subtree, root, then right subtree?",
                        a("Inorder", "Preorder", "Postorder", "Reverse order"),
                        0,
                        "Inorder is Left → Root → Right.")
        ));

        // LEVEL 7 - BST
        dataStructures.put(7, Arrays.asList(
                q("In a Binary Search Tree, left child values are usually:",
                        a("Greater than parent", "Less than parent", "Equal only", "Random"),
                        1,
                        "BST rule: left subtree values are less than the node."),

                q("In a Binary Search Tree, right child values are usually:",
                        a("Less than parent", "Greater than parent", "Always equal", "Negative"),
                        1,
                        "BST rule: right subtree values are greater than the node."),

                q("Which traversal of a BST gives sorted output?",
                        a("Preorder", "Postorder", "Inorder", "Level order"),
                        2,
                        "Inorder traversal of a BST produces values in sorted order."),

                q("Average search time in a balanced BST is:",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        1,
                        "Balanced BST operations are typically logarithmic."),

                q("Worst-case search time in a skewed BST is:",
                        a("O(1)", "O(log n)", "O(n)", "O(n log n)"),
                        2,
                        "A skewed BST behaves like a linked list."),

                q("What makes a BST skewed?",
                        a("Values inserted in sorted order", "Random insertions only", "Too many leaves", "No root"),
                        0,
                        "Sorted insertions can make the BST unbalanced and skewed."),

                q("Which is a major advantage of BST over unsorted array?",
                        a("Sorted traversal", "No memory use", "Always O(1)", "No insertion needed"),
                        0,
                        "BST supports ordered traversal naturally."),

                q("A balanced BST aims to keep height:",
                        a("Very large", "Near logarithmic", "Equal to number of nodes", "Always 1"),
                        1,
                        "Balanced trees keep height relatively small."),

                q("Searching in BST compares target with:",
                        a("Every node randomly", "Current node value", "Array index", "Queue front"),
                        1,
                        "At each step, search compares with the current node to go left or right."),

                q("Which structure property makes BST efficient?",
                        a("Hierarchical ordering", "FIFO behavior", "LIFO behavior", "Circular memory"),
                        0,
                        "BST efficiency comes from ordered hierarchical structure.")
        ));

        // LEVEL 8 - HEAPS
        dataStructures.put(8, Arrays.asList(
                q("A max heap stores the largest value at the:",
                        a("Last leaf", "Root", "Middle", "Random node"),
                        1,
                        "In a max heap, the root contains the largest element."),

                q("A min heap stores the smallest value at the:",
                        a("Root", "Last index", "Lowest leaf", "Right child"),
                        0,
                        "In a min heap, the root contains the smallest element."),

                q("Heaps are commonly used to implement:",
                        a("Linked list", "Priority queue", "Hash map", "Matrix"),
                        1,
                        "Priority queues are efficiently implemented with heaps."),

                q("Is a heap always fully sorted?",
                        a("Yes", "No", "Only min heap", "Only max heap"),
                        1,
                        "A heap only guarantees parent-child ordering, not full sorting."),

                q("Which property is true in a max heap?",
                        a("Parent <= children", "Parent >= children", "Left child always bigger than right", "All leaves sorted"),
                        1,
                        "Each parent is greater than or equal to its children."),

                q("Heap insertion usually involves:",
                        a("Bubble up / sift up", "Inorder traversal", "Hashing", "Dequeuing"),
                        0,
                        "Inserted elements may move upward to restore heap order."),

                q("Removing the root from a heap usually requires:",
                        a("Sift down", "Binary search", "DFS", "Array reverse"),
                        0,
                        "After root removal, the replacement element moves downward if needed."),

                q("A heap is commonly represented using:",
                        a("Array", "Only linked list", "Queue nodes only", "Graph edges"),
                        0,
                        "Binary heaps are commonly stored in arrays."),

                q("What is the main use of a heap?",
                        a("Fast direct indexing", "Fast access to highest/lowest priority", "String matching", "File storage"),
                        1,
                        "Heaps are great when you repeatedly need the max or min."),

                q("Which of these is NOT a heap type?",
                        a("Min heap", "Max heap", "FIFO heap", "Binary heap"),
                        2,
                        "FIFO is queue behavior, not a heap type.")
        ));

        // LEVEL 9 - GRAPHS
        dataStructures.put(9, Arrays.asList(
                q("A graph is made of vertices and:",
                        a("Roots", "Edges", "Heads", "Indexes"),
                        1,
                        "Graphs are formed by vertices (nodes) and edges (connections)."),

                q("Another common word for a vertex is:",
                        a("Node", "Leaf", "Stack", "Pointer"),
                        0,
                        "Vertex and node are often used interchangeably."),

                q("An edge in a graph represents:",
                        a("A variable", "A connection", "A loop only", "An array"),
                        1,
                        "Edges connect pairs of vertices."),

                q("Which graph stores connections in a table-like 2D form?",
                        a("Adjacency matrix", "Adjacency list", "Queue graph", "Heap map"),
                        0,
                        "Adjacency matrix uses rows and columns to store edges."),

                q("Which graph representation is usually more memory-efficient for sparse graphs?",
                        a("Adjacency matrix", "Adjacency list", "2D heap", "Binary tree"),
                        1,
                        "Adjacency list is efficient when there are relatively few edges."),

                q("A directed graph has edges with:",
                        a("No meaning", "Direction", "Only two weights", "Always symmetry"),
                        1,
                        "Directed edges go from one vertex to another in a specific direction."),

                q("An undirected graph edge means:",
                        a("No connection", "Two-way connection", "Priority only", "Tree child"),
                        1,
                        "Undirected edges represent mutual connections."),

                q("Which traversal method uses a queue?",
                        a("DFS", "BFS", "Heapify", "Inorder"),
                        1,
                        "BFS uses a queue to explore breadth-wise."),

                q("Which traversal method commonly uses recursion or a stack?",
                        a("BFS", "DFS", "Binary search", "Enqueue"),
                        1,
                        "DFS is commonly implemented with recursion or an explicit stack."),

                q("Social networks are often modeled as:",
                        a("Stacks", "Graphs", "Arrays only", "Heaps"),
                        1,
                        "Users can be represented as nodes and their relationships as edges.")
        ));

        // LEVEL 10 - ADVANCED REVIEW
        dataStructures.put(10, Arrays.asList(
                q("Which structure is best for LIFO processing?",
                        a("Queue", "Stack", "Heap", "Graph"),
                        1,
                        "Stack is specifically designed for LIFO behavior."),

                q("Which structure is best for FIFO processing?",
                        a("Tree", "Queue", "Heap", "Stack"),
                        1,
                        "Queue is specifically designed for FIFO behavior."),

                q("Which structure gives O(1) indexed access?",
                        a("Linked List", "Array", "Tree", "Graph"),
                        1,
                        "Arrays allow direct indexed access."),

                q("Which structure is usually better for frequent insertions at the beginning?",
                        a("Array", "Linked List", "Heap", "Matrix"),
                        1,
                        "Linked lists are better for frequent front insertions."),

                q("Which traversal of BST gives sorted values?",
                        a("Preorder", "Postorder", "Inorder", "Level order"),
                        2,
                        "Inorder traversal gives sorted values in a BST."),

                q("Which structure is best for repeatedly taking the highest priority element?",
                        a("Heap / Priority Queue", "Array", "Linked List", "Stack"),
                        0,
                        "Heaps are ideal for priority-based removal."),

                q("Which structure best represents hierarchical data?",
                        a("Queue", "Tree", "Stack", "Array"),
                        1,
                        "Trees naturally model hierarchy."),

                q("Which structure best represents network connections?",
                        a("Array", "Graph", "Stack", "Deque"),
                        1,
                        "Graphs model entities and their connections."),

                q("What is the main goal of choosing the right data structure?",
                        a("To make code longer", "To improve efficiency and clarity", "To avoid variables", "To remove logic"),
                        1,
                        "The right data structure improves runtime, memory use, and maintainability."),

                q("A good programmer chooses data structures based on:",
                        a("Color of UI", "Problem requirements", "Random choice", "File size only"),
                        1,
                        "Data structure choice depends on required operations and constraints.")
        ));

        bank.put("1", dataStructures);

        // Placeholder topics
        bank.put("2", new HashMap<>());
        bank.put("3", new HashMap<>());
        bank.put("4", new HashMap<>());
        bank.put("5", new HashMap<>());

        return bank;
    }

    private static Question q(String question, List<String> options, int correctAnswer, String explanation) {
        return new Question(question, options, correctAnswer, explanation);
    }

    private static List<String> a(String o1, String o2, String o3, String o4) {
        return Arrays.asList(o1, o2, o3, o4);
    }
}