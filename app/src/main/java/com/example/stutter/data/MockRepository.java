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

        String[] titles;
        switch (topicId) {
            case "2":
                titles = new String[]{
                        "Foundations", "Big O Notation", "Linear Search", "Binary Search",
                        "Bubble & Selection Sort", "Insertion Sort", "Merge Sort",
                        "Quick Sort", "Recursion", "Advanced Review"
                };
                break;
            case "3":
                titles = new String[]{
                        "Foundations", "Classes & Objects", "Constructors", "Encapsulation",
                        "Inheritance", "Polymorphism", "Abstraction",
                        "Interfaces", "Design Patterns", "Advanced Review"
                };
                break;
            default:
                titles = new String[]{
                        "Foundations", "Arrays", "Linked Lists", "Stacks",
                        "Queues", "Trees", "Binary Search Trees",
                        "Heaps", "Graphs", "Advanced Review"
                };
                break;
        }

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

        // ══════════════════════════════════════════════════════════════════════
        // TOPIC 1 — DATA STRUCTURES
        // ══════════════════════════════════════════════════════════════════════
        Map<Integer, List<Question>> dataStructures = new HashMap<>();

        dataStructures.put(1, Arrays.asList(
                q("What is a data structure?",
                        a("A way of organizing data", "A programming language", "A compiler", "An operating system"),
                        0, "A data structure is a way to store and organize data so it can be used efficiently."),
                q("Which of the following is a linear data structure?",
                        a("Tree", "Graph", "Stack", "Heap"),
                        2, "A stack is linear because elements are arranged in sequence."),
                q("What is the first valid index of a Java array?",
                        a("1", "0", "-1", "Depends on array size"),
                        1, "Java arrays are zero-indexed, so the first position is index 0."),
                q("Arrays usually store elements in:",
                        a("Random memory", "Contiguous memory", "Cloud memory", "Separate files"),
                        1, "Arrays use contiguous memory locations, which allows fast index access."),
                q("Which data structure follows LIFO?",
                        a("Queue", "Stack", "Array", "Tree"),
                        1, "LIFO means Last In, First Out, which is the principle of a stack."),
                q("Which data structure follows FIFO?",
                        a("Stack", "Queue", "Heap", "Graph"),
                        1, "FIFO means First In, First Out, which is the principle of a queue."),
                q("Which stack operation removes the top element?",
                        a("push", "peek", "pop", "enqueue"),
                        2, "pop removes the top element from the stack."),
                q("Which queue operation adds a new element?",
                        a("dequeue", "enqueue", "pop", "peek"),
                        1, "enqueue inserts an element into the queue."),
                q("What is the time complexity of array access by index?",
                        a("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2, "Array access by index is constant time because the memory position is known directly."),
                q("Which of these is NOT a data structure?",
                        a("Linked List", "Queue", "Binary", "Tree"),
                        2, "Linked list, queue and tree are data structures. 'Binary' by itself is not one.")
        ));

        dataStructures.put(2, Arrays.asList(
                q("What is the main advantage of an array?",
                        a("Dynamic size", "Fast indexed access", "No memory usage", "Always sorted"),
                        1, "Arrays are excellent for fast direct access using an index."),
                q("What is the worst-case time complexity of inserting into the middle of an array?",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2, "Elements may need to be shifted, so insertion in the middle is O(n)."),
                q("Deleting an element from the middle of an array is usually:",
                        a("O(1)", "O(n)", "O(log n)", "Impossible"),
                        1, "Removing from the middle often requires shifting elements left."),
                q("What happens if you access an invalid array index in Java?",
                        a("Returns null", "Returns 0", "ArrayIndexOutOfBoundsException", "Nothing"),
                        2, "Java throws ArrayIndexOutOfBoundsException for invalid indexes."),
                q("What does traversing an array mean?",
                        a("Sorting it", "Checking one element only", "Visiting each element", "Deleting it"),
                        2, "Traversal means processing elements one by one."),
                q("Which of these arrays stores rows and columns?",
                        a("1D array", "2D array", "Linked list", "Stack"),
                        1, "A 2D array is used for row-column style data."),
                q("Binary search on an array requires the array to be:",
                        a("Reversed", "Dynamic", "Sorted", "2D"),
                        2, "Binary search only works correctly on sorted data."),
                q("What is a disadvantage of arrays?",
                        a("Fast access", "Fixed size", "Simple traversal", "Contiguous storage"),
                        1, "A standard array has fixed size after creation."),
                q("Which structure is best for constant-time indexed access?",
                        a("Array", "Linked List", "Queue", "Tree"),
                        0, "Arrays are best for O(1) index-based access."),
                q("If an array has length 10, what is the last valid index?",
                        a("10", "9", "8", "11"),
                        1, "Indexing starts at 0, so the last valid index is length - 1.")
        ));

        dataStructures.put(3, Arrays.asList(
                q("A linked list is made of:", a("Indexes", "Nodes", "Matrices", "Stacks"), 1, "A linked list is composed of nodes connected by references."),
                q("Each node in a singly linked list usually contains:", a("Only data", "Data and next reference", "Two arrays", "A hash function"), 1, "A singly linked list node stores data and a reference to the next node."),
                q("What is the first node of a linked list called?", a("Root", "Head", "Top", "Base"), 1, "The first node is commonly called the head."),
                q("Linked lists are especially good at:", a("Random access", "Insertion at beginning", "Binary search", "Indexing"), 1, "Insertion at the beginning of a linked list is O(1)."),
                q("Accessing the 8th element in a linked list is usually:", a("O(1)", "O(log n)", "O(n)", "O(n²)"), 2, "You must traverse from the head, so access is linear."),
                q("A doubly linked list node has:", a("One reference", "Two references", "No data", "An index only"), 1, "Doubly linked lists usually store previous and next references."),
                q("Which is easier in a doubly linked list than singly linked list?", a("Backward traversal", "Array indexing", "Sorting", "Compilation"), 0, "Doubly linked lists allow moving in both directions."),
                q("What is a major disadvantage of linked lists?", a("Fast insertion", "Extra memory for references", "No dynamic size", "No traversal"), 1, "References require additional memory per node."),
                q("Which structure does NOT require contiguous memory?", a("Array", "Linked List", "Static array", "Matrix"), 1, "Linked list nodes can exist in different memory locations."),
                q("Deleting the head of a linked list is typically:", a("O(1)", "O(log n)", "O(n)", "O(n²)"), 0, "Removing the head only requires moving the head reference.")
        ));

        dataStructures.put(4, Arrays.asList(
                q("Which operation adds an item to a stack?", a("enqueue", "push", "insertAfter", "poll"), 1, "push adds an element to the top of the stack."),
                q("Which operation returns the top item without removing it?", a("peek", "pop", "push", "dequeue"), 0, "peek reads the top element but keeps it in the stack."),
                q("What happens when you pop from an empty stack?", a("Normal behavior", "Underflow", "Overflow", "Resize"), 1, "Trying to remove from an empty stack is called underflow."),
                q("Function calls in programming are commonly managed using:", a("Queue", "Tree", "Stack", "Graph"), 2, "The call stack tracks function calls and returns."),
                q("Which expression style is often evaluated using a stack?", a("Postfix", "CSV", "Binary image", "HTML table"), 0, "Stacks are commonly used to evaluate postfix expressions."),
                q("Undo functionality in text editors is commonly implemented with a:", a("Graph", "Heap", "Stack", "Matrix"), 2, "The most recent action must be undone first, which fits LIFO."),
                q("What is stack overflow?", a("Too many queue items", "Too many recursive or stack allocations", "Deleting everything", "A sorting algorithm"), 1, "Stack overflow occurs when the stack exceeds its capacity."),
                q("Which item is removed first from a stack with A, then B, then C pushed?", a("A", "B", "C", "None"), 2, "The last item pushed, C, is the first popped."),
                q("Balanced parentheses checking often uses a:", a("Stack", "Queue", "Array only", "Heap"), 0, "Stacks help match opening and closing symbols in order."),
                q("Which of the following is NOT a stack use case?", a("Undo", "Backtracking", "Breadth-first search", "Function call management"), 2, "BFS uses a queue, not a stack.")
        ));

        dataStructures.put(5, Arrays.asList(
                q("Which operation removes an item from a queue?", a("push", "dequeue", "peek", "append"), 1, "dequeue removes the front item from the queue."),
                q("Which item leaves first in a queue?", a("Last inserted", "First inserted", "Largest", "Random"), 1, "Queues follow FIFO order."),
                q("Which real-life example is most like a queue?", a("Plate stack", "Browser history", "People waiting in line", "Recursive calls"), 2, "The first person in line is served first, like FIFO."),
                q("Which algorithm commonly uses a queue?", a("DFS", "BFS", "Quick Sort", "Binary Search"), 1, "Breadth-first search explores nodes level by level using a queue."),
                q("A circular queue is useful because it:", a("Prevents all errors", "Reuses empty spaces", "Sorts automatically", "Uses recursion"), 1, "Circular queues reuse freed positions efficiently."),
                q("Which queue type removes highest-priority items first?", a("Simple queue", "Circular queue", "Priority queue", "Deque"), 2, "Priority queues order removals by priority, not arrival time."),
                q("Which operation reads the front without removing it?", a("peek/front", "dequeue", "push", "rotate"), 0, "peek or front returns the next value to be removed."),
                q("Printing jobs are often modeled using a:", a("Stack", "Queue", "Tree", "Graph"), 1, "Jobs are usually processed in arrival order."),
                q("What is queue underflow?", a("Queue too large", "Removing from empty queue", "Adding to full dynamic queue", "Sorting backwards"), 1, "Underflow means the structure is empty when removal is attempted."),
                q("Which structure supports insertion and deletion from both ends?", a("Deque", "Stack", "Heap", "Tree"), 0, "Deque means double-ended queue.")
        ));

        dataStructures.put(6, Arrays.asList(
                q("What is the top node of a tree called?", a("Head", "Root", "Leaf", "Tail"), 1, "The root is the top-most node of a tree."),
                q("A node with no children is called:", a("Root", "Internal node", "Leaf", "Edge"), 2, "A leaf node has no child nodes."),
                q("A tree with at most two children per node is a:", a("Graph", "Binary tree", "Heap only", "Queue tree"), 1, "A binary tree allows up to two children per node."),
                q("The number of edges from root to a node is often called:", a("Depth", "Width", "Weight", "Balance"), 0, "Depth measures distance from the root."),
                q("The longest path from root to leaf helps define:", a("Density", "Height", "Priority", "Capacity"), 1, "Height is based on the longest root-to-leaf path."),
                q("Trees are useful for representing:", a("Hierarchical data", "Only arrays", "Only random numbers", "Only queues"), 0, "Trees naturally represent hierarchical relationships."),
                q("Which is NOT a tree term?", a("Root", "Leaf", "Vertex", "Child"), 2, "Vertex is more commonly used in graph terminology."),
                q("In a binary tree, a node can have at most:", a("1 child", "2 children", "3 children", "Unlimited children"), 1, "Binary means at most two children."),
                q("Which traversal visits root, then left subtree, then right subtree?", a("Inorder", "Postorder", "Preorder", "Level order"), 2, "Preorder is Root → Left → Right."),
                q("Which traversal visits left subtree, root, then right subtree?", a("Inorder", "Preorder", "Postorder", "Reverse order"), 0, "Inorder is Left → Root → Right.")
        ));

        dataStructures.put(7, Arrays.asList(
                q("In a BST, left child values are usually:", a("Greater than parent", "Less than parent", "Equal only", "Random"), 1, "BST rule: left subtree values are less than the node."),
                q("In a BST, right child values are usually:", a("Less than parent", "Greater than parent", "Always equal", "Negative"), 1, "BST rule: right subtree values are greater than the node."),
                q("Which traversal of a BST gives sorted output?", a("Preorder", "Postorder", "Inorder", "Level order"), 2, "Inorder traversal of a BST produces values in sorted order."),
                q("Average search time in a balanced BST is:", a("O(1)", "O(log n)", "O(n)", "O(n²)"), 1, "Balanced BST operations are typically logarithmic."),
                q("Worst-case search time in a skewed BST is:", a("O(1)", "O(log n)", "O(n)", "O(n log n)"), 2, "A skewed BST behaves like a linked list."),
                q("What makes a BST skewed?", a("Values inserted in sorted order", "Random insertions only", "Too many leaves", "No root"), 0, "Sorted insertions can make the BST unbalanced and skewed."),
                q("Which is a major advantage of BST over unsorted array?", a("Sorted traversal", "No memory use", "Always O(1)", "No insertion needed"), 0, "BST supports ordered traversal naturally."),
                q("A balanced BST aims to keep height:", a("Very large", "Near logarithmic", "Equal to number of nodes", "Always 1"), 1, "Balanced trees keep height relatively small."),
                q("Searching in BST compares target with:", a("Every node randomly", "Current node value", "Array index", "Queue front"), 1, "At each step, search compares with the current node to go left or right."),
                q("Which structure property makes BST efficient?", a("Hierarchical ordering", "FIFO behavior", "LIFO behavior", "Circular memory"), 0, "BST efficiency comes from ordered hierarchical structure.")
        ));

        dataStructures.put(8, Arrays.asList(
                q("A max heap stores the largest value at the:", a("Last leaf", "Root", "Middle", "Random node"), 1, "In a max heap, the root contains the largest element."),
                q("A min heap stores the smallest value at the:", a("Root", "Last index", "Lowest leaf", "Right child"), 0, "In a min heap, the root contains the smallest element."),
                q("Heaps are commonly used to implement:", a("Linked list", "Priority queue", "Hash map", "Matrix"), 1, "Priority queues are efficiently implemented with heaps."),
                q("Is a heap always fully sorted?", a("Yes", "No", "Only min heap", "Only max heap"), 1, "A heap only guarantees parent-child ordering, not full sorting."),
                q("Which property is true in a max heap?", a("Parent <= children", "Parent >= children", "Left child always bigger than right", "All leaves sorted"), 1, "Each parent is greater than or equal to its children."),
                q("Heap insertion usually involves:", a("Bubble up / sift up", "Inorder traversal", "Hashing", "Dequeuing"), 0, "Inserted elements may move upward to restore heap order."),
                q("Removing the root from a heap usually requires:", a("Sift down", "Binary search", "DFS", "Array reverse"), 0, "After root removal, the replacement element moves downward if needed."),
                q("A heap is commonly represented using:", a("Array", "Only linked list", "Queue nodes only", "Graph edges"), 0, "Binary heaps are commonly stored in arrays."),
                q("What is the main use of a heap?", a("Fast direct indexing", "Fast access to highest/lowest priority", "String matching", "File storage"), 1, "Heaps are great when you repeatedly need the max or min."),
                q("Which of these is NOT a heap type?", a("Min heap", "Max heap", "FIFO heap", "Binary heap"), 2, "FIFO is queue behavior, not a heap type.")
        ));

        dataStructures.put(9, Arrays.asList(
                q("A graph is made of vertices and:", a("Roots", "Edges", "Heads", "Indexes"), 1, "Graphs are formed by vertices (nodes) and edges (connections)."),
                q("Another common word for a vertex is:", a("Node", "Leaf", "Stack", "Pointer"), 0, "Vertex and node are often used interchangeably."),
                q("An edge in a graph represents:", a("A variable", "A connection", "A loop only", "An array"), 1, "Edges connect pairs of vertices."),
                q("Which graph stores connections in a 2D table form?", a("Adjacency matrix", "Adjacency list", "Queue graph", "Heap map"), 0, "Adjacency matrix uses rows and columns to store edges."),
                q("Which is usually more memory-efficient for sparse graphs?", a("Adjacency matrix", "Adjacency list", "2D heap", "Binary tree"), 1, "Adjacency list is efficient when there are relatively few edges."),
                q("A directed graph has edges with:", a("No meaning", "Direction", "Only two weights", "Always symmetry"), 1, "Directed edges go from one vertex to another in a specific direction."),
                q("An undirected graph edge means:", a("No connection", "Two-way connection", "Priority only", "Tree child"), 1, "Undirected edges represent mutual connections."),
                q("Which traversal method uses a queue?", a("DFS", "BFS", "Heapify", "Inorder"), 1, "BFS uses a queue to explore breadth-wise."),
                q("Which traversal commonly uses recursion or a stack?", a("BFS", "DFS", "Binary search", "Enqueue"), 1, "DFS is commonly implemented with recursion or an explicit stack."),
                q("Social networks are often modeled as:", a("Stacks", "Graphs", "Arrays only", "Heaps"), 1, "Users can be represented as nodes and their relationships as edges.")
        ));

        dataStructures.put(10, Arrays.asList(
                q("Which structure is best for LIFO processing?", a("Queue", "Stack", "Heap", "Graph"), 1, "Stack is specifically designed for LIFO behavior."),
                q("Which structure is best for FIFO processing?", a("Tree", "Queue", "Heap", "Stack"), 1, "Queue is specifically designed for FIFO behavior."),
                q("Which structure gives O(1) indexed access?", a("Linked List", "Array", "Tree", "Graph"), 1, "Arrays allow direct indexed access."),
                q("Which is usually better for frequent insertions at the beginning?", a("Array", "Linked List", "Heap", "Matrix"), 1, "Linked lists are better for frequent front insertions."),
                q("Which traversal of BST gives sorted values?", a("Preorder", "Postorder", "Inorder", "Level order"), 2, "Inorder traversal gives sorted values in a BST."),
                q("Which structure is best for repeatedly taking the highest priority element?", a("Heap / Priority Queue", "Array", "Linked List", "Stack"), 0, "Heaps are ideal for priority-based removal."),
                q("Which structure best represents hierarchical data?", a("Queue", "Tree", "Stack", "Array"), 1, "Trees naturally model hierarchy."),
                q("Which structure best represents network connections?", a("Array", "Graph", "Stack", "Deque"), 1, "Graphs model entities and their connections."),
                q("What is the main goal of choosing the right data structure?", a("To make code longer", "To improve efficiency and clarity", "To avoid variables", "To remove logic"), 1, "The right data structure improves runtime, memory use, and maintainability."),
                q("A good programmer chooses data structures based on:", a("Color of UI", "Problem requirements", "Random choice", "File size only"), 1, "Data structure choice depends on required operations and constraints.")
        ));

        bank.put("1", dataStructures);

        // ══════════════════════════════════════════════════════════════════════
        // TOPIC 2 — ALGORITHMS
        // ══════════════════════════════════════════════════════════════════════
        Map<Integer, List<Question>> algorithms = new HashMap<>();

        // LEVEL 1 - FOUNDATIONS
        algorithms.put(1, Arrays.asList(
                q("What is an algorithm?",
                        a("A step-by-step procedure to solve a problem", "A programming language", "A type of variable", "A database"),
                        0, "An algorithm is a well-defined sequence of steps to solve a problem."),
                q("Which property is NOT required for a valid algorithm?",
                        a("Finiteness", "Definiteness", "Being written in code", "Effectiveness"),
                        2, "An algorithm does not need to be written in code — it can be expressed in plain language or pseudocode."),
                q("What does it mean for an algorithm to be correct?",
                        a("It runs fast", "It produces the right output for all valid inputs", "It uses little memory", "It is short"),
                        1, "Correctness means the algorithm produces the expected result for every valid input."),
                q("Which of these is an example of an algorithm?",
                        a("A variable declaration", "A recipe for cooking", "A class definition", "A loop variable"),
                        1, "A recipe is a classic analogy — it is a step-by-step process to achieve a goal."),
                q("What is pseudocode?",
                        a("A compiled language", "An informal description of an algorithm", "A type of loop", "Machine code"),
                        1, "Pseudocode is a human-readable description of logic without strict syntax rules."),
                q("An algorithm that never terminates is called:",
                        a("Efficient", "Infinite loop", "Recursive", "Sorted"),
                        1, "An algorithm must terminate — one that never ends is an infinite loop."),
                q("Which of these is NOT a common way to express an algorithm?",
                        a("Flowchart", "Pseudocode", "Java code", "A spreadsheet cell value"),
                        3, "Flowcharts, pseudocode, and actual code are all valid ways to express algorithms."),
                q("What is the purpose of analyzing an algorithm?",
                        a("To make it look nicer", "To understand its time and space requirements", "To rename variables", "To add more comments"),
                        1, "Analysis helps us understand how an algorithm behaves as input size grows."),
                q("Which factor does algorithm analysis primarily focus on?",
                        a("Number of lines of code", "How input size affects performance", "Font size in the editor", "The programmer's experience"),
                        1, "Algorithm analysis studies how time and space grow relative to the input size."),
                q("The study of algorithm efficiency is important because:",
                        a("Faster hardware solves all problems", "Poorly chosen algorithms can be unusable on large inputs", "All algorithms run equally fast", "Compilers fix inefficient code"),
                        1, "Even with fast hardware, a poor algorithm can make a problem unsolvable at scale.")
        ));

        // LEVEL 2 - BIG O NOTATION
        algorithms.put(2, Arrays.asList(
                q("What does Big O notation describe?",
                        a("The exact runtime in seconds", "The upper bound of an algorithm's growth rate", "The number of lines of code", "The memory size of a variable"),
                        1, "Big O describes how the runtime or space grows in the worst case as input size increases."),
                q("What is O(1)?",
                        a("Linear time", "Constant time", "Quadratic time", "Logarithmic time"),
                        1, "O(1) means the operation takes the same time regardless of input size."),
                q("What is O(n)?",
                        a("Constant time", "Quadratic time", "Linear time", "Logarithmic time"),
                        2, "O(n) means runtime grows directly proportional to the input size."),
                q("Which is faster for large n: O(n) or O(log n)?",
                        a("O(n)", "O(log n)", "They are equal", "Depends on hardware"),
                        1, "O(log n) grows much slower than O(n) as n increases."),
                q("What is O(n²) commonly called?",
                        a("Linear", "Logarithmic", "Quadratic", "Constant"),
                        2, "O(n²) is quadratic — common in nested loops over the same input."),
                q("A nested loop over n elements typically gives:",
                        a("O(1)", "O(n)", "O(n²)", "O(log n)"),
                        2, "Two nested loops each running n times results in n × n = O(n²)."),
                q("Which Big O is best (fastest growth)?",
                        a("O(n²)", "O(n)", "O(log n)", "O(1)"),
                        3, "O(1) is constant time — the best possible complexity."),
                q("Binary search has a time complexity of:",
                        a("O(n)", "O(n²)", "O(log n)", "O(1)"),
                        2, "Binary search halves the search space each step, giving O(log n)."),
                q("What does Big O ignore?",
                        a("The algorithm logic", "Constant factors and lower-order terms", "The input type", "The output size"),
                        1, "Big O focuses on growth rate, ignoring constants like 2n being written as O(n)."),
                q("Which notation describes the best-case complexity?",
                        a("Big O", "Big Theta", "Big Omega", "Little o"),
                        2, "Big Omega (Ω) describes the lower bound, i.e., the best-case scenario.")
        ));

        // LEVEL 3 - LINEAR SEARCH
        algorithms.put(3, Arrays.asList(
                q("What does linear search do?",
                        a("Jumps to the middle", "Checks each element one by one", "Sorts then finds", "Uses a hash"),
                        1, "Linear search scans every element from start to finish until it finds the target."),
                q("What is the best-case time complexity of linear search?",
                        a("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2, "Best case is O(1) — the target is the very first element."),
                q("What is the worst-case time complexity of linear search?",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2, "Worst case is O(n) — the target is the last element or not present."),
                q("Does linear search require a sorted array?",
                        a("Yes always", "No", "Only for integers", "Only for strings"),
                        1, "Linear search works on unsorted data — it checks every element."),
                q("Linear search is best used when:",
                        a("The array is very large", "The array is small or unsorted", "Speed is critical", "The array is always sorted"),
                        1, "Linear search is practical for small or unsorted datasets where sorting overhead is not worth it."),
                q("In the worst case, linear search makes how many comparisons for n elements?",
                        a("1", "log n", "n", "n²"),
                        2, "In the worst case every element is checked, giving n comparisons."),
                q("Linear search can be applied to:",
                        a("Only arrays", "Only sorted lists", "Any sequence of elements", "Only numbers"),
                        2, "Linear search works on any sequence — arrays, linked lists, or other collections."),
                q("What happens when linear search does not find the target?",
                        a("It crashes", "It returns the last element", "It returns -1 or null to indicate not found", "It sorts the array"),
                        2, "A common convention is to return -1 or null when the target is not found."),
                q("Compared to binary search, linear search is:",
                        a("Always faster", "Slower on large sorted data", "The same speed", "Better on sorted data"),
                        1, "On large sorted datasets binary search is much faster than linear search."),
                q("Which of these is a real-world analogy of linear search?",
                        a("Looking up a word in a dictionary", "Scanning a list of names one by one", "Using an index in a book", "Dividing a phone book in half"),
                        1, "Reading a list name by name until you find the right one is exactly linear search.")
        ));

        // LEVEL 4 - BINARY SEARCH
        algorithms.put(4, Arrays.asList(
                q("What is the key requirement for binary search?",
                        a("The array must be empty", "The array must be sorted", "The array must have even length", "The array must contain integers"),
                        1, "Binary search only works correctly on sorted data."),
                q("What does binary search compare the target to first?",
                        a("First element", "Last element", "Middle element", "Random element"),
                        2, "Binary search always compares the target to the middle element of the current range."),
                q("If the target is less than the middle element, binary search:",
                        a("Stops immediately", "Searches the right half", "Searches the left half", "Starts over"),
                        2, "If target < middle, the answer must be in the left half."),
                q("What is the time complexity of binary search?",
                        a("O(n)", "O(n²)", "O(log n)", "O(1)"),
                        2, "Binary search halves the search space each step — O(log n)."),
                q("How many comparisons does binary search need for 1024 elements in the worst case?",
                        a("1024", "512", "10", "1"),
                        2, "log₂(1024) = 10, so at most 10 comparisons are needed."),
                q("Binary search is an example of which strategy?",
                        a("Greedy", "Divide and conquer", "Dynamic programming", "Backtracking"),
                        1, "Binary search divides the problem in half at each step — classic divide and conquer."),
                q("What is the best-case time complexity of binary search?",
                        a("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2, "Best case is O(1) — the middle element is the target on the first check."),
                q("Binary search can be implemented:",
                        a("Only iteratively", "Only recursively", "Both iteratively and recursively", "Neither"),
                        2, "Binary search has both iterative and recursive implementations."),
                q("Compared to linear search on a sorted array of 1,000,000 elements, binary search makes at most:",
                        a("1,000,000 comparisons", "500,000 comparisons", "20 comparisons", "1 comparison"),
                        2, "log₂(1,000,000) ≈ 20, so binary search needs at most ~20 comparisons."),
                q("If binary search narrows down to an empty range without finding the target, it means:",
                        a("The array is unsorted", "The target is not in the array", "There is a bug", "The array is too large"),
                        1, "An empty search range means the target does not exist in the array.")
        ));

        // LEVEL 5 - BUBBLE & SELECTION SORT
        algorithms.put(5, Arrays.asList(
                q("What does bubble sort do in each pass?",
                        a("Finds the minimum", "Swaps adjacent elements if out of order", "Inserts elements in order", "Divides the array"),
                        1, "Bubble sort repeatedly compares and swaps adjacent elements until the array is sorted."),
                q("What is the worst-case time complexity of bubble sort?",
                        a("O(n)", "O(log n)", "O(n²)", "O(n log n)"),
                        2, "Bubble sort has O(n²) worst-case due to nested comparisons."),
                q("After the first complete pass of bubble sort, what is guaranteed?",
                        a("The array is fully sorted", "The smallest element is at the front", "The largest element is at the end", "Nothing"),
                        2, "The largest element 'bubbles up' to the last position after one full pass."),
                q("Selection sort works by:",
                        a("Swapping adjacent elements", "Finding the minimum and placing it at the front", "Inserting each element in order", "Splitting the array"),
                        1, "Selection sort finds the smallest unsorted element and swaps it into the correct position."),
                q("What is the time complexity of selection sort?",
                        a("O(n)", "O(n log n)", "O(n²)", "O(1)"),
                        2, "Selection sort always performs O(n²) comparisons regardless of input order."),
                q("How many swaps does selection sort make at most?",
                        a("n²", "n log n", "n-1", "1"),
                        2, "Selection sort makes at most n-1 swaps — one per pass."),
                q("Which sort is generally considered more efficient in practice?",
                        a("Bubble sort", "Selection sort", "They are equal", "Depends on nothing"),
                        1, "Selection sort makes fewer swaps than bubble sort, making it slightly more efficient in practice."),
                q("Is bubble sort stable?",
                        a("No", "Yes", "Only for numbers", "Only for strings"),
                        1, "Bubble sort is stable — equal elements retain their relative order."),
                q("What is the best-case time complexity of bubble sort with early termination?",
                        a("O(n²)", "O(n log n)", "O(n)", "O(1)"),
                        2, "With early termination (no swaps made), bubble sort achieves O(n) on an already sorted array."),
                q("Selection sort is called 'selection' because:",
                        a("It selects random elements", "It selects the minimum element in each pass", "It selects half the array", "It selects pivot elements"),
                        1, "Each pass selects the minimum from the unsorted portion and places it correctly.")
        ));

        // LEVEL 6 - INSERTION SORT
        algorithms.put(6, Arrays.asList(
                q("How does insertion sort build the sorted array?",
                        a("By dividing in half", "By inserting each element into its correct position", "By swapping adjacent pairs", "By finding maximums"),
                        1, "Insertion sort takes each element and inserts it into the correct position in the already-sorted portion."),
                q("What is the best-case time complexity of insertion sort?",
                        a("O(n²)", "O(n log n)", "O(n)", "O(1)"),
                        2, "Best case is O(n) when the array is already sorted — no shifts needed."),
                q("What is the worst-case time complexity of insertion sort?",
                        a("O(n)", "O(n log n)", "O(n²)", "O(log n)"),
                        2, "Worst case is O(n²) — occurs when the array is sorted in reverse order."),
                q("Insertion sort is efficient for:",
                        a("Very large random arrays", "Small or nearly sorted arrays", "Sorted descending arrays", "Arrays with many duplicates only"),
                        1, "Insertion sort excels on small datasets or arrays that are already mostly sorted."),
                q("Is insertion sort stable?",
                        a("No", "Yes", "Only sometimes", "Never"),
                        1, "Insertion sort is stable — equal elements preserve their original order."),
                q("Insertion sort is similar to how most people:",
                        a("Shuffle a deck of cards", "Sort playing cards in their hand", "Search for a word in a book", "Divide a pizza"),
                        1, "Sorting cards in hand — picking each card and inserting it in the right place — mirrors insertion sort."),
                q("In insertion sort, the left portion of the array is always:",
                        a("Unsorted", "Sorted", "Empty", "Reversed"),
                        1, "The left portion grows as a sorted subarray with each iteration."),
                q("How many comparisons does insertion sort make in the worst case for n elements?",
                        a("n", "n-1", "n(n-1)/2", "log n"),
                        2, "In the worst case, each element is compared with all previous elements — n(n-1)/2 comparisons."),
                q("Which sort is generally fastest on a nearly sorted array?",
                        a("Quick sort", "Merge sort", "Insertion sort", "Selection sort"),
                        2, "Insertion sort approaches O(n) on nearly sorted arrays, making it very efficient for that case."),
                q("Insertion sort is classified as:",
                        a("A divide and conquer algorithm", "An in-place comparison sort", "A non-comparison sort", "A recursive-only algorithm"),
                        1, "Insertion sort is an in-place comparison-based sorting algorithm.")
        ));

        // LEVEL 7 - MERGE SORT
        algorithms.put(7, Arrays.asList(
                q("Merge sort is based on which strategy?",
                        a("Greedy", "Dynamic programming", "Divide and conquer", "Backtracking"),
                        2, "Merge sort divides the array in half, sorts each half, then merges them."),
                q("What is the time complexity of merge sort in all cases?",
                        a("O(n²)", "O(n)", "O(n log n)", "O(log n)"),
                        2, "Merge sort is O(n log n) in best, average, and worst cases."),
                q("What is the space complexity of merge sort?",
                        a("O(1)", "O(log n)", "O(n)", "O(n²)"),
                        2, "Merge sort requires O(n) extra space for the temporary arrays during merging."),
                q("Is merge sort stable?",
                        a("No", "Yes", "Only for numbers", "Depends on implementation"),
                        1, "Merge sort is stable — equal elements maintain their relative order."),
                q("What does the merge step in merge sort do?",
                        a("Splits the array", "Combines two sorted halves into one sorted array", "Finds the pivot", "Removes duplicates"),
                        1, "The merge step combines two already-sorted subarrays into a single sorted array."),
                q("Merge sort is preferred over quick sort when:",
                        a("Speed is the only concern", "Stability is required or worst-case matters", "Memory is very limited", "The array is tiny"),
                        1, "Merge sort guarantees O(n log n) and stability, making it preferable when those matter."),
                q("The base case of recursive merge sort is:",
                        a("Array of size 10", "Array of size 0 or 1", "Array already sorted", "Array with all duplicates"),
                        1, "An array of size 0 or 1 is already sorted — that is the base case."),
                q("Which of these is a disadvantage of merge sort?",
                        a("Slow worst case", "Not stable", "Requires extra memory", "Cannot sort numbers"),
                        2, "Merge sort requires O(n) extra space, which can be a drawback for memory-constrained environments."),
                q("How many times does merge sort split a 16-element array before reaching base cases?",
                        a("16", "8", "4", "4 levels deep"),
                        3, "log₂(16) = 4 levels of splitting are needed."),
                q("Merge sort on a linked list is often preferred over quick sort because:",
                        a("Quick sort is always slower", "Merge sort does not require random access", "Linked lists cannot use quick sort at all", "Merge sort uses less memory on linked lists"),
                        1, "Merge sort only needs sequential access, making it a natural fit for linked lists.")
        ));

        // LEVEL 8 - QUICK SORT
        algorithms.put(8, Arrays.asList(
                q("Quick sort works by:",
                        a("Merging halves", "Selecting a pivot and partitioning around it", "Finding minimums repeatedly", "Inserting elements one by one"),
                        1, "Quick sort picks a pivot element and rearranges the array so smaller elements are left and larger are right."),
                q("What is the average-case time complexity of quick sort?",
                        a("O(n²)", "O(n)", "O(n log n)", "O(log n)"),
                        2, "On average, quick sort runs in O(n log n) time."),
                q("What is the worst-case time complexity of quick sort?",
                        a("O(n log n)", "O(n)", "O(n²)", "O(log n)"),
                        2, "Worst case O(n²) occurs when the pivot is always the smallest or largest element."),
                q("Quick sort worst case often happens when:",
                        a("The array is random", "The array is already sorted and pivot is first or last element", "The array has duplicates", "The array has odd length"),
                        1, "Choosing the first element as pivot on a sorted array degrades quick sort to O(n²)."),
                q("Is quick sort stable?",
                        a("Yes always", "No", "Only with median pivot", "Only for integers"),
                        1, "Standard quick sort is not stable — equal elements may change relative order."),
                q("Quick sort is generally preferred in practice because:",
                        a("It has better worst-case than merge sort", "It has small constants and works well in cache", "It requires O(n) extra space", "It is always O(n log n)"),
                        1, "Quick sort's cache efficiency and low overhead make it fast in practice despite O(n²) worst case."),
                q("What is the space complexity of quick sort on average?",
                        a("O(n)", "O(n²)", "O(log n)", "O(1)"),
                        2, "Quick sort uses O(log n) stack space on average due to recursion depth."),
                q("Which pivot selection strategy helps avoid worst case?",
                        a("Always pick first element", "Always pick last element", "Pick median of three", "Pick a fixed index"),
                        2, "Choosing the median of the first, middle, and last elements reduces the chance of worst-case behavior."),
                q("After partitioning in quick sort, the pivot is:",
                        a("At the beginning", "Somewhere random", "In its final sorted position", "Removed"),
                        2, "After partitioning, the pivot is placed in its correct final position in the sorted array."),
                q("Quick sort is classified as:",
                        a("A stable sort", "A non-comparison sort", "A divide and conquer comparison sort", "An O(n) sort"),
                        2, "Quick sort is a divide and conquer, comparison-based sorting algorithm.")
        ));

        // LEVEL 9 - RECURSION
        algorithms.put(9, Arrays.asList(
                q("What is recursion?",
                        a("A loop construct", "A function that calls itself", "A sorting technique", "A data structure"),
                        1, "Recursion is when a function calls itself to solve a smaller version of the same problem."),
                q("What is the base case in recursion?",
                        a("The first recursive call", "The condition that stops the recursion", "The largest input", "The return value"),
                        1, "The base case is the condition under which the function returns without making another recursive call."),
                q("What happens if a recursive function has no base case?",
                        a("It runs once", "It causes a stack overflow", "It returns null", "It sorts the input"),
                        1, "Without a base case, the function calls itself indefinitely, eventually causing a stack overflow."),
                q("Which of these is a classic recursive problem?",
                        a("Finding array length", "Calculating factorial", "Printing a string", "Declaring a variable"),
                        1, "Factorial is the classic recursion example: n! = n × (n-1)!"),
                q("What is the factorial of 0?",
                        a("0", "1", "Undefined", "-1"),
                        1, "By definition, 0! = 1. This is often the base case in factorial recursion."),
                q("Each recursive call creates:",
                        a("A new array", "A new stack frame", "A new class", "A new thread"),
                        1, "Each function call gets its own stack frame holding local variables and return address."),
                q("Recursion and iteration are:",
                        a("Always equally efficient", "Interchangeable in terms of what can be computed", "Never equivalent", "Only for numbers"),
                        1, "Any recursive algorithm can be rewritten iteratively and vice versa."),
                q("The Fibonacci sequence computed recursively without memoization has complexity:",
                        a("O(n)", "O(n log n)", "O(2^n)", "O(log n)"),
                        2, "Naive recursive Fibonacci has exponential complexity because it recalculates the same values repeatedly."),
                q("Tail recursion is special because:",
                        a("It uses more memory", "The recursive call is the last operation, allowing optimization", "It cannot be converted to iteration", "It always uses O(n) stack space"),
                        1, "In tail recursion, the recursive call is the last thing done, allowing compilers to optimize it into a loop."),
                q("Which sorting algorithms commonly use recursion?",
                        a("Bubble sort and selection sort", "Merge sort and quick sort", "Insertion sort and bubble sort", "Selection sort and insertion sort"),
                        1, "Merge sort and quick sort are the primary recursion-based sorting algorithms.")
        ));

        // LEVEL 10 - ADVANCED REVIEW
        algorithms.put(10, Arrays.asList(
                q("Which sorting algorithm guarantees O(n log n) in all cases?",
                        a("Quick sort", "Bubble sort", "Merge sort", "Insertion sort"),
                        2, "Merge sort is always O(n log n) regardless of input order."),
                q("Which algorithm is best for a nearly sorted small array?",
                        a("Merge sort", "Quick sort", "Insertion sort", "Binary search"),
                        2, "Insertion sort approaches O(n) on nearly sorted data, making it ideal for that case."),
                q("Binary search requires the input to be:",
                        a("Randomized", "Sorted", "In a linked list", "Larger than 100 elements"),
                        1, "Binary search only works on sorted data."),
                q("What is the time complexity of accessing a hash table on average?",
                        a("O(n)", "O(log n)", "O(1)", "O(n²)"),
                        2, "Hash tables provide O(1) average-case access, insertion, and deletion."),
                q("Which algorithm strategy breaks a problem into smaller subproblems, solves them independently, and combines results?",
                        a("Greedy", "Dynamic programming", "Divide and conquer", "Backtracking"),
                        2, "Divide and conquer splits, solves, and merges — the pattern of merge sort and binary search."),
                q("Dynamic programming improves recursive algorithms by:",
                        a("Using more memory only", "Storing results of subproblems to avoid recomputation", "Removing the base case", "Sorting the input first"),
                        1, "Memoization or tabulation stores subproblem results so they are not recalculated."),
                q("Which is true about O(n log n) algorithms compared to O(n²)?",
                        a("They are always slower", "They are always faster for any n", "They are faster for large n", "They use less memory"),
                        2, "For large n, O(n log n) is significantly faster than O(n²)."),
                q("A greedy algorithm always:",
                        a("Finds the globally optimal solution", "Makes the locally optimal choice at each step", "Uses recursion", "Requires sorted input"),
                        1, "Greedy algorithms pick the best immediate option — they do not always find the global optimum."),
                q("Which search algorithm is most efficient on a sorted array of 1 million elements?",
                        a("Linear search", "Binary search", "Jump search", "Sequential scan"),
                        1, "Binary search finds the answer in about 20 steps on 1 million elements — far better than linear search."),
                q("The main trade-off in algorithm design is usually between:",
                        a("Color and font", "Time complexity and space complexity", "Variable names and comments", "Input size and output size"),
                        1, "Algorithm design often involves trading memory usage for speed or vice versa.")
        ));

        bank.put("2", algorithms);

        // ══════════════════════════════════════════════════════════════════════
        // TOPIC 3 — OBJECT-ORIENTED PROGRAMMING
        // ══════════════════════════════════════════════════════════════════════
        Map<Integer, List<Question>> oop = new HashMap<>();

        // LEVEL 1 - FOUNDATIONS
        oop.put(1, Arrays.asList(
                q("What is Object-Oriented Programming (OOP)?",
                        a("A type of database", "A programming paradigm based on objects and classes", "A sorting algorithm", "A network protocol"),
                        1, "OOP organizes software around objects that combine data and behavior."),
                q("Which of these is NOT one of the four main OOP principles?",
                        a("Encapsulation", "Polymorphism", "Compilation", "Inheritance"),
                        2, "The four pillars of OOP are encapsulation, inheritance, polymorphism, and abstraction."),
                q("What is an object in OOP?",
                        a("A primitive data type", "An instance of a class", "A loop construct", "A sorting method"),
                        1, "An object is a specific instance created from a class blueprint."),
                q("What is a class in OOP?",
                        a("A running program", "A blueprint for creating objects", "A type of variable", "A network connection"),
                        1, "A class defines the structure and behavior that its objects will have."),
                q("Which keyword is used to create an object in Java?",
                        a("create", "make", "new", "build"),
                        2, "The 'new' keyword allocates memory and creates a new object instance."),
                q("OOP improves software development mainly by:",
                        a("Making code shorter always", "Promoting reusability and modularity", "Removing the need for testing", "Avoiding the use of variables"),
                        1, "OOP encourages code reuse through inheritance and modular design through encapsulation."),
                q("Which of these languages is primarily object-oriented?",
                        a("C", "Assembly", "Java", "SQL"),
                        2, "Java is an object-oriented language where almost everything is an object."),
                q("In OOP, data and methods are bundled together in:",
                        a("Arrays", "Functions", "Objects", "Loops"),
                        2, "Objects combine state (fields) and behavior (methods) in a single unit."),
                q("What is the relationship between a class and an object?",
                        a("They are the same thing", "A class is an instance of an object", "An object is an instance of a class", "Neither contains the other"),
                        2, "A class is the template; an object is a concrete instance of that template."),
                q("Which concept allows different objects to be treated as instances of the same class?",
                        a("Encapsulation", "Abstraction", "Polymorphism", "Compilation"),
                        2, "Polymorphism allows objects of different classes to be treated through a common interface.")
        ));

        // LEVEL 2 - CLASSES & OBJECTS
        oop.put(2, Arrays.asList(
                q("What are the members of a class?",
                        a("Only methods", "Only variables", "Fields and methods", "Only constructors"),
                        2, "A class contains fields (data) and methods (behavior)."),
                q("What is a field in a class?",
                        a("A method that returns a value", "A variable that belongs to the class", "A loop inside a method", "A class inside a class"),
                        1, "Fields are variables that store the state of an object."),
                q("What is a method in a class?",
                        a("A variable declaration", "A block of code that defines behavior", "A type of field", "A constructor only"),
                        1, "Methods define the behavior or actions that objects of the class can perform."),
                q("How do you access a field of an object in Java?",
                        a("object->field", "object::field", "object.field", "object[field]"),
                        2, "In Java, dot notation (object.field) is used to access fields and methods."),
                q("What does 'this' keyword refer to in Java?",
                        a("The parent class", "The current object instance", "The next object", "A static field"),
                        1, "'this' refers to the current object on which the method is being called."),
                q("Which access modifier makes a field accessible only within the same class?",
                        a("public", "protected", "private", "default"),
                        2, "'private' restricts access to within the class only — a key encapsulation tool."),
                q("What is instantiation?",
                        a("Deleting an object", "Creating an object from a class", "Extending a class", "Overriding a method"),
                        1, "Instantiation is the process of creating an object using the 'new' keyword."),
                q("A class can have how many objects created from it?",
                        a("Only one", "Exactly two", "Any number", "Up to 100"),
                        2, "You can create as many objects from a class as memory allows."),
                q("Static fields in a class belong to:",
                        a("Each object individually", "The class itself, shared by all objects", "Only the first object", "No object"),
                        1, "Static fields are shared across all instances of a class — they belong to the class, not individual objects."),
                q("What is the default value of an integer field in Java?",
                        a("null", "1", "0", "Undefined"),
                        2, "Java initializes integer fields to 0 by default.")
        ));

        // LEVEL 3 - CONSTRUCTORS
        oop.put(3, Arrays.asList(
                q("What is a constructor in Java?",
                        a("A method that deletes objects", "A special method called when an object is created", "A static method", "A type of loop"),
                        1, "A constructor initializes a new object and is called automatically with 'new'."),
                q("What is the name rule for a constructor?",
                        a("It can be any name", "It must match the class name", "It must start with 'init'", "It must start with 'new'"),
                        1, "A constructor must have exactly the same name as the class."),
                q("What return type does a constructor have?",
                        a("void", "int", "The class type", "None — constructors have no return type"),
                        3, "Constructors do not have a return type — not even void."),
                q("What is a default constructor?",
                        a("A constructor with parameters", "A constructor provided by Java when none is defined", "A private constructor", "A constructor that returns null"),
                        1, "Java provides a no-argument default constructor if no constructor is explicitly defined."),
                q("What is constructor overloading?",
                        a("Having two classes with the same name", "Defining multiple constructors with different parameter lists", "Calling a constructor twice", "Inheriting a constructor"),
                        1, "Constructor overloading allows a class to have multiple constructors with different parameters."),
                q("What does 'super()' do in a constructor?",
                        a("Calls the current constructor again", "Calls the parent class constructor", "Creates a new object", "Deletes the object"),
                        1, "'super()' calls the constructor of the parent class."),
                q("If a class defines a parameterized constructor, Java will:",
                        a("Still provide a default constructor", "Not provide a default constructor automatically", "Delete the parameterized one", "Throw an error"),
                        1, "Once you define any constructor, Java no longer provides the default no-argument constructor."),
                q("What is a copy constructor?",
                        a("A constructor that copies the class definition", "A constructor that creates a new object as a copy of an existing one", "A constructor without parameters", "A static constructor"),
                        1, "A copy constructor creates a new object with the same state as an existing object."),
                q("Which keyword must be the first statement in a constructor to call another constructor of the same class?",
                        a("super", "this", "new", "init"),
                        1, "'this()' calls another constructor in the same class and must be the first statement."),
                q("Constructors cannot be:",
                        a("Overloaded", "Public", "Abstract", "Called with 'new'"),
                        2, "Constructors cannot be abstract — they must have a concrete implementation.")
        ));

        // LEVEL 4 - ENCAPSULATION
        oop.put(4, Arrays.asList(
                q("What is encapsulation?",
                        a("Inheriting from multiple classes", "Bundling data and methods, and restricting direct access to data", "Using the same method name for different behaviors", "Hiding the class definition"),
                        1, "Encapsulation bundles state and behavior together and protects internal data through access modifiers."),
                q("Which access modifier is most commonly used to implement encapsulation?",
                        a("public", "protected", "private", "static"),
                        2, "Fields are typically made private so they cannot be accessed directly from outside the class."),
                q("What are getters and setters used for?",
                        a("To delete objects", "To provide controlled access to private fields", "To sort arrays", "To override methods"),
                        1, "Getters and setters allow controlled read and write access to private fields."),
                q("What is a getter method?",
                        a("A method that modifies a field", "A method that returns the value of a private field", "A constructor", "A static method"),
                        1, "A getter (accessor) returns the value of a private field without exposing it directly."),
                q("What is a setter method?",
                        a("A method that returns a value", "A method that sets the value of a private field", "A constructor", "A destructor"),
                        1, "A setter (mutator) updates the value of a private field, often with validation."),
                q("Why is encapsulation important?",
                        a("It makes code run faster", "It protects data integrity and hides implementation details", "It removes the need for methods", "It allows multiple inheritance"),
                        1, "Encapsulation prevents external code from putting an object into an invalid state."),
                q("A class with all private fields and public getters/setters is an example of:",
                        a("Inheritance", "Polymorphism", "Encapsulation", "Abstraction"),
                        2, "This is the standard encapsulation pattern — hide data, expose a controlled interface."),
                q("Which access modifier allows access from the same package and subclasses?",
                        a("private", "public", "protected", "static"),
                        2, "'protected' allows access within the same package and from subclasses."),
                q("What happens to encapsulation if all fields are public?",
                        a("It improves", "It is broken — any code can modify the fields directly", "It stays the same", "It causes a compile error"),
                        1, "Public fields break encapsulation because external code can modify them without any control."),
                q("Encapsulation is sometimes called:",
                        a("Data hiding", "Data sorting", "Data inheritance", "Data cloning"),
                        0, "'Data hiding' is another name for encapsulation — internal data is hidden from the outside.")
        ));

        // LEVEL 5 - INHERITANCE
        oop.put(5, Arrays.asList(
                q("What is inheritance in OOP?",
                        a("Creating multiple objects", "A class acquiring fields and methods from another class", "Overriding a method", "Hiding data in a class"),
                        1, "Inheritance allows a class to reuse the fields and methods of another class."),
                q("Which keyword is used for inheritance in Java?",
                        a("implements", "inherits", "extends", "uses"),
                        2, "The 'extends' keyword creates an inheritance relationship between two classes."),
                q("What is the class that is inherited from called?",
                        a("Child class", "Subclass", "Parent / superclass", "Interface"),
                        2, "The class being inherited from is the parent class or superclass."),
                q("What is the class that inherits called?",
                        a("Superclass", "Parent class", "Base class", "Child / subclass"),
                        3, "The class that inherits is the child class or subclass."),
                q("Does Java support multiple inheritance of classes?",
                        a("Yes", "No", "Only with interfaces", "Only for abstract classes"),
                        1, "Java does not support multiple class inheritance to avoid the diamond problem."),
                q("What does a subclass inherit from its superclass?",
                        a("Only private methods", "Public and protected fields and methods", "Only constructors", "Only static members"),
                        1, "A subclass inherits all non-private members of its superclass."),
                q("What keyword is used to call the parent class method from a subclass?",
                        a("parent", "base", "super", "this"),
                        2, "'super.methodName()' calls the parent class's version of a method."),
                q("Which of these best describes the 'is-a' relationship?",
                        a("Composition", "Aggregation", "Inheritance", "Encapsulation"),
                        2, "Inheritance represents an 'is-a' relationship: a Dog is-a Animal."),
                q("Can a subclass override methods from its superclass?",
                        a("No", "Yes", "Only static methods", "Only private methods"),
                        1, "Method overriding is a core feature — a subclass can provide its own implementation of a superclass method."),
                q("What is the root class of all Java classes?",
                        a("Object", "Base", "Root", "Class"),
                        0, "In Java, every class implicitly extends java.lang.Object.")
        ));

        // LEVEL 6 - POLYMORPHISM
        oop.put(6, Arrays.asList(
                q("What does polymorphism mean?",
                        a("One class, one method", "One interface, many implementations", "Multiple inheritance", "Hiding data"),
                        1, "Polymorphism means 'many forms' — the same interface can behave differently in different contexts."),
                q("What is method overriding?",
                        a("Defining two methods with the same name but different parameters", "Redefining a superclass method in a subclass", "Calling a method twice", "Deleting a method"),
                        1, "Method overriding is when a subclass provides a new implementation of a method already defined in the superclass."),
                q("What is method overloading?",
                        a("Redefining a method in a subclass", "Defining multiple methods with the same name but different parameters", "Calling a method too many times", "Inheriting a method"),
                        1, "Overloading is when multiple methods share a name but differ in number or type of parameters."),
                q("Which annotation is used in Java to mark an overriding method?",
                        a("@Override", "@Overload", "@Super", "@Polymorphic"),
                        0, "@Override tells the compiler the method is intended to override a superclass method."),
                q("What is runtime polymorphism?",
                        a("Deciding method at compile time", "Deciding which method to call at runtime based on the object type", "Using multiple constructors", "Static method binding"),
                        1, "Runtime polymorphism occurs when the actual method called is determined at runtime — also called dynamic dispatch."),
                q("What is compile-time polymorphism?",
                        a("Method overriding", "Method overloading — resolved at compile time", "Dynamic dispatch", "Interface implementation"),
                        1, "Method overloading is resolved at compile time based on the method signature."),
                q("Which of these demonstrates polymorphism?",
                        a("Two classes with completely different methods", "A superclass reference pointing to a subclass object", "A private method in a class", "A static field"),
                        1, "Storing a subclass object in a superclass reference and calling overridden methods demonstrates polymorphism."),
                q("Can a parent class reference variable hold a child class object?",
                        a("No", "Yes", "Only if the child has no extra methods", "Only for abstract classes"),
                        1, "A parent reference can hold a child object — this is called upcasting."),
                q("What is the output of calling an overridden method through a parent reference?",
                        a("The parent's version is always called", "The child's overridden version is called", "A compile error", "null"),
                        1, "Due to dynamic dispatch, the overridden version in the actual object's class is called."),
                q("Method overloading is an example of:",
                        a("Runtime polymorphism", "Compile-time polymorphism", "Inheritance", "Encapsulation"),
                        1, "Overloading is resolved at compile time based on the method signature — compile-time polymorphism.")
        ));

        // LEVEL 7 - ABSTRACTION
        oop.put(7, Arrays.asList(
                q("What is abstraction in OOP?",
                        a("Hiding implementation details and showing only essential features", "Copying one object to another", "Sorting objects by value", "Restricting field access"),
                        0, "Abstraction focuses on what an object does rather than how it does it."),
                q("Which Java keyword is used to declare an abstract class?",
                        a("interface", "abstract", "virtual", "base"),
                        1, "The 'abstract' keyword declares a class or method as abstract."),
                q("Can you instantiate an abstract class in Java?",
                        a("Yes", "No", "Only with a special keyword", "Only if it has no abstract methods"),
                        1, "Abstract classes cannot be instantiated directly — they must be subclassed."),
                q("What is an abstract method?",
                        a("A method with no parameters", "A method declared without a body", "A static method", "A private method"),
                        1, "An abstract method has no body — subclasses must provide the implementation."),
                q("A class that contains at least one abstract method must be:",
                        a("Static", "Final", "Abstract", "Private"),
                        2, "Any class with at least one abstract method must itself be declared abstract."),
                q("What is the difference between an abstract class and an interface?",
                        a("Abstract classes can have state; interfaces traditionally cannot (before Java 8)", "They are identical", "Interfaces can be instantiated", "Abstract classes cannot have constructors"),
                        0, "Abstract classes can have fields and constructors; interfaces define a pure contract."),
                q("Abstraction helps in software development by:",
                        a("Making code run faster", "Reducing complexity and increasing maintainability", "Removing all methods", "Avoiding inheritance"),
                        1, "Abstraction reduces complexity by hiding unnecessary details from the user of a class."),
                q("Which of these is a real-world example of abstraction?",
                        a("A car's engine details", "A TV remote — you press a button without knowing the circuit", "A sorted array", "A private field"),
                        1, "Using a remote without knowing the electronics is abstraction — you see only the interface, not the implementation."),
                q("A subclass of an abstract class must:",
                        a("Also be abstract always", "Override all abstract methods or itself be abstract", "Have a default constructor", "Have no fields"),
                        1, "A concrete subclass must implement all abstract methods, or it must also be declared abstract."),
                q("Abstraction and encapsulation are related but different. Abstraction focuses on:",
                        a("Hiding data", "Hiding complexity by exposing only relevant behavior", "Access modifiers", "Memory management"),
                        1, "Abstraction hides complexity; encapsulation hides data.")
        ));

        // LEVEL 8 - INTERFACES
        oop.put(8, Arrays.asList(
                q("What is an interface in Java?",
                        a("A class with private fields", "A contract defining methods a class must implement", "A type of constructor", "A loop structure"),
                        1, "An interface defines a contract — any class implementing it must provide the listed methods."),
                q("Which keyword is used to implement an interface in Java?",
                        a("extends", "inherits", "implements", "uses"),
                        2, "The 'implements' keyword is used by a class to fulfill an interface contract."),
                q("Can a class implement multiple interfaces in Java?",
                        a("No", "Yes", "Only two at a time", "Only if they share no methods"),
                        1, "Java allows a class to implement multiple interfaces, solving the multiple inheritance limitation."),
                q("Can an interface contain fields?",
                        a("No fields at all", "Only public static final constants", "Any type of field", "Only private fields"),
                        1, "Interface fields are implicitly public, static, and final — they are constants."),
                q("Before Java 8, interface methods were always:",
                        a("Private", "Abstract and public", "Static only", "Final"),
                        1, "Before Java 8, all interface methods were implicitly public and abstract."),
                q("What did Java 8 add to interfaces?",
                        a("Private constructors", "Default and static method implementations", "Field declarations", "Abstract classes"),
                        1, "Java 8 introduced default and static methods with bodies in interfaces."),
                q("What is the difference between an interface and an abstract class?",
                        a("Interfaces can be instantiated", "A class can extend multiple abstract classes", "A class can implement multiple interfaces but extend only one class", "They are identical"),
                        2, "Multiple interface implementation is the key advantage of interfaces over abstract classes."),
                q("An interface is best used when:",
                        a("You want to share code between closely related classes", "You want to define a common capability for unrelated classes", "You need a constructor", "You need private fields"),
                        1, "Interfaces are ideal for defining capabilities (like Serializable or Comparable) that unrelated classes can share."),
                q("What is a functional interface?",
                        a("An interface with many methods", "An interface with exactly one abstract method", "An interface that extends another", "An interface with only static methods"),
                        1, "A functional interface has exactly one abstract method and can be used with lambda expressions."),
                q("Which of these is a built-in Java functional interface?",
                        a("Serializable", "Runnable", "Cloneable", "Comparable"),
                        1, "Runnable has exactly one abstract method (run()) and is a functional interface.")
        ));

        // LEVEL 9 - DESIGN PATTERNS
        oop.put(9, Arrays.asList(
                q("What is a design pattern in OOP?",
                        a("A specific algorithm", "A reusable solution to a commonly occurring design problem", "A database schema", "A type of loop"),
                        1, "Design patterns are proven, reusable templates for solving common software design problems."),
                q("Which design pattern ensures a class has only one instance?",
                        a("Factory", "Observer", "Singleton", "Decorator"),
                        2, "The Singleton pattern restricts a class to a single instance and provides a global access point."),
                q("The Factory pattern is used to:",
                        a("Sort objects", "Create objects without specifying the exact class", "Observe state changes", "Add behavior to objects"),
                        1, "Factory pattern delegates object creation to subclasses or factory methods."),
                q("What does the Observer pattern do?",
                        a("Creates objects on demand", "Defines a one-to-many dependency so dependents are notified of state changes", "Wraps an object with extra behavior", "Restricts instantiation"),
                        1, "Observer defines a publish-subscribe relationship between objects."),
                q("The Decorator pattern is used to:",
                        a("Sort a collection", "Add behavior to objects dynamically without subclassing", "Create a single instance", "Define object creation"),
                        1, "Decorator wraps objects to add new behavior without modifying the original class."),
                q("Design patterns are grouped into which three categories?",
                        a("Simple, Complex, Advanced", "Creational, Structural, Behavioral", "Static, Dynamic, Abstract", "Data, Logic, View"),
                        1, "The Gang of Four classifies patterns as Creational, Structural, and Behavioral."),
                q("Which pattern is used by Android's View system for click listeners?",
                        a("Singleton", "Factory", "Observer / Listener", "Decorator"),
                        2, "Click listeners are an implementation of the Observer / Listener pattern."),
                q("The MVC pattern stands for:",
                        a("Model, Variable, Controller", "Model, View, Controller", "Method, View, Class", "Module, Variable, Component"),
                        1, "MVC separates an application into Model (data), View (UI), and Controller (logic)."),
                q("Which pattern would you use to ensure database connections are reused rather than created each time?",
                        a("Observer", "Singleton", "Factory", "Decorator"),
                        1, "Singleton ensures only one instance exists — ideal for connection pools or shared resources."),
                q("Why are design patterns important?",
                        a("They guarantee the fastest code", "They provide shared vocabulary and proven solutions for common problems", "They replace the need for testing", "They are required by all compilers"),
                        1, "Design patterns give developers a shared language and battle-tested solutions for recurring challenges.")
        ));

        // LEVEL 10 - ADVANCED REVIEW
        oop.put(10, Arrays.asList(
                q("Which OOP principle is best described as 'hiding how something works and showing only what it does'?",
                        a("Inheritance", "Polymorphism", "Abstraction", "Encapsulation"),
                        2, "Abstraction hides implementation and exposes only the relevant interface."),
                q("Which OOP principle allows code reuse through parent-child class relationships?",
                        a("Encapsulation", "Inheritance", "Polymorphism", "Abstraction"),
                        1, "Inheritance lets a child class reuse and extend the parent class's code."),
                q("Which OOP principle is best described as 'protecting data by restricting access'?",
                        a("Inheritance", "Polymorphism", "Abstraction", "Encapsulation"),
                        3, "Encapsulation bundles data with methods and restricts direct external access."),
                q("A method that has the same name but different behavior in different subclasses is an example of:",
                        a("Encapsulation", "Inheritance", "Polymorphism", "Abstraction"),
                        2, "Different behaviors under one method name is polymorphism."),
                q("What does the SOLID principle stand for?",
                        a("Single responsibility, Open/closed, Liskov substitution, Interface segregation, Dependency inversion", "Sort, Order, Load, Index, Delegate", "Simple, Object, Linked, Interface, Dynamic", "None of the above"),
                        0, "SOLID is a set of five design principles for writing maintainable, scalable OOP code."),
                q("Which of these violates encapsulation?",
                        a("Private field with getter/setter", "Public field accessed directly", "Abstract method", "Interface method"),
                        1, "Public fields allow direct uncontrolled modification — this breaks encapsulation."),
                q("In Java, which combination allows you to achieve full abstraction?",
                        a("Abstract class only", "Interface only", "Both abstract class and interface can achieve it", "Neither"),
                        2, "Both abstract classes and interfaces can be used to achieve abstraction depending on the use case."),
                q("What is composition in OOP?",
                        a("A class extending another class", "A class containing an instance of another class as a field", "A method calling itself", "An interface with one method"),
                        1, "Composition is a 'has-a' relationship — one class contains another as a field."),
                q("When is composition preferred over inheritance?",
                        a("Always", "When the relationship is 'has-a' rather than 'is-a'", "Only for interfaces", "Never"),
                        1, "Composition is preferred for 'has-a' relationships — it provides more flexibility than inheritance."),
                q("Which OOP concept is used in Java's ArrayList when you can store any object type?",
                        a("Encapsulation", "Polymorphism via generics", "Abstraction", "Inheritance only"),
                        1, "Generics and polymorphism allow ArrayList to store different object types through a common interface.")
        ));

        bank.put("3", oop);

        // Placeholder topics
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