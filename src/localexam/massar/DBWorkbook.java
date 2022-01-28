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

package localexam.massar;


import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Surfer
 * @updated by Sicut
 */
public final class DBWorkbook {
    private final int firstRow;
    private int sheetsNumber;
    private final String classRef, dirRef, acadRef, schoolRef, yearRef, levRef;
    private final String numCol, codeCol, fNameCol, sNameCol, genderCol, bDateCol;
    private final ArrayList<Integer> validSheets;
    private HSSFWorkbook wb;
    private HSSFSheet sht;
            
    public DBWorkbook(){
        firstRow = 16;
        codeCol = "X";
        numCol = "AA";
        fNameCol = "M";
        sNameCol = "Q";
        genderCol = "L";
        bDateCol = "F";
        classRef = "I11";
        dirRef = "U8";
        acadRef = "T6";
        schoolRef = "H8";
        yearRef = "C6";
        levRef = "T10";
        validSheets = new ArrayList();
        sht = null;
    }
    
    public boolean setWorkbook(File f){
        validSheets.clear();
        try {
            wb = new HSSFWorkbook(new FileInputStream(f));
        } catch (IOException ex) {
            wb = null;
            return false;
        }
        return isItFromMassar();
    }
    
    private void loadValidSheets(){
        sheetsNumber = wb.getNumberOfSheets();
        for ( int i = 0; i < sheetsNumber; i++ ) {
            if ( isFromMassar( wb.getSheetAt(i) ) )
                validSheets.add(i);
        }  
    }
    
    private boolean isItFromMassar(){
        loadValidSheets();
        return ( validSheets.size() > 0 );
    }
    
    private boolean isFromMassar(HSSFSheet sh){
        return (sh == null)?false:(
                    getStringValue(sh, "E6").contains("السنة الدراسية") &&
                    getStringValue(sh, "K3").contains("لائحة التلاميذ ")
                );
    }

    public boolean isValid() {
        return ( validSheets.size() > 0 );
    }
    
    public boolean isNull(){
        return (wb == null);
    }
    
    public String getStringValue(HSSFSheet sht, String cellRef){
        CellReference cr;
        DataFormatter df;
        cr = new CellReference(cellRef);
        df = new DataFormatter();
        try {
            return df.formatCellValue(sht.getRow(cr.getRow()).getCell(cr.getCol()));
        }
        catch (NullPointerException ex) {
            return "";
        }
    }
    
    public void load(School school, ArrayList<Student> students) {
        if ( validSheets.isEmpty() )  return;
        sht = wb.getSheetAt(validSheets.get(0));
        school.setInfos(getStringValue(sht, acadRef), getStringValue(sht, dirRef), 
                getStringValue(sht, schoolRef), getStringValue(sht, yearRef));
        String code;
        int num, row;
        Random rand = new Random();
        for( int i : validSheets ) {
            row = firstRow;
            String cls, levName;
            sht = wb.getSheetAt(i);
            cls = getStringValue(sht, classRef);
            levName = getStringValue(sht, levRef);
            num = 1;
            while( !(code = getStringValue(sht, codeCol + row)).isEmpty()) {
                Student stu = new Student();
                try {
                    stu.setNumero(Integer.parseInt(getStringValue(sht, numCol + row)));
                }
                catch(NumberFormatException e) {
                    stu.setNumero(num++);
                }
                stu.setCode(code);
                stu.setName(getStringValue(sht, fNameCol + row) + " " +
                        getStringValue(sht, sNameCol + row));
                stu.setGender(getStringValue(sht, genderCol + row));
                stu.setGroup(cls);
                stu.setLevelFull(levName);
                stu.setBirthDate(getStringValue(sht, bDateCol + row));
                stu.setRAND_ID(rand.nextInt());
                students.add(stu);
                row++;
            }
        }
    }
    
}
