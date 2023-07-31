package com.ruoshui.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListUtils {




        private static void stupidMethod2(List<String> listA, List<String> listB) {
            System.out.println("数量级为 " + listA.size() + "集合的不同元素为");
            List<String> differList = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            for (String str : listB) {
                if (!listA.contains(str)) {
                    differList.add(str);
                }
            }
            traverse(differList);
            long endTime = System.currentTimeMillis();
            System.out.println("使用双层遍历方法 对比耗时: " + (endTime - startTime));
        }

        /**
         * 遍历集合,打印出每个元素
         *
         * @param list List集合
         */
        private static void traverse(List<String> list) {
            for (String str : list) {
                System.out.print(str + " ");
            }
            System.out.println();
        }

        private static void stupidMethod(List<String> listA, List<String> listB) {
            System.out.println("数量级为 " + listA.size() + "集合的不同元素为");
            List<String> listABak = new ArrayList<>(listA); // 复制A集合作为备份
            long startTime = System.currentTimeMillis();
            listB.removeAll(listA); // B集合与A集合的不同元素
            traverse(listB);
            long endTime = System.currentTimeMillis();
            System.out.println("直接调用java api 方法 对比耗时: " + (endTime - startTime));
//        listABak.removeAll(listB); // A集合与B集合的相同元素
//        listA.removeAll(listABak); // A集合与B集合的不同元素
        }

        /**
         * 制造任意个元素的的List集合
         *
         * @param size List集合的size
         * @return List<String>
         */
        private static List<String> dataList(int size) {
            List<String> dataList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                dataList.add("" + i);
            }
            return dataList;
        }


        /**
         * 借助Map来获取listA、listB的不同元素集合
         *
         * @param listA 集合A
         * @param listB 集合B
         * @return list<String>
         */
        public static List<String> getDifferListByMap(List<String> listA, List<String> listB) {
            System.out.println("数量级为 " + listA.size() + "集合的不同元素为");
            List<String> differList = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            long beginTime = System.currentTimeMillis();
            for (String strA : listA) {
                map.put(strA, 1);
            }
            for (String strB : listB) {
                Integer value = map.get(strB);
                if (value != null) {
                    map.put(strB, ++value);
                    continue;
                }
                map.put(strB, 1);
            }

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 1) { //获取不同元素集合
                    differList.add(entry.getKey());
                }
            }
            traverse(differList);
            long endTime = System.currentTimeMillis();
            System.out.println("使用map方式遍历, 对比耗时: " + (endTime - beginTime));
            return differList;
        }

    }


