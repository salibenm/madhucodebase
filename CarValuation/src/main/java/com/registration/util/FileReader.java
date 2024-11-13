package com.registration.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * FileReader is util class to read car registrations based on pattern from input file and expected output details.
 */
public class FileReader {

    private static final String CAR_REG_PATTERN = "(^[A-Z]{2}[0-9]{2}([A-Z]{3}|\\s[A-Z]{3})$)";

    public static List<String> getCarRegistrationNumbers(String inputFileName) throws IOException {
        BufferedReader bufferedReader = null;
        String[] words = null;
        List<String> carRegNumbers = new ArrayList<String>();
        try {
            File inputFile = getFile(inputFileName);
            bufferedReader = new BufferedReader(new java.io.FileReader(inputFile.getAbsolutePath()));
            String strLine = null;
            while ((strLine = bufferedReader.readLine()) != null)  {
                String registrationNum = CAR_REG_PATTERN;
                words = strLine.split(" ");
                for (String str : words) {
                    if (Pattern.matches(registrationNum, str)) {
                        carRegNumbers.add(str);
                    }
                }
            }
        } catch (Exception exception) {
            System.err.println("Exception occured while retrieve car registration number: " + exception.getMessage());
        } finally {
            bufferedReader.close();
        }
        return carRegNumbers;
    }

    public static  Map<String,List<String>> getCarOutputDetails(String outputFileName) throws IOException {
        BufferedReader bufferedReader = null;
        String outLine = " ";
        List<String> outputValues = new ArrayList<String>();
        Map<String,List<String>> expectedMap = new HashMap<String, List<String>>();
        try {
            File inputFile = getFile(outputFileName);
            bufferedReader = new BufferedReader(new java.io.FileReader(inputFile.getAbsolutePath()));
            while ((outLine = bufferedReader.readLine()) != null) {
                outputValues.add(outLine);
            }

            outputValues.remove(0);

            for (String expectedValue:outputValues) {
                String[] exp = expectedValue.split(",");
                List<String> list = Arrays.asList(exp);
                expectedMap.put(list.get(0),list);
            }
        } catch (Exception e) {
            System.err.println("Exception occured while retrieve car output details: " + e.getMessage());
        } finally {
            bufferedReader.close();
        }
        return  expectedMap;
    }

    private static File getFile(String filepath) {
        return new File(FileReader.class.getClassLoader().getResource(filepath).getFile());
    }
}

