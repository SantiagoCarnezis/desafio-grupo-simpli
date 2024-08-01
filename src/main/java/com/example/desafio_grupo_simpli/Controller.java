package com.example.desafio_grupo_simpli;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Objects;

@RestController
public class Controller {

    private static final String NOT_POSSIBLE = "not possible";
    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final String COMA = ",";
    private static final String YES = "yes";

    @PostMapping("/array")
    public String array(@RequestBody int[] arr) {

//        int[] arr = {1,2, //1
//                4,3, //3
//                2,2,
//                2,2,
//                1,1,
//                6,7, //5
//                3,2, //7
//                2,1, //9
//                4,3, //11
//                1,3, //13
//                3,1, //15
//                1,1,
//                7,6, //17
//                2,3}; //19

        LinkedList<Integer> indexesProcessed = new LinkedList<>();
        StringBuilder pairsNotFound = new StringBuilder();
        int index = 1; //starts in position 2 [0, 1]
        int comparatorIndex;
        boolean pairWasFound;
        int pairToFound1;
        int pairToFound2;

        while (index <= arr.length) {

            if (!indexesProcessed.contains(index)) {

                pairToFound1 = arr[index - 1];
                pairToFound2 = arr[index];
                comparatorIndex = index + 2;
                pairWasFound = false;

                while (comparatorIndex <= arr.length && !pairWasFound) {

                    if (indexesProcessed.contains(comparatorIndex))
                        comparatorIndex = comparatorIndex + 2;
                    else if (pairToFound1 == arr[comparatorIndex] && pairToFound2 == arr[comparatorIndex - 1]) {
                        pairWasFound = true;
                        indexesProcessed.add(comparatorIndex);
                    }
                    else
                        comparatorIndex = comparatorIndex + 2;
                }

                if (!pairWasFound) {
                    pairsNotFound.append(arr[index - 1]);
                    pairsNotFound.append(COMA);
                    pairsNotFound.append(arr[index]);
                    pairsNotFound.append(COMA);
                }
            }
            index = index + 2;
        }

        if (pairsNotFound.isEmpty())
            return YES;
        else {
            pairsNotFound.deleteCharAt(pairsNotFound.length() - 1);
            return pairsNotFound.toString();
        }
    }

    @PostMapping("/num/{number}")
    public String num(@PathVariable int number) {

        //int num = 26712; // -+-- and not +-+-
        LinkedList<Integer> digits = getDigits(number);
        int total = digits.stream().mapToInt(Integer::intValue).sum();

        if(total%2 == 0) {

            int half = total / 2;

            String combination = NOT_POSSIBLE;
            String combinationAux = null;

            long maxPlus = digits.size() - 1;

            while (!Objects.equals(combinationAux, NOT_POSSIBLE)) {

                combinationAux = getCombination(digits.get(0), digits, half, 0,(int) maxPlus, 1, new StringBuilder());
                if(!Objects.equals(combinationAux, NOT_POSSIBLE)) {
                    combination = combinationAux;
                    maxPlus = combination.chars().filter(c -> c == PLUS).count() - 1;
                }
            }

            return combination;
        }
        else {
            return NOT_POSSIBLE;
        }
    }

    private String getCombination(int accumulated, LinkedList<Integer> digits, int finalValue,
                                  int amountPlus, int maxPlus, int index, StringBuilder stringBuilder) {

        int accumulatedAux;

        while(index < digits.size() && !(accumulated == finalValue) && (amountPlus <= maxPlus)) {

            accumulatedAux = accumulated + digits.get(index);
            if(amountPlus == maxPlus) {

                if (accumulatedAux == finalValue) {

                    accumulated = accumulatedAux;
                    stringBuilder.append(PLUS);
                    break;
                }
                else
                    break;
            }
            else if(accumulatedAux > finalValue) {
                index ++;
                stringBuilder.append(MINUS);
            }
            else if (accumulatedAux == finalValue) {

                stringBuilder.append(PLUS);
                accumulated = accumulatedAux;
            }
            else {
                accumulated = accumulatedAux;
                index ++;
                stringBuilder.append(PLUS);
                amountPlus ++;
            }
        }

        if(index >= digits.size() || (amountPlus == maxPlus && !(accumulated == finalValue))) {
            index = stringBuilder.lastIndexOf(String.valueOf(PLUS));

            if (index < 0)
                return NOT_POSSIBLE;
            else {
                stringBuilder.delete(index, stringBuilder.length());
                stringBuilder.append(MINUS);
                return getCombination(accumulated - digits.get(index + 1), digits, finalValue,
                        amountPlus, maxPlus, index + 2, stringBuilder);
            }
        }
        else {
            completeWithMinus(digits, stringBuilder);
            return stringBuilder.toString();
        }
    }

    private void completeWithMinus(LinkedList<Integer> digits, StringBuilder stringBuilder) {
        int c = digits.size() - 1 - stringBuilder.length();
        stringBuilder.append(String.valueOf(MINUS).repeat(c));
    }


    public LinkedList<Integer> getDigits(int num) {

        String numStr = String.valueOf(num);
        LinkedList<Integer> digits = new LinkedList<>();
        for (int i = 0; i < numStr.length(); i++) {

            int value = Character.getNumericValue(numStr.charAt(i));
            digits.add(value);
        }

        return digits;
    }
}
