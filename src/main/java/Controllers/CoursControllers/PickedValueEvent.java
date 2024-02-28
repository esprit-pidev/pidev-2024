package Controllers.CoursControllers;



import javafx.event.Event;
import javafx.event.EventType;
import tn.esprit.entities.Cours.Option;
import tn.esprit.entities.Cours.Questions;

public class PickedValueEvent extends Event {
    public static final EventType<PickedValueEvent> OPTION_PICKED =
            new EventType<>(Event.ANY, "OPTION_PICKED");
    private final Option pickedOption;
    private final Questions question;
    public PickedValueEvent(Option pickedOption, Questions question) {
        super(OPTION_PICKED);
        this.pickedOption = pickedOption;
        this.question=question;
    }
    public Option getPickedOption() {
        return pickedOption;
    }
    public Questions getQuestion(){
        return question;
    }
}
