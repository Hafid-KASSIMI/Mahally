/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam.template.label;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.sicut.util.ALIGNMENT;
import org.sicut.util.pdf.BasePDFFile;
import static localexam.Settings.PREF_BUNDLE;
import static localexam.Settings.SCHOOL;
import static localexam.Settings.SORTED_STUDENTS;

/**
 *
 * @author Sicut
 */
public class LabelPDFFile extends BasePDFFile {
    
    private LabelPage METRICS;
    private final DecimalFormat INTEGER;
    Map<Integer, Double> map;
    private PDPage pg;
    private int counter, mod;
    
    public LabelPDFFile() {
        super();
        METRICS = null;
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/localexam/template/resources/LABEL_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        reset();
        counter = 0;
        if ( METRICS == null )
            METRICS = new LabelPage();
        SORTED_STUDENTS.forEach(stu -> {
            mod = counter % METRICS.getSIZE();
            if ( mod == 0 ) {
                pg = clonePage(0);
            }
            try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                placeMultilineString(METRICS.getMinisteries()[mod], pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
                placeMultilineString(METRICS.getSchoolInfos()[mod], pcs, SCHOOL.getAcademy() + "\n" + SCHOOL.getDirection() + "\n" + SCHOOL.getSchool(), ALIGNMENT.RIGHT);
                placeString(METRICS.getCodes()[mod], pcs, stu.getExamCode() + "");
                placeString(METRICS.getNames()[mod], pcs, stu.getName());
                placeString(METRICS.getRooms()[mod], pcs, stu.getRoom() + "", ALIGNMENT.RIGHT);
            } catch (IOException | IllegalArgumentException | CloneNotSupportedException | ArabicShapingException ex) { } 
            counter++;
        });
        removePage(0);
        return ( doc.getPages().getCount() > 0 ) ? 
            save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("Label-", "pdf")) : false;
    }
}
