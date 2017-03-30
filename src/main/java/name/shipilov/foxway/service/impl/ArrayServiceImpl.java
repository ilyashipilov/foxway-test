package name.shipilov.foxway.service.impl;

import name.shipilov.foxway.service.ArrayService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ArrayServiceImpl implements ArrayService {

    static class ValueWIthIndex {
        public int value;
        public int index;

        public ValueWIthIndex(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    @Override
    public int minimalSumOfTwoElements(List<Integer> data) {
        if (data == null)
            throw new NullPointerException("no data");
        if (data.size() < 2)
            throw new IllegalArgumentException("data too short");
        if (!data.stream().allMatch(i -> i != null && i >= -1000 && i <= 1000))
            throw new IllegalArgumentException("all elements must be from [-1000;1000]");

        data = new ArrayList<>(data);
        Collections.sort(data);
        return data.get(0) + data.get(1);
    }

    @Override
    public int minimalSumOfTwoElementsNoFirstLastBeside(List<Integer> data) {
        if (data == null)
            throw new NullPointerException("no data");
        if (data.size() < 5)
            throw new IllegalArgumentException("data too short");
        if (!data.stream().allMatch(i -> i != null && i >= -1000 && i <= 1000))
            throw new IllegalArgumentException("all elements must be from [-1000;1000]");

        data = data.subList(1, data.size() - 1);
        final List<ValueWIthIndex> valueWIthIndices = convert(data);

        Collections.sort(valueWIthIndices, Comparator.comparingInt(o -> o.value));
        for (int i=1; i < valueWIthIndices.size(); i++ ) {
            if (Math.abs(valueWIthIndices.get(0).index - valueWIthIndices.get(i).index) != 1)
                return valueWIthIndices.get(0).value + valueWIthIndices.get(i).value;
        }
        throw new IllegalArgumentException();
    }

    private List<ValueWIthIndex> convert(List<Integer> chain) {
        List<ValueWIthIndex> result = new ArrayList<>(chain.size());
        for(int i=0; i<chain.size(); i++) {
            result.add(new ValueWIthIndex(chain.get(i), i));
        }
        return result;
    }

}
