import java.util.*;

public class NumberOfAtoms {

    public static String countOfAtoms(String formula) {
        // The stack will hold a map of counts for each nested scope (parenthesis).
        Deque<Map<String, Integer>> stack = new ArrayDeque<>();
        // The currentMap holds the counts for the scope we are currently in.
        Map<String, Integer> currentMap = new TreeMap<>();
        
        int i = 0;
        int n = formula.length();

        while (i < n) {
            char c = formula.charAt(i);

            if (c == '(') {
                // Start of a new scope. Push the current map to the stack to "save" it.
                stack.push(currentMap);
                // Start a new, empty map for the nested scope.
                currentMap = new TreeMap<>();
                i++;
            } else if (c == ')') {
                // End of a scope.
                i++;
                
                // 1. Parse the multiplier after the ')'
                int multiplier = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    multiplier = multiplier * 10 + (formula.charAt(i) - '0');
                    i++;
                }
                if (multiplier == 0) {
                    multiplier = 1; // Default multiplier is 1
                }

                // 2. Apply the multiplier to all atoms in the current scope's map.
                if (multiplier > 1) {
                    for (String atom : currentMap.keySet()) {
                        currentMap.put(atom, currentMap.get(atom) * multiplier);
                    }
                }

                // 3. Merge the inner scope's map back into the outer scope's map.
                Map<String, Integer> outerMap = stack.pop();
                for (String atom : currentMap.keySet()) {
                    outerMap.put(atom, outerMap.getOrDefault(atom, 0) + currentMap.get(atom));
                }
                // The outer scope's map is now our current map.
                currentMap = outerMap;
                
            } else {
                // This must be an atom name.
                // 1. Parse the full atom name (e.g., "Mg", "H").
                StringBuilder atomName = new StringBuilder();
                atomName.append(c);
                i++;
                while (i < n && Character.isLowerCase(formula.charAt(i))) {
                    atomName.append(formula.charAt(i));
                    i++;
                }

                // 2. Parse the count for this atom.
                int count = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    count = count * 10 + (formula.charAt(i) - '0');
                    i++;
                }
                if (count == 0) {
                    count = 1; // Default count is 1
                }

                // 3. Add the atom and its count to the current map.
                currentMap.put(atomName.toString(), 
                               currentMap.getOrDefault(atomName.toString(), 0) + count);
            }
        }

        // After the loop, format the final map into the result string.
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : currentMap.entrySet()) {
            result.append(entry.getKey());
            if (entry.getValue() > 1) {
                result.append(entry.getValue());
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(countOfAtoms("H2O"));            // H2O
        System.out.println(countOfAtoms("Mg(OH)2"));        // H2MgO2
        System.out.println(countOfAtoms("K4(ON(SO3)2)2"));  // K4N2O14S4
    }
}