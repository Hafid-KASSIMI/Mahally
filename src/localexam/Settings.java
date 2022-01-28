/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam;

import java.util.ArrayList;
import localexam.massar.School;
import org.sicut.db.Preferences;
import org.sicut.util.EnvVariable;
import localexam.massar.Student;

/**
 *
 * @author Sicut
 */
public class Settings {
    public static final String DB_FOLDER_PATH = EnvVariable.APPDATADirectory() + "/Local-Exam/";
    public static final String PREF_DB_NAME = "preferences.pvs";
    public static final String PREF_DB_PATH = DB_FOLDER_PATH + PREF_DB_NAME;
    
    public static final String APP_NAME = "محلي";
    public static final String APP_TITLE = "إعداد لوائح المترشحين";
    public static final String APP_YEAR = "2022";
    public static final String APP_VERSION = "0.0.3";
    public static final String APP_DATE = "28/01/2022";
    
    public static Preferences PREF_BUNDLE;
    public static final ArrayList<Student> STUDENTS = new ArrayList();
    public static final ArrayList<Student> SORTED_STUDENTS = new ArrayList();
    public static final ArrayList<Integer> DIVISIONS = new ArrayList();
    public static final School SCHOOL = new School();
}
