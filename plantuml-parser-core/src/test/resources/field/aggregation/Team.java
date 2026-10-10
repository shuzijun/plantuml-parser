import java.util.List;
import java.util.Map;

public class Team {
    private Member leader;
    private Member deputy;
    private List<Member> members;
    private Member[] alumni;
    private Map<String, Role> roles;
    private static Team instance;
    public Role duty;
}
