package org.excavator.boot.experiment;

import java.util.Map;
import java.util.HashMap;

public class LRUCache {
    final Node head = new Node();
    final Node tail = new Node();
    Map<Integer, Node> map;
    int cache_capacity;

    public LRUCache(int capacity){
        this.cache_capacity = capacity;
        map = new HashMap<Integer, Node>();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        Node node = map.get(key);
        int result = -1;

        if(null != node){
            result = node.value;
            remove(node);
            add(node);
        }

        return result;
    }

    public void put(int key, int value){
        Node node = map.get(key);
        if(null != node){
            remove(node);
            node.value = value;
            add(node);
        }else{
            if(map.size() == cache_capacity){
                map.remove(tail.prev.key);
                remove(tail.prev);
            }

            Node newnode = new Node();
            newnode.key = key;
            newnode.value = value;
            map.put(key, newnode);
            add(newnode);
        }
    }

    public void add(Node newNode){
        Node tempNode = head.next;
        newNode.next = tempNode;
        tempNode.prev = newNode;
        head.next = newNode;
        newNode.prev = head;
    }

    public void remove(Node deleteNode){
        Node nextNode = deleteNode.next;
        Node prevNode = deleteNode.prev;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
}

class Node{
    int key;
    int value;
    Node prev;
    Node next;
}
