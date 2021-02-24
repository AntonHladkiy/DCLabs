package a;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BeeTask {
    private final boolean[] field;
    private final int y;
}
