package libraries.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFrame {
    //List<String> lines = new ArrayList<String>();
    Collection<String> lines = new ArrayList<String>();

    public DataFrame filter(Filter filter) {
        //for each row/line in current data frame, if passes filter, add to new data frame
        List<String> newLines = new ArrayList<>();
        for(String line: lines){
            if(filter.filter(line)){
                newLines.add(line);
            }
        }

        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines = newLines;
        return newDataFrame;
    }

    public DataFrame map(Mapper mapper) {
        //for each line in the current dataframe or list of lines, add the result returned by the mapper

        List<String> newLines = new ArrayList<>();
        for(String line: lines){
            newLines.add(mapper.map(line));
        }

        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines = newLines;
        return newDataFrame;
    }

    public DataFrame reduceByKey(ReduceByKey reduceByKey) {

        //the first string is the key, the next string is the total sofar
        //we have two maps, one for checking if key exists, another to store final result
        Map<String, String> totals = new HashMap<>();
        Map<String, String> resultMap = new HashMap<>(); //this one is avoid a second loop to populate the RDD.

        for(String line: lines){
            String[] tokens = line.split(" ");
            String key = tokens[0]; //assumes the data type of the line is string, but we may change later
            String value = tokens[1]; //splitting a line might be expensive, so do only once

            if(!totals.containsKey(key)){
                totals.put(key, ""); //but if the type for value is numeric such as it, we put zero
            }

            String newLine = reduceByKey.reduce(totals.get(key), value);
            totals.put(key, newLine);
            resultMap.put(key, key.concat(" ").concat(newLine));

        }

        //generate new RDD
        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines =resultMap.values();
        return newDataFrame;
    }

    public DataFrame sumByKey(final Integer numOfDecimals){
        return reduceByKey(new ReduceByKey() {
            @Override
            public String reduce(String prevSum, String value) {
                if(prevSum == null || prevSum.trim().equalsIgnoreCase("")){
                    prevSum = "0";
                }
                if(value == null || value.trim().equalsIgnoreCase("")){
                    value = "0";
                }
                Double result = Double.valueOf(prevSum) + Double.valueOf(value);

                String formatString = "%.".concat(numOfDecimals.toString()).concat("f");
                return String.format(formatString,result);
            }
        });
    }


    public <Type>DataFrame sort(final SortingFactor<Type> sortingFactor) {

        List<String> newLines = new ArrayList<>(lines);
        Collections.sort(newLines, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return sortingFactor.getSortingFactor(lhs).compareTo(sortingFactor.getValue(rhs));
            }
        });
        //we shall cate for the issue, of ascending and descending later, perhaps by reversing order
        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines = newLines;

        return newDataFrame;
    }



    private Splitter splitterUsingNewLine = new Splitter() {
        @Override
        public String[] split(String text) {
            return text.split("\\r?\\n");
        }
    };
    private Splitter splitterUsingAnySpace = new Splitter() {
        @Override
        public String[] split(String text) {
            return text.split("\\s+");
        }
    };
    public static DataFrame fromText(String text, Splitter splitter) {
        String[] lines = splitter.split(text);
        DataFrame frame = new DataFrame();
        frame.lines = Arrays.asList(lines);
        return frame;
    };

    public DataFrame forEach(ForEach forEach) {
        for(String line: lines){
            forEach.foreach(line);
        }
        return this;
    }

    public DataFrame reverse() {
        ArrayList<String> newLines = new ArrayList<>(lines);
        Collections.reverse(newLines);

        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines = newLines;
        return newDataFrame;
    }


    public DataFrame take(int howMany) {
        ArrayList<String> sourceList = new ArrayList<>(lines);
        ArrayList<String> newLines = new ArrayList<>();


        int count = sourceList.size();
        for(int i = 0; i < howMany && i < count;i++){
            newLines.add(sourceList.get(i));
        }

        DataFrame newDataFrame = new DataFrame();
        newDataFrame.lines = newLines;
        return newDataFrame;
    }

    public DataFrame toLowerCase() {
        return map(new Mapper() {
            @Override
            public String map(String line) {
                return line.toLowerCase();
            }
        });
    }

    public int count() {
        return lines.size();
    }

    //a filter returns the condition that shd be true for a line to be retained.
    //a like is a data type
    public static interface Filter {
        boolean filter(String line);
    }

    public static interface ForEach {
        void foreach(String line);
    }

    //mapper returns the line that shd replace the old line
    //a line does not always have to be of a string type
    public static interface Mapper {
        String map(String line);
    }

    //a reduce by key returns how the new line is generated based on the current total and the new value
    public static interface ReduceByKey {
        String reduce(String prevSum, String value);
    }

    //returns the value that shd be used to determine the order of lines in the new data frame
    //returns the value that shd be compared to the others when sorting. Provides u with the line in case u might want to use a value in the line
    public abstract static class SortingFactor<Type> {
        public Comparable<Type> getSortingFactor(String line){
            try{
                return (Comparable<Type>)getValue(line);
            }
            catch (Exception ex){
                return new Comparable<Type>() {
                    @Override
                    public int compareTo(Type another) {
                        return 0;
                    }
                };
            }

        }
        public abstract Type getValue(String line);
    }

    public static interface Splitter {
        String[] split(String text);
    }
}
