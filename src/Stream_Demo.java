package _stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Stream_Demo {
    public static void main(String[] args) {
        /*-------Collection集合获取流-------- */
        Collection<String> list =new ArrayList<>();
        Stream<String> s = list.stream();

        /*-------Map集合获取流-------- */
        Map<String,Integer> maps = new HashMap<>();

        //键流
        Stream<String> keyStream = maps.keySet().stream();
        //值流
        Stream<Integer> vStream = maps.values().stream();
        //键值对流（拿整体）
        Stream<Map.Entry<String,Integer>> keyAndValuStream = maps.entrySet().stream();

        /*-------s数组获取流-------- */
        String[] names = {"aaa","bbb","ccc","dddd"};
       Stream<String> nameStream = Arrays.stream(names);

    }
}
