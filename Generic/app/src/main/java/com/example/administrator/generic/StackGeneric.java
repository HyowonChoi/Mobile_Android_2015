package com.example.administrator.generic;

class Node<T> {
    Node<T> next;
    T data;
    public Node(T a) {
        data = a;
    }
    public T getData() {
        return this.data;
    }
    public Node<T> getNext() {
        return this.next;
    }
    public void setNext(Node<T> tmp) {
        next = tmp;
    }
}


class Stack<T> {
    Node<T> top;

    public T pop() {
        T d = top.getData();
        top = top.getNext();
        return d;
    }

    public void push(T d) {
        if (top == null)
            top = new Node<T>(d);
        else {
            Node<T> newNode = new Node<T>(d);
            newNode.setNext(top);
            top = newNode;
        }
    }
}



public class StackGeneric {
    public static void main(String arg[]){
        Stack<Integer> stackInteger = new Stack<Integer>();
        stackInteger.push(1);
        stackInteger.push(2);
        System.out.println(stackInteger.pop());
        System.out.println(stackInteger.pop());

        Stack<String> stackString = new Stack<String>();
        stackString.push("Kookmin University");
        stackString.push("Korea Seoul");
        System.out.println(stackString.pop());
        System.out.println(stackString.pop());
    }
}

