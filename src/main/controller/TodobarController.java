package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.EditTaskDemo;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static ui.PomoTodoApp.setScene;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsFile = new File(todoOptionsPopUpFXML);
    private File todoActionsFile = new File(todoActionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;//no
    @FXML
    private JFXRippler todoOptionsPopUpRippler;//no
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup toolbarPopUp;
    private JFXPopup viewPopUp;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadToolbarPopUp();
        loadToolbarPopUpActionListener();
        loadViewOptionsPopUp();
        loadViewOptionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (edit, delete)
    private void loadToolbarPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            toolbarPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load view selector pop up (to do, up next, in progress, done)
    private void loadViewOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            viewPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    private void loadViewOptionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                viewPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadToolbarPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                toolbarPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class TodoActionsPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            if (0 <= selectedIndex && selectedIndex <= 4) {
                selection(selectedIndex);
            } else {
                Logger.log("ToolbarViewOptionsPopUpController", "No action is implemented for the selected option");
            }
            viewPopUp.hide();
        }
    }

    private void selection(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                Logger.log("ToolbarViewOptionsPopUpController", "To Do Selected");
                break;
            case 1:
                Logger.log("ToolbarViewOptionsPopUpController", "Up Next Selected");
                break;
            case 2:
                Logger.log("ToolbarViewOptionsPopUpController", "In Progress Selected");
                break;
            case 3:
                Logger.log("ToolbarViewOptionsPopUpController", "Done Selected");
                break;
            case 4:
                Logger.log("ToolbarViewOptionsPopUpController", "Pomodoro Selected");
                break;
            default:

        }
    }

    // Inner class: option pop up controller
    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("ToolbarViewOptionsPopUpController", "Edit Selected");
                    setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("ToolbarViewOptionsPopUpController", "DELETE");
                    deleteTask(task);
                    break;
                default:
                    Logger.log("ToolbarViewOptionsPopUpController", "No action is implemented for the selected option");
            }
            toolbarPopUp.hide();
        }
    }



    private void deleteTask(Task task) {
        try {
            List<Task> tasks = PomoTodoApp.getTasks();
            tasks.remove(task);
            JsonFileIO.write(tasks);
            Logger.log("ToolbarViewOptionsPopUpController", "DELETED!!!");
            setScene(new ListView(PomoTodoApp.getTasks()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
