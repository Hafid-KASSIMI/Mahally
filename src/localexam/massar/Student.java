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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import org.sicut.util.Misc;

/**
 *
 * @author Sicut
 */
public final class Student {
        
    private String code, name, gender, group, levelFull, levelAbrev, birthDate;
    private Integer numero, groupIndex, examCode, room;
    private Long age, daysAge;
    private Integer RAND_ID;

    public Student() {
        
    }

    public Student(Integer numero, String code, String name, String gender, String group, String levelFull) {
        this();
        this.numero = numero;
        this.code = code;
        setName(name);
        setGender(gender);
        setGroup(group);
        setLevelFull(levelFull);
        try {
            groupIndex = Integer.parseInt(Misc.getGroupIndex(group));
        } catch ( NumberFormatException nfe ) { 
            groupIndex = 0;
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Misc.justLetters(name);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = Misc.justLetters(gender);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
        levelAbrev = Misc.getLevelAbrev(group);
    }

    public String getLevelFull() {
        return levelFull;
    }

    public void setLevelFull(String levelFull) {
        this.levelFull = Misc.justLetters(levelFull);
    }

    public String getLevelAbrev() {
        return levelAbrev;
    }

    public void setLevelAbrev(String levelAbrev) {
        this.levelAbrev = levelAbrev;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Integer getGroupIndex() {
        return groupIndex;
    }

    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public boolean isFemale() {
        return gender.matches("(F)|(Female)|(Fille)|(أنثى)");
    }

    public Integer getRAND_ID() {
        return RAND_ID;
    }

    public void setRAND_ID(Integer RAND_ID) {
        this.RAND_ID = RAND_ID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        LocalDate dt;
        try {
            dt = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/uuuu"));
            age = ChronoUnit.YEARS.between(dt, LocalDate.now());
            daysAge = ChronoUnit.DAYS.between(dt, LocalDate.now());
        } catch ( DateTimeParseException ex ) {
            dt = LocalDate.now();
            age = 0L;
            daysAge = 0L;
        }
        this.birthDate = dt.format(DateTimeFormatter.ofPattern("uuuu/MM/dd"));
    }

    public Long getAge() {
        return age;
    }

    public Long getDaysAge() {
        return daysAge;
    }
    
}
