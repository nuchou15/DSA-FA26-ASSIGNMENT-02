#!/usr/bin/env kotlin

interface LinkedList<T> {
    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T)

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T)

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists
     */
    fun popFront(): T?

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popBack(): T?

    /**
     * @return the value at the front of the list or nil if none exists
     */
    fun peekFront(): T?

    /**
     * @return the value at the back of the list or nil if none exists
     */
    fun peekBack(): T?

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

interface Stack<T> {
    /**
     * Add [data] to the top of the stack
     */
    fun push(data: T)
    /**
     * Remove the element at the top of the stack.  If the stack is empty, it remains unchanged.
     * @return the value at the top of the stack or nil if none exists
     */
    fun pop(): T?
    /**
     * @return the value on the top of the stack or nil if none exists
     */
    fun peek(): T?
    /**
     * @return true if the stack is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

interface Queue<T> {
    /**
     * Add [data] to the end of the queue.
     */
    fun enqueue(data: T)
    /**
     * Remove the element at the front of the queue.  If the queue is empty, it remains unchanged.
     * @return the value at the front of the queue or nil if none exists
     */
    fun dequeue(): T?
    /**
     * @return the value at the front of the queue or nil if none exists
     */
    fun peek(): T?
    /**
     * @return true if the queue is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

// each node stores its data and links to the nodes before and after it
// head and tail are null
class Node<T>(
    var data: T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null
)

class MyLinkedList<T> : LinkedList<T> {
    // head points to the first node in the list
    private var head: Node<T>? = null
    // tail points to the last node in the list
    private var tail: Node<T>? = null

    // Adds an element to the front of the list
    override fun pushFront(data: T) {
        // Create a new node containing the data
        val newNode = Node(data)
        // If the list is empty, the new node is both the head and the tail
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            // The new node points forward to the old head
            newNode.next = head
            // The old head points backward to the new node
            head!!.previous = newNode
            // The new node is now the head
            head = newNode
        }
    }

    // adds an element to the back of the list
    override fun pushBack(data: T) {
        // create a new node containing the data
        val newNode = Node(data)
        // if the list is empty then new node is both the head and the tail
        if (tail == null) {
            head = newNode
            tail = newNode
        } else {
            // new node points to old tail
            newNode.previous = tail
            // old tail points forward to the new node
            tail!!.next = newNode
            // new node is now tail
            tail = newNode
        }
    }

    // remove and return value of element at front of list
    override fun popFront(): T? {
        // If the list is empty,, nothing to remove
        if (head == null) {
            return null
        }
        // save data before removing node
        val value = head!!.data
        // If head and tail are the same, then there's only one node in list
        if (head == tail) {
            head = null
            tail = null
        } else {
            // move head to the next node
            head = head!!.next
            head!!.previous = null
        }
        // return value that was removed
        return value
    }

    // Remove and return element at the back
    override fun popBack(): T? {

        // if list is empty, nothing to remove
        if (tail == null) {
            return null
        }
        val value = tail!!.data

        // If head and tail are the same,there is only one node in the list
        if (head == tail) {
            head = null
            tail = null
        } else {
            // move tail to previous node
            tail = tail!!.previous
            tail!!.next = null
        }
        // return value that was removed
        return value
    }

    // return value of front of list
    override fun peekFront(): T? {
        return head?.data
    }

    // return value at back of list
    override fun peekBack(): T? {
        return tail?.data
    }

    // return true if list is empty false otherwise
    override fun isEmpty(): Boolean {
        return head == null
    }
}

// Test LinkedList
fun testLinkedList() {
    val list = MyLinkedList<Int>()
    check(list.isEmpty()) {
        "New list should be empty"
    }

    // Add 10 to the front
    list.pushFront(10)
    check(list.peekFront() == 10) {
        "Front should be 10"
    }
    check(list.peekBack() == 10) {
        "Back should be 10"
    }

    // Add 20 to the front
    list.pushFront(20)
    check(list.peekFront() == 20) {
        "Front should be 20"
    }
    check(list.peekBack() == 10) {
        "Back should be 10"
    }

    // Add 30 to the back
    list.pushBack(30)
    check(list.peekFront() == 20) {
        "Front should still be 20"
    }
    check(list.peekBack() == 30) {
        "Back should now be 30"
    }

    // Remove the top element
    check(list.popFront() == 20) {
        "popFront should return 20"
    }
    check(list.peekFront() == 10) {
        "Front should now be 10"
    }

    // Remove the back element
    check(list.popBack() == 30) {
        "popBack should return 30"
    }
    check(list.peekFront() == 10) {
        "Front should be 10"
    }
    check(list.peekBack() == 10) {
        "Back should be 10"
    }

    // Remove the last element
    check(list.popFront() == 10) {
        "popFront should return 10"
    }
    check(list.isEmpty()) {
        "List should be empty"
    }

    // Popping an empty list should return null
    check(list.popFront() == null) {
        "popFront on empty list should return null"
    }
    check(list.popBack() == null) {
        "popBack on empty list should return null"
    }
    println("LinkedList tests passed!")
}

// Exercise 1
class MyStack<T> : Stack<T> {
    // use linked list interface to store stack's elements
    private val list = MyLinkedList<T>()
    // add data to top of stack
    override fun push(data: T) {
        list.pushFront(data)
    }

    // remove element from top of stack
    override fun pop(): T? {
        return list.popFront()
    }

    // return value of top of stack
    override fun peek(): T? {
        return list.peekFront()
    }

    // true if stack is empty false otherwise
    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

fun testStack() {
    println("Testing Stack...")
    // Create an empty stack
    val stack = MyStack<Int>()
    // A new stack should be empty
    check(stack.isEmpty()) {
        "New stack should be empty"
    }

    // add elements to the stack
    stack.push(10)
    stack.push(20)
    stack.push(30)

    // last element added should be on top
    check(stack.peek() == 30) {
        "Top of stack should be 30"
    }

    // Peek should not remove the element
    check(stack.peek() == 30) {
        "Peek should not remove the top element"
    }

    // Remove elements from the stack
    check(stack.pop() == 30) {
        "First pop should return 30"
    }
    check(stack.pop() == 20) {
        "Second pop should return 20"
    }
    check(stack.pop() == 10) {
        "Third pop should return 10"
    }

    // The stack should now be empty
    check(stack.isEmpty()) {
        "Stack should be empty"
    }

    // Popping an empty stack should return null
    check(stack.pop() == null) {
        "Pop on empty stack should return null"
    }
    println("Stack tests passed!")
}

//Exercise 2
class MyQueue<T> : Queue<T> {
    // use linked list interface to store queue's elemtns
    private val list = MyLinkedList<T>()
    // add an element to the end of queue
    override fun enqueue(data: T) {
        list.pushBack(data)
    }

    // remove element from front of queue
    override fun dequeue(): T? {
        return list.popFront()
    }

    // value of front of queue
    override fun peek(): T? {
        return list.peekFront()
    }

    // true if empty false otherwise
    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

fun testQueue() {
    println("Testing Queue...")
    // Create an empty queue
    val queue = MyQueue<Int>()

    // A new queue should be empty
    check(queue.isEmpty()) {
        "New queue should be empty"
    }

    // Add elements to the queue
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)

    // The first element added should be at the front
    check(queue.peek() == 10) {
        "Front of queue should be 10"
    }

    // Remove elements from the queue
    check(queue.dequeue() == 10) {
        "First dequeue should return 10"
    }
    check(queue.dequeue() == 20) {
        "Second dequeue should return 20"
    }
    check(queue.dequeue() == 30) {
        "Third dequeue should return 30"
    }

    // The queue should now be empty
    check(queue.isEmpty()) {
        "Queue should be empty"
    }

    // Dequeueing an empty queue should return null
    check(queue.dequeue() == null) {
        "Dequeue on empty queue should return null"
    }
    println("Queue tests passed!")
}

// Exercise 3
fun <T> reverseStack(stack: Stack<T>) {

    // creating temp stack to hold elments while removing from og stack
    val temp1 = MyStack<T>()

    // create another temp stack to reverse order of elemnts
    val temp2 = MyStack<T>()

    // remove elements from og stack to temp 1 till it's empty
    while (!stack.isEmpty()) {
        temp1.push(stack.pop()!!)
    }

    // remove elements from temp stack 1 to 2 until empty
    while (!temp1.isEmpty()) {
        temp2.push(temp1.pop()!!)
    }

    // move all elements from temp stack 2 back into og stack
    while (!temp2.isEmpty()) {
        stack.push(temp2.pop()!!)
    }
}

// To implement Exercise 4, whenever we see an opening bracket we can add it to a stack.
// then whenever we notice a closing bracket, we can check the top of the stack to
// see if it has by a valid opening bracket.

// To implement exercise 5, first you can put the elements from the stack into the queue.
// Since queue is FIFO, you put it back into the og stack, but it'll be reversed.
// Then you put it back into the new stack, and it'll be in the same order as it was
// initially in the og stack, because you're reversing the reversed order.

