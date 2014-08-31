/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.regex.Pattern;

/**
 *
 * @author tomwilkins
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String line = "print \"Hello\" + v + \"World\"";
        StringBuilder sb;
        
        // splits the line on \"
        String[] code = line.split("print");
        /*
        // every 2nd word split on a \" will be a string
        if(code.length > 0){
            for(int i =1; i < code.length; i=i+2){
                code[i] = "\""+code[i]+"\"";
            }
        }*/
            
        for (String word : code){
            System.out.println(word);
        }
            
    }
}
