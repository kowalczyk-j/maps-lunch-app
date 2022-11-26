import java.time.DayOfWeek;
import java.util.List;

public class Menu {
    private List<Dish> options;
    private boolean hasVegetarianOptions;
    private boolean hasVeganOptions;
    private DayOfWeek day;

    public Menu(List<Dish> options, DayOfWeek day) {
        setOptions(options);
        setDay(day);
        hasVegetarianOptions = checkVegetarian();
        hasVeganOptions = checkVegan();
    }

    // Getters
    public List<Dish> getOptions() {return options;}
    public boolean getHasVegetarianOptions() {return hasVegetarianOptions;}
    public boolean getHasVeganOptions() {return hasVeganOptions;}
    public DayOfWeek getDay() {return day;}

    // Setters
    public void setOptions(List<Dish> options) {this.options = options;}
    public void setHasVegetarianOptions(boolean hasVegetarianOptions) {this.hasVegetarianOptions = hasVegetarianOptions;}
    public void setHasVeganOptions(boolean hasVeganOptions) {this.hasVeganOptions = hasVeganOptions;}
    public void setDay(DayOfWeek day) {this.day = day;}

    public void addDish(Dish dish) {
        options.add(dish);
        if (dish.getIsVegan()) hasVeganOptions = true;
        if (dish.getIsVegetarian()) hasVegetarianOptions = true;
    }

    public boolean checkVegetarian() {
        for (Dish dish : options) {
            if (dish.getIsVegetarian()) return true;
        }
        return false;
    }

    public boolean checkVegan() {
        for (Dish dish : options) {
            if (dish.getIsVegan()) return true;
        }
        return false;
    }

}
