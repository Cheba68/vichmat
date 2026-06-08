package systems;

import java.util.List;

public class SystemRepository {

    public static List<SystemFunction> getSystems() {

        return List.of(
                new System1(),
                new System2()
        );
    }
}