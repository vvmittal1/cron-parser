package deliveroo.cronparser;

import lombok.Getter;

import java.util.List;

@Getter
public enum TimeUnit {
    MINUTE(0,59),
    HOUR(0, 23),
    DAY_OF_MONTH(1, 31),
    MONTH(1, 12),
    DAY_OF_WEEK(0, 6);

    private final Integer lowerBound;
    private final Integer upperBound;

    private TimeUnit(Integer lowerBound, Integer upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Boolean validate(int value) {
        return (value >= this.lowerBound && value <= this.upperBound);
    }

    public Boolean validate(List<Integer> valuesList) {
        return valuesList.stream().allMatch(this::validate);
    }

//    public List<String> generateAbsoluteValues(String expression) {
//        if (validate())
//    }

}
