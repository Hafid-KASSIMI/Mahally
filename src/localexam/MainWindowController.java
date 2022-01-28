/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localexam;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import static org.sicut.util.EnvVariable.HOMEDirectory;
import static localexam.Settings.*;
import localexam.massar.DBWorkbook;
import localexam.template.call.CallPDFFile;
import localexam.template.card.CardPDFFile;
import localexam.template.label.LabelPDFFile;
import localexam.template.list.CandsList1PDFFile;
import localexam.template.list.CandsListPDFFile;
import localexam.template.pv.CandsPVPDFFile;
import org.sicut.util.Misc;

/**
 *
 * @author Sicut
 */
public class MainWindowController implements Initializable {

    @FXML
    private ComboBox<String> levelCB, dayCB;
    @FXML
    private Spinner<Integer> maxCandsSP, firstCodeSP, firstRoomSP;
    @FXML
    private CheckBox listsCHB, pvsCHB, lablsCHB, lists1CHB, callsCHB, techCHB;
    @FXML
    private Button generateBtn, selectDbBtn, addMatterBtn, delMatterBtn;
    @FXML
    private Label appName, buildInfos, allStusLbl, levStusLbl, roomsLbl, rooms1DetLbl, rooms2DetLbl, appInfo;
    @FXML
    private MenuItem outputMI, quitMI, mattersMI, aboutMI;
    @FXML
    private Pane overlayP;
    @FXML
    private VBox mattersVB, genVB, aboutVB;
    @FXML
    private ComboBox<String> matter1CB, matter2CB, matter3CB, matter4CB;
    @FXML
    private ComboBox<String> matter5CB, matter6CB, matter7CB, matter8CB;
    @FXML
    private ComboBox<String> matter9CB, matter10CB, matter11CB, matter12CB;
    @FXML
    private ComboBox<String> matter13CB, matter14CB, editMatCB;
    @FXML
    private RadioMenuItem noOrderRMI, codeDescOrderRMI, codeAscOrderRMI;
    @FXML
    private RadioMenuItem nameAscOrderRMI, nameDescOrderRMI, genAscOrderRMI;
    @FXML
    private RadioMenuItem genDescOrderRMI, ageAscOrderRMI, ageDescOrderRMI, randOrderRMI;

    private final FileChooser fc = new FileChooser();
    private final DirectoryChooser dc = new DirectoryChooser();
    private final SimpleBooleanProperty LOADING_STATE = new SimpleBooleanProperty();
    private final SimpleBooleanProperty GENERATION_STATE = new SimpleBooleanProperty();
    private final CandsListPDFFile candsList;
    private final CandsList1PDFFile candsList1;
    private final CandsPVPDFFile candsPv;
    private final LabelPDFFile labels;
    private final CallPDFFile calls;
    private final CardPDFFile cards;
    private VBox currentNotifier;
    private final Duration SM_ANIMATION_DURATION = Duration.seconds(0.7);
    private Boolean mattersShown;
    private EventHandler eh;

    public MainWindowController() {
        candsList = new CandsListPDFFile();
        candsList1 = new CandsList1PDFFile();
        candsPv = new CandsPVPDFFile();
        labels = new LabelPDFFile();
        calls = new CallPDFFile();
        cards = new CardPDFFile();
        mattersShown = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 20, 1);
        SpinnerValueFactory<Integer> svf2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 1, 10);
        SpinnerValueFactory<Integer> svf3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 1, 10);
        TextFormatter tf1 = new TextFormatter(svf1.getConverter(), svf1.getValue());
        TextFormatter tf2 = new TextFormatter(svf2.getConverter(), svf2.getValue());
        TextFormatter tf3 = new TextFormatter(svf3.getConverter(), svf3.getValue());
        ComboBox<String>[] matsCBs = new ComboBox[]{matter1CB, matter2CB, matter3CB, matter4CB,
            matter5CB, matter6CB, matter7CB, matter8CB,
            matter9CB, matter10CB, matter11CB, matter12CB, matter13CB, matter14CB, editMatCB};
        svf1.valueProperty().bindBidirectional(tf1.valueProperty());
        svf2.valueProperty().bindBidirectional(tf2.valueProperty());
        svf3.valueProperty().bindBidirectional(tf3.valueProperty());
        maxCandsSP.setValueFactory(svf1);
        firstCodeSP.setValueFactory(svf2);
        firstRoomSP.setValueFactory(svf3);
        maxCandsSP.getEditor().setTextFormatter(tf1);
        firstCodeSP.getEditor().setTextFormatter(tf2);
        firstRoomSP.getEditor().setTextFormatter(tf3);
        File output_dir = new File(PREF_BUNDLE.get("OUTPUT_DIR"));
        if (output_dir.exists()) {
            dc.setInitialDirectory(output_dir);
        } else {
            PREF_BUNDLE.update("OUTPUT_DIR", HOMEDirectory());
        }
        Locale.setDefault(new Locale("ar", "AR"));
        try {
            svf1.setValue(Integer.valueOf(PREF_BUNDLE.get("MAX_CANDS_PER_ROOM")));
        } catch (NumberFormatException nfe) {
        }
        try {
            svf2.setValue(Integer.valueOf(PREF_BUNDLE.get("FIRST_EXAM_CODE")));
        } catch (NumberFormatException nfe) {
        }
        try {
            svf3.setValue(Integer.valueOf(PREF_BUNDLE.get("FIRST_ROOM")));
        } catch (NumberFormatException nfe) {
        }

        noOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_NO_ORDER")));
        codeDescOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_CODE_DESC")));
        codeAscOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_CODE_ASC")));
        nameAscOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_NAME_ASC")));
        nameDescOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_NAME_DESC")));
        genAscOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_GENDER_ASC")));
        genDescOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_GENDER_DESC")));
        ageAscOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_AGE_ASC")));
        ageDescOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_GENDER_DESC")));
        randOrderRMI.setSelected("1".equals(PREF_BUNDLE.get("LIST_SORT_RANDOM_ORDER")));

        noOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_NO_ORDER", cur ? "1" : "0");
        });
        codeDescOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_CODE_DESC", cur ? "1" : "0");
        });
        codeAscOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_CODE_ASC", cur ? "1" : "0");
        });
        nameAscOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_NAME_ASC", cur ? "1" : "0");
        });
        nameDescOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_NAME_DESC", cur ? "1" : "0");
        });
        genAscOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_GENDER_ASC", cur ? "1" : "0");
        });
        genDescOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_GENDER_DESC", cur ? "1" : "0");
        });
        ageAscOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_AGE_ASC", cur ? "1" : "0");
        });
        ageDescOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_GENDER_DESC", cur ? "1" : "0");
        });
        randOrderRMI.selectedProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("LIST_SORT_RANDOM_ORDER", cur ? "1" : "0");
        });
        
        appInfo.setText(appInfo.getText().replace("_version_", Settings.APP_VERSION));
        
        listsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_LISTS")));
        lists1CHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_LISTS1")));
        pvsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_PVS")));
        lablsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_LABELS")));
        callsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_CALLS")));
        techCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_CARDS")));
        levelCB.getSelectionModel().selectedItemProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("SELECTED_LEVEL", cur);
            refreshStats();
        });
        levelCB.getSelectionModel().select(PREF_BUNDLE.get("SELECTED_LEVEL"));
        dayCB.getSelectionModel().selectedIndexProperty().addListener((obs, old, cur) -> {
            if (old.intValue() >= 0) {
                for (int i = 0, n = old.intValue(); i < matsCBs.length; i++) {
                    PREF_BUNDLE.update("DAY_" + n + "_MAT_" + i, matsCBs[i].getValue());
                }
            }
            for (int i = 0, n = cur.intValue(); i < matsCBs.length; i++) {
                matsCBs[i].getSelectionModel().select(PREF_BUNDLE.get("DAY_" + n + "_MAT_" + i));
            }
            PREF_BUNDLE.update("SELECTED_DAY", dayCB.getSelectionModel().getSelectedItem());
        });
        mattersVB.visibleProperty().addListener((obs, old, cur) -> {
            if (!cur && !dayCB.getSelectionModel().isEmpty()) {
                for (int i = 0; i < matsCBs.length; i++) {
                    PREF_BUNDLE.update("DAY_" + dayCB.getSelectionModel().getSelectedIndex() + "_MAT_" + i, matsCBs[i].getValue());
                }
            } else if (!mattersShown) {
                mattersShown = true;
                String[] arr = PREF_BUNDLE.get("MATTERS").split("_MAT_");
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().add("");
                    matsCB.getItems().addAll(arr);
                }
                dayCB.getItems().addAll(PREF_BUNDLE.get("DAYS").split("_DAY_"));
                dayCB.setValue(PREF_BUNDLE.get("SELECTED_DAY"));
            }

        });

        generateBtn.setOnAction(evt -> {
            if (!listsCHB.isSelected() && !lists1CHB.isSelected() && !pvsCHB.isSelected() && 
                    !lablsCHB.isSelected() && !callsCHB.isSelected() && !techCHB.isSelected()) {
                return;
            }
            PREF_BUNDLE.update("MAX_CANDS_PER_ROOM", svf1.getValue().toString());
            PREF_BUNDLE.update("FIRST_EXAM_CODE", svf2.getValue().toString());
            PREF_BUNDLE.update("FIRST_ROOM", svf3.getValue().toString());
            PREF_BUNDLE.update("SELECTED_LEVEL", levelCB.getValue());
            PREF_BUNDLE.update("GENERATE_LISTS", listsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_LISTS1", lists1CHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_PVS", pvsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_LABELS", lablsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_CALLS", callsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_CARDS", techCHB.isSelected() ? "1" : "0");
            GENERATION_STATE.set(false);
            showOverlay(true);
            genVB.setVisible(true);
            sortStudentsArray();
            DIVISIONS.clear();
            DIVISIONS.addAll(Misc.getSemiEvenDivArray(SORTED_STUDENTS.size(), svf1.getValue()));
            for (int i = 0, n = DIVISIONS.size(), counter = 0, cands, room; i < n; i++) {
                cands = DIVISIONS.get(i);
                room = svf3.getValue() + i;
                for (int j = 0; j < cands; j++, counter++) {
                    SORTED_STUDENTS.get(counter).setRoom(room);
                    SORTED_STUDENTS.get(counter).setExamCode(counter + svf2.getValue());
                }
            }
            DIVISIONS.clear();
            new Thread(() -> {
                if (listsCHB.isSelected()) {
                    candsList.generate();
                }
                if (lists1CHB.isSelected()) {
                    candsList1.generate();
                }
                if (lablsCHB.isSelected()) {
                    labels.generate();
                }
                if (pvsCHB.isSelected()) {
                    candsPv.generate();
                }
                if (callsCHB.isSelected()) {
                    calls.generate();
                }
                if (techCHB.isSelected()) {
                    cards.generate();
                }
                GENERATION_STATE.set(true);
            }).start();
        });

        GENERATION_STATE.addListener((obs, old, cur) -> {
            if (cur) {
                showOverlay(false);
                genVB.setVisible(false);
                try {
                    Desktop.getDesktop().open(new File(PREF_BUNDLE.get("OUTPUT_DIR")));
                } catch (IOException ioe) {
                }
            }
        });

        LOADING_STATE.addListener((obs, old, cur) -> {
            if (cur) {
                showOverlay(false);
                genVB.setVisible(false);
                refreshLevels();
                Platform.runLater(() -> {
                    allStusLbl.setText(ARStudentsCounter(STUDENTS.size()));
                    refreshStats();
                });
            }
        });
        selectDbBtn.setOnAction(evt -> {
            File ini_db_dir = new File(PREF_BUNDLE.get("INPUT_DIR"));
            fc.setTitle("تحديد قاعدة بيانات المؤسسة");
            if (ini_db_dir.exists() && ini_db_dir.isDirectory()) {
                fc.setInitialDirectory(ini_db_dir);
            }
            fc.getExtensionFilters().clear();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("مرتبات إكسيل", "*.xls"));
            File f = fc.showOpenDialog(generateBtn.getScene().getWindow());
            if (f == null) {
                return;
            }
            if (f.getParentFile() != ini_db_dir) {
                PREF_BUNDLE.update("INPUT_DIR", f.getParent());
            }
            generateBtn.setDisable(false);
            showOverlay(true);
            genVB.setVisible(true);
            LOADING_STATE.set(false);
            new Thread(() -> {
                DBWorkbook db = new DBWorkbook();
                db.setWorkbook(f);
                if (db.isValid()) {
                    STUDENTS.clear();
                    db.load(Settings.SCHOOL, STUDENTS);
                }
                LOADING_STATE.set(true);
            }).start();
        });

        outputMI.setOnAction(evt -> {
            File f = dc.showDialog(generateBtn.getScene().getWindow());
            if (f != null) {
                PREF_BUNDLE.update("OUTPUT_DIR", f.getPath());
                dc.setInitialDirectory(f);
            }
        });

        quitMI.setOnAction(evt -> {
            PREF_BUNDLE.commit();
            generateBtn.getScene().getWindow().hide();
        });

        mattersMI.setOnAction(evt -> {
            showOverlayMessage(mattersVB, true);
        });

        aboutMI.setOnAction(evt -> {
            showOverlayMessage(aboutVB, true);
        });

        eh = evt -> {
            showOverlayMessage(currentNotifier, false);
        };
        overlayP.addEventHandler(MouseEvent.MOUSE_RELEASED, eh);
        overlayP.addEventHandler(TouchEvent.TOUCH_RELEASED, eh);

        addMatterBtn.setOnAction(evt -> {
            String mat = editMatCB.getValue();
            if (matter1CB.getItems().contains(mat)) {

            } else {
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().add(mat);
                }
                PREF_BUNDLE.update("MATTERS", PREF_BUNDLE.get("MATTERS") + "_MAT_" + mat);
            }
        });

        delMatterBtn.setOnAction(evt -> {
            String mat = editMatCB.getValue();
            if (matter1CB.getItems().contains(mat)) {
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().remove(mat);
                }
                PREF_BUNDLE.update("MATTERS", PREF_BUNDLE.get("MATTERS").replaceAll("(" + "_MAT_" + mat + ")|(" + mat + "_MAT_" + ")", ""));
            } else {
            }
        });

        svf1.valueProperty().addListener((obs, old, cur) -> {
            refreshStats();
        });

        appName.setText(Settings.APP_NAME);
        buildInfos.setText(buildInfos.getText().replace("%version%", Settings.APP_VERSION).replace("%date%", Settings.APP_DATE));
    }

    private void showOverlayMessage(VBox vb, boolean show) {
        if (show && currentNotifier == vb) {
            return;
        }
        Timeline t = new Timeline();
        if (show) {
            overlayP.setVisible(true);
            vb.setVisible(true);
            if (currentNotifier != null) {
                currentNotifier.setVisible(false);
                currentNotifier.setOpacity(0);
                currentNotifier.setTranslateY(-500);
            }
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.translateYProperty(), 6, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.opacityProperty(), 1, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 1, Interpolator.EASE_IN)));
            currentNotifier = vb;
        } else {
            currentNotifier = null;
            t.setOnFinished(evt -> {
                overlayP.setVisible(false);
                vb.setVisible(false);
            });
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.translateYProperty(), -600, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.opacityProperty(), 0, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 0, Interpolator.EASE_IN)));
        }
        t.play();
    }

    private void showOverlay(boolean show) {
        Timeline t = new Timeline();
        if (show) {
            overlayP.removeEventHandler(MouseEvent.MOUSE_RELEASED, eh);
            overlayP.removeEventHandler(TouchEvent.TOUCH_RELEASED, eh);
            overlayP.setVisible(true);
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 1, Interpolator.EASE_IN)));

        } else {
            t.setOnFinished(evt -> {
                overlayP.setVisible(false);
                overlayP.addEventHandler(MouseEvent.MOUSE_RELEASED, eh);
                overlayP.addEventHandler(TouchEvent.TOUCH_RELEASED, eh);
            });
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 0, Interpolator.EASE_IN)));
        }
        t.play();
    }

    private void refreshLevels() {
        levelCB.getItems().clear();
        STUDENTS.stream().map(s -> s.getLevelFull()).distinct().forEach(lev -> {
            levelCB.getItems().add(lev);
        });
    }

    private String ARRoomsCounter(int n) {
        if (n == 1) {
            return "قاعة واحدة";
        } else if (n == 2) {
            return "قاعتان اثنتان";
        } else if (n > 2 && n < 11) {
            return String.format("%02d قاعات", n);
        } else if (n % 100 == 0) {
            return n + " قاعة";
        } else {
            return String.format("%02d قاعة", n);
        }
    }

    private String ARStudentsCounter(int n) {
        if (n == 1) {
            return "تلميذ واحد";
        } else if (n == 2) {
            return "تلميذان اثنان";
        } else if (n > 2 && n < 11) {
            return String.format("%02d تلاميذ", n);
        } else if (n % 100 == 0) {
            return n + " تلميذ";
        } else {
            return String.format("%02d تلميذا", n);
        }
    }

    private void refreshStats() {
        if (STUDENTS.isEmpty()) {
            levStusLbl.setText("00");
            roomsLbl.setText("00");
            rooms1DetLbl.setText("");
            rooms2DetLbl.setText("");
        } else {
            int n = (int) STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).count();
            HashMap<String, Integer> map = Misc.getSemiEvenDivStats(n, maxCandsSP.getValue());
            levStusLbl.setText(ARStudentsCounter(n));
            roomsLbl.setText(ARRoomsCounter(map.get("nMin") + map.get("nMax")));
            rooms1DetLbl.setText((map.get("nMax") > 0) ? ARRoomsCounter(map.get("nMax")) + " [" + ARStudentsCounter(map.get("max")) + "]" : "");
            rooms2DetLbl.setText((map.get("nMin") > 0) ? ARRoomsCounter(map.get("nMin")) + " [" + ARStudentsCounter(map.get("min")) + "]" : "");
        }
    }

    private void sortStudentsArray() {
        SORTED_STUDENTS.clear();
        if ( codeAscOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s1.getCode().compareTo(s2.getCode())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( codeDescOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s2.getCode().compareTo(s1.getCode())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( nameAscOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s1.getName().compareTo(s2.getName())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( nameDescOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s2.getName().compareTo(s1.getName())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( genAscOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s1.getGender().compareTo(s2.getGender())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( genDescOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s2.getGender().compareTo(s1.getGender())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( ageAscOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s1.getDaysAge().compareTo(s2.getDaysAge())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( ageDescOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s2.getDaysAge().compareTo(s1.getDaysAge())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else if ( randOrderRMI.isSelected() ) {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> s1.getRAND_ID().compareTo(s2.getRAND_ID())).collect(Collectors.toCollection(ArrayList::new)));
        }
        else {
            SORTED_STUDENTS.addAll(STUDENTS.stream().filter(s -> PREF_BUNDLE.get("SELECTED_LEVEL").equals(s.getLevelFull())).sorted((s1, s2) -> Misc.getGroupWeight(s1.getGroup()).compareTo(Misc.getGroupWeight(s2.getGroup()))).collect(Collectors.toCollection(ArrayList::new)));
        }
    }
}
