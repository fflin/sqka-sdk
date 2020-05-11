package com.hengxin.mall.test;

import java.util.HashMap;
import java.util.Map;

/**
 * author : fflin
 * date   : 2020/5/11 19:08
 * desc   : https://blog.csdn.net/wangxilong1991/article/details/70172302
 * version: 1.0
 */
public class LRUCache<K, V> {

    private int currentCacheSize;
    private int CacheCapcity;
    private HashMap<K,CacheNode> caches;
    private CacheNode first;
    private CacheNode last;

    public LRUCache(int size){
        currentCacheSize = 0;
        this.CacheCapcity = size;
        caches = new HashMap<K,CacheNode>(size);
    }

    public void put(K k,V v){
        CacheNode node = caches.get(k);
        if(node == null){
            if(caches.size() >= CacheCapcity){

                caches.remove(last.key);
                removeLast();
            }
            node = new CacheNode();
            node.key = k;
        }
        node.value = v;
        moveToFirst(node);
        caches.put(k, node);
    }

    private void moveToFirst(CacheNode node){
        if(first == node){
            return;
        }
        if(node.next != null){
            node.next.pre = node.pre;
        }
        if(node.pre != null){
            node.pre.next = node.next;
        }
        if(node == last){
            last= last.pre;
        }
        if(first == null || last == null){
            first = last = node;
            return;
        }

        node.next=first;
        first.pre = node;
        first = node;
        first.pre=null;

    }

    public Object  get(K k){
        CacheNode node = caches.get(k);
        if(node == null){
            return null;
        }
        moveToFirst(node);
        return node.value;
    }

    public Object remove(K k){
        CacheNode node = caches.get(k);
        if(node != null){
            if(node.pre != null){
                node.pre.next=node.next;
            }
            if(node.next != null){
                node.next.pre=node.pre;
            }
            if(node == first){
                first = node.next;
            }
            if(node == last){
                last = node.pre;
            }
        }

        return caches.remove(k);
    }

    public void clear(){
        first = null;
        last = null;
        caches.clear();
    }





    private void removeLast(){
        if(last != null){
            last = last.pre;
            if(last == null){
                first = null;
            }else{
                last.next = null;
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        CacheNode node = first;
        while(node != null){
            sb.append(String.format("%s:%s ", node.key,node.value));
            node = node.next;
        }

        return sb.toString();
    }

    public Map getMap() {
        return caches;
    }

    class CacheNode{
        CacheNode pre;
        CacheNode next;
        Object key;
        Object value;
        public CacheNode(){

        }
    }


}