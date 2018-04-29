import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Ekaterina Yashkina on 11-10-2017.
 */
public class Main {
    /**
     * Clears all blank spaces in expression, pastes "(" symbol before "and" to maintain order
     * of calculations, checks for negative numbers and adds "0" before "-" to get expression
     * that has left and right parts
     * @param a - string to change
     * @return
     */
    private static String prepareExpression(String a){
        a = a.replaceAll(" ", "");//deletes spaces
        String buffer = a;
        a="";
        //loop replaces "-" in negative numbers to "0-" to simplify future calculations
        while(buffer!=""){
            if (buffer.contains("-")) {
                int position = buffer.indexOf("-");
                if(position==0 || !(buffer.charAt(position-1)+"").matches("\\d")){
                    buffer = buffer.replaceFirst("-", "0-");
                }
                a+=buffer.substring(0, buffer.indexOf("-")+1);
                buffer = buffer.substring(buffer.indexOf("-")+1);
            }else {
                a+=buffer;
                buffer="";
            }
        }
        //pastes "(" before "and" for automatically creating objects in right order
        a = a.replaceAll("or", "ori");
        a = a.replaceAll("xor", "xory");
        int or = -1, and = -1, xor = -1;
        if (a.contains("ori"))  or = a.indexOf("o");
        if (a.contains("and")) and = a.indexOf("a");
        if (a.contains("xory")) xor = a.indexOf("x");
        if (and>or && and!=-1 && or!=-1) a = a.replace("i", "(");
        if (and>xor && and!=-1 && xor!=-1) a = a.replace("y", "(");
        a = a.replaceAll("i", "");
        a = a.replaceAll("y", "");
        return a;
    }

    /**
     * Reading from the file
     * @return
     */
    private static String readFile() {
        try {
            String a;
            Scanner sc = new Scanner(new File("input.txt"));
            if (sc.hasNextLine()) a = sc.nextLine();
            else {return "";}
            a = prepareExpression(a);
            return a;
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * Writing to the file
     * @param number
     */
    private static void writeFile(String number, String name) {
        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(name), "ascii"));
            writer.write(number);
            writer.close();
        } catch (IOException ex) {

        }
    }

    public static void main(String[] args) {
        System.out.println("Print 1, if you want read expression from the file 'input.txt'(but check first that you have file)");
        System.out.println("Print 2, if you want to read expression from the console");
        Scanner in = new Scanner(System.in);
        String input;
        int number = Integer.parseInt(in.nextLine());
        while (true){
            if (number == 1){
                input = readFile();
                System.out.println("Your output will be written to file 'output.txt'");
                if (input=="") writeFile("", "output.txt");
                else {
                    Parser parser = new Parser(input);
                    try {
                        Logical parsed = parser.parse();
                        String a = parsed.calculate();
                        writeFile(a, "output.txt");
                        System.out.println("Serialized file in json format will be written to 'serialized.json' ");
                        writeFile(parsed.toJson(0), "serialized.json");
                        //System.out.println(a);
                        //System.out.println(parsed.toJson(0));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                break;
            }

            else if (number == 2){
                input = in.nextLine();
                input = prepareExpression(input);
                Parser parser = new Parser(input);
                try {
                    Logical parsed = parser.parse();
                    String a = parsed.calculate();
                    System.out.println(a);
                    System.out.println("Serialized file in json format will be written to 'serialized.json' ");
                    writeFile(parsed.toJson(0), "serialized.json");
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
            else System.out.println("Invalid input. Try again: ");
            number = Integer.parseInt(in.nextLine());
        }

        }
}
