import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PolynomialSolver {

    // Convert base-N string to decimal
    static long convertToDecimal(String value, int base) {
        return Long.parseLong(value, base);
    }

    // Build polynomial coefficients from roots
    static long[] buildPolynomial(List<Long> roots) {
        int m = roots.size();
        long[] coeff = new long[m + 1];
        coeff[0] = 1;  // leading coefficient

        for (long r : roots) {
            long[] newCoeff = new long[coeff.length];
            for (int i = 0; i < coeff.length - 1; i++) {
                newCoeff[i] += coeff[i];
                newCoeff[i + 1] -= coeff[i] * r;
            }
            coeff = newCoeff;
        }
        return coeff;
    }

    // Solve one test case JSON file
    static void solveCase(String fileName) throws Exception {
        // Read JSON
        String jsonStr = new String(Files.readAllBytes(Paths.get(fileName)));
        JSONObject obj = new JSONObject(jsonStr);

        JSONObject keys = obj.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        int degree = k - 1; // polynomial degree

        // Extract exactly (k-1) roots: 1,2,...,(k-1)
        List<Long> roots = new ArrayList<>();
        for (int i = 1; i <= degree; i++) {
            JSONObject rootObj = obj.getJSONObject(String.valueOf(i));
            int base = Integer.parseInt(rootObj.getString("base"));
            String value = rootObj.getString("value");
            long root = convertToDecimal(value, base);
            roots.add(root);
        }

        // Build polynomial
        long[] coeff = buildPolynomial(roots);

        // Print results
        System.out.println("File: " + fileName);
        System.out.println("Roots selected: " + roots);
        System.out.println("Polynomial coefficients: " + Arrays.toString(coeff));
        System.out.println("Constant term c = " + coeff[coeff.length - 1]);
        System.out.println("---------------------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        solveCase("testcase1.json");
        solveCase("testcase2.json");
    }
}
