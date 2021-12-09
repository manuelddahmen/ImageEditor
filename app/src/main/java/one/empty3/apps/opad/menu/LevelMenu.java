package one.empty3.apps.opad.menu;

/*__
 * Created by manuel on 21-05-17.
 */
public class LevelMenu {
    private final  String[] level = new String[]{
            "SolPlan",
            "SolRelief",
            "SolReliefMouvant",
            "SolSphere",
            "SolTube"};
    private int index = 0;
    private int maxIndex = level.length;

    public String[] getLevel() {
        return level;
    }

    public void setLevel(String[] level) {
        level = level;
    }

    public Class loadClass() {
        String package1 ="one.empty3.apps.opad.";
        String solClass = package1+(getLevel()[index]);
        try {
            return Class.forName(solClass);
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException - Error: "+solClass);
            System.exit(1);
        } catch (NoClassDefFoundError e) {
            System.err.println("NoClassDefFoundError - Error: "+solClass);
            System.exit(1);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index % maxIndex;
        if (index < 0)
            this.index = maxIndex - 1;
        System.out.println(index);
    }

}
