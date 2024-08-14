package org.super89.supermegamod.testnewfeautres.Utils;

public class MathUtils {
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
