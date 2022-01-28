/*
 * Copyright (C) 2020 Sicut
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/* 
    Author     : H. KASSIMI
*/

package org.sicut.util;

import java.util.ArrayList;
import java.util.HashMap;

public final class Misc {
    
    public static String getLevelAbrev(String groupName) {
        return ( "".equals(groupName) || groupName == null ) ? 
                "" : groupName.substring(0, groupName.lastIndexOf("-"));
    }
    
    public static String getGroupIndex(String groupName) {
        return ( "".equals(groupName) || groupName == null ) ? 
                "" : groupName.substring(groupName.lastIndexOf("-") + 1);
    }
    
    public static Integer getGroupWeight(String groupName) {
        if ( "".equals(groupName) || groupName == null )
            return 0;
        String levels = "1A2A3ATC1B2B";
        return levels.indexOf(groupName.substring(0, 2)) * 200 + getIntGroupIndex(groupName);
    }
    
    public static Integer getLevelWeight(String groupName) {
        if ( "".equals(groupName) || groupName == null )
            return 0;
        String levels = "1A2A3ATC1B2B";
        return levels.indexOf(groupName.substring(0, 2)) / 2;
    }
    
    public static Integer getIntGroupIndex(String groupName) {
        try {
            return Integer.parseInt(getGroupIndex(groupName));
        } catch ( NumberFormatException nfe ) {
            return 0;
        }
    }
    
    public static Boolean isArabic(String text) {
        return text.matches(".*[أبجدهـوزحطيكلمنسعفصقرشتثخذضظغ].*");
    }
    
    public static String cleanReversedAR(String text) {
        return text.replace("(", "%OP%").replace("[", "%OB%").replace("{", "%OC%").replace(")", "(").replace("]", "[").replace("}", "{").replace("%OP%", ")").replace("%OB%", "]").replace("%OC%", "}");
    }
    
    public static String justLetters(String arText) {
        return arText.replaceAll("[ًٌٍَُِّْـ]", "");
    }
    
    public static ArrayList<Integer> getSemiEvenDivArray(int n, int max) {
        ArrayList<Integer> arr = new ArrayList();
        int countMin, countMax, tmp = max + 1;
        if ( n % max == 0 ) {
            countMax = n / max;
            countMin = 0;
            tmp--;
        }
        else {
            do {
                tmp--;
                countMin  = tmp * ( n / tmp + 1) - n; // Count min
                countMax = n / tmp + 1 - countMin; // count max
            } while ( countMax < 0 );
        }
        for ( int i = 0, k = tmp - 1; i < countMin; i++ ) {
            arr.add(k);
        }
        for ( int i = 0; i < countMax; i++ ) {
            arr.add(tmp);
        }
        return arr;
    }
    
    public static HashMap<String, Integer> getSemiEvenDivStats(int n, int max) {
        HashMap<String, Integer> map = new HashMap();
        int countMin, countMax, tmp = max + 1;
        if ( n % max == 0 ) {
            countMax = n / max;
            countMin = 0;
            tmp--;
        }
        else {
            do {
                tmp--;
                countMin  = tmp * ( n / tmp + 1) - n; // Count min
                countMax = n / tmp + 1 - countMin; // count max
            } while ( countMax < 0 );
        }
        map.put("min", tmp - 1);
        map.put("nMin", countMin);
        map.put("max", tmp);
        map.put("nMax", countMax);
        return map;
    }
}
