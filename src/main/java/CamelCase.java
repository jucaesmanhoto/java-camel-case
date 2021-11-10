import exceptions.InvalidEntryException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCase {

    public List<String> converterCamelCase(String original){

        checkForException(original);

        List<String> results = new ArrayList<>();
        if (hasOnlyUpperCase(original)){
            results.add(original);
        }else{
            results = makeListOfStrings(original);
        }

        return results;
    }

    private void checkForException(String original){
        if (Character.isDigit(original.charAt(0)))
            throw new InvalidEntryException("Must not start with a number.");
        if (hasSpecialCharacters(original))
            throw new InvalidEntryException("Must not contains any special characters.");
    }

    private boolean hasSpecialCharacters(String original){
        Pattern specialChars = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = specialChars.matcher(original);
        return hasSpecial.find();
    }

    private boolean hasOnlyUpperCase(String original) {
        return original.toUpperCase().equals(original);
    }

    private List<String> makeListOfStrings(String original){
        char[] characters = original.toCharArray();
        List<String> results = new ArrayList<>();

        List<Integer> newWordStartIndexes = getNewWordStartIndexes(characters);

        if (!newWordStartIndexes.isEmpty())
            results = splitStrings(original, newWordStartIndexes);
        else
            results.add(original.toLowerCase());

        return results;
    }

    private boolean isTheBeginOfNewWord(char lastChar, char thisChar, char nextChar){
        boolean isLastDigit = Character.isDigit(lastChar);
        boolean isThisDigit = Character.isDigit(thisChar);
        boolean isLastUpCased = Character.isUpperCase(lastChar);
        boolean isThisUpCased = Character.isUpperCase(thisChar);
        boolean isLastDownCased = Character.isLowerCase(lastChar);
        boolean isThisDownCased = Character.isLowerCase(thisChar);
        boolean isNextDownCased = Character.isLowerCase(nextChar);

        return !isThisDownCased && ((isLastDownCased && isThisUpCased) || (isLastUpCased && isThisDigit) ||
                (isLastDownCased && isThisDigit) || (isLastDigit && isThisUpCased) || (isThisUpCased && isNextDownCased));
    }

    private List<Integer> getNewWordStartIndexes(char[] characters){
        List<Integer> results = new ArrayList<>();

        // the first word always start on index 0
        results.add(0);

        for (int i = 0; i < characters.length; i++) {
            if (i != 0 && i < characters.length-1
                    && isTheBeginOfNewWord(characters[i-1], characters[i], characters[i+1])){
                results.add(i);
            }
        }

        return results;
    }

    private List<String> splitStrings(String original, List<Integer> substringIndexes){
        List<String> results = new ArrayList<>();
        String substring;

        for (int i = 0; i < substringIndexes.size(); i++){
            if (i == substringIndexes.size() - 1)
                substring = original.substring(substringIndexes.get(i));
            else
                substring = original.substring(substringIndexes.get(i), substringIndexes.get(i+1));
            results.add(hasOnlyUpperCase(substring) ? substring : substring.toLowerCase());
        }
        return results;
    }
}
