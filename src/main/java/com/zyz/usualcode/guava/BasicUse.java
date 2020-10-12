package com.zyz.usualcode.guava;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;

public class BasicUse {
    public static void main(String[] args) {
        // Optional 在jdk8中已经引入
        Optional<Integer> possible = Optional.of(5);
        possible.ifPresent(System.out::println);

        // 看各种用法感觉基本已经被jdk吸收了？
        // 交并差集
        HashSet<Integer> setA = newHashSet(1, 2, 3, 4, 5);
        HashSet<Integer> setB = newHashSet(4, 5, 6, 7, 8);
        Sets.SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection: " + intersection);
        Sets.SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union: " + union);
        Sets.SetView<Integer> difference = Sets.difference(setA, setB);
        System.out.println("difference: " + difference);
    }
}
